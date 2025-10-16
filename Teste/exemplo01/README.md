# Testes Unitários - exemplo01

Este projeto contém exemplos de testes unitários utilizando JUnit e Mockito para a classe Product e ProductController.

## Como executar os testes

1. Abra o terminal na pasta `Teste/exemplo01/demo`.
2. Execute os testes (PowerShell):

```powershell
mvn clean test
```

3. Os resultados dos testes aparecerão no terminal (Surefire). Em caso de falhas, veja relatórios em `target/surefire-reports/`.

## Estrutura dos testes
- `ProductTest.java`: Testes unitários do serviço ProductService (CRUD com mock do repositório).
- `ProductControllerTest.java`: Testes unitários dos endpoints do controlador ProductController (mock do serviço).
- `ProductFullTest.java`: Exemplo de todos os testes juntos em um único arquivo.

## Tecnologias
- JUnit 5
- Mockito
- Maven

## Observação
Os testes são isolados e não dependem de banco de dados real.

## Dicas e Troubleshooting

- Se o pacote/classe não for encontrado, confira o `package` no topo do teste (`com.example.demo`).
- Para mockar dependências, use `@Mock`, `@InjectMocks` e `MockitoAnnotations.openMocks(this)` no `@BeforeEach`.
- Para simular retorno do repositório/serviço: `when(repo.save(...)).thenReturn(...)`.
- Para verificar interações: `verify(repo, times(1)).save(...)`.
