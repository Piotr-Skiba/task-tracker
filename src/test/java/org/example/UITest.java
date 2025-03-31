package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

class UITest {

    private UI ui;
    private TaskTrackerService taskTrackerService;
    private Scanner scanner;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        scanner = mock(Scanner.class);
        taskTrackerService = mock(TaskTrackerService.class);
        ui = new UI(scanner, taskTrackerService);
    }

    @Test
    void testAddCommand() {
        when(scanner.nextLine()).thenReturn("add", "Test task");

        ui.taskCLI();

        verify(taskTrackerService).addTask("Test task");
    }

    @Test
    void testListCommand() {
        when(scanner.nextLine()).thenReturn("list all");

        ui.taskCLI();

        verify(taskTrackerService).getTasks("all");
    }

    @Test
    void testDeleteCommand() {
        when(scanner.nextLine()).thenReturn("delete 1");

        ui.taskCLI();

        verify(taskTrackerService).deleteTask("1");
    }

    @Test
    void testMarkDoneCommand() {
        when(scanner.nextLine()).thenReturn("mark-done 1");

        ui.taskCLI();

        verify(taskTrackerService).mark("1", "done");
    }

    @Test
    void testInvalidCommand() {
        when(scanner.nextLine()).thenReturn("invalid");

        ui.taskCLI();

        verify(logger).info("Invalid command");
    }
}