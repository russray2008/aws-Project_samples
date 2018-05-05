package net.clouddeveloper.aws.samples.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
	
	public JSONObject readGroups() {
		
		JSONObject jsonObject = null;
		
		JSONParser parser = new JSONParser();
		// JsonParser to convert JSON string into Json Object
		
		Path resourceDirectory = Paths.get("src", "main", "resources", "groups");

		try {
			Object obj = parser.parse(new FileReader(resourceDirectory + "/group_list.json"));
			// parsing the JSON string inside the file that we created earlier.

			jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			// Json string has been converted into JSONObject

			String groupName = (String) jsonObject.get("group");
			System.out.println(groupName);

			String description = (String) jsonObject.get("description");
			System.out.println(description);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public JSONObject readPolicies() {
		
        JSONObject jsonObject = null;
		
		JSONParser parser = new JSONParser();
		// JsonParser to convert JSON string into Json Object
		
		Path resourceDirectory = Paths.get("src", "main", "resources", "policies");
		
		try {
			Object obj = parser.parse(new FileReader(resourceDirectory + "/enterprisedeveloper.org_s3_policy.json"));
			// parsing the JSON string inside the file that we created earlier.

			jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			// Json string has been converted into JSONObject

			String policyName = (String) jsonObject.get("name");
			System.out.println(policyName);

			String assumeRolePolicyDocument = (String) jsonObject.get("policy");
			System.out.println(assumeRolePolicyDocument);
			
			String description = (String) jsonObject.get("description");
			System.out.println(description);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}

	
	public JSONObject readRoles() {
		
         JSONObject jsonObject = null;
		
		JSONParser parser = new JSONParser();
		// JsonParser to convert JSON string into Json Object
		
		Path resourceDirectory = Paths.get("src", "main", "resources", "roles");

		try {
			Object obj = parser.parse(new FileReader(resourceDirectory + "/roles_list.json"));
			// parsing the JSON string inside the file that we created earlier.

			jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			// Json string has been converted into JSONObject

			String roleName = (String) jsonObject.get("RoleName");
			System.out.println(roleName);

			String assumeRolePolicyDocument = (String) jsonObject.get("AssumeRolePolicyDocument");
			System.out.println(assumeRolePolicyDocument);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	
	public JSONObject readUsers() {
		
        JSONObject jsonObject = null;
		
		JSONParser parser = new JSONParser();
		// JsonParser to convert JSON string into Json Object
		
		Path resourceDirectory = Paths.get("src", "main", "resources", "users");

		try {
			Object obj = parser.parse(new FileReader(resourceDirectory + "/user_list.json"));
			// parsing the JSON string inside the file that we created earlier.

			jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			// Json string has been converted into JSONObject

			String lastName = (String) jsonObject.get("last");
			System.out.println(lastName);

			String middle_initial = (String) jsonObject.get("middle_initial");
			System.out.println(middle_initial);
			
			String firstName = (String) jsonObject.get("first");
			System.out.println(firstName);
			
			String userName = (String) jsonObject.get("username");
			System.out.println(userName);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	
    public JSONObject readDynamodbSchema() {
		
        JSONObject jsonObject = null;
		
		JSONParser parser = new JSONParser();
		// JsonParser to convert JSON string into Json Object
		
		Path resourceDirectory = Paths.get("src", "main", "resources", "dynamodb");

		try {
			Object obj = parser.parse(new FileReader(resourceDirectory + "/cities_data_schema.json"));
			// parsing the JSON string inside the file that we created earlier.

			jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			// Json string has been converted into JSONObject

			//String lastName = (String) jsonObject.get("last");
			//System.out.println(lastName);

			//String middle_initial = (String) jsonObject.get("middle_initial");
			//System.out.println(middle_initial);
			
			//String firstName = (String) jsonObject.get("first");
			//System.out.println(firstName);
			
			//String userName = (String) jsonObject.get("username");
			//System.out.println(userName);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	
	public static void main(String[] args) {

		JsonReader reader = new JsonReader();
		//JSONObject jsonObject  = reader.readGroups();
		//jsonObject = reader.readRoles();
		//jsonObject = reader.readUsers();
		JSONObject jsonObject = reader.readDynamodbSchema();
	}

}
