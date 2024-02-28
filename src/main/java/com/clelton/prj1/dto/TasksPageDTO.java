package com.clelton.prj1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

	public record TasksPageDTO(@JsonProperty("tasks") List<TaskDTO> tasks,
							   @JsonProperty("totalElements") long totalElements,
							   @JsonProperty("totalPages") int totalPages) {
		@JsonCreator
		public static TasksPageDTO of(
				@JsonProperty("tasks") List<TaskDTO> tasks,
				@JsonProperty("totalElements") long totalElements,
				@JsonProperty("totalPages") int totalPages
		) {
			return new TasksPageDTO(tasks, totalElements, totalPages);
		}
	}

	


