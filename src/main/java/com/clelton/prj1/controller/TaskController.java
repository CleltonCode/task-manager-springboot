package com.clelton.prj1.controller;

import com.clelton.prj1.dto.TasksPageDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import com.clelton.prj1.entity.TaskEntity;
import com.clelton.prj1.exception.TaskNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.service.TaskService;

@Validated
@RestController
@RequestMapping("api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;

	private static final Logger LOG = LogManager.getLogger(TaskController.class);
	
	@PostMapping
	@ResponseStatus
	public ResponseEntity<Boolean> saveTask(@RequestBody TaskDTO taskDTO){
		try{
			this.taskService.saveTask(taskDTO);
		    return ResponseEntity.status(HttpStatus.OK).body(true);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
		}

	}

	@GetMapping
	public ResponseEntity<TasksPageDTO> getAllTasks(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
													@RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize ) {

		try{
			TasksPageDTO listTask = this.taskService.getAllTasks(page, pageSize);
			return ResponseEntity.status(HttpStatus.OK).body(listTask);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskEntity> findTask(@PathVariable("id") Long id){
		try{
			TaskEntity task;
			task = this.taskService.getTaskById(id);
			return ResponseEntity.status(HttpStatus.OK).body(task);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable("id") Long id){
			this.taskService.deleteTask(id);
	}


	@PutMapping("/{id}")
	public ResponseEntity<TaskDTO> editTaskCompleted(@RequestBody TaskDTO taskDTO, @PathVariable("id") Long id){
		try{
			TaskDTO editedTask;
            editedTask = this.taskService.editTask(taskDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(editedTask);
		}catch (TaskNotFoundException e){
			LOG.info("Erro", e);
			return  ResponseEntity.notFound().build();
		}
	}


}
