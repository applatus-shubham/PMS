package com.example.grappler.Repository;

import com.example.grappler.Entity.Projects;
import com.example.grappler.Entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Projects,Long> {
    List<Tickets> findByProjectId(Long project_id);
}
