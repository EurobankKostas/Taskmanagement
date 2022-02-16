package com.taskmanagement.taskmanagement.controller;

import com.taskmanagement.taskmanagement.dto.RequestIds;
import com.taskmanagement.taskmanagement.dto.TaskDto;
import com.taskmanagement.taskmanagement.exception.NotFoundException;
import com.taskmanagement.taskmanagement.service.TaskServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task/v1/")
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl){
        this.taskServiceImpl= taskServiceImpl;
    }

    @PostMapping("save")
    public ResponseEntity<String> saveTask(@RequestBody TaskDto taskDto) throws NotFoundException {
        logger.info("Invoking saveTask in TaskController");
        if(taskDto == null ){
            logger.error("Input of taskDto to save not valid");
            throw  new NotFoundException("Input of taskDto to save not valid");
        }
        taskServiceImpl.saveTask(taskDto);
        logger.info("Task stored");
        return new ResponseEntity<>("Task stored" , HttpStatus.OK);
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) throws NotFoundException {
        logger.info("Invoking deleteTask in TaskController");
        if(taskId == null ){
            logger.error("Input of taskDto for deletion not valid");
            throw  new NotFoundException("Input of taskDto for deletion not valid");
        }
        taskServiceImpl.deleteTask(taskId);
        logger.info("\"Task deleted --taskId \" + taskId");
        return new ResponseEntity<>("Task deleted --taskId " + taskId  , HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto taskDto) throws NotFoundException {
        logger.info("Invoking updateTask in TaskController");
        if(taskDto == null || taskDto.getId() == null){
            logger.error("Input of taskDto not valid");
            throw  new NotFoundException("Input of taskDto not valid");
        }
        taskServiceImpl.updateTask(taskDto);
        logger.info("Task updated --taskId " + taskDto.getId());
        return new ResponseEntity<>("Task deleted --taskId " + taskDto.getId()  , HttpStatus.OK);
    }

    @GetMapping("get")
    @ResponseBody
    public ResponseEntity<List<TaskDto>> getTask(@RequestBody RequestIds requestIds) throws NotFoundException {
        logger.info("Invoking getTask in TaskController");
        if(requestIds.getIds().isEmpty()){
            throw  new NotFoundException("Input of ids not valid");
        }
        return new ResponseEntity<>(taskServiceImpl.getTask(requestIds.getIds()), HttpStatus.OK);
    }

    @GetMapping("getTaskWithFilter/{assignee}/{taskName}")
    @ResponseBody
    public ResponseEntity<List<TaskDto>> getTaskWithFilter(@PathVariable String assignee , @PathVariable String taskName) throws NotFoundException {
        logger.info("Invoking getTaskWithFilter in TaskController");
        if(assignee.isEmpty() || taskName.isEmpty()){
            throw  new NotFoundException("Input of assignee or taskName is not valid");
        }
        return new ResponseEntity<>(taskServiceImpl.getTaskWithFilter(assignee, taskName), HttpStatus.OK);
    }

    @PutMapping("updateAssignee/{taskId}/{assignee}")
    @ResponseBody
    public ResponseEntity<Void> updateAssignee(@PathVariable Long taskId , @PathVariable String assignee) throws NotFoundException {
        logger.info("Invoking updateAssignee in TaskController");
        if(assignee.isEmpty() || taskId == null){
            logger.error("Input of assignee or taskName is not valid");
            throw  new NotFoundException("Input of assignee or taskName is not valid");
        }
        taskServiceImpl.updateTaskWithAssignee(taskId,assignee);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
