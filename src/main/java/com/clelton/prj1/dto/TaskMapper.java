package com.clelton.prj1.dto;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.clelton.prj1.entity.TaskEntity;


@Component
public class TaskMapper {
	
	public TaskDTO toDTO(TaskEntity taskEntity) {
		Objects.requireNonNull(taskEntity, "TaskkEntity não pode ser nulo.");
        return new TaskDTO(taskEntity.getId(), taskEntity.getDescription(), taskEntity.getData(), taskEntity.getCompleted());
	}
	
	public TaskEntity toEntity(TaskDTO taskDTO) {
		
		Objects.requireNonNull(taskDTO, "TaskDTO não pode ser nulo.");
		TaskEntity taskEntity = new TaskEntity();
		
        
        taskEntity.setId(taskDTO.getId());
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setData(taskDTO.getData());
        taskEntity.setCompleted(taskDTO.getCompleted());

        return taskEntity;
	}

}
