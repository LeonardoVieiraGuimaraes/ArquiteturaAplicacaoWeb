CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Chave primária com incremento automático
    name VARCHAR(100) NOT NULL,           -- Nome do usuário, obrigatório
    email VARCHAR(100) UNIQUE NOT NULL,   -- Email único, obrigatório
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Data de criação com valor padrão
);

-- Adicione mais tabelas ou dados iniciais, se necessário
-- INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com');
