# 🎨 Frontend - Marketplace React

Interface React moderna para o sistema de vendas de produtos de mercado.

## 🎯 Funcionalidades

- ✅ Página inicial com produtos em destaque
- ✅ Catálogo de produtos com busca e filtros
- ✅ Filtro por categoria
- ✅ Carrinho de compras (em desenvolvimento)
- ✅ Interface responsiva (mobile, tablet, desktop)
- ✅ Design moderno com CSS3
- ✅ Integração com API REST

## 🔧 Requisitos

- **Node.js 16+**
- **npm 8+** (ou yarn)
- **Navegador moderno** (Chrome, Firefox, Safari, Edge)

## 🚀 Como Iniciar

### 1. Instalar Dependências

```bash
cd marketplace-frontend
npm install
```

### 2. Executar em Desenvolvimento

```bash
npm run dev
```

A aplicação abrirá automaticamente em `http://localhost:5173`

### 3. Build para Produção

```bash
npm run build
```

Gera a pasta `dist/` pronta para deploy.

## 📁 Estrutura do Projeto

```
marketplace-frontend/
├── src/
│   ├── components/               # Componentes reutilizáveis
│   │   ├── Header.jsx           # Cabeçalho com navegação
│   │   ├── Header.css
│   │   ├── ProductCard.jsx      # Card de produto
│   │   └── ProductCard.css
│   │
│   ├── pages/                   # Páginas da aplicação
│   │   ├── Home.jsx             # Página inicial
│   │   ├── Home.css
│   │   ├── Produtos.jsx         # Catálogo de produtos
│   │   └── Produtos.css
│   │
│   ├── services/                # Serviços e APIs
│   │   └── api.js               # Cliente HTTP (Axios)
│   │
│   ├── styles/                  # Estilos globais
│   │   └── global.css           # CSS global da app
│   │
│   ├── App.jsx                  # Componente raiz
│   ├── App.css
│   ├── main.jsx                 # Ponto de entrada
│   └── index.html               # HTML base (em public/)
│
├── public/
│   └── index.html               # HTML principal
│
├── package.json                 # Dependências
├── vite.config.js              # Config do Vite
└── README.md                    # Este arquivo
```

## 🎨 Componentes

### Header
Componente de cabeçalho com navegação principal.

```jsx
<Header 
  usuarioLogado={false}
  onLogout={() => {}}
/>
```

**Props:**
- `usuarioLogado`: boolean - Se usuário está logado
- `onLogout`: function - Callback para logout

### ProductCard
Cartão exibindo informações de um produto.

```jsx
<ProductCard 
  product={product}
  onAddToCart={(product) => {}}
/>
```

**Props:**
- `product`: object - Dados do produto
- `onAddToCart`: function - Callback ao adicionar ao carrinho

### Pages

#### Home
Página inicial com destaques e informações.
- Produtos em destaque
- Benefícios do marketplace
- Call-to-action

#### Produtos
Catálogo completo de produtos.
- Lista de todos os produtos
- Barra de busca por nome
- Filtros por categoria
- Contador de resultados

## 🔌 Integração com API

### Arquivo: `src/services/api.js`

Centraliza todas as requisições HTTP:

```javascript
// Produtos
obterProdutos()                    // GET /produtos
obterProduto(id)                   // GET /produtos/{id}
buscarProdutosPorNome(nome)       // GET /produtos/buscar/nome
buscarProdutosPorCategoria(cat)   // GET /produtos/buscar/categoria
obterProdutosEmEstoque()           // GET /produtos/estoque/disponiveis
criarProduto(data)                 // POST /produtos
atualizarProduto(id, data)        // PUT /produtos/{id}
deletarProduto(id)                 // DELETE /produtos/{id}

// Usuários
obterUsuarios()                    // GET /usuarios
criarUsuario(data)                 // POST /usuarios
// ... e mais

// Pedidos
obterPedidos()                     // GET /pedidos
criarPedido(data)                  // POST /pedidos
adicionarItemAoPedido(pedidoId, item)  // POST /pedidos/{id}/itens
// ... e mais
```

## 🎨 Estilização

### Sistema de Cores

Definido em `src/styles/global.css`:

```css
--primary: #2563eb          /* Azul principal */
--secondary: #10b981        /* Verde secundário */
--danger: #ef4444          /* Vermelho */
--gray-X00: #...           /* Escalas de cinza */
```

### Componentes CSS Utilitários

```html
<!-- Botões -->
<button class="btn btn-primary">Primário</button>
<button class="btn btn-secondary">Secundário</button>
<button class="btn btn-danger">Perigo</button>

<!-- Espaçamento -->
<div class="mt-2 mb-3 p-1">Conteúdo</div>

<!-- Alerta -->
<div class="alert alert-success">Sucesso!</div>
<div class="alert alert-danger">Erro!</div>

<!-- Badge -->
<span class="badge badge-primary">Novo</span>
```

