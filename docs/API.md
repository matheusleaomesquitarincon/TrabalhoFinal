# 📚 Documentação da API REST

## Base URL
```
http://localhost:8080/api
```

## Autenticação
Atualmente sem autenticação. Em produção, implementar JWT.

## Headers
```
Content-Type: application/json
Accept: application/json
```

---

## 📦 PRODUTOS

### 1. Listar Todos os Produtos

```http
GET /produtos
```

**Resposta de Sucesso (200 OK):**
```json
[
    {
        "id": 1,
        "nome": "Maçã Vermelha",
        "descricao": "Maçã fresca e suculenta",
        "preco": 5.50,
        "quantidade": 100,
        "imagem": "https://example.com/maca.jpg",
        "categoria": "Frutas",
        "dataCriacao": "2024-05-15T10:30:00",
        "dataAtualizacao": "2024-05-15T10:30:00"
    },
    {
        "id": 2,
        "nome": "Banana",
        "descricao": "Banana amarela madura",
        "preco": 3.20,
        "quantidade": 50,
        "imagem": "https://example.com/banana.jpg",
        "categoria": "Frutas",
        "dataCriacao": "2024-05-15T10:30:00",
        "dataAtualizacao": "2024-05-15T10:30:00"
    }
]
```

---

### 2. Obter Produto por ID

```http
GET /produtos/{id}
```

**Exemplo:**
```http
GET /produtos/1
```

**Resposta de Sucesso (200 OK):**
```json
{
    "id": 1,
    "nome": "Maçã Vermelha",
    "descricao": "Maçã fresca e suculenta",
    "preco": 5.50,
    "quantidade": 100,
    "imagem": "https://example.com/maca.jpg",
    "categoria": "Frutas",
    "dataCriacao": "2024-05-15T10:30:00",
    "dataAtualizacao": "2024-05-15T10:30:00"
}
```

**Resposta de Erro (404 Not Found):**
```json
"Produto não encontrado"
```

---

### 3. Buscar Produtos por Nome

```http
GET /produtos/buscar/nome?nome=termo
```

**Exemplo:**
```http
GET /produtos/buscar/nome?nome=maçã
```

**Resposta:**
```json
[
    {
        "id": 1,
        "nome": "Maçã Vermelha",
        ...
    }
]
```

---

### 4. Buscar Produtos por Categoria

```http
GET /produtos/buscar/categoria?categoria=Frutas
```

**Resposta:**
```json
[
    {
        "id": 1,
        "nome": "Maçã Vermelha",
        "categoria": "Frutas",
        ...
    },
    {
        "id": 2,
        "nome": "Banana",
        "categoria": "Frutas",
        ...
    }
]
```

---

### 5. Obter Produtos em Estoque

```http
GET /produtos/estoque/disponiveis
```

**Resposta:** Lista de produtos com quantidade > 0

---

### 6. Criar Novo Produto

```http
POST /produtos
Content-Type: application/json

{
    "nome": "Laranja",
    "descricao": "Laranja fresca do sitio",
    "preco": 4.75,
    "quantidade": 200,
    "imagem": "https://example.com/laranja.jpg",
    "categoria": "Frutas"
}
```

**Resposta (201 Created):**
```json
{
    "id": 3,
    "nome": "Laranja",
    "descricao": "Laranja fresca do sitio",
    "preco": 4.75,
    "quantidade": 200,
    "imagem": "https://example.com/laranja.jpg",
    "categoria": "Frutas",
    "dataCriacao": "2024-05-15T11:45:00",
    "dataAtualizacao": "2024-05-15T11:45:00"
}
```

---

### 7. Atualizar Produto

```http
PUT /produtos/{id}
Content-Type: application/json

{
    "nome": "Maçã Vermelha Premium",
    "descricao": "Maçã gourmet importada",
    "preco": 8.50,
    "quantidade": 75,
    "categoria": "Frutas Premium"
}
```

**Resposta (200 OK):** Produto atualizado

---

### 8. Deletar Produto

```http
DELETE /produtos/{id}
```

**Resposta (204 No Content)**

---

### 9. Reduzir Estoque

```http
PATCH /produtos/{id}/estoque?quantidade=10
```

**Resposta (200 OK):**
```json
"Estoque reduzido com sucesso"
```

---

## 👥 USUÁRIOS

### 1. Listar Todos os Usuários

```http
GET /usuarios
```

**Resposta:**
```json
[
    {
        "id": 1,
        "nome": "João Silva",
        "email": "joao@example.com",
        "telefone": "11999999999",
        "endereco": "Rua A, 123",
        "tipo": "CLIENTE",
        "status": "ATIVO",
        "dataCriacao": "2024-05-15T10:00:00",
        "dataAtualizacao": "2024-05-15T10:00:00"
    }
]
```

---

### 2. Obter Usuário por ID

```http
GET /usuarios/{id}
```

---

### 3. Buscar Usuário por Email

```http
GET /usuarios/email/{email}
```

**Exemplo:**
```http
GET /usuarios/email/joao@example.com
```

---

### 4. Buscar Usuários por Nome

```http
GET /usuarios/buscar/nome?nome=João
```

---

### 5. Listar Clientes

