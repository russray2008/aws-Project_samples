package net.clouddeveloper.aws.samples.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

public class JsonWriter {

	public void createUsers() {

		JSONObject obj = new JSONObject();

		// JSONObject class creates a json object,
		// provides a put function to insert the details into json object
		obj.put("last", "Eastwood");
		obj.put("first", "Clint");
		obj.put("middle_initial", "D");
		obj.put("username", "ceastwood");

		Path resourceDirectory = Paths.get("src", "main", "resources", "users");

		try (FileWriter file = new FileWriter(resourceDirectory + "/user_list.json")) {
			// File Writer creates a file in write mode at the given location
			file.write(obj.toJSONString());

			// write function is use to write in file,
			// here we write the Json object in the file
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(obj);
		// to print our JSon object

	}

	public void createGroups() {

		JSONObject obj = new JSONObject();

		// JSONObject class creates a json object,
		// provides a put function to insert the details into json object
		obj.put("group", "Testers");
		obj.put("description", "Testing Group");

		Path resourceDirectory = Paths.get("src", "main", "resources", "groups");

		try (FileWriter file = new FileWriter(resourceDirectory + "/group_list.json")) {
			// File Writer creates a file in write mode at the given location
			file.write(obj.toJSONString());

			// write function is use to write in file,
			// here we write the Json object in the file
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(obj);
		// to print our JSon object

	}

	public void createRoles() {

		JSONObject obj = new JSONObject();

		// JSONObject class creates a json object,
		// provides a put function to insert the details into json object
		obj.put("RoleName", "Testing");
		obj.put("AssumeRolePolicyDocument",
				"{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"Service\":[\"ec2.amazonaws.com\"]},\"Action\":[\"sts:AssumeRole\"]}]}\n");

		Path resourceDirectory = Paths.get("src", "main", "resources", "roles");

		try (FileWriter file = new FileWriter(resourceDirectory + "/roles_list.json")) {
			// File Writer creates a file in write mode at the given location
			file.write(obj.toJSONString());

			// write function is use to write in file,
			// here we write the Json object in the file
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(obj);
		// to print our JSon object

	}

	public void createPolicies() {

	}

	public static void main(String[] args) {

		JsonWriter writer = new JsonWriter();
		writer.createUsers();
		writer.createGroups();
		writer.createRoles();

	}

}
