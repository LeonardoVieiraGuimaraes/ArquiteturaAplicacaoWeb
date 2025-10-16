// Script de inicialização do MongoDB
// Este arquivo é executado automaticamente quando o container é criado

db = db.getSiblingDB('productdb');

// Cria a collection products (opcional, será criada automaticamente pelo Spring Boot)
db.createCollection('products');

// Cria índice no campo name para buscas mais rápidas
db.products.createIndex({ "name": 1 });

// Mensagem de sucesso
print('✅ Database productdb inicializado com sucesso!');
print('✅ Collection products criada');
print('✅ Índice no campo "name" criado');
