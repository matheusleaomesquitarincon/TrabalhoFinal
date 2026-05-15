# 💾 Documentação do Banco de Dados

## Visão Geral

O Marketplace utiliza um banco de dados relacional com 4 tabelas principais:

1. **USUARIOS** - Armazena dados de clientes e administradores
2. **PRODUTOS** - Catálogo de produtos disponíveis
3. **PEDIDOS** - Pedidos realizados pelos usuários
4. **ITENS_PEDIDO** - Itens que compõem cada pedido

---

## Diagrama Entidade-Relacionamento (ER)

```
        ┌────────────────────┐
        │     USUARIOS       │
        ├────────────────────┤
        │ id (PK)            │───┐
        │ nome               │   │
        │ email (UNIQUE)     │   │
        │ senha              │   │
        │ telefone           │   │
        │ endereco           │   │
        │ tipo               │   │
        │ status             │   │
        │ dataCriacao        │   │
        │ dataAtualizacao    │   │
        └────────────────────┘   │
                                 │
                                 │ 1:N
                                 │
                                 ▼
        ┌────────────────────┐
        │     PEDIDOS        │
        ├────────────────────┤
        │ id (PK)            │
        │ usuario_id (FK)    │───┬─────┐
        │ total              │   │     │
        │ status             │   │     │
        │ dataPedido         │   │     │
        │ dataEntrega...     │   │     │
        │ observacoes        │   │     │
        │ enderecoEntrega    │   │     │
        │ dataAtualizacao    │   │     │
        └────────────────────┘   │     │
                                 │     │
                                 │  1:N
                                 │     │
                                 ▼     │
        ┌────────────────────┐        │
        │   ITENS_PEDIDO     │        │
        ├────────────────────┤        │
        │ id (PK)            │        │
        │ pedido_id (FK)     │        │
        │ produto_id (FK)    │────────┼──┐
        │ quantidade         │        │  │
        │ precoUnitario      │        │  │
        │ subtotal           │        │  │
        └────────────────────┘        │  │
                                      │  │
                                      │  │ N:1
                                      │  │
                                      ▼  ▼
        ┌────────────────────┐
        │    PRODUTOS        │
        ├────────────────────┤
        │ id (PK)            │
        │ nome               │
        │ descricao          │
        │ preco              │
        │ quantidade         │
        │ imagem             │
        │ categoria          │
        │ dataCriacao        │
        │ dataAtualizacao    │
        └────────────────────┘
```

---

## Definição das Tabelas

### 1. USUARIOS

Armazena informações de usuários (clientes e administradores).

```sql
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    tipo VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_tipo (tipo),
    INDEX idx_status (status)
);
```

**Campos:**

| Campo | Tipo | Constraints | Descrição |
|-------|------|-----------|-----------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | ID único |
| nome | VARCHAR(150) | NOT NULL | Nome completo |
| email | VARCHAR(150) | UNIQUE, NOT NULL | Email único para login |
| senha | VARCHAR(255) | NOT NULL | Senha (hashada) |
| telefone | VARCHAR(20) | - | Telefone de contato |
| endereco | VARCHAR(255) | - | Endereço principal |
| tipo | VARCHAR(20) | NOT NULL, DEFAULT 'CLIENTE' | CLIENTE ou ADMIN |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'ATIVO' | ATIVO ou INATIVO |
| data_criacao | TIMESTAMP | NOT NULL | Quando foi criado |
| data_atualizacao | TIMESTAMP | NOT NULL | Última modificação |

**Valores de TIPO:**
- `CLIENTE` - Usuário comum que compra
- `ADMIN` - Administrador com permissões extras

**Valores de STATUS:**
- `ATIVO` - Usuário pode fazer login
- `INATIVO` - Usuário bloqueado

---

### 2. PRODUTOS

Catálogo de produtos disponíveis para venda.

```sql
CREATE TABLE produtos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(500),
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    imagem VARCHAR(255),
    categoria VARCHAR(100),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nome (nome),
    INDEX idx_categoria (categoria),
    INDEX idx_quantidade (quantidade)
);
```

**Campos:**

