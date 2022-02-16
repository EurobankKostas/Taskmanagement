package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.dto.TaskDto;
import com.taskmanagement.taskmanagement.entity.SubTask;
import com.taskmanagement.taskmanagement.entity.Task;
import com.taskmanagement.taskmanagement.exception.NotFoundException;
import com.taskmanagement.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService  {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public Task buildTaskFromReq(TaskDto taskDto){
        List<SubTask> subTaskList = new ArrayList<>();
        Task task = Task.builder().id(taskDto.getId()).taskName(taskDto.getTaskName()).taskGroup(taskDto.getTaskGroup()).assignee(taskDto.getAssignee()).timeSpent(taskDto.getTimeSpent()).taskDescription(taskDto.getTaskDescription()).build();
        List<TaskDto> subTaskListReq = taskDto.getTaskDtoList() != null ? taskDto.getTaskDtoList() : new ArrayList<>();
        if(!subTaskListReq.isEmpty()){
            subTaskListReq.forEach(e->{
                SubTask subTask = SubTask.builder().taskName(e.getTaskName()).taskGroup(e.getTaskGroup()).assignee(e.getAssignee()).taskDescription(e.getTaskDescription()).timeSpent(e.getTimeSpent()).task(task).build();
                subTaskList.add(subTask);
            });
            task.setSubTaskList(subTaskList);
        }
        return  task;
    }

    @Override
    public void saveTask(TaskDto taskDto) {
        taskRepository.save(buildTaskFromReq(taskDto));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTask(TaskDto taskDto) throws NotFoundException {
        Optional<Task> task = taskRepository.findById(taskDto.getId());
        if(task.isEmpty()){
            throw  new NotFoundException("Task for Update not Found");
        }
        saveTask(taskDto);
    }

    public List<TaskDto> populateResp(List<Task> taskList){
        List<TaskDto> taskDtoListFinal = new ArrayList<>();
        taskList.forEach(e->{
            TaskDto taskDto = new TaskDto();
            taskDto.setId(e.getId());
            taskDto.setTaskName(e.getTaskName());
            taskDto.setTaskGroup(e.getTaskGroup());
            taskDto.setAssignee(e.getAssignee());
            taskDto.setTaskDescription(e.getTaskDescription());
            if(e.getSubTaskList() != null ){
                List<TaskDto> taskDtoList = new ArrayList();
                e.getSubTaskList().forEach(p->{
                    TaskDto taskDtoInner = new TaskDto();
                    taskDtoInner.setId(p.getId());
                    taskDtoInner.setTaskName(p.getTaskName());
                    taskDtoInner.setTaskGroup(p.getTaskGroup());
                    taskDtoInner.setAssignee(p.getAssignee());
                    taskDtoInner.setTaskDescription(p.getTaskDescription());
                    taskDtoList.add(taskDtoInner);
                });
                taskDto.setTaskDtoList(taskDtoList);
            }
            taskDtoListFinal.add(taskDto);
        });
        return  taskDtoListFinal;
    }
    @Override
    public List<TaskDto> getTask(List<Long> ids) {
        List<Task> taskList = taskRepository.findAllById(ids);
        List<TaskDto> taskDtoListFinal = populateResp( taskList);
        return taskDtoListFinal;
    }

    @Override
    public List<TaskDto> getTaskWithFilter(String assignee, String taskName) {
        Optional<List<Task>> taskListOptional = taskRepository.findByAssigneeAndTaskName(assignee , taskName);
        List<Task> taskList = new ArrayList<>();
        if(taskListOptional.isPresent()){
            taskList=taskListOptional.get();
        }
        List<TaskDto> taskDtoListFinal = populateResp( taskList);
        return taskDtoListFinal;
    }

    @Override
    public void updateTaskWithAssignee(Long Id, String assignee) {
        Optional<Task> task = taskRepository.findById(Id);
        task.ifPresent(value ->{
            value.setAssignee(assignee);
            taskRepository.save(task.get());
        });
    }


}