## 📱 Responsividade

Layout responsivo com breakpoints:

```css
/* Desktop: 1200px+ */
.container { max-width: 1200px; }

/* Tablet: 768px - 1199px */
@media (max-width: 768px) { ... }

/* Mobile: < 768px */
@media (max-width: 768px) { ... }
```

## 🚦 Fluxo de Dados

```
Componente
    ↓
useState (estado local)
    ↓
useEffect (chamadas de API)
    ↓
api.js (requisição HTTP)
    ↓
Backend (http://localhost:8080)
    ↓
Resposta JSON
    ↓
Atualizar estado
    ↓
Re-render
```

## 📦 Dependências Principais

```json
{
  "react": "^18.2.0",         // Biblioteca UI
  "react-dom": "^18.2.0",     // Renderização em DOM
  "axios": "^1.6.0"           // Cliente HTTP
}
```

### Dev Dependencies

```json
{
  "vite": "^5.0.0",                      // Build tool
  "@vitejs/plugin-react": "^4.0.0",     // Plugin React
  "@types/react": "^18.2.0"             // TypeScript types
}
```

## 🏗️ Fluxo de Componentes

```
App.jsx (raiz)
├── Header
│   ├── Logo
│   └── Navegação
├── main-content
│   ├── Home (página)
│   │   ├── Hero banner
│   │   ├── ProductCard × N
│   │   └── Benefits
│   └── Produtos (página)
│       ├── Barra de busca
│       ├── Filtros
│       └── ProductCard × N
└── Footer
```

## 🔄 Gerenciamento de Estado

Atualmente usando `useState` do React:

```javascript
// Estado local de um componente
const [produtos, setProdutos] = useState([]);
const [carregando, setCarregando] = useState(true);
const [erro, setErro] = useState(null);
```

**Para produção, considerar:**
- Context API
- Redux
- Zustand

## 🧪 Testar a Aplicação

### Verificar Conexão com API

```javascript
// Abrir console do navegador (F12)
// Testar API
fetch('http://localhost:8080/api/produtos')
  .then(r => r.json())
  .then(d => console.log(d))
```

### Verificar Console

```
F12 → Console → Verificar logs e erros
```

## 🚀 Deploy

### GitHub Pages

```bash
# Adicionar a vite.config.js
base: '/marketplace-frontend/'

# Build
npm run build

# Fazer push da pasta dist/
```

### Netlify

```bash
# Conectar repositório ao Netlify
# Build command: npm run build
# Publish directory: dist
```

### Vercel

```bash
# Instalar Vercel CLI
npm i -g vercel

# Deploy
vercel
```

## 🔐 Variáveis de Ambiente

Criar arquivo `.env`:

```
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=Marketplace
```

Usar:

```javascript
import.meta.env.VITE_API_URL
```

## 🐛 Troubleshooting

### "Module not found"
```bash
npm install
```

### "CORS error"
```javascript
// Verificar se API está com CORS habilitado
@CrossOrigin(origins = "http://localhost:3000")
```

### "Vite port already in use"
```bash
# Usar porta diferente
npm run dev -- --port 5174
```

### "API não responde"
```javascript
// Verificar:
// 1. API está rodando? http://localhost:8080/api/produtos
// 2. URL correta em api.js? const API_BASE_URL = ...
// 3. CORS habilitado?
```

## 🌟 Melhorias Futuras

- [ ] Sistema de autenticação/login
- [ ] Carrinho de compras funcional
- [ ] Checkout e pagamento
- [ ] Página de perfil do usuário
- [ ] Historico de pedidos
- [ ] Avaliações e comentários
- [ ] Dark mode
- [ ] PWA (Progressive Web App)
- [ ] Testes unitários (Jest)
- [ ] TypeScript

## 📚 Recursos

- [React Documentation](https://react.dev)
- [Vite Documentation](https://vitejs.dev)
- [Axios Documentation](https://axios-http.com)
- [CSS Reference](https://developer.mozilla.org/en-US/docs/Web/CSS)

## 🎯 Guia de Estilo

### Nomes de Componentes
- PascalCase: `Header`, `ProductCard`, `Home`

### Nomes de Funções
- camelCase: `handleAddToCart`, `carregarProdutos`

### Nomes de Arquivos
- kebab-case para estilos: `header.css`
- PascalCase para componentes: `Header.jsx`

## 📝 Documentação de Código

Cada função e componente deve ter comentário JSDoc:

```jsx
/**
 * Descrição breve do componente
 * 
 * @param {type} propName - Descrição
 * @returns {JSX.Element} O que retorna
 */
export const MeuComponente = ({ prop }) => {
  return <div>{prop}</div>;
};
```

## 📞 Suporte

Para dúvidas ou problemas:
1. Verificar console do navegador (F12)
2. Revisar logs do back-end
3. Testar API com Postman
4. Consultar documentação em `/docs`

---

**Desenvolvido como trabalho final - 2024**
