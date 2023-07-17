package com.API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
