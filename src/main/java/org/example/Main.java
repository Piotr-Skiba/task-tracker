package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                //.registerTypeAdapter(Task.class, new TaskAdapter())
                .create();
        UI cliUI = new UI(new Scanner(System.in), new TaskTrackerService(new TaskRepository(new File("tasks.json"), gson)));
        cliUI.taskCLI();

    }
}