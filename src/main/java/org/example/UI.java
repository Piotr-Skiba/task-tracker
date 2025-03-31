package org.example;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

@AllArgsConstructor
public class UI {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Scanner scanner;
    private final TaskTrackerService taskTrackerService;

    public void taskCLI() {
        logger.info("Available commands: add, list, delete, help, exit");

        Map<String, Consumer<String[]>> commandHandlers = new HashMap<>();
        commandHandlers.put("add", input -> taskTrackerService.addTask(String.join(" ", Arrays.copyOfRange(input, 1, input.length))));
        commandHandlers.put("list", input -> listTasks(taskTrackerService.getTasks(input.length > 1 ? input[1] : "all")));
        commandHandlers.put("delete", this::handleDelete);
        commandHandlers.put("mark-done", input -> handleMark(input, "done"));
        commandHandlers.put("mark-in-progress", input -> handleMark(input, "in-progress"));
        commandHandlers.put("exit", input -> System.exit(0));

        while (true) {
            String[] input = scanner.nextLine().split(" ", 20);
            String command = input[0];
            commandHandlers.getOrDefault(command, cmd -> logger.info("Invalid command")).accept(input);
        }
    }

    private void handleDelete(String[] input) {
        try {
            taskTrackerService.deleteTask(input[1]);
        } catch (IllegalArgumentException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            logger.info(e.getMessage());
        }
    }

    private void handleMark(String[] input, String status) {
        try {
            taskTrackerService.mark(input[1], status);
        } catch (IllegalArgumentException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            logger.info(e.getMessage());
        }
    }

    private void listTasks(Collection<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            logger.info("No tasks found");
            return;
        }
        tasks.forEach(task -> logger.info(task.toString()));
    }
}