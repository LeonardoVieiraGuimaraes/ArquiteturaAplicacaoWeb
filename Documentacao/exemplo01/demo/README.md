# 📊 Tabela: Diferenças entre Stateless e Stateful

| Característica                | Stateful                                      | Stateless                                         |
|-------------------------------|-----------------------------------------------|---------------------------------------------------|
| Armazenamento de estado       | No servidor (ex: sessão HTTP)                 | No cliente (ex: JWT, token)                       |
| Escalabilidade                | Menor (requer sessão mantida no servidor)     | Maior (servidores não precisam manter estado)     |
| Performance                   | Pode ter sobrecarga por manter sessões        | Mais leve, sem gerenciamento de sessões           |
| Exemplo de autenticação       | Sessão com ID armazenado em HttpSession       | JWT enviado em cada requisição                    |
| Consistência entre servidores | Precisa de sticky session ou replicação de sessão | Fácil load balancing, qualquer servidor pode responder |
| Controle de logout            | Fácil (basta invalidar sessão no servidor)    | Mais difícil (precisa de blacklist ou controle no cliente) |
| Uso de memória no servidor    | Maior (mantém sessões ativas)                 | Menor (sem sessões no servidor)                   |
| Exemplo prático               | Spring Security com HttpSession               | Spring Security com JWT (Bearer Token, OAuth2)    |
| Resiliência                   | Menor (falha no servidor pode perder sessões) | Maior (sem dependência de memória do servidor)    |
| Segurança                     | Sessão pode expirar ou ser roubada            | JWT pode ser interceptado ou manipulado se não assinado bem |

---

## ✅ Quando usar cada um?

### Stateful (com sessão):
- Aplicações monolíticas
- Sistemas administrativos ou internos
- Quando precisa invalidar sessões dinamicamente

### Stateless (com token/JWT):
- APIs REST
- Microsserviços
- Aplicações mobile ou SPAs (Single Page Applications)

