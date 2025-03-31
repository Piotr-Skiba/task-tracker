package org.example;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private TaskRepository taskRepository;
    private File file;

    @BeforeEach
    public void setUp() {
        file = new File("tasks.json");
        taskRepository = new TaskRepository(file,
                new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .registerTypeAdapter(Task.class, new TaskAdapter())
                        .create());
    }

    @Test
    void testSaveTasks() throws IOException {
        List<Task> tasks = List.of(new Task(1, "Test task", "todo", LocalDateTime.now(), LocalDateTime.now()));

        taskRepository.saveTasks(tasks);

        try (FileReader reader = new FileReader(file)) {
            assertNotNull(reader);
        }
    }

    @Test
    void testFetchAllTasks() throws IOException {
        List<Task> tasks = List.of(new Task(1, "Test task", "todo", LocalDateTime.now(), LocalDateTime.now()));
        try (FileWriter writer = new FileWriter(file)) {
            taskRepository.getGson().toJson(tasks, writer);
        }

        List<Task> result = taskRepository.fetchAllTasks();

        assertEquals(1, result.size());
        assertEquals("Test task", result.getFirst().getDescription());
    }
}