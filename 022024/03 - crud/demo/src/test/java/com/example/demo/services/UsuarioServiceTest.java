package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Usuario> mockUsuarios = Arrays.asList(new Usuario(1L, "João", "joao@example.com"),
                new Usuario(2L, "Maria", "maria@example.com"));
        when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> usuarios = usuarioService.findAll();

        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Usuario mockUsuario = new Usuario(1L, "João", "joao@example.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(mockUsuario));

        Optional<Usuario> usuario = usuarioService.findById(1L);

        assertTrue(usuario.isPresent());
        assertEquals("João", usuario.get().getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Usuario mockUsuario = new Usuario(null, "João", "joao@example.com");
        Usuario savedUsuario = new Usuario(1L, "João", "joao@example.com");
        when(usuarioRepository.save(mockUsuario)).thenReturn(savedUsuario);

        Usuario usuario = usuarioService.save(mockUsuario);

        assertNotNull(usuario.getId());
        assertEquals("João", usuario.getNome());
        verify(usuarioRepository, times(1)).save(mockUsuario);
    }

    @Test
    void testUpdate() {
        Usuario existingUsuario = new Usuario(1L, "João", "joao@example.com");
        Usuario updatedUsuario = new Usuario(null, "João Silva", "joao.silva@example.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUsuario));
        when(usuarioRepository.save(existingUsuario)).thenReturn(existingUsuario);

        Optional<Usuario> result = usuarioService.update(1L, updatedUsuario);

        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getNome());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(existingUsuario);
    }

    @Test
    void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.deleteById(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSomar() {
        int result = usuarioService.somar(2, 3);

        assertEquals(5, result);
    }
}
