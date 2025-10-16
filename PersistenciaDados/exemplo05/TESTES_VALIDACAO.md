# Exemplo de Testes de Validação com Lombok e Bean Validation

## Requisições de Teste para Validação

### 1. Criar Categoria Válida (✅ Deve funcionar)

```bash
curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Eletrônicos",
    "description": "Produtos eletrônicos e tecnologia"
  }'
```

### 2. Criar Categoria Inválida - Nome curto (❌ Deve falhar)

```bash
curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "PC",
    "description": "Computadores"
  }'
```

**Resposta Esperada:**
```json
{
  "timestamp": "2025-10-16T...",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "name": "Nome deve ter entre 3 e 50 caracteres"
  }
}
```

### 3. Criar Categoria Inválida - Nome vazio (❌ Deve falhar)

```bash
curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "description": "Teste"
  }'
```

**Resposta Esperada:**
```json
{
  "timestamp": "2025-10-16T...",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "name": "Nome da categoria é obrigatório"
  }
}
```

### 4. Criar Tag Válida (✅ Deve funcionar)

```bash
curl -X POST http://localhost:8080/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Novo",
    "color": "#FF5733"
  }'
```

### 5. Criar Tag Inválida - Cor no formato errado (❌ Deve falhar)

```bash
curl -X POST http://localhost:8080/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Promoção",
    "color": "red"
  }'
```

**Resposta Esperada:**
```json
{
  "timestamp": "2025-10-16T...",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "color": "Cor deve estar no formato hexadecimal (#RRGGBB)"
  }
}
```

### 6. Criar Tag Inválida - Nome muito curto (❌ Deve falhar)

```bash
curl -X POST http://localhost:8080/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "N",
    "color": "#00FF00"
  }'
```

**Resposta Esperada:**
```json
{
  "timestamp": "2025-10-16T...",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "name": "Nome deve ter entre 2 e 30 caracteres"
  }
}
```

## Validações Implementadas

### Product
- **name**: @NotBlank, @Size(min=3, max=100)
- **price**: @NotNull, @DecimalMin("0.01")
- **stock**: @NotNull, @Min(0)
- **description**: @Size(max=1000)

### Category
- **name**: @NotBlank, @Size(min=3, max=50)
- **description**: @Size(max=500)

### Tag
- **name**: @NotBlank, @Size(min=2, max=30)
- **color**: @Pattern(regexp="^#[0-9A-Fa-f]{6}$")

## Lombok Utilizado

Todas as entidades usam:
- **@Data**: gera getters, setters, toString, equals e hashCode
- **@NoArgsConstructor**: construtor vazio (obrigatório para JPA)
- **@AllArgsConstructor**: construtor com todos os campos

## Como Testar

1. Inicie a aplicação:
```powershell
.\mvnw.cmd spring-boot:run
```

2. Execute os comandos curl acima

3. Observe as respostas de erro com mensagens personalizadas

## Benefícios

✅ **Lombok**: Reduz boilerplate (getters/setters/construtores)
✅ **Bean Validation**: Valida dados antes de persistir no banco
✅ **GlobalExceptionHandler**: Respostas de erro padronizadas e informativas
✅ **Mensagens Customizadas**: Ajudam o cliente a entender o erro
