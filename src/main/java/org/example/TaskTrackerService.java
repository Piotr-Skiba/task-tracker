package org.example;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Data
public class TaskTrackerService {

    private TaskRepository taskRepository;
    private List<Task> tasks;
    private static int taskCounter = 1;

    public TaskTrackerService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks(String status) {
        tasks = taskRepository.fetchAllTasks();
        if (status.equals("all")) return tasks;

        return tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .toList();
    }

    public void deleteTask(String taskId) {
        tasks = taskRepository.fetchAllTasks();
        Task toDelete = getTaskById(tasks, taskId);
        if (toDelete == null) {
            throw new IllegalArgumentException("Couldn't delete task with given id");
        } else {
            tasks.remove(toDelete);
            taskRepository.saveTasks(tasks);
        }
    }

    public void addTask(String description) {
        tasks = taskRepository.fetchAllTasks();
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(new Task(nextId(), description, "todo", LocalDateTime.now(), LocalDateTime.now()));
        taskRepository.saveTasks(tasks);
    }

    private Task getTaskById(List<Task> tasks, String taskId) {
        try {
            int id = Integer.parseInt(taskId);
            return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public int nextId() {
        return tasks.stream().map(Task::getId).max(Integer::compareTo).orElse(0) + 1;
    }

    public void mark(String id, String status) {
        try {
            int taskId = Integer.parseInt(id);
            Task task = tasks.stream()
                    .filter(t -> t.getId() == taskId)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Task with given id not found"));
            task.setStatus(status);
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.saveTasks(tasks);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}