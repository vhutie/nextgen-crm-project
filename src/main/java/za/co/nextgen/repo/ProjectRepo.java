package za.co.nextgen.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.nextgen.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

}
