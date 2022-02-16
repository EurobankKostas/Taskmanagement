package com.taskmanagement.taskmanagement.dto;

import com.taskmanagement.taskmanagement.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskDto {

    private Long id;

    private String taskName;

    private String taskDescription;

    private String taskGroup;

    private TaskStatus taskStatus;

    private String assignee;

    private String timeSpent;

    private List<TaskDto> taskDtoList;

}