| Campo | Tipo | Constraints | Descrição |
|-------|------|-----------|-----------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | ID único |
| nome | VARCHAR(150) | NOT NULL | Nome do produto |
| descricao | VARCHAR(500) | - | Descrição detalhada |
| preco | DECIMAL(10, 2) | NOT NULL | Preço unitário |
| quantidade | INT | NOT NULL | Estoque disponível |
| imagem | VARCHAR(255) | - | URL da imagem |
| categoria | VARCHAR(100) | - | Categoria do produto |
| data_criacao | TIMESTAMP | NOT NULL | Quando foi criado |
| data_atualizacao | TIMESTAMP | NOT NULL | Última atualização |

**Exemplo de Dados:**

```sql
INSERT INTO produtos VALUES (
    1,
    'Maçã Vermelha',
    'Maçã fresca e suculenta importada',
    5.50,
    100,
    'https://example.com/maca.jpg',
    'Frutas',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
```

---

### 3. PEDIDOS

Pedidos realizados pelos clientes.

```sql
CREATE TABLE pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_entrega_estimada VARCHAR(50),
    observacoes VARCHAR(500),
    endereco_entrega VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    INDEX idx_usuario (usuario_id),
    INDEX idx_status (status),
    INDEX idx_data (data_pedido)
);
```

**Campos:**

| Campo | Tipo | Constraints | Descrição |
|-------|------|-----------|-----------|
| id | INT | PRIMARY KEY | ID único |
| usuario_id | INT | FOREIGN KEY, NOT NULL | Quem fez o pedido |
| total | DECIMAL(10, 2) | NOT NULL | Valor total |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'PENDENTE' | Estado do pedido |
| data_pedido | TIMESTAMP | NOT NULL | Quando foi feito |
| data_entrega_estimada | VARCHAR(50) | - | Data estimada |
| observacoes | VARCHAR(500) | - | Notas especiais |
| endereco_entrega | VARCHAR(255) | NOT NULL | Onde entregar |
| data_atualizacao | TIMESTAMP | NOT NULL | Última modificação |

**Valores de STATUS:**
- `PENDENTE` - Aguardando confirmação
- `CONFIRMADO` - Confirmado e em separação
- `ENVIADO` - Em trânsito
- `ENTREGUE` - Entregue ao cliente
- `CANCELADO` - Cancelado

---

### 4. ITENS_PEDIDO

Detalhes dos itens que compõem cada pedido.

```sql
CREATE TABLE itens_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE RESTRICT,
    INDEX idx_pedido (pedido_id),
    INDEX idx_produto (produto_id)
);
```

**Campos:**

| Campo | Tipo | Constraints | Descrição |
|-------|------|-----------|-----------|
| id | INT | PRIMARY KEY | ID único |
| pedido_id | INT | FOREIGN KEY | Qual pedido |
| produto_id | INT | FOREIGN KEY | Qual produto |
| quantidade | INT | NOT NULL | Quantos itens |
| preco_unitario | DECIMAL(10, 2) | NOT NULL | Preço na compra |
| subtotal | DECIMAL(10, 2) | NOT NULL | quantidade × preço |

---

## Relacionamentos

### 1. USUARIOS ↔ PEDIDOS

**Tipo:** Um-para-Muitos (1:N)

- Um usuário pode ter MUITOS pedidos
- Cada pedido pertence a UM usuário

**Integridade Referencial:** ON DELETE CASCADE
- Se um usuário é deletado, seus pedidos também são

---

### 2. PEDIDOS ↔ ITENS_PEDIDO

**Tipo:** Um-para-Muitos (1:N)

- Um pedido contém MUITOS itens
- Cada item pertence a UM pedido

**Integridade Referencial:** ON DELETE CASCADE
- Se um pedido é deletado, seus itens também são

---

### 3. PRODUTOS ↔ ITENS_PEDIDO

**Tipo:** Um-para-Muitos (1:N)

- Um produto aparece em MUITOS itens de pedidos
- Cada item refere-se a UM produto

**Integridade Referencial:** ON DELETE RESTRICT
- Produtos não podem ser deletados se estão em pedidos

---

## Índices

Os índices melhoram a performance das buscas:

