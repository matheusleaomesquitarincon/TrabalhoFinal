# 📦 Marketplace - Sistema de Vendas de Produtos

Sistema completo de vendas online de produtos de mercado, desenvolvido com React JS no front-end e Spring Boot no back-end.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Banco de Dados](#banco-de-dados)
- [API REST](#api-rest)
- [Funcionalidades](#funcionalidades)
- [Como Usar](#como-usar)
- [Tecnologias](#tecnologias)

## 🎯 Visão Geral

O Marketplace é uma plataforma de e-commerce que permite:

- **Clientes**: Navegar e comprar produtos
- **Administradores**: Gerenciar produtos, pedidos e usuários
- **Sistema**: Controlar estoque, processar pedidos e manter histórico

### Características Principais

✅ Catálogo de produtos com busca e filtro por categoria  
✅ Carrinho de compras funcional  
✅ Gerenciamento completo de pedidos  
✅ Controle de estoque integrado  
✅ Gestão de usuários (clientes e admins)  
✅ API REST bem documentada  
✅ Interface responsiva e moderna  
✅ Banco de dados relacional  

## 🔧 Requisitos

### Back-end (Java/Spring Boot)
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+ (opcional, usa H2 em desenvolvimento)

### Front-end (React)
- Node.js 16+
- npm ou yarn

## 💻 Instalação

### 1. Clonar o Repositório

```bash
cd trabalhofinal
```

### 2. Configurar e Executar o Back-end

```bash
cd marketplace-api

# Instalar dependências
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

Acesso ao banco H2: `http://localhost:8080/h2-console`

### 3. Configurar e Executar o Front-end

```bash
cd marketplace-frontend

# Instalar dependências
npm install

# Executar em desenvolvimento
npm run dev

# Build para produção
npm run build
```

O front-end estará disponível em: `http://localhost:3000` (ou porta indicada pelo Vite)

## 📁 Estrutura do Projeto

```
trabalhofinal/
├── marketplace-api/                    # Backend Spring Boot
│   ├── src/main/java/com/marketplace/
│   │   ├── api/
│   │   │   ├── controller/            # Controladores REST
│   │   │   ├── service/               # Serviços de negócio
│   │   │   ├── model/                 # Entidades JPA
│   │   │   ├── repository/            # Interfaces de dados
│   │   │   └── MarketplaceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── pom.xml                        # Dependências Maven
│
├── marketplace-frontend/               # Frontend React
│   ├── src/
│   │   ├── components/                # Componentes reutilizáveis
│   │   ├── pages/                     # Páginas da aplicação
│   │   ├── services/                  # Serviço de API
│   │   ├── styles/                    # Estilos globais
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── public/
│   │   └── index.html
│   └── package.json
│
└── docs/                              # Documentação
    ├── README.md
    ├── API.md
    ├── DATABASE.md
    └── DIAGRAMA_BD.txt
```

## 💾 Banco de Dados

### Diagrama Entidade-Relacionamento (ER)

```
┌─────────────────┐          ┌─────────────────┐
│    USUARIOS     │          │   PRODUTOS      │
├─────────────────┤          ├─────────────────┤
│ id (PK)         │          │ id (PK)         │
│ nome            │          │ nome            │
│ email (UNIQUE)  │          │ descricao       │
│ senha           │          │ preco           │
│ telefone        │          │ quantidade      │
│ endereco        │          │ imagem          │
│ tipo            │          │ categoria       │
│ status          │          │ dataCriacao     │
│ dataCriacao     │          │ dataAtualizacao │
│ dataAtualizacao │          └─────────────────┘
└─────────────────┘                   ▲
        ▲                              │
        │                              │
        │         ┌────────────────────┴──────┐
        │         │                           │
        │         │                      (FOREIGN KEY)
        │    (FOREIGN KEY)               PRODUTO
        │         │
   USUARIO    ┌───┴─────────────────┐
        └────┤    PEDIDOS          │
             ├─────────────────────┤
             │ id (PK)             │
             │ usuario_id (FK)     │
             │ total               │
             │ status              │
             │ dataPedido          │
             │ dataEntregaEstimada │
             │ observacoes         │
             │ enderecoEntrega     │
             │ dataAtualizacao     │
             └─────────────────────┘
                      ▲
                      │ (FOREIGN KEY)
                      │
                ┌─────┴──────────────┐
                │   ITENS_PEDIDO     │
                ├────────────────────┤
                │ id (PK)            │
                │ pedido_id (FK)     │
                │ produto_id (FK)    │
                │ quantidade         │
                │ precoUnitario      │
                │ subtotal           │
                └────────────────────┘
```

### Tabelas

#### USUARIOS
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único (auto-increment) |
| nome | VARCHAR(150) | Nome completo |
| email | VARCHAR(150) | Email único |
| senha | VARCHAR(255) | Senha hashada |
| telefone | VARCHAR(20) | Telefone de contato |
| endereco | VARCHAR(255) | Endereço |
| tipo | VARCHAR(20) | CLIENTE ou ADMIN |
| status | VARCHAR(20) | ATIVO ou INATIVO |
| dataCriacao | TIMESTAMP | Data de criação |
| dataAtualizacao | TIMESTAMP | Última atualização |

#### PRODUTOS
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único (auto-increment) |
| nome | VARCHAR(150) | Nome do produto |
| descricao | VARCHAR(500) | Descrição detalhada |
| preco | DECIMAL(10,2) | Preço unitário |
| quantidade | INT | Quantidade em estoque |
| imagem | VARCHAR(255) | URL da imagem |
| categoria | VARCHAR(100) | Categoria do produto |
| dataCriacao | TIMESTAMP | Data de criação |
| dataAtualizacao | TIMESTAMP | Última atualização |

#### PEDIDOS
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único (auto-increment) |
| usuario_id | INT (FK) | ID do usuário |
| total | DECIMAL(10,2) | Valor total do pedido |
| status | VARCHAR(20) | PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO |
| dataPedido | TIMESTAMP | Data do pedido |
| dataEntregaEstimada | VARCHAR(50) | Data estimada de entrega |
| observacoes | VARCHAR(500) | Observações do pedido |
| enderecoEntrega | VARCHAR(255) | Endereço de entrega |
| dataAtualizacao | TIMESTAMP | Última atualização |

#### ITENS_PEDIDO
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único (auto-increment) |
| pedido_id | INT (FK) | ID do pedido |
| produto_id | INT (FK) | ID do produto |
| quantidade | INT | Quantidade do item |
| precoUnitario | DECIMAL(10,2) | Preço no momento da compra |
| subtotal | DECIMAL(10,2) | quantidade × preçoUnitario |

## 🔌 API REST

### Base URL
```
http://localhost:8080/api
```

### Endpoints Principais

#### Produtos
- `GET /produtos` - Listar todos os produtos
- `GET /produtos/{id}` - Obter produto por ID
- `GET /produtos/buscar/nome?nome=termo` - Buscar por nome
- `GET /produtos/buscar/categoria?categoria=nome` - Buscar por categoria
- `GET /produtos/estoque/disponiveis` - Produtos em estoque
- `POST /produtos` - Criar novo produto
- `PUT /produtos/{id}` - Atualizar produto
- `DELETE /produtos/{id}` - Deletar produto
- `PATCH /produtos/{id}/estoque?quantidade=n` - Reduzir estoque

#### Usuários
- `GET /usuarios` - Listar todos os usuários
- `GET /usuarios/{id}` - Obter usuário por ID
- `GET /usuarios/email/{email}` - Buscar por email
- `GET /usuarios/buscar/nome?nome=termo` - Buscar por nome
- `GET /usuarios/tipo/clientes` - Listar clientes
- `GET /usuarios/tipo/admin` - Listar administradores
- `POST /usuarios` - Criar novo usuário
- `PUT /usuarios/{id}` - Atualizar usuário
- `DELETE /usuarios/{id}` - Deletar usuário
- `PATCH /usuarios/{id}/ativar` - Ativar usuário
- `PATCH /usuarios/{id}/desativar` - Desativar usuário

#### Pedidos
- `GET /pedidos` - Listar todos os pedidos
- `GET /pedidos/{id}` - Obter pedido por ID
- `GET /pedidos/usuario/{usuarioId}` - Pedidos de um usuário
- `GET /pedidos/status/{status}` - Pedidos por status
- `POST /pedidos` - Criar novo pedido
- `PUT /pedidos/{id}` - Atualizar pedido
- `DELETE /pedidos/{id}` - Deletar pedido
- `PATCH /pedidos/{id}/status?status=novo` - Alterar status
- `POST /pedidos/{pedidoId}/itens` - Adicionar item
- `DELETE /pedidos/itens/{itemId}` - Remover item
- `GET /pedidos/{pedidoId}/itens` - Listar itens do pedido

### Exemplo de Requisição

```bash
# Obter todos os produtos
curl -X GET http://localhost:8080/api/produtos

# Criar novo produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maçã",
    "descricao": "Maçã vermelha fresca",
    "preco": 5.50,
    "quantidade": 100,
    "categoria": "Frutas"
  }'
```

## ✨ Funcionalidades

### Cliente
- ✅ Navegar no catálogo de produtos
- ✅ Buscar produtos por nome
- ✅ Filtrar produtos por categoria
- ✅ Visualizar detalhes do produto
- ✅ Adicionar produtos ao carrinho
- ✅ Visualizar carrinho
- ✅ Fazer pedido
- ✅ Acompanhar pedidos
- ✅ Gerenciar perfil

### Administrador
- ✅ Gerenciar produtos (CRUD)
- ✅ Controlar estoque
- ✅ Visualizar todos os pedidos
- ✅ Alterar status de pedidos
- ✅ Gerenciar usuários
- ✅ Gerar relatórios

## 🚀 Como Usar

### 1. Iniciar o Sistema

```bash
# Terminal 1: Backend
cd marketplace-api
mvn spring-boot:run

# Terminal 2: Frontend
cd marketplace-frontend
npm run dev
```

### 2. Acessar a Aplicação

- Frontend: `http://localhost:3000` (ou porta indicada)
- Backend API: `http://localhost:8080/api`
- Console H2: `http://localhost:8080/h2-console`

### 3. Usar a Aplicação

1. **Página Inicial**: Visualize produtos em destaque
2. **Produtos**: Acesse o catálogo completo com busca e filtros
3. **Carrinho**: Adicione produtos (funcionalidade em desenvolvimento)
4. **Login**: Faça login (funcionalidade em desenvolvimento)

## 🛠️ Tecnologias

### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.1.5**: Framework web
- **Spring Data JPA**: ORM e persistência
- **H2 Database**: Banco em memória (desenvolvimento)
- **MySQL Driver**: Para produção
- **Lombok**: Redução de boilerplate
- **Maven**: Gerenciador de dependências

### Frontend
- **React 18**: Biblioteca UI
- **Vite**: Build tool
- **Axios**: Cliente HTTP
- **CSS3**: Estilização
- **HTML5**: Markup

## 📝 Documentação do Código

Todos os arquivos `.java` e `.jsx` contêm comentários detalhados:

- **Javadoc**: Para classes, métodos e parâmetros
- **Comentários de bloco**: Para lógica complexa
- **Comentários inline**: Para explicar decisões

### Exemplo de Documentação

```java
/**
 * Obtém todos os produtos cadastrados.
 * 
 * @return lista de todos os produtos
 */
public List<Product> obterTodos() {
    log.info("Obtendo todos os produtos");
    return productRepository.findAll();
}
```

## 📊 Fluxo da Aplicação

```
┌─────────────────┐
│ React Frontend  │
│   (SPA)         │
└────────┬────────┘
         │
    HTTP/REST
         │
         ▼
┌──────────────────┐
│  Spring Boot API │
│  (Controllers)   │
└────────┬─────────┘
         │
    Business Logic
         │
         ▼
┌──────────────────┐
│  Services        │
│  (Business)      │
└────────┬─────────┘
         │
    Data Access
         │
         ▼
┌──────────────────┐
│  Repositories    │
│  (JPA)           │
└────────┬─────────┘
         │
    SQL
         │
         ▼
┌──────────────────┐
│  H2/MySQL DB     │
│  (Banco Dados)   │
└──────────────────┘
```

## 🔐 Segurança (Futuro)

Melhorias de segurança a implementar:
- [ ] Autenticação JWT
- [ ] Hash de senhas (BCrypt)
- [ ] Validação de entrada
- [ ] HTTPS
- [ ] CORS configurado
- [ ] Rate limiting

## 📈 Performance

- Cache de produtos
- Paginação de resultados
- Índices no banco de dados
- Lazy loading de imagens

## 🐛 Tratamento de Erros

Todos os endpoints retornam:
- **200 OK**: Sucesso
- **201 CREATED**: Recurso criado
- **204 NO CONTENT**: Deleção bem-sucedida
- **400 BAD REQUEST**: Dados inválidos
- **404 NOT FOUND**: Recurso não encontrado
- **500 INTERNAL SERVER ERROR**: Erro do servidor

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique a documentação
2. Analise os logs da aplicação
3. Teste os endpoints com Postman/Insomnia

## 📄 Licença

Este projeto é para fins educacionais.

---

**Desenvolvido como trabalho final de Web Development**
**Ano: 2024**
