package za.co.nextgen.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import za.co.nextgen.exception.ResourceNotFoundException;
import za.co.nextgen.model.Project;
import za.co.nextgen.repo.ProjectRepo;

@Service
public class ProjectService {

	
	@Autowired
	private ProjectRepo repo;
	
	@Value( "${environment_host}" )
	private String environmentHost;
	
	@Value( "${resource_not_found_exception}" )
	private String resourceNotFound;
	
	@Value( "${list_of_resources_not_found_exception}" )
	private String listOfResourcesNotFound;
	
	//POST
	public Project create(Project project) {
		Project savedProject  = null;
		try {
			project.setCreateDate(new Date());
			savedProject = repo.saveAndFlush(project);
			//http://nextgen-crm-project/api/project/{ID} whatever the ID is 
			savedProject.setHref(String.format("%s%s%s", environmentHost,"/api/project/",savedProject.getId()));
			repo.save(savedProject);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return savedProject;
	}
	
	//GET ALL
	public List<Project> getAll() {
		List<Project> resources = repo.findAll();
		
		if(Objects.isNull(resources) || resources.isEmpty()){
			throw new ResourceNotFoundException(listOfResourcesNotFound);
		}
		
		return resources;
	}
	
	//GET BY PROJECT ID
	public Project getByProjectId(Long id) {
		//Find Resource or Throw ResourceNotFoundException
				Project resource = repo.
						findById(id).
						orElseThrow(()-> new ResourceNotFoundException(String.format(resourceNotFound, id)));
		return resource;
	}
	
}
