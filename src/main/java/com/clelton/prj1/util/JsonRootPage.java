package com.clelton.prj1.util;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Essa Classe altera o rootName response json
 * para tasks
 * 
 * @author Clelton Henrique
 */


@JsonRootName("tasks")
public class JsonRootPage<T> extends PageImpl<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public JsonRootPage(Page<T> page) {

		super(page.getContent(), page.getPageable(), page.getTotalElements());
	}
}
