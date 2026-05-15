# 📦 Backend - Marketplace API

API REST desenvolvida em Java com Spring Boot para gerenciar um sistema de vendas de produtos.

## 🎯 Funcionalidades

- ✅ CRUD completo de Produtos
- ✅ CRUD completo de Usuários
- ✅ CRUD completo de Pedidos
- ✅ Gerenciamento de itens de pedido
- ✅ Controle de estoque
- ✅ Busca e filtros avançados
- ✅ Logging detalhado

## 🔧 Requisitos

- **Java 17+**
- **Maven 3.8+**
- **H2 Database** (em memória para desenvolvimento)
- **MySQL 8.0+** (opcional, para produção)

## 🚀 Como Iniciar

### 1. Compilar o Projeto

```bash
cd marketplace-api
mvn clean install
```

### 2. Executar a Aplicação

```bash
mvn spring-boot:run
```

Ou:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
```

A API estará disponível em: `http://localhost:8080`

### 3. Console H2 (Desenvolvimento)

Acesse: `http://localhost:8080/h2-console`

**Configuração:**
- Driver: `org.h2.Driver`
- URL: `jdbc:h2:mem:marketplacedb`
- User: `sa`
- Password: (deixar em branco)

## 📁 Estrutura do Projeto

```
marketplace-api/
├── src/main/java/com/marketplace/api/
│   ├── controller/              # Controladores REST
│   │   ├── ProductController.java
│   │   ├── UserController.java
│   │   └── OrderController.java
│   │
│   ├── service/                 # Serviços (lógica de negócio)
│   │   ├── ProductService.java
│   │   ├── UserService.java
│   │   └── OrderService.java
│   │
│   ├── model/                   # Entidades JPA
│   │   ├── Product.java
│   │   ├── User.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   │
│   ├── repository/              # Interfaces de acesso a dados
│   │   ├── ProductRepository.java
│   │   ├── UserRepository.java
│   │   ├── OrderRepository.java
│   │   └── OrderItemRepository.java
│   │
│   └── MarketplaceApplication.java  # Classe principal
│
├── src/main/resources/
│   └── application.properties   # Configurações
│
└── pom.xml                      # Dependências Maven
```

## 🔌 API Endpoints

### Produtos
```
GET    /api/produtos                          # Listar todos
GET    /api/produtos/{id}                     # Obter por ID
GET    /api/produtos/buscar/nome?nome=termo  # Buscar por nome
GET    /api/produtos/buscar/categoria        # Buscar por categoria
GET    /api/produtos/estoque/disponiveis     # Em estoque
POST   /api/produtos                          # Criar
PUT    /api/produtos/{id}                     # Atualizar
DELETE /api/produtos/{id}                     # Deletar
PATCH  /api/produtos/{id}/estoque             # Reduzir estoque
```

### Usuários
```
GET    /api/usuarios                          # Listar todos
GET    /api/usuarios/{id}                     # Obter por ID
GET    /api/usuarios/email/{email}            # Buscar por email
GET    /api/usuarios/tipo/clientes            # Listar clientes
GET    /api/usuarios/tipo/admin               # Listar admins
POST   /api/usuarios                          # Criar
PUT    /api/usuarios/{id}                     # Atualizar
DELETE /api/usuarios/{id}                     # Deletar
PATCH  /api/usuarios/{id}/ativar              # Ativar
PATCH  /api/usuarios/{id}/desativar           # Desativar
```

### Pedidos
```
GET    /api/pedidos                           # Listar todos
GET    /api/pedidos/{id}                      # Obter por ID
GET    /api/pedidos/usuario/{usuarioId}       # Por usuário
GET    /api/pedidos/status/{status}           # Por status
POST   /api/pedidos                           # Criar
PUT    /api/pedidos/{id}                      # Atualizar
DELETE /api/pedidos/{id}                      # Deletar
PATCH  /api/pedidos/{id}/status               # Alterar status
POST   /api/pedidos/{pedidoId}/itens          # Adicionar item
DELETE /api/pedidos/itens/{itemId}            # Remover item
```

## 📝 Configuração (application.properties)

```properties
# Banco de Dados
spring.datasource.url=jdbc:h2:mem:marketplacedb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate/JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Servidor
server.port=8080

# Timezone
spring.jackson.time-zone=America/Sao_Paulo
```

## 🧪 Testar a API

### Com cURL

```bash
# Listar produtos
curl http://localhost:8080/api/produtos

# Criar produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maçã",
    "descricao": "Maçã fresca",
    "preco": 5.50,
    "quantidade": 100,
    "categoria": "Frutas"
  }'
```

### Com Postman

