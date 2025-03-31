package org.example;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

public class TaskAdapter extends TypeAdapter<Task> {

    @Override
    public void write(JsonWriter out, Task task) throws IOException {
        out.beginObject();
        out.name("id").value(task.getId());
        out.name("description").value(task.getDescription());
        out.name("status").value(task.getStatus());
        out.name("created").value(task.getCreatedAt().toString());
        out.name("updated").value(task.getUpdatedAt().toString());
        out.endObject();
    }

    @Override
    public Task read(JsonReader in) throws IOException {
        in.beginObject();
        int id = 0;
        String description = null;
        String status = null;
        LocalDateTime created = null;
        LocalDateTime updated = null;

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    id = in.nextInt();
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "status":
                    status = in.nextString();
                    break;
                case "created":
                    created = LocalDateTime.parse(in.nextString());
                    break;
                case "updated":
                    updated = LocalDateTime.parse(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return new Task(id, description, status, created, updated);
    }
}