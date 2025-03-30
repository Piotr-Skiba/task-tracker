package org.example;

import java.util.*;
import java.util.logging.Logger;

public class UI {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Scanner scanner;
    private final TaskTrackerService taskTrackerService;

    public UI(Scanner scanner, TaskTrackerService taskTrackerService) {
        this.scanner = scanner;
        this.taskTrackerService = taskTrackerService;
    }


    public void taskCLI() {
        logger.info("Available commands: add, list, delete, help, exit");

        String[] input;
        boolean exit = false;
        while (!exit) {
            input = scanner.nextLine().split(" ", 20);

            String command = input[0];
            String arg1 = input.length > 1 ? input[1] : null;
            //String arg2 = input.length > 2 ? input[2] : null;

            if (input[0].equals("add")) {
                arg1 = String.join(" ", Arrays.copyOfRange(input, 1, input.length));
            }

            if ("exit".equalsIgnoreCase(command)) {
                exit = true;
            }


            performCommand(command, arg1);

        }
    }

    private void performCommand(String command, String arg1) {
        switch (command) {
            case "add":
                taskTrackerService.addTask(arg1);
                break;
            case "list":
                if (arg1 == null) {
                    listTasks(taskTrackerService.getTasks("all"));
                    break;
                }

                switch (arg1.toLowerCase()) {
                    case "done":
                        listTasks(taskTrackerService.getTasks("done"));
                        break;
                    case "todo":
                        listTasks(taskTrackerService.getTasks("todo"));
                        break;
                    case "in-progress":
                        listTasks(taskTrackerService.getTasks("in-progress"));
                        break;
                    default:
                        logger.info("Invalid status");
                        break;
                }
                break;
            case "delete":
                try {
                    taskTrackerService.deleteTask(arg1);
                } catch (IllegalArgumentException | NoSuchElementException e) {
                    logger.info(e.getMessage());
                }
                break;
            default:
                logger.info("Invalid command");
        }
    }


    private void listTasks(Collection<Task> tasks) {
        for (Task task : tasks) {
            logger.info(task.toString());
        }
    }


}