1. Abrir Postman
2. Criar nova requisição
3. Selecionar método (GET, POST, etc.)
4. Preencher URL: `http://localhost:8080/api/...`
5. Para POST/PUT, ir em "Body" → "raw" → "JSON"
6. Clicar "Send"

## 🔍 Estrutura de Classes

### Product (Produto)

```java
public class Product {
    Long id;
    String nome;           // Obrigatório
    String descricao;
    BigDecimal preco;      // Obrigatório, > 0
    Integer quantidade;    // Obrigatório
    String imagem;
    String categoria;
    LocalDateTime dataCriacao;
    LocalDateTime dataAtualizacao;
}
```

### User (Usuário)

```java
public class User {
    Long id;
    String nome;           // Obrigatório
    String email;          // Obrigatório, único
    String senha;          // Obrigatório
    String telefone;
    String endereco;
    String tipo;           // CLIENTE ou ADMIN
    String status;         // ATIVO ou INATIVO
    LocalDateTime dataCriacao;
    LocalDateTime dataAtualizacao;
}
```

### Order (Pedido)

```java
public class Order {
    Long id;
    User usuario;                      // Obrigatório
    BigDecimal total;                  // Obrigatório, > 0
    String status;                     // PENDENTE, CONFIRMADO, etc.
    LocalDateTime dataPedido;
    String dataEntregaEstimada;
    String observacoes;
    String enderecoEntrega;            // Obrigatório
    LocalDateTime dataAtualizacao;
}
```

### OrderItem (Item de Pedido)

```java
public class OrderItem {
    Long id;
    Order pedido;                      // Obrigatório
    Product produto;                   // Obrigatório
    Integer quantidade;                // Obrigatório, > 0
    BigDecimal precoUnitario;          // Obrigatório
    BigDecimal subtotal;               // Calculado automaticamente
}
```

## 🗄️ Banco de Dados

### Diagrama Simplificado

```
USUARIOS (1) ──→ (N) PEDIDOS (1) ──→ (N) ITENS_PEDIDO (N) ←── (1) PRODUTOS
```

### Tabelas Principais

- **usuarios**: Clientes e administradores
- **produtos**: Catálogo de produtos
- **pedidos**: Pedidos realizados
- **itens_pedido**: Itens que compõem os pedidos

## 🛡️ Validações

### Produto
- Nome: obrigatório, até 150 caracteres
- Preço: obrigatório, deve ser positivo (> 0)
- Quantidade: não-negativa

### Usuário
- Nome: obrigatório, até 150 caracteres
- Email: obrigatório, deve ser válido, único
- Senha: obrigatória, até 255 caracteres

### Pedido
- Usuário: obrigatório (deve existir)
- Total: obrigatório, deve ser positivo
- Endereço: obrigatório

## 📊 Logs

Os logs são configurados para DEBUG na aplicação:

```properties
logging.level.com.marketplace.api=DEBUG
```

**Saída típica:**
```
2024-05-15 10:30:00 - ProductService - Obtendo todos os produtos
2024-05-15 10:30:01 - ProductController - GET /api/produtos - Obtendo todos os produtos
```

## 🚀 Build e Deploy

### Gerar JAR para Produção

```bash
# Build sem testes
mvn clean package -DskipTests

# Gera: target/marketplace-api-1.0.0.jar
```

### Executar JAR

```bash
java -jar target/marketplace-api-1.0.0.jar

# Ou com porta customizada
java -Dserver.port=8081 -jar target/marketplace-api-1.0.0.jar
```

## 🔐 Segurança (Futuro)

Melhorias a implementar:
- [ ] Autenticação JWT
- [ ] Hash de senhas (BCrypt)
- [ ] CORS restrito
- [ ] Validação mais rigorosa
- [ ] Rate limiting
- [ ] HTTPS

## 🐛 Troubleshooting

### "Port 8080 is already in use"
```bash
# Encontrar processo na porta
netstat -ano | findstr :8080

# Matar processo (Windows)
taskkill /PID <PID> /F

# Ou mudar porta em application.properties
server.port=8081
```

### "H2 console not accessible"
- Verificar se a aplicação está rodando
- Acessar: http://localhost:8080/h2-console
- Verificar credenciais no console

### Erros de Compilação
```bash
# Limpar e rebuild
mvn clean install -X

# Verificar Java version
javac -version
```

## 📚 Recursos Adicionais

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](http://www.h2database.com)
- [Lombok](https://projectlombok.org)

## 📞 Suporte

Para dúvidas:
1. Verificar logs da aplicação
2. Consultar documentação em `/docs`
3. Revisar código comentado

---

**Desenvolvido como trabalho final - 2024**
