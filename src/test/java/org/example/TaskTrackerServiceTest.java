package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskTrackerServiceTest {

    private TaskTrackerService taskTrackerService;
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskTrackerService = new TaskTrackerService(taskRepository);
    }

    @Test
    void testAddTask() {
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.fetchAllTasks()).thenReturn(tasks);

        taskTrackerService.addTask("Test task");

        assertEquals(1, tasks.size());
        assertEquals("Test task", tasks.get(0).getDescription());
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = List.of(new Task(1, "Test task", "todo", LocalDateTime.now(), LocalDateTime.now()));
        taskRepository.saveTasks(tasks);
        when(taskRepository.fetchAllTasks()).thenReturn(tasks);

        List<Task> result = taskTrackerService.getTasks("all");

        assertEquals(1, result.size());
    }

    @Test
    void testDeleteTask() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, "Test task", "todo", LocalDateTime.now(), LocalDateTime.now());
        tasks.add(task);
        when(taskRepository.fetchAllTasks()).thenReturn(tasks);

        taskTrackerService.deleteTask("1");

        assertTrue(tasks.isEmpty());
    }

    @Test
    void testMarkTask() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, "Test task", "todo", LocalDateTime.now(), LocalDateTime.now());
        tasks.add(task);
        taskRepository.saveTasks(tasks);
        when(taskRepository.fetchAllTasks()).thenReturn(tasks);

        taskTrackerService.mark("1", "done");
        task = taskRepository.fetchAllTasks().getFirst();
        System.out.println(task);
        assertEquals("done", task.getStatus());
    }

    @Test
    void testMarkTaskNotFound() {
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.fetchAllTasks()).thenReturn(tasks);

        assertThrows(NoSuchElementException.class, () -> taskTrackerService.mark("1", "done"));
    }
}