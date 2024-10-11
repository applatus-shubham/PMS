package com.example.grappler.Service;

import com.example.grappler.Entity.Projects;
import com.example.grappler.Exception.ProjectNotFoundException;
import com.example.grappler.Repository.ProjectRepository;
import com.example.grappler.dto.ProjectsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private ModelMapper modelMapper;

    public void ProjectsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectsDTO mapProjectsToDTO(Projects projects) {
        return modelMapper.map(projects, ProjectsDTO.class);
    }
    @Autowired
    private ProjectRepository projectRepository;
    public Projects findProjectById(Long projectId) {
        Optional<Projects> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        } else {
            throw new ProjectNotFoundException("Project not found");
        }
    }
}
