package net.clouddeveloper.aws.samples.utility;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteConflictException;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserResult;

public class AwsUserUtility {

	
	
	
	public void createAWSUser(String name) {

		final String USAGE = "To run this example, supply a username\n" + "Ex: CreateUser <username>\n";

		if (null == name || name.isEmpty()) {
			System.out.println(USAGE);
			System.exit(1);
		}

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

		CreateUserRequest request = new CreateUserRequest().withUserName(name);

		CreateUserResult response = iam.createUser(request);

		System.out.println("Successfully created user: " + response.getUser().getUserName());
	}
	
	
	

	public void deleteAWSUser(String name) {

		final String USAGE = "To run this example, supply a username\n" + "Ex: DeleteUser <username>\n";

		if (null == name || name.isEmpty()) {
			System.out.println(USAGE);
			System.exit(1);
		}

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

		DeleteUserRequest request = new DeleteUserRequest().withUserName(name);

		try {
			iam.deleteUser(request);
		} catch (DeleteConflictException e) {
			System.out.println("Unable to delete user. Verify user is not" + " associated with any resources");
			throw e;
		}

		System.out.println("Successfully deleted IAM user " + name);
	}
	
	
	

	public void updateAWSUser(String existingName, String newName) {

		final String USAGE = "To run this example, supply the current username and a new\n" + "username. Ex:\n\n"
				+ "UpdateUser <current-name> <new-name>\n";

		if (existingName == null || newName == null) {
			System.out.println(USAGE);
			System.exit(1);
		}

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

		UpdateUserRequest request = new UpdateUserRequest().withUserName(existingName).withNewUserName(newName);

		UpdateUserResult response = iam.updateUser(request);

		System.out.printf("Successfully updated user to username %s", newName);
	}
	
	
	
	

	public void listAWSUsers() {

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

		boolean done = false;
		ListUsersRequest request = new ListUsersRequest();

		while (!done) {
			ListUsersResult response = iam.listUsers(request);

			for (User user : response.getUsers()) {
				System.out.format("Retrieved user: %s", user.getUserName());
			}

			request.setMarker(response.getMarker());

			if (!response.getIsTruncated()) {
				done = true;
			}
		}
	}
	
	

	public static void main(String[] args) {

		AwsUserUtility userUtility = new AwsUserUtility();
		userUtility.listAWSUsers();

	}

}
