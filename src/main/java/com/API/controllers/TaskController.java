package com.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.entities.Task;
import com.API.exceptions.DataException;
import com.API.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@PostMapping
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		try {
			return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
		} catch (DataException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Task>> findAllTasks() {
		return ResponseEntity.ok().body(taskService.findAllTasks());
	}

	@GetMapping("/{requestedId}")
	public ResponseEntity<?> findById(@PathVariable Long requestedId) {
		try {
			return ResponseEntity.ok().body(taskService.findById(requestedId));
		} catch(DataException exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{requestedId}")
	public ResponseEntity<?> updateById(@RequestBody Task task, @PathVariable Long requestedId) {
		try {
			return ResponseEntity.accepted().body(taskService.updateById(requestedId, task));
		} catch(DataException exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{requestedId}")
	public ResponseEntity<?> deleteById(@PathVariable Long requestedId) {
		try {
			taskService.deleteById(requestedId);
			return ResponseEntity.noContent().build();
		} catch (DataException exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("/{requestedId}")
	public ResponseEntity<?> updateStatus(@PathVariable Long requestedId) {
		try {
			return new ResponseEntity<Task>(taskService.updateStatus(requestedId), HttpStatus.ACCEPTED);
		} catch (DataException exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
