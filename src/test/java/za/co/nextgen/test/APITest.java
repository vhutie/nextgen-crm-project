package za.co.nextgen.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.exception.JsonPathException;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;

import za.co.nextgen.NextgenServiceApplication;
import za.co.nextgen.exception.ResourceNotFoundException;
import za.co.nextgen.model.Project;
import za.co.nextgen.service.ProjectService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT,
		classes = NextgenServiceApplication.class)
public class APITest {

	
	//Test 1 Fail Fetch All
	@Test
	@Order(1)
	public void test_fetch_all_fail() {
		
		Response response = APITest.doGetRequest("/api/project");
		
		Assert.assertEquals(response.getStatusCode(), 404);
		
	}
	
	//Test 2 Fail Fecth By Id
	@Test
	@Order(2)
	public void test_fetch_by_id_fail() {
		Response response = APITest.doGetRequest("/api/project/1");
		
		Assert.assertEquals(response.getStatusCode(), 404);
	}
	
	//Test 3 Create Resource
	@Test
	@Order(3)
	public void test_create_resource_success() {
		String projectJSON = new Gson().toJson(createProject());
		
		Response response = APITest.doPostRequest("/api/project", projectJSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(Objects.nonNull(response.jsonPath().get()), true);
		
		Map responseMap = (HashMap) response.jsonPath().get();
		Iterator it = responseMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey());
			if(pair.getKey().equals("name")) {
				Assert.assertEquals(pair.getValue(), "Project From Test");
				
			}
			
		}
	}
	
	//Test 4 Fetch All Success
	@Test
	@Order(4)
	public void test_fetch_all_success() {
		Response response = APITest.doGetRequest("/api/project");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}
	
	//Test 5 Fecth By Id Success
	@Test
	@Order(5)
	public void test_fetch_by_id_success() {
		Response response = APITest.doGetRequest("/api/project/1");
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	public Project createProject() {
		Project project = new Project();
		project.setName("Project From Test");
		project.setDescription("Project From Test, Spring-Boot Demo");
		return project;
	}
	
	
	public static Response doGetRequest(String endpoint){
		
		return
	            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
	            when().get(endpoint).
	            then().contentType(ContentType.JSON).
	            extract().response();
	}
	
	public static Response doPostRequest(String endpoint, String body){
		
		return
	            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
	            body(body).
	            when().post(endpoint).
	            then().contentType(ContentType.JSON).
	            extract().response();
	}
}
