package com.cadastro.usuario;

// Importações necessárias para o funcionamento dos testes
import java.util.Arrays; // Importa a classe Arrays, que contém métodos utilitários para manipulação de arrays.
import java.util.List; // Importa a interface List, que representa uma lista ordenada de elementos.
import java.util.Optional; // Importa a classe Optional, que é um contêiner para um valor que pode estar presente ou ausente.

import static org.junit.jupiter.api.Assertions.assertEquals; // Importa o método assertEquals da biblioteca JUnit para verificar se dois valores são iguais em testes.
import org.junit.jupiter.api.BeforeEach; // Importa a anotação BeforeEach da biblioteca JUnit para marcar um método que deve ser executado antes de cada teste.
import org.junit.jupiter.api.Test; // Importa a anotação Test da biblioteca JUnit para marcar um método como um teste.
import org.mockito.InjectMocks; // Importa a anotação InjectMocks da biblioteca Mockito para injetar mocks nos objetos de teste.
import org.mockito.Mock; // Importa a anotação Mock da biblioteca Mockito para criar objetos mock.
import static org.mockito.Mockito.doNothing; // Importa o método doNothing da biblioteca Mockito para definir um comportamento de não fazer nada em um mock.
import static org.mockito.Mockito.times; // Importa o método times da biblioteca Mockito para verificar o número de vezes que um método foi chamado.
import static org.mockito.Mockito.verify; // Importa o método verify da biblioteca Mockito para verificar se um método foi chamado em um mock.
import static org.mockito.Mockito.when; // Importa o método when da biblioteca Mockito para definir o comportamento de um mock.
import org.mockito.MockitoAnnotations; // Importa a classe MockitoAnnotations da biblioteca Mockito para inicializar as anotações Mockito.
import org.springframework.http.ResponseEntity; // Importa a classe ResponseEntity do Spring Framework para representar uma resposta HTTP.

import com.cadastro.usuario.controllers.UsuarioController;
import com.cadastro.usuario.models.Usuario; // Importa a classe Usuario do pacote com.cadastro.usuario.models, que representa o modelo de dados de um usuário.
import com.cadastro.usuario.services.UsuarioService; // Importa a classe UsuarioService do pacote com.cadastro.usuario.services, que representa o serviço de usuário.


// Classe de teste para o controlador de usuários
public class UsuarioTest {

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
    // @Test
    // void testSomarErro() {
    //     int a = 1;
    //     int b = 2;
    //     int resultadoEsperado = 10;

    //     // Define o comportamento do método somar do controlador
    //     when(usuarioController.somar(a, b)).thenReturn(3);

    //     // Chama o método do controlador
    //     int resultado = usuarioController.somar(a, b);

    //     // Verifica se o resultado é o esperado (este teste falhará)
    //     assertEquals(resultadoEsperado, resultado, "O resultado da soma está incorreto");
    //     // Verifica se o método somar do serviço foi chamado uma vez
    //     verify(usuarioService, times(1)).somar(a, b);
    // }
}