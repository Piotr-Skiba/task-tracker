package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class Task {
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
