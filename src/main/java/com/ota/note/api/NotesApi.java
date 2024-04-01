package com.ota.note.api;

import com.ota.note.models.Note;
import com.ota.note.models.Result;
import com.ota.note.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/***
 * Notes Api Version 1
 */
@RestController
@RequestMapping("/notes")
public class NotesApi {
    private final NoteService noteService;

    public NotesApi(NoteService noteService) {
        this.noteService = noteService;
    }

    /***
     * POST /notes: Create a new note.
     * @param note Note
     * @return Result<?>
     */
    @PostMapping
    public Result<?> add(@Valid @RequestBody Note note) {
        return noteService.add(note);
    }

    /***
     * GET /notes: Retrieve all notes.
     * @return Result<List<Note>>
     */
    @GetMapping
    public Result<List<Note>> getAll() {
        return Result.<List<Note>>builder()
            .success(true)
            .obj(noteService.getAll()).build();
    }

    /***
     * GET /notes/:id: Retrieve a specific note by ID.
     * @param id UUID
     * @return Result<?>
     */
    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable UUID id) {
        return noteService.findById(id);
    }

    /***
     * PUT /notes/:id: Update a specific note.
     * @param id UUID
     * @param note Note
     * @return Result<?>
     */
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable UUID id, @Valid @RequestBody Note note) {
        note.setId(id);
        return noteService.update(note);
    }

    /***
     * DELETE /notes/:id: Delete a specific note.
     * @param id UUID
     * @return Result<?>
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable UUID id) {
        return noteService.deleteById(id);
    }
}
