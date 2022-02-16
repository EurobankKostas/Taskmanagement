package com.taskmanagement.taskmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="task")
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_group")
    private String taskGroup;

    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "time_Spent")
    private String timeSpent;

    @Column(name = "time_Spent")
    @OneToMany(mappedBy = "task" , cascade = CascadeType.ALL)
    private List<SubTask> subTaskList;

}
