package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TaskTrackerService {


    private static ArrayList<Task> tasks = new ArrayList<>();

    static {
        tasks.add(new Task(1, "Create a new project", "todo", LocalDateTime.parse("2023-01-01T00:00:00"), LocalDateTime.parse("2023-01-01T00:00:00")));
        tasks.add(new Task(2, "Write documentation", "In-Progress", LocalDateTime.parse("2023-01-02T00:00:00"), LocalDateTime.parse("2023-01-02T00:00:00")));
        tasks.add(new Task(3, "Implement feature X", "ToDo", LocalDateTime.parse("2023-01-03T00:00:00"), LocalDateTime.parse("2023-01-03T00:00:00")));
        tasks.add(new Task(4, "Fix bug Y", "done", LocalDateTime.parse("2023-01-04T00:00:00"), LocalDateTime.parse("2023-01-04T00:00:00")));
    }


    public Collection<Task> getTasks(String status) {
        if (status.equals("all")) return tasks;

        return tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .toList();

    }

    public void deleteTask(String taksId) {
        try {
            Task toDelete = getTaskById(taksId);
            tasks.remove(toDelete);

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Task with given id not found");
        }

    }


    public void addTask(String description) {
        tasks.add(new Task(tasks.size() + 1, description, "todo", LocalDateTime.now(), LocalDateTime.now()));

    }


    private Task getTaskById(String taksId) {
        Optional<Task> tasktoGet = tasks.stream().filter(task-> task.getId() == Integer.parseInt(taksId)).findFirst();
        if (tasktoGet.isEmpty()) {
            throw new NoSuchElementException("Task with given id not found");
        }
        return tasktoGet.get();
    }
}
