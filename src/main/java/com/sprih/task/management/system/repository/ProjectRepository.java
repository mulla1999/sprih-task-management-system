package com.sprih.task.management.system.repository;

import com.sprih.task.management.system.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}