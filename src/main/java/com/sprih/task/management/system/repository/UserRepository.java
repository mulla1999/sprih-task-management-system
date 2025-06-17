package com.sprih.task.management.system.repository;

import com.sprih.task.management.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}