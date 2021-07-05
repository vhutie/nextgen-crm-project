package za.co.nextgen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import za.co.nextgen.exception.ResourceNotFoundException;
import za.co.nextgen.model.Project;
import za.co.nextgen.service.ProjectService;

@RestController
public class ProjectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService service;
	
	@RequestMapping(value = "/api/project/{projectId}", method = RequestMethod.GET, produces = "application/json")
	 private @ResponseBody
	    ResponseEntity<Object> getById(
	            @PathVariable(name = "projectId", required = false) Long id) {

	        LOGGER.info("obtaining project by id {} ", id);

	        try {
	            return new ResponseEntity<>(service.getByProjectId(id), HttpStatus.OK);
	        } catch (ResourceNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	}
	
	@RequestMapping(value = "/api/project", method = RequestMethod.GET, produces = "application/json")
	 private @ResponseBody
	    ResponseEntity<Object> getAll() {

	        LOGGER.info("obtaining all projects ");

	        try {
	            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	        } catch (ResourceNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	}
	
	
	@RequestMapping(value = "/api/project", method = RequestMethod.POST, produces = "application/json")
	 private @ResponseBody
	    ResponseEntity<Object> create(@RequestBody(required = true) Project project ) {

	        LOGGER.info("create project");

	        try {
	            return new ResponseEntity<>(service.create(project), HttpStatus.OK);
	        } catch (ResourceNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	}
}
