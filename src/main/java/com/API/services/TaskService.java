package com.API.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.entities.Task;
import com.API.enums.TaskStatus;
import com.API.exceptions.DataException;
import com.API.repositories.TaskRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	public Task createTask(Task task) {
		taskValidation(task);

		Task newTask = new Task();
		newTask.setTitle(task.getTitle());
		newTask.setDescription(task.getDescription());
		newTask.setStatus(TaskStatus.NOT_COMPLETED);

		return taskRepository.save(newTask);
	}

	public List<Task> findAllTasks() {
		return taskRepository.findAll();
	}

	public Task findById(Long requestedId) {
		Optional<Task> task = taskRepository.findById(requestedId);
		if(task.isPresent()) {
			return task.get();
		} 
		throw new DataException("Task not found!");
	}

	public Task updateById(Long requestedId, Task task) {
		taskValidation(task);
		Optional<Task> optionalTask = taskRepository.findById(requestedId);
		if(optionalTask.isPresent()) {
			optionalTask.get().setTitle(task.getTitle());
			optionalTask.get().setDescription(task.getDescription());
			return taskRepository.save(optionalTask.get());
		} 
		throw new DataException("Task not found!");
	}

	public void deleteById(Long requestedId) {
		Optional<Task> optionalTask = taskRepository.findById(requestedId);
		if(optionalTask.isPresent()) {
			taskRepository.delete(optionalTask.get());
		} else {
			throw new DataException("Task not found!");
		}
	}

	public Task updateStatus(Long requestedId) {
		Optional<Task> optionalTask = taskRepository.findById(requestedId);
		if(optionalTask.isPresent()) {
			TaskStatus taskStatus = optionalTask.get().getStatus();
			if(taskStatus.equals(TaskStatus.NOT_COMPLETED)){
				optionalTask.get().setStatus(TaskStatus.COMPLETED);
			} else if(taskStatus.equals(TaskStatus.COMPLETED)){
				optionalTask.get().setStatus(TaskStatus.NOT_COMPLETED);
			}
			return taskRepository.save(optionalTask.get());
		} else {
			throw new DataException("Task not found!");
		}
	}


	private void taskValidation(Task task) {
		if(task.getTitle() == null || task.getTitle().isBlank() || task.getTitle().isEmpty()) {
			throw new DataException("Task title invalid!");
		}
		if(task.getDescription() == null || task.getDescription().isBlank() || task.getDescription().isEmpty()) {
			throw new DataException("Task description invalid!");
		}
	}

}
