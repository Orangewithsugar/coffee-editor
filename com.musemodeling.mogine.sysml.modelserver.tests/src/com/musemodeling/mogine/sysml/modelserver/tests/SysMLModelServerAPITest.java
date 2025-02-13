package com.musemodeling.mogine.sysml.modelserver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SysMLModelServerAPITest {

	private static String modelName;

	@BeforeAll
	public static void setmodelName() {
		modelName = UUID.randomUUID() + ".sysmlx";
	}

	@Test
	@Order(1)
	public void testCreateNewModel() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");

		// JSON request body as a string
		String jsonBody = """
				    {
				        "data": {
				            "$type": "https://www.omg.org/spec/SysML/20240201#//Namespace",
				            "$id": "573386c1-df75-4e98-ac6a-7648b1f675e5",
				            "elementId": "573386c1-df75-4e98-ac6a-7648b1f675e5",
				            "ownedRelationship": [
				                {
				                    "$type": "https://www.omg.org/spec/SysML/20240201#//OwningMembership",
				                    "$id": "3e43d02b-9f01-487f-82b6-aae8bdb8e5fe",
				                    "elementId": "3e43d02b-9f01-487f-82b6-aae8bdb8e5fe",
				                    "ownedRelatedElement": [
				                        {
				                            "$type": "https://www.omg.org/spec/SysML/20240201#//Package",
				                            "$id": "a3f5cf57-541b-412a-b7ad-9115ce9db9ed",
				                            "elementId": "a3f5cf57-541b-412a-b7ad-9115ce9db9ed",
				                            "ownedRelationship": [
				                                {
				                                    "$type": "https://www.omg.org/spec/SysML/20240201#//NamespaceImport",
				                                    "$id": "a8e5a8e6-a3e2-4290-8d27-4f13dcd92e84",
				                                    "elementId": "a8e5a8e6-a3e2-4290-8d27-4f13dcd92e84",
				                                    "importedNamespace": {
				                                        "$type": "https://www.omg.org/spec/SysML/20240201#//Namespace",
				                                        "$ref": "test1.sysml#|0"
				                                    }
				                                }
				                            ],
				                            "declaredName": "left"
				                        }
				                    ]
				                }
				            ]
				        }
				    }
				""";

		// Use the new RequestBody.create method with byte[] and StandardCharsets.UTF_8
		RequestBody body = RequestBody.create(mediaType, jsonBody.getBytes(StandardCharsets.UTF_8));

		// Create request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/models?modeluri=" + modelName)
				.method("POST", body).addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the type field and validate
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

	@Test
	@Order(2)
	public void testGetModel() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/models?modeluri=" + modelName).get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

	@Test
	@Order(3)
	public void testGetElementById() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// MediaType for the request body
		MediaType mediaType = MediaType.parse("text/plain");

		// Build the GET request with body (even though it's unconventional)
		Request request = new Request.Builder()
				.url("http://localhost:8081/api/v2/modelelement?modeluri=" + modelName
						+ "&elementid=a8e5a8e6-a3e2-4290-8d27-4f13dcd92e84")
				.get().addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}
	
	@Test
	@Order(4)
	public void testUpdateModelUsingJSON() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// MediaType for JSON request
		MediaType mediaType = MediaType.parse("application/json");

		// Prepare the JSON request body
		String jsonBody = """
				    {
				        "data": {
				            "type": "modelserver.patch",
				            "data": [
				                {
				                    "op": "replace",
				                    "path": "elementId",
				                    "value": "adgadgaega123123"
				                },
				                {
				                    "op": "add",
				                    "path": "declaredShortName",
				                    "value": "testAttr..."
				                },
				                {
				                    "op": "replace",
				                    "path": "ownedRelationship/0/ownedRelatedElement/0/declaredName",
				                    "value": "right"
				                },
				                {
				                    "op": "add",
				                    "path": "ownedRelationship/0/ownedRelatedElement",
				                    "value": {
				                        "$type": "https://www.omg.org/spec/SysML/20240201#//Package",
				                        "elementId": "a3f5cf57-541b-412a-b7ad-9115ce9db9ed123",
				                        "declaredName": "right123123"
				                    }
				                }
				            ]
				        }
				    }
				""";

		// Create the request body
		RequestBody body = RequestBody.create(mediaType, jsonBody);

		// Build the PATCH request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/models?modeluri=" + modelName)
				.method("PATCH", body) // Using PATCH method
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

	@Test
	@Order(5)
	public void testUndo() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/undo?modeluri=" + modelName).get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

	@Test
	@Order(6)
	public void testRedo() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/redo?modeluri=" + modelName).get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

	@Test
	@Order(7)
	public void testSave() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/save?modeluri=" + modelName).get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}
	@Test
	@Order(8)
	public void testSaveAll() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/saveall").get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}
	
	@Test
	@Order(9)
	public void testgetAllModelUri() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// Build the request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/modeluris").get()
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}
	
	@Test
	@Order(999)
	public void testDeleteModel() throws IOException {
		// Create OkHttpClient
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// MediaType for the DELETE request (even though the body is empty, OkHttp still
		// requires it)
		MediaType mediaType = MediaType.parse("text/plain");

		// Create an empty body for the DELETE request
		RequestBody body = RequestBody.create(mediaType, "");

		// Build the DELETE request
		Request request = new Request.Builder().url("http://localhost:8081/api/v2/models?modeluri=" + modelName)
				.method("DELETE", body) // Using
				.addHeader("Content-Type", "application/json").build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			// Read the response body
			String responseBody = response.body().string();

			// Use Gson to parse the JSON response
			JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

			// Get the "type" field and check if it's "success"
			String type = jsonResponse.get("type").getAsString();
			assertEquals("success", type, "Expected type to be 'success', but got: " + type);
		}
	}

}
