package com.shruti.taskmanager.controller;

import com.shruti.taskmanager.entity.Task;
import com.shruti.taskmanager.entity.User;
import com.shruti.taskmanager.repository.TaskRepository;
import com.shruti.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. GET ALL TASKS (Matches fetchTasks in App.js)
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false, defaultValue = "1") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return taskRepository.findByUser(user);
        }
        return taskRepository.findAll(); // Fallback if user doesn't exist yet
    }

    // 2. ADD A TASK (Matches handleAddTask in App.js)
    @PostMapping
    public Task addTask(@RequestBody Task task, @RequestParam(required = false, defaultValue = "1") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            task.setUser(user);
        }
        return taskRepository.save(task);
    }

    // 3. UPDATE A TASK (Matches toggleComplete in App.js)
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTaskData) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            // Update the necessary fields
            existingTask.setIsCompleted(updatedTaskData.getIsCompleted());
            existingTask.setStatus(updatedTaskData.getStatus());
            existingTask.setTitle(updatedTaskData.getTitle());
            existingTask.setDescription(updatedTaskData.getDescription());
            existingTask.setPriority(updatedTaskData.getPriority());
            existingTask.setStartTime(updatedTaskData.getStartTime());
            existingTask.setEndTime(updatedTaskData.getEndTime());
            existingTask.setDate(updatedTaskData.getDate());

            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    // 4. DELETE A TASK (Matches handleDeleteTask in App.js)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}