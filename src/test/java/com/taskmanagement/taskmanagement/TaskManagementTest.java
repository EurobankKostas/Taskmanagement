package com.taskmanagement.taskmanagement;

import com.taskmanagement.taskmanagement.controller.TaskController;
import com.taskmanagement.taskmanagement.dto.TaskDto;
import com.taskmanagement.taskmanagement.entity.Task;
import com.taskmanagement.taskmanagement.exception.NotFoundException;
import com.taskmanagement.taskmanagement.repository.TaskRepository;
import com.taskmanagement.taskmanagement.service.TaskService;
import com.taskmanagement.taskmanagement.service.TaskServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TaskManagementTest {
    private static final Logger logger = LogManager.getLogger(TaskManagementTest.class);

    @Autowired
    private TaskService taskService;


    @Autowired
    private TaskRepository taskRepository;

    @MockBean
    private TaskRepository repository;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        logger.info("Starting testFindById method");
        // Setup our mock repository
        Task task = new Task();
        task.setTaskName("test1");
        task.setAssignee("Takis");
        task.setTaskGroup("testGroup");
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskName("test1");
        taskDto.setAssignee("Takis");
        taskDto.setTaskGroup("testGroup");
        doReturn(Optional.of(task)).when(repository).findById(1l);

        // Execute the service call
        taskService.saveTask(taskDto);
        Optional<Task> taskValid = taskRepository.findById(1l);

        // Assert the response
        Assertions.assertTrue(taskValid.isPresent(), "Task was found");
        Assertions.assertSame(taskValid.get(), task, "The Task returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test deleteById Success")
    void deleteById() {

        TaskDto taskDto = new TaskDto();
        taskDto.setTaskName("test1");
        taskDto.setId(1L);
        taskDto.setTaskName("test1");
        taskDto.setAssignee("Takis");
        taskDto.setTaskGroup("testGroup");
        taskService.saveTask(taskDto);

        //Check if created
        Optional<Task> taskValid = taskRepository.findById(1L);
        if(taskValid.isPresent()){
            logger.info("Task created");
        }
        taskService.deleteTask(1L);

        //Check if deleted
        Optional<Task> taskDeleted = taskRepository.findById(1L);
        // Assert the response
        Assertions.assertFalse(taskDeleted.isPresent(), "Task was not found");

    }

    @Test
    @DisplayName("Test updateTask Success")
    void updateTask() throws NotFoundException {
        Task task = new Task();
        task.setTaskName("test1");
        task.setAssignee("Takis");
        task.setTaskGroup("testGroup");
        task.setId(1L);

        Task taskUpdated = new Task();
        task.setTaskName("test1");
        task.setAssignee("Takis1");
        task.setTaskGroup("testGroup");
        task.setId(1L);

        TaskDto taskDtoUpdate = new TaskDto();
        taskDtoUpdate.setTaskName("test2");
        taskDtoUpdate.setId(1L);
        taskDtoUpdate.setTaskName("test1");
        taskDtoUpdate.setAssignee("Takis1");
        taskDtoUpdate.setTaskGroup("testGroup");
        doReturn(Optional.of(task)).when(repository).findById(1L);
        doReturn(Optional.of(taskUpdated)).when(repository).save(taskUpdated);
        taskService.updateTask(taskDtoUpdate);

        //Check if updated
        Optional<Task> taskUpdatedFinal = taskRepository.findById(1L);
        Assertions.assertTrue(taskUpdatedFinal.get().getAssignee().equals("Takis1"),"Assignee changed");

    }

    @Test
    @DisplayName("Test getTask Success")
    void getTask() throws NotFoundException {
        List<Task> listReq = new ArrayList<>();
        Task task = new Task();
        task.setTaskName("test1");
        task.setAssignee("Takis");
        task.setTaskGroup("testGroup");
        task.setId(1L);
        listReq.add(task);
        List<Long> idsReq = new ArrayList<>();
        idsReq.add(1L);
        doReturn(listReq).when(repository).findAllById(idsReq);

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<TaskDto> taskQuery = taskService.getTask(ids);

        // Assert the response
        Assertions.assertFalse(taskQuery.isEmpty());

    }
}
