package com.ota.note.services;

import com.ota.note.models.Note;
import com.ota.note.models.Result;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/***
 * Note: All Test cases are positive test cases.
 */
class NoteServiceTest {
    private final NoteService service = new NoteService();

    /***
     * Adding new notes to the store
     */
    @Test
    void add() {
        Note note = Note.builder()
            .id(UUID.randomUUID())
            .title("Test Title")
            .body("Test Body")
            .build();

        Note result = service.add(note).getObj();

        assertEquals(note.getId(), result.getId());
        assertEquals(note.getTitle(), result.getTitle());
        assertEquals(note.getBody(), result.getBody());
        assertNull(note.getUpdated());
        assertNotNull(note.getCreated());
    }

    /***
     * Updating notes existing in the store
     */
    @Test
    void update() {
        add();
        Note oldNote = service.getAll().get(0);
        Note note = Note.builder()
                .id(oldNote.getId())
                .title("Test Update Title")
                .body("Test Update Body")
                .build();

        Result<?> result = service.update(note);
        Note updatedNote = (Note) result.getObj();

        assertEquals(updatedNote.getTitle(), note.getTitle());
        assertEquals(updatedNote.getBody(), note.getBody());
        assertNotNull(updatedNote.getUpdated());
    }

    /***
     * Fetch all notes in the store
     */
    @Test
    void getAll() {
        add();
        assertNotEquals(service.getAll().size(), 0);
    }

    /***
     * Finding note by its unique key
     */
    @Test
    void findById() {
        add();
        Note oldNote = service.getAll().get(0);
        Result<?> result =  service.findById(oldNote.getId());
        assertTrue(result.isSuccess());
    }

    /***
     * Deleting note by its unique key
     */
    @Test
    void deleteById() {
        add();
        Note oldNote = service.getAll().get(0);
        Result<?> result = service.deleteById(oldNote.getId());
        assertTrue(result.isSuccess());
    }
}