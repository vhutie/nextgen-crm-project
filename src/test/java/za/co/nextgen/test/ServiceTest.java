package za.co.nextgen.test;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.co.nextgen.NextgenServiceApplication;
import za.co.nextgen.exception.ResourceNotFoundException;
import za.co.nextgen.model.Project;
import za.co.nextgen.service.ProjectService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = NextgenServiceApplication.class)
public class ServiceTest {

	@Autowired
	private ProjectService service;
	
	//Test 1 Fail Fetch All
	@Test
	@Order(1)
	public void test_fetch_all_fail() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.getAll();
		});
	}
	
	//Test 2 Fail Fecth By Id
	@Test
	@Order(2)
	public void test_fetch_by_id_fail() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.getByProjectId(1L);
		});
	}
	
	//Test 3 Create Resource
	@Test
	@Order(3)
	public void test_create_resource_success() {
		Project project = service.create(createProject());
		
		Assert.assertEquals(Objects.nonNull(project), true);
	}
	
	//Test 4 Fetch All Success
	@Test
	@Order(4)
	public void test_fetch_all_success() {
		List<Project> projects = service.getAll();
		
		Assert.assertEquals(Objects.nonNull(projects), true);
		
		Assert.assertEquals(projects.isEmpty(), false);
	}
	
	//Test 5 Fecth By Id Success
	@Test
	@Order(5)
	public void test_fetch_by_id_success() {
		Project project = service.getByProjectId(1L);
		
		Assert.assertEquals(Objects.nonNull(project), true);
		
		Assert.assertEquals(Objects.nonNull(project.getHref()), true);
	}
	
	
	public Project createProject() {
		Project project = new Project();
		project.setName("Project From Test");
		project.setDescription("Project From Test, Spring-Boot Demo");
		return project;
	}
}
