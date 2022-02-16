package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.dto.TaskDto;
import com.taskmanagement.taskmanagement.exception.NotFoundException;

import java.util.List;

public interface TaskService {

  void saveTask(TaskDto taskDto);
  void deleteTask(Long id);
  void updateTask(TaskDto taskDto) throws NotFoundException;
  List<TaskDto> getTask(List<Long> ids);
  List<TaskDto> getTaskWithFilter(String assignee , String taskName);
  void updateTaskWithAssignee(Long Id , String assignee);
}
