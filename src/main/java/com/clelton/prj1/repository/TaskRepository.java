package com.clelton.prj1.repository;

import com.clelton.prj1.dto.TaskDTO;
import com.clelton.prj1.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskDTO findByDescription(String description);
    TaskDTO findByData(Date data);
    TaskDTO findByCompleted(String completed);
}
