package net.clouddeveloper.aws.samples.utility;

import java.util.List;

import org.json.simple.JSONObject;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;

public class DynamodbCreateTable {

	private JSONObject tableDefinition = null;
	
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);
    
    /**
     * 
     */
    public DynamodbCreateTable() {}
    
    
    /**
     * 
     * @param schemaDefinition
     */
	public DynamodbCreateTable(JSONObject schemaDefinition) {
		this.tableDefinition = schemaDefinition;
	}

	
	
	/**
	 * 
	 * @param tableDefinition
	 * @param type
	 * @return com.amazonaws.services.dynamodbv2.document.Table
	 */
	public Table createDynamodbTable(JSONObject tableDefinition, CREATION_TYPE type) {
		
		Table table = null;
		this.tableDefinition = tableDefinition;
		
		if (type.equals(CREATION_TYPE.BASIC)){
			table = createDynamodbBasicTable();
		
		}else if (type.equals(CREATION_TYPE.DETAIL)){
		    table = createDynamodbDetailTable();
			
		}else{
			
		}
		     
		return table;
	}
	
	
	/**
	 * Entry point into Class
	 * @param type
	 * @return com.amazonaws.services.dynamodbv2.document.Table
	 */
	public Table createDynamodbTable(CREATION_TYPE type) {
		Table table = null;
		
		if (type.equals(CREATION_TYPE.BASIC)){
			table = createDynamodbBasicTable();
		
		}else if (type.equals(CREATION_TYPE.DETAIL)){
		    table = createDynamodbDetailTable();
			
		}else {
			
		}
		     
		return table;
	}
	
	
	
	
	
	private Table createDynamodbBasicTable() {
		//minimum required
		CreateTableRequest req = createDynamodbRequest();
		return dynamoDB.createTable(req);
	}

	
	
	private Table createDynamodbDetailTable() {

		List<KeySchemaElement> keySchemaList = createSchemaList();
		List<AttributeDefinition> attributeList = createAttributes();
		ProvisionedThroughput provisionedThroughput = createProvisionedThroughtput();
		String tableName = createTableName();
		return dynamoDB.createTable(tableName, keySchemaList, attributeList, provisionedThroughput);
	}

	
	
	
	private CreateTableRequest createDynamodbRequest() {
		//minimum required
		return null;
	}
	
	
	
	
	private String createTableName() {
		//get from JSON object
		return null;
	}
	
	
	
	private CreateTableRequest createDynamodbTableRequest() {
		//adding indexes
		
		CreateTableRequest request = new CreateTableRequest();
		request.setAttributeDefinitions(createAttributes());
		request.setKeySchema(createSchemaList());
		request.setProvisionedThroughput(createProvisionedThroughtput());
		request.setLocalSecondaryIndexes(createLocalSecondaryIndex() );
		
		return request;
	}

	
	private List<LocalSecondaryIndex> createLocalSecondaryIndex() {
		
		return null;
	}
	
	
	
	private List<KeySchemaElement> createSchemaList() {

		return null;
	}

	
	
	
	private List<AttributeDefinition> createAttributes() {

		return null;
	}

	
	
	
	private ProvisionedThroughput createProvisionedThroughtput() {

		return null;
	}

	
	
	
	public JSONObject getTableDefinition() {
		
		return tableDefinition;
	}

	
	
	
	public void setTableDefinition(JSONObject tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	
	
	
	public enum CREATION_TYPE {
	    BASIC, DETAIL
	}
}
