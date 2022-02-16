package com.taskmanagement.taskmanagement.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="sub_task")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
