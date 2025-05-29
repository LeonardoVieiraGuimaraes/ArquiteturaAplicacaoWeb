package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;

@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testSaveAuthor() throws Exception {
        Author author = new Author();
        author.setId(1L); // Alterado para Long
        author.setNome("Test Author");
        author.setDataNascimento(LocalDate.of(1980, 1, 1)); // Certifique-se de que o tipo é LocalDate
        author.setNacionalidade("Brazilian");

        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nome": "Test Author",
                            "dataNascimento": "1980-01-01",
                            "nacionalidade": "Brazilian"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Author"))
                .andExpect(jsonPath("$.nacionalidade").value("Brazilian"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllAuthors() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
