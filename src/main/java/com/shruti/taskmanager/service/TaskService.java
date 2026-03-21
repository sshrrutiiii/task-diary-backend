package com.shruti.taskmanager.service;

import com.shruti.taskmanager.entity.Task;
import com.shruti.taskmanager.entity.User;
import com.shruti.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }
}
