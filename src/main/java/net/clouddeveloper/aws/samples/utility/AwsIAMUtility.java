package net.clouddeveloper.aws.samples.utility;

import org.json.simple.JSONObject;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateGroupRequest;
import com.amazonaws.services.identitymanagement.model.CreateGroupResult;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.CreateRoleResult;
import com.amazonaws.services.identitymanagement.model.DeleteGroupRequest;
import com.amazonaws.services.identitymanagement.model.DeleteGroupResult;

public class AwsIAMUtility {

	 public void createGroup() {
		 
		 JsonReader reader = new JsonReader();
		 JSONObject jsonObject = reader.readGroups();
		 
		 final AmazonIdentityManagement client = AmazonIdentityManagementClientBuilder.defaultClient();
		 String groupName = (String) jsonObject.get("group");
		
		 CreateGroupResult response  = client.createGroup(new CreateGroupRequest().withGroupName(groupName));
		 System.out.println("Successfully created group: " + response.getGroup().getGroupName());
		 
		 
	 }
	 
	 public void updateGroup() {
		 
	 }
	 
	 public void deleteGroup(String groupName) {
		 
		 final AmazonIdentityManagement client = AmazonIdentityManagementClientBuilder.defaultClient();
		
		 DeleteGroupResult response = client.deleteGroup(new DeleteGroupRequest().withGroupName(groupName));
		 System.out.println("Successfully deleted group: " + response.toString());
	 }
	 
     public void createRole() {
    	 
    	     JsonReader reader = new JsonReader();
		 JSONObject jsonObject = reader.readPolicies();
		 String name = (String) jsonObject.get("name");
		 String policy = (String) jsonObject.get("policy");
		 String description = (String) jsonObject.get("description");
    	 
    	 final AmazonIdentityManagement client = AmazonIdentityManagementClientBuilder.defaultClient();
    	 
    	   
    	 
    	      CreateRoleResult response = client.createRole(new CreateRoleRequest().withRoleName(name)
    	    		                                       .withDescription(description)
    	    		                                       .withAssumeRolePolicyDocument(policy));
    	  
    	      System.out.println("Successfully create role: " + response.toString());
	 }
	 
	 public void updateRole() {
		 
	 }
	 
	 public void deleteRole() {
		 
	 } 
	 
	 
	 public static void main(String[] args) {

			AwsIAMUtility iamUtility = new AwsIAMUtility();
			//iamUtility.createGroup();
			//iamUtility.deleteGroup("Testers");
			iamUtility.createRole();

		}

}
