package com.ota.note.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Note {
    private UUID id;
    @NotNull(message = "Title is required.")
    private String title;
    @NotNull(message = "Body is required.")
    private String body;
    private Instant created;
    private Instant updated;
    private boolean deleted;
}
