package com.clelton.prj1.service;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.dto.TaskPageDTO;
import com.clelton.prj1.entity.TaskEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;



@Validated
@Service
public interface TaskService {
    Boolean saveTask(TaskDTO taskDTO);
    Page<TaskDTO> getAllTasks(@PositiveOrZero int page, @Positive @Max(100) int pageSize);
    TaskDTO editTask(TaskDTO taskDTO, Long id);
    void deleteTask(Long id);
    TaskEntity getTaskById(Long id);
}
