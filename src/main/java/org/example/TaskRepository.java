package org.example;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class TaskRepository {

    private File file;
    private Gson gson = new Gson();

    public void save(Task task) {
        //implement this method
    }




}
