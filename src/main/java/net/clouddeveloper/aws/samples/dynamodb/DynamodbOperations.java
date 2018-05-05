package net.clouddeveloper.aws.samples.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamodbOperations {
	
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    //public static String tableName = "Issues";
	
	public static void createTable() {

	}
	public static void addIndex(String index) {
		
	}
	public static void queryIndex(String indexName) {

	}
	
	public static void deleteTable(String tableName) {

	}
	
	
	 public static void loadData() {

	 }
	
	 

	 public static void putItem(String issueId,  String title, String description, String createDate, 
			 				   String lastUpdateDate, String dueDate, Integer priority, String status) {
		 
     }
	 


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
