package com.ota.note.services;

import com.ota.note.models.Note;
import com.ota.note.models.Result;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ota.note.models.Result.ResultBuilder;

/***
 * NoteService with Singleton scope.
 */
@Service
@Scope("singleton")
@NoArgsConstructor
public class NoteService {
    private final List<Note> notes = new ArrayList<>();

    /***
     * Adding new note toe the store
     * @param note Param notes
     * @return Result<Note>
     */
    public Result<Note> add(Note note) {
        note.setId(UUID.randomUUID());
        note.setCreated(Instant.now());
        notes.add(note);
        return Result.<Note>builder().success(true).obj(note).build();
    }

    /***
     * Internal filter by unique key.
     * @param id UUID
     * @return Optional<Note>
     */
    private Optional<Note> filterNote(UUID id) {
        return notes.stream()
            .filter(candidate -> candidate.getId().equals(id) && !candidate.isDeleted())
            .findAny();
    }

    /***
     * Get specific Note base on its unique id
     * @param id UUID
     * @return Result<Object> - Object must be Note or String
     */
    public Result<?> findById(UUID id) {
        ResultBuilder builder = Result.builder();

        filterNote(id).ifPresentOrElse(
            note -> builder.success(true).obj(note),
            () -> builder.success(false).obj("Not Found")
        );

        return builder.build();
    }

    /***
     * Update specific Note base on its unique id
     * @param note Param Notes
     * @return Result<?> - Object must be Note or String
     */
    public Result<?> update(Note note) {
        ResultBuilder builder = Result.builder();

        filterNote(note.getId()).ifPresentOrElse(noted -> {
            noted.setUpdated(Instant.now());
            noted.setTitle(note.getTitle());
            noted.setBody(note.getBody());
            builder.success(true).obj(noted);
        }, () -> builder.success(false).obj("Not Found"));

        return builder.build();
    }

    /***
     * Delete note by unique id;
     * @param id UUID
     * @return Result<?> - Object must be Note or String
     */
    public Result<?> deleteById(UUID id) {
        ResultBuilder builder = Result.builder();

        filterNote(id).ifPresentOrElse(noted -> {
            noted.setDeleted(true);
            builder.success(true).obj("Note : " + noted.getTitle() + " has been deleted.");
        }, () -> builder.success(false).obj("Not Found"));

        return builder.build();
    }

    /***
     * Get All existing notes.
     * @return List<Note>
     */
    public List<Note> getAll() {
        return notes.stream().filter(note -> !note.isDeleted()).toList();
    }
}
