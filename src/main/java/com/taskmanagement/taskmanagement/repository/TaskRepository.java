package com.taskmanagement.taskmanagement.repository;

import com.taskmanagement.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    Optional<Task> findById(Long aLong);

    Optional<List<Task>> findByAssigneeAndTaskName(String assignee, String taskName);


}
