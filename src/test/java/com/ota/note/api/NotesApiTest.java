package com.ota.note.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ota.note.models.Note;
import com.ota.note.models.Result;
import com.ota.note.services.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

/***
 * NoteApi Test Case.
 */
@WebMvcTest(NotesApi.class)
class NotesApiTest {

    private final Note note = Note.builder().id(UUID.randomUUID()).title("Test Title").body("Test Body").build();

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private NoteService noteService;

    @Test
    void add() throws Exception {
        Mockito.when(noteService.add(note))
            .thenReturn(Result.<Note>builder().success(true).obj(note).build());

        mvc.perform(MockMvcRequestBuilders
            .post("/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(note)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void getAll() throws Exception {
        Mockito.when(noteService.getAll()).thenReturn(List.of(note));

        mvc.perform(MockMvcRequestBuilders
            .get("/notes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.obj", hasSize(1)));
    }

    @SuppressWarnings("unchecked")
    @Test
    void findById() throws Exception {
        Mockito.when((Result<Note>) noteService.findById(note.getId()))
            .thenReturn(Result.<Note>builder().success(true).obj(note).build());

        mvc.perform(MockMvcRequestBuilders
            .get("/notes/" + note.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.obj.title", is(note.getTitle())));
    }

    @SuppressWarnings("unchecked")
    @Test
    void update() throws Exception  {
        Note updateNote = Note.builder()
            .id(UUID.randomUUID())
            .title("Updated note")
            .body("Updated Body").build();

        Mockito.when((Result<Note>) noteService.update(updateNote))
            .thenReturn(Result.<Note>builder().success(true).obj(updateNote).build());

        mvc.perform(MockMvcRequestBuilders
            .put("/notes/" + updateNote.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(updateNote)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void delete() throws Exception {
        Mockito.when((Result<String>) noteService.deleteById(note.getId()))
            .thenReturn(Result.<String>builder()
                .success(true)
                .obj("Note : " + note.getTitle() + " has been deleted.").build()
            );

        mvc.perform(MockMvcRequestBuilders
            .delete("/notes/" + note.getId()))
            .andExpect(status().isOk());
    }
}