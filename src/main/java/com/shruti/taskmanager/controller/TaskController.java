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
@CrossOrigin(origins = "https://task-diary-frontend.vercel.app") // ✅ FIXED
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // GET TASKS
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false, defaultValue = "1") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return taskRepository.findByUser(user);
        }
        return taskRepository.findAll();
    }

    // ADD TASK
    @PostMapping
    public Task addTask(@RequestBody Task task,
                        @RequestParam(required = false, defaultValue = "1") Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            task.setUser(user);
        }

        return taskRepository.save(task);
    }

    // UPDATE TASK
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTaskData) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

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

    // DELETE TASK
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}