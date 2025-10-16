package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.controllers.UsuarioController;
import com.example.demo.models.Usuario;

// Classe de teste para o controlador de usuários
public class UsuarioControllerTest {

    // Injeta uma instância mockada do UsuarioController
    @InjectMocks
    private UsuarioController usuarioController;

    // Cria um mock do serviço de usuário
    @Mock
    private UsuarioService usuarioService;

    // Método que é executado antes de cada teste para inicializar os mocks
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para o método obterTodos do controlador
    @Test
    void testObterTodos() {
        // Cria uma lista de usuários mockada usando o construtor com todos os parâmetros
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "User1", "user1@example.com"),
                new Usuario(2L, "User2", "user2@example.com")
        );

        // Define o comportamento do serviço mockado
        when(usuarioService.findAll()).thenReturn(usuarios);

        // Chama o método do controlador
        List<Usuario> response = usuarioController.obterTodos();

        // Verifica se o tamanho da lista retornada é 2
        assertEquals(2, response.size());
        // Verifica se o método findAll do serviço foi chamado uma vez
        verify(usuarioService, times(1)).findAll();
    }

    // Teste para o método obterPorId do controlador
    @Test
    void testObterPorId() {
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "User1", "user1@example.com");

        // Define o comportamento do serviço mockado
        when(usuarioService.findById(usuarioId)).thenReturn(Optional.of(usuario));

        // Chama o método do controlador
        ResponseEntity<Usuario> response = usuarioController.obterPorId(usuarioId);

        // Verifica se a resposta é a esperada
        assertEquals(ResponseEntity.ok(usuario), response);
        // Verifica se o método findById do serviço foi chamado uma vez
        verify(usuarioService, times(1)).findById(usuarioId);
    }

    // Teste para o método inserir do controlador
    @Test
    void testInserir() {
        Usuario usuario = new Usuario(1L, "User1", "user1@example.com");

        // Define o comportamento do serviço mockado
        when(usuarioService.save(usuario)).thenReturn(usuario);

        // Chama o método do controlador
        Usuario response = usuarioController.inserir(usuario);

        // Verifica se a resposta é a esperada
        assertEquals(usuario, response);
        // Verifica se o método save do serviço foi chamado uma vez
        verify(usuarioService, times(1)).save(usuario);
    }

    // Teste para o método atualizar do controlador
    @Test
    void testAtualizar() {
        // Define o ID do usuário que será atualizado
        Long usuarioId = 1L;

        // Cria um objeto Usuario com os dados iniciais
        Usuario usuario = new Usuario(usuarioId, "User1", "user1@example.com");

        // Cria um objeto Usuario com os dados atualizados
        Usuario updatedUsuario = new Usuario(usuarioId, "User2", "user2@example.com");

        // Configura o comportamento simulado do serviço de usuário para o método update
        // Quando o método update é chamado com os parâmetros usuarioId e usuario,
        // ele deve retornar um Optional contendo updatedUsuario
        when(usuarioService.update(usuarioId, usuario)).thenReturn(Optional.of(updatedUsuario));

        // Chama o método atualizar do controlador e obtém a resposta
        ResponseEntity<Usuario> response = usuarioController.atualizar(usuarioId, usuario);

        // Verifica se a resposta é igual à resposta esperada (ResponseEntity.ok(updatedUsuario))
        assertEquals(ResponseEntity.ok(updatedUsuario), response);

        // Verifica se o método update do serviço de usuário foi chamado exatamente uma vez com os parâmetros fornecidos
        verify(usuarioService, times(1)).update(usuarioId, usuario);
    }

    // Teste para o método excluir do controlador
    @Test
    void testExcluir() {
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "User1", "user1@example.com");

        // Define o comportamento do serviço mockado
        when(usuarioService.findById(usuarioId)).thenReturn(Optional.of(usuario));
        // Define o comportamento do mock para o método deleteById do serviço de usuário.
        doNothing().when(usuarioService).deleteById(usuarioId);

        // Chama o método do controlador
        ResponseEntity<Void> response = usuarioController.excluir(usuarioId);

        // Verifica se a resposta é a esperada
        assertEquals(ResponseEntity.noContent().build(), response);
        // Verifica se o método deleteById do serviço foi chamado uma vez
        verify(usuarioService, times(1)).deleteById(usuarioId);
    }

    // Teste para o método somar do controlador, que está incorreto
    @Test
    void testSomarErro() {
        int a = 1;
        int b = 2;
        int resultadoEsperado = 10;

        // Define o comportamento do método somar do controlador
        when(usuarioController.somar(a, b)).thenReturn(3);

        // Chama o método do controlador
        int resultado = usuarioController.somar(a, b);

        // Verifica se o resultado é o esperado (este teste falhará)
        assertEquals(resultadoEsperado, resultado, "O resultado da soma está incorreto");
        // Verifica se o método somar do serviço foi chamado uma vez
        verify(usuarioService, times(1)).somar(a, b);
    }
}