```sql
-- USUARIOS
CREATE INDEX idx_email ON usuarios(email);       -- Buscar por email
CREATE INDEX idx_tipo ON usuarios(tipo);         -- Filtrar por tipo
CREATE INDEX idx_status ON usuarios(status);     -- Filtrar por status

-- PRODUTOS
CREATE INDEX idx_nome ON produtos(nome);         -- Buscar por nome
CREATE INDEX idx_categoria ON produtos(categoria); -- Filtrar por categoria
CREATE INDEX idx_quantidade ON produtos(quantidade); -- Produtos em estoque

-- PEDIDOS
CREATE INDEX idx_usuario ON pedidos(usuario_id); -- Pedidos de um usuário
CREATE INDEX idx_status ON pedidos(status);      -- Pedidos por status
CREATE INDEX idx_data ON pedidos(data_pedido);   -- Pedidos por data

-- ITENS_PEDIDO
CREATE INDEX idx_pedido ON itens_pedido(pedido_id);
CREATE INDEX idx_produto ON itens_pedido(produto_id);
```

---

## Consultas Comuns

### 1. Listar pedidos de um usuário com itens

```sql
SELECT p.id, p.status, p.total, p.data_pedido,
       GROUP_CONCAT(pr.nome, ', ') as produtos
FROM pedidos p
LEFT JOIN itens_pedido ip ON p.id = ip.pedido_id
LEFT JOIN produtos pr ON ip.produto_id = pr.id
WHERE p.usuario_id = ?
GROUP BY p.id
ORDER BY p.data_pedido DESC;
```

### 2. Calcular receita total por período

```sql
SELECT DATE(data_pedido) as data,
       COUNT(*) as quantidade_pedidos,
       SUM(total) as receita_total
FROM pedidos
WHERE status = 'ENTREGUE'
  AND DATE(data_pedido) BETWEEN ? AND ?
GROUP BY DATE(data_pedido)
ORDER BY data DESC;
```

### 3. Produtos mais vendidos

```sql
SELECT pr.id, pr.nome, pr.categoria,
       SUM(ip.quantidade) as total_vendido,
       SUM(ip.subtotal) as receita
FROM itens_pedido ip
JOIN produtos pr ON ip.produto_id = pr.id
JOIN pedidos p ON ip.pedido_id = p.id
WHERE p.status = 'ENTREGUE'
GROUP BY pr.id, pr.nome
ORDER BY total_vendido DESC
LIMIT 10;
```

### 4. Estoque baixo

```sql
SELECT id, nome, quantidade, categoria
FROM produtos
WHERE quantidade < 50
ORDER BY quantidade ASC;
```

---

## Backup e Restore

### Backup

```bash
# H2 Database
java -cp h2-1.4.200.jar org.h2.tools.Shell

# MySQL
mysqldump -u root -p marketplace > backup.sql
```

### Restore

```bash
# MySQL
mysql -u root -p marketplace < backup.sql
```

---

## Migração de Dados

Se precisar migrar de H2 para MySQL:

1. **Exportar dados do H2:**
```sql
SELECT * FROM usuarios INTO OUTFILE 'usuarios.csv' FIELDS TERMINATED BY ',';
```

2. **Atualizar application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marketplace
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
```

3. **Importar dados:**
```sql
LOAD DATA INFILE 'usuarios.csv' INTO TABLE usuarios;
```

---

## Performance

### Otimizações Implementadas

1. **Índices** nos campos mais consultados
2. **Lazy Loading** de relacionamentos
3. **Paginação** de resultados
4. **Cache** de produtos

### Dicas de Otimização

- Usar LIMIT em SELECT grandes
- Criar índices compostos para filtros múltiplos
- Arquivar pedidos antigos
- Limpar logs regularmente

---

## Monitoramento

### Consultas Lentas

```sql
-- Ativar log de queries lentas
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;
```

### Verificar Índices Não Usados

```sql
SELECT * FROM information_schema.statistics
WHERE table_schema = 'marketplace'
ORDER BY seq_in_index;
```

---

## Segurança

### Boas Práticas

1. **Senhas:** Sempre armazenar hashadas (BCrypt)
2. **SQL Injection:** Usar prepared statements (JPA faz isso)
3. **Backup:** Fazer backup regularmente
4. **Permissões:** Restringir acesso ao banco
5. **Encriptação:** Dados sensíveis encriptados

