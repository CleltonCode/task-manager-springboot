package com.clelton.prj1.service.impl;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.dto.TaskMapper;
import com.clelton.prj1.entity.TaskEntity;
import com.clelton.prj1.exception.TaskNotFoundException;
import com.clelton.prj1.repository.TaskRepository;
import com.clelton.prj1.service.TaskService;
import com.clelton.prj1.util.JsonRootPage;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LogManager.getLogger(TaskServiceImpl.class);
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TaskMapper taskMapper;
    

    @Override
    public Boolean saveTask(TaskDTO taskDTO) {
        TaskEntity taskEntity = mapTaskDTOToEntity(taskDTO, null);
        try {
        	LOG.info("Salvar ",taskEntity);
            this.taskRepository.save(taskEntity);
            return true;
        }catch (TaskNotFoundException e){
            throw new TaskNotFoundException("Erro ao inserir Task por ID!");
        }
    }

    @Override
    public Page<TaskDTO> getAllTasks(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        
        Page<TaskEntity> pageTask = this.taskRepository.findAll(pageRequest);
        
        List<TaskDTO> taskDTOList = pageTask
        		.getContent()
        		.stream()
        		.map(this.taskMapper::toDTO)
        		.collect(Collectors.toList());
        return new JsonRootPage<>(createTaskPage(taskDTOList, pageRequest, pageTask));
        
    }
    
    private Page<TaskDTO> createTaskPage(List<TaskDTO> taskDTOList, PageRequest pageRequest, Page<TaskEntity> pageTask) {
    	return new PageImpl<>(taskDTOList, pageRequest, pageTask.getTotalElements());
	}


    @Override
    public TaskDTO editTask(TaskDTO taskDTO, Long id) {
        this.taskRepository.findById(id)
                .ifPresentOrElse(item->{this.taskRepository.save(this.mapTaskDTOToEntity(taskDTO, id)); },
                        ()->{
                            throw new TaskNotFoundException("Erro ao Editar Task por ID!");
        });
        return taskDTO;
    }

    @Override
    public void deleteTask(Long id) {
        this.taskRepository.findById(id)
                .ifPresentOrElse(item->{this.taskRepository.findById(id); },
                        ()->{
                            throw new TaskNotFoundException("Erro ao deletar Task por ID!");
                });
    }


    @Override
    public TaskEntity getTaskById(Long id) {
        Optional<TaskEntity> taskOptinal =  this.taskRepository.findById(id);

        return taskOptinal
                .orElseThrow(() -> new TaskNotFoundException("Erro ao buscar Task por ID!"));
    }


    private TaskEntity mapTaskDTOToEntity(TaskDTO taskDTO, Long id){
    	
        TaskEntity taskEntity = new TaskEntity();
        if(id != null) {
            taskEntity.setId(id);
        }
        
        
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setData(taskDTO.getData());
        taskEntity.setCompleted(taskDTO.getCompleted());

        return taskEntity;
    }

    private TaskDTO mapTaskEntityToDTO(TaskEntity taskEntity) {
       return TaskDTO.builder()
               .description(taskEntity.getDescription())
               .data(taskEntity.getData())
               .completed(taskEntity.getCompleted())
               .build();
    }


    
    
}
