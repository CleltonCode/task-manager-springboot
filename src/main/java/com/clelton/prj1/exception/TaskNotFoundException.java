package com.clelton.prj1.exception;


import com.clelton.prj1.service.impl.TaskServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class TaskNotFoundException extends RuntimeException {
    private static final Logger LOG = LogManager.getLogger(TaskNotFoundException.class);
    public TaskNotFoundException(String mensagem){
        super(mensagem );
    }


}
