package com.clelton.prj1.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import com.clelton.prj1.entity.TaskEntity;
import com.clelton.prj1.exception.TaskNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.dto.TaskPageDTO;
import com.clelton.prj1.service.TaskService;

@Validated
@RestController
@RequestMapping(value="task",  produces = { "application/json", "application/xml" })
public class TaskController {
	@Autowired
	private TaskService taskService;

	private static final Logger LOG = LogManager.getLogger(TaskController.class);
	
	@PostMapping("/save")
	@ResponseStatus
	public ResponseEntity<Boolean> saveTask(@RequestBody TaskDTO taskDTO){
		try{
			this.taskService.saveTask(taskDTO);
		    return ResponseEntity.status(HttpStatus.OK).body(true);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
		}

	}

	@GetMapping("/list")
	public ResponseEntity<Page<TaskDTO>> getAllTasks(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
			@RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize ) {
		
		Page<TaskDTO> taskPage =this.taskService.getAllTasks(page, pageSize);
				return new ResponseEntity<>(taskPage, HttpStatus.OK);
//ResponseEntity<List<TaskDTO>>
//		try{
//			List<TaskDTO> listTask = this.taskService.getAllTasks(page, pageSize);
//			return ResponseEntity.status(HttpStatus.OK).body(listTask);
//		}catch (TaskNotFoundException e){
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<TaskEntity> findTask(@PathVariable("id") Long id){
		try{
			TaskEntity task;
			task = this.taskService.getTaskById(id);
			return ResponseEntity.status(HttpStatus.OK).body(task);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteTask(@PathVariable("id") Long id){
		try{
			this.taskService.deleteTask(id);
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}catch (TaskNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
		}
	}


	@PutMapping("/edit/{id}")
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