```http
GET /usuarios/tipo/clientes
```

---

### 6. Listar Administradores

```http
GET /usuarios/tipo/admin
```

---

### 7. Criar Novo Usuário

```http
POST /usuarios
Content-Type: application/json

{
    "nome": "Maria Santos",
    "email": "maria@example.com",
    "senha": "senha123",
    "telefone": "11988888888",
    "endereco": "Rua B, 456",
    "tipo": "CLIENTE",
    "status": "ATIVO"
}
```

**Resposta (201 Created):**
```json
{
    "id": 2,
    "nome": "Maria Santos",
    "email": "maria@example.com",
    ...
}
```

---

### 8. Atualizar Usuário

```http
PUT /usuarios/{id}
Content-Type: application/json

{
    "nome": "Maria Santos Silva",
    "telefone": "11987654321",
    "endereco": "Rua C, 789"
}
```

---

### 9. Deletar Usuário

```http
DELETE /usuarios/{id}
```

---

### 10. Ativar Usuário

```http
PATCH /usuarios/{id}/ativar
```

---

### 11. Desativar Usuário

```http
PATCH /usuarios/{id}/desativar
```

---

## 📋 PEDIDOS

### 1. Listar Todos os Pedidos

```http
GET /pedidos
```

**Resposta:**
```json
[
    {
        "id": 1,
        "usuario": {
            "id": 1,
            "nome": "João Silva",
            "email": "joao@example.com"
        },
        "total": 150.75,
        "status": "CONFIRMADO",
        "dataPedido": "2024-05-15T14:30:00",
        "dataEntregaEstimada": "2024-05-20",
        "observacoes": "Entregar à noite",
        "enderecoEntrega": "Rua A, 123",
        "dataAtualizacao": "2024-05-15T14:30:00"
    }
]
```

---

### 2. Obter Pedido por ID

```http
GET /pedidos/{id}
```

---

### 3. Obter Pedidos de um Usuário

```http
GET /pedidos/usuario/{usuarioId}
```

---

### 4. Obter Pedidos por Status

```http
GET /pedidos/status/{status}
```

**Status válidos:** PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO

---

### 5. Criar Novo Pedido

```http
POST /pedidos
Content-Type: application/json

{
    "usuario": {
        "id": 1
    },
    "total": 150.75,
    "status": "PENDENTE",
    "enderecoEntrega": "Rua A, 123",
    "observacoes": "Entregar à noite"
}
```

---

### 6. Atualizar Pedido

```http
PUT /pedidos/{id}
Content-Type: application/json

{
    "status": "CONFIRMADO",
    "enderecoEntrega": "Rua A, 123 - Apto 42",
    "observacoes": "Entregar após 18h"
}
```

---

### 7. Deletar Pedido

```http
DELETE /pedidos/{id}
```

---

### 8. Alterar Status do Pedido

```http
PATCH /pedidos/{id}/status?status=ENVIADO
```

---

### 9. Adicionar Item ao Pedido

```http
POST /pedidos/{pedidoId}/itens
Content-Type: application/json

{
    "produto": {
        "id": 1
    },
    "quantidade": 5,
    "precoUnitario": 5.50
}
```

**Resposta:**
```json
{
    "id": 1,
    "pedido": {
        "id": 1
    },
    "produto": {
        "id": 1,
        "nome": "Maçã Vermelha"
    },
    "quantidade": 5,
    "precoUnitario": 5.50,
    "subtotal": 27.50
}
```

---

### 10. Remover Item do Pedido

```http
DELETE /pedidos/itens/{itemId}
```

---

### 11. Listar Itens de um Pedido

```http
GET /pedidos/{pedidoId}/itens
```

---

## 🔍 Códigos de Status HTTP

| Código | Significado |
|--------|------------|
| 200 | OK - Sucesso |
| 201 | Created - Recurso criado |
| 204 | No Content - Sucesso sem conteúdo |
| 400 | Bad Request - Dados inválidos |
| 404 | Not Found - Recurso não encontrado |
| 500 | Internal Server Error - Erro do servidor |

---

## 📝 Validações

### Produto
- Nome: obrigatório, máx 150 caracteres
- Preço: obrigatório, deve ser positivo
- Quantidade: obrigatório, não-negativo

### Usuário
- Nome: obrigatório, máx 150 caracteres
- Email: obrigatório, deve ser válido
- Senha: obrigatório, máx 255 caracteres

### Pedido
- Usuário: obrigatório
- Total: obrigatório, deve ser positivo
- Endereço de entrega: obrigatório

---

## 💡 Exemplos com cURL

### Criar Produto

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Tomate",
    "descricao": "Tomate fresco",
    "preco": 4.50,
    "quantidade": 150,
    "categoria": "Vegetais"
  }'
```

### Buscar Produtos

```bash
curl http://localhost:8080/api/produtos
```

### Listar Pedidos de um Usuário

```bash
curl http://localhost:8080/api/pedidos/usuario/1
```

---

## 🔐 Notas de Segurança

- Atual: Sem autenticação
- Futuro: Implementar JWT
- Senhas: Devem ser hashadas com BCrypt
- CORS: Configurado para localhost:3000

