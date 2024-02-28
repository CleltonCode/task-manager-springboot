package com.clelton.prj1.service.impl;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.dto.mapper.TaskMapper;
import com.clelton.prj1.dto.TasksPageDTO;
import com.clelton.prj1.entity.TaskEntity;
import com.clelton.prj1.exception.TaskNotFoundException;
import com.clelton.prj1.repository.TaskRepository;
import com.clelton.prj1.service.TaskService;

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
    public void saveTask(TaskDTO taskDTO) {
        TaskEntity taskEntity = this.taskMapper.toEntity(taskDTO);
        try {
            this.taskRepository.save(taskEntity);
        }catch (TaskNotFoundException e){
            throw new TaskNotFoundException("Erro ao inserir Task por ID!");
        }
    }


    @Override
    public TasksPageDTO getAllTasks(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        Page<TaskEntity> pageTask = this.taskRepository.findAll(pageRequest);

        List<TaskDTO> taskDTOList = pageTask.get().map(taskMapper::toDTO).collect(Collectors.toList());

        return new TasksPageDTO(taskDTOList, pageTask.getTotalElements(), pageTask.getTotalPages());

    }
    @Override
    public TaskDTO editTask(TaskDTO taskDTO, Long id) {
        this.taskRepository.findById(id)
                .ifPresentOrElse(item-> this.taskRepository.save(this.taskMapper.toEntity(taskDTO)),
                        ()->{
                            throw new TaskNotFoundException("Erro ao Editar Task por ID!");
        });
        return taskDTO;
    }

    @Override
    public void deleteTask(Long id) {
        this.taskRepository.findById(id)
            .ifPresentOrElse(item->{this.taskRepository.deleteById(id); },
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


    private Page<TasksPageDTO> createTaskPage(List<TasksPageDTO> tasksList, PageRequest pageRequest, Page<TaskEntity> pageTask) {
        return new PageImpl<>(tasksList, pageRequest, pageTask.getTotalElements());
    }


}
