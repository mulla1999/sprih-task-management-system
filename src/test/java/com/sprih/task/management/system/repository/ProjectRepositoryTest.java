package com.sprih.task.management.system.repository;

import com.sprih.task.management.system.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;

    @BeforeEach
    void setup() {
        project = Project.builder()
                .name("New Project")
                .description("Project Description")
                .status("Active")
                .build();

        project = projectRepository.save(project);
    }

    @Test
    void shouldSaveAndFindProjectById() {
        Optional<Project> found = projectRepository.findById(project.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("New Project");
        assertThat(found.get().getDescription()).isEqualTo("Project Description");
        assertThat(found.get().getStatus()).isEqualTo("Active");
    }

    @Test
    void shouldFindAllProjects() {
        assertThat(projectRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldDeleteProject() {
        projectRepository.deleteById(project.getId());
        assertThat(projectRepository.findById(project.getId())).isNotPresent();
    }
}
