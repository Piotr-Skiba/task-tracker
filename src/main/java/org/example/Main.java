package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


    UI cliUI = new UI(new Scanner(System.in), new TaskTrackerService());
    cliUI.taskCLI();

    }
}