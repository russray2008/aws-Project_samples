package net.clouddeveloper.aws.samples.dynamodb;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DynamodbCreation {

	public void createTable(String table_name, String end_point, String aws_region) {

		// String tableName = "Movies";
		// String end_point = ""http://localhost:8000";
		// String aws_region = "us-east-1";

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(end_point, aws_region)).build();

		DynamoDB dynamoDB = new DynamoDB(client);

		try {
			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDB.createTable(table_name, Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
																														// key
					new KeySchemaElement("title", KeyType.RANGE)), // Sort key

					Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
							new AttributeDefinition("title", ScalarAttributeType.S)),
					new ProvisionedThroughput(10L, 10L));
			table.waitForActive();

			System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}

	}

	public void loadData(String table_name, String end_point, String aws_region, String file_name) {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(end_point, aws_region)).build();

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable(table_name);

		Path resourceDirectory = Paths.get("src", "main", "resources", "dynamodb");

		JsonParser parser = null;
		try {
			parser = new JsonFactory().createParser(new FileReader(resourceDirectory + "/" + file_name));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// JsonParser parser = new JsonFactory().createParser(new
		// File("moviedata.json"));

		JsonNode rootNode = null;
		try {
			rootNode = new ObjectMapper().readTree(parser);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Iterator<JsonNode> iter = rootNode.iterator();

		ObjectNode currentNode;

		while (iter.hasNext()) {
			currentNode = (ObjectNode) iter.next();

			int year = currentNode.path("year").asInt();
			String title = currentNode.path("title").asText();

			try {
				table.putItem(new Item().withPrimaryKey("year", year, "title", title).withJSON("info",
						currentNode.path("info").toString()));
				System.out.println("PutItem succeeded: " + year + " " + title);

			} catch (Exception e) {
				System.err.println("Unable to add movie: " + year + " " + title);
				System.err.println(e.getMessage());
				break;
			}
		}

		try {
			parser.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void queryTable(String table_name, String end_point, String aws_region) {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(end_point, aws_region)).build();

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable(table_name);

		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("#yr", "year");

		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(":yyyy", 1985);

		QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
				.withValueMap(valueMap);

		ItemCollection<QueryOutcome> items = null;
		Iterator<Item> iterator = null;
		Item item = null;

		try {
			System.out.println("Movies from 1985");
			items = table.query(querySpec);

			iterator = items.iterator();
			while (iterator.hasNext()) {
				item = iterator.next();
				System.out.println(item.getNumber("year") + ": " + item.getString("title"));
			}

		} catch (Exception e) {
			System.err.println("Unable to query movies from 1985");
			System.err.println(e.getMessage());
		}

		valueMap.put(":yyyy", 1992);
		valueMap.put(":letter1", "A");
		valueMap.put(":letter2", "L");

		querySpec.withProjectionExpression("#yr, title, info.genres, info.actors[0]")
				.withKeyConditionExpression("#yr = :yyyy and title between :letter1 and :letter2").withNameMap(nameMap)
				.withValueMap(valueMap);

		try {
			System.out.println("Movies from 1992 - titles A-L, with genres and lead actor");
			items = table.query(querySpec);

			iterator = items.iterator();
			while (iterator.hasNext()) {
				item = iterator.next();
				System.out.println(item.getNumber("year") + ": " + item.getString("title") + " " + item.getMap("info"));
			}

		} catch (Exception e) {
			System.err.println("Unable to query movies from 1992:");
			System.err.println(e.getMessage());
		}
	}

	
	
	public void scanTable(String table_name, String end_point, String aws_region) {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(end_point, aws_region)).build();

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable(table_name);

		ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#yr, title, info.rating")
				.withFilterExpression("#yr between :start_yr and :end_yr")
				.withNameMap(new NameMap().with("#yr", "year"))
				.withValueMap(new ValueMap().withNumber(":start_yr", 1950).withNumber(":end_yr", 1959));

		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				System.out.println(item.toString());
			}

		} catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		
		DynamodbCreation dynamodbCreate = new DynamodbCreation();
		dynamodbCreate.createTable("Movies", "http://localhost:8000", "us-east-1");
		dynamodbCreate.loadData("Movies", "http://localhost:8000", "us-east-1", "/moviedata.json");
		dynamodbCreate.queryTable("Movies", "http://localhost:8000", "us-east-1");
		dynamodbCreate.scanTable("Movies", "http://localhost:8000", "us-east-1");
	}

}
