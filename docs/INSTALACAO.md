# 🚀 Guia de Instalação

## Pré-requisitos

### Sistema Operacional
- Windows 10+, macOS 10.14+, ou Linux (Ubuntu 20.04+)

### Software Necessário

#### Back-end (Java/Spring Boot)
```bash
# 1. Java 17 ou superior
java -version
# Esperado: openjdk version "17.x.x"

# 2. Maven 3.8+
mvn -version
# Esperado: Apache Maven 3.8.x

# 3. Git (opcional, mas recomendado)
git --version
```

#### Front-end (React)
```bash
# 1. Node.js 16+ e npm
node --version
# Esperado: v16.x.x ou v18.x.x

npm --version
# Esperado: 8.x.x ou 9.x.x
```

---

## Instalação Passo a Passo

### 1. Preparar o Ambiente

#### Windows

**Instalar Java 17:**
1. Baixar em: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. Executar o instalador
3. Adicionar ao PATH:
   - Abrir: `Variáveis de Ambiente` no Painel de Controle
   - Clique em `Variáveis de Ambiente`
   - Novo: `JAVA_HOME` = `C:\Program Files\Java\jdk-17.x.x`
   - Editar `Path`: adicionar `%JAVA_HOME%\bin`

**Instalar Maven:**
1. Baixar em: https://maven.apache.org/download.cgi
2. Extrair em: `C:\Program Files\apache-maven-3.8.x`
3. Adicionar ao PATH:
   - Novo: `MAVEN_HOME` = `C:\Program Files\apache-maven-3.8.x`
   - Editar `Path`: adicionar `%MAVEN_HOME%\bin`

**Instalar Node.js:**
1. Baixar em: https://nodejs.org (versão LTS)
2. Executar o instalador

**Verificar instalação:**
```powershell
java -version
mvn -version
node -version
npm -version
```

#### macOS

```bash
# Usando Homebrew
brew install java@17
brew install maven
brew install node

# Ou baixar manualmente dos sites oficiais
```

#### Linux (Ubuntu)

```bash
sudo apt-get update
sudo apt-get install openjdk-17-jdk maven nodejs npm
```

---

### 2. Clonar o Projeto

```bash
# Navegar para o diretório de trabalho
cd Documents/

# Clonar o repositório (ou copiar os arquivos)
# Se usando git:
git clone <URL do repositório> trabalhofinal

# Ou simplesmente ir para a pasta existente
cd trabalhofinal
```

---

### 3. Configurar o Back-end

```bash
cd marketplace-api

# Limpar e compilar o projeto
mvn clean install

# Isso fará:
# - Download de todas as dependências
# - Compilação do código Java
# - Execução de testes (se houver)
# - Criação do arquivo .jar

# Mensagem de sucesso esperada:
# [INFO] BUILD SUCCESS
```

**Se houver erros:**

```bash
# Limpar cache do Maven
mvn clean

# Atualizar dependências
mvn -U clean install

# Verificar versão do Java
javac -version
```

---

### 4. Configurar o Front-end

```bash
cd ../marketplace-frontend

# Instalar dependências
npm install

# Ou com yarn
yarn install

# Mensagem de sucesso esperada:
# added XXX packages

# Verificar instalação
npm list
```

**Se houver erros de versão:**

```bash
# Atualizar npm
npm install -g npm@latest

# Limpar cache
npm cache clean --force

# Reinstalar
rm -rf node_modules
npm install
```

---

## Executar a Aplicação

### Opção 1: Dois Terminais Separados (Recomendado)

**Terminal 1 - Back-end:**
```bash
cd marketplace-api
mvn spring-boot:run

# Aguarde até ver:
# Tomcat started on port(s): 8080
# Started MarketplaceApplication
```

**Terminal 2 - Front-end:**
```bash
cd marketplace-frontend
npm run dev

# Aguarde até ver algo como:
# ➜  Local:   http://localhost:5173/
```

### Opção 2: Usar IDE

#### IntelliJ IDEA / Eclipse

1. **Back-end:**
   - Abrir pasta `marketplace-api`
   - Clicar em `MarketplaceApplication.java`
   - Clicar no botão ▶ (Run)

2. **Front-end:**
   - Abrir Terminal em `marketplace-frontend`
   - Executar: `npm run dev`

#### VS Code

1. **Back-end:**
   - Extensão: Install "Extension Pack for Java"
   - Abrir `marketplace-api/pom.xml`
   - Clicar em ▶ do lado da classe `MarketplaceApplication`

2. **Front-end:**
   - Terminal: `npm run dev`

---

## Verificar Instalação

### Testes da API (com cURL ou Postman)

```bash
# Obter todos os produtos (deve retornar [])
curl http://localhost:8080/api/produtos

# Resposta esperada:
# []

# Acessar console H2
# http://localhost:8080/h2-console
# Driver: org.h2.Driver
# URL: jdbc:h2:mem:marketplacedb
# User: sa
# Password: (deixar em branco)
```

### Testes do Front-end

1. Abrir browser em: `http://localhost:5173` ou `http://localhost:3000`
2. Deve aparecer a página inicial do Marketplace
3. Clicar em "Produtos"
4. Deve aparecer a listagem (vazia no início)

---

## Populating com Dados de Teste

### Via SQL (H2 Console)

```sql
-- Criar usuário de teste
INSERT INTO usuarios (nome, email, senha, tipo, status) 
VALUES ('João Silva', 'joao@test.com', 'senha123', 'CLIENTE', 'ATIVO');

-- Criar produtos de teste
INSERT INTO produtos (nome, descricao, preco, quantidade, categoria) 
VALUES ('Maçã', 'Maçã vermelha fresca', 5.50, 100, 'Frutas');

INSERT INTO produtos (nome, descricao, preco, quantidade, categoria) 
VALUES ('Banana', 'Banana amarela madura', 3.20, 150, 'Frutas');

INSERT INTO produtos (nome, descricao, preco, quantidade, categoria) 
VALUES ('Cenoura', 'Cenoura fresca alaranjada', 2.80, 200, 'Vegetais');
```

### Via API (cURL)

```bash
# Criar produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Tomate",
    "descricao": "Tomate fresco",
    "preco": 4.50,
    "quantidade": 100,
    "categoria": "Vegetais"
  }'

# Criar usuário
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@test.com",
    "senha": "senha123",
    "tipo": "CLIENTE",
    "status": "ATIVO"
  }'
```

---

## Troubleshooting

### Erro: "Java not found"
```bash
# Solução: Instalar Java ou adicionar ao PATH
# Verificar instalação
java -version

# Se não encontrar, reinstalar Java
```

### Erro: "Maven not found"
```bash
# Solução: Instalar Maven ou adicionar ao PATH
mvn -version

# Se não encontrar:
# Windows: Adicionar C:\Program Files\apache-maven-x.x.x\bin ao PATH
# Mac/Linux: export PATH="/opt/maven/bin:$PATH"
```

### Erro: "npm ERR! code ERESOLVE"
```bash
# Solução: Use --legacy-peer-deps
npm install --legacy-peer-deps

# Ou atualize dependências
npm update
npm audit fix
```

### Porta 8080 já em uso
```bash
# Solução 1: Matar processo na porta
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8080
kill -9 <PID>

# Solução 2: Mudar porta em application.properties
# server.port=8081
```

### Porta 5173/3000 já em uso
```bash
# Matar processo
# Windows
netstat -ano | findstr :5173
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :5173
kill -9 <PID>
```

### Erro de conexão CORS
```javascript
// Verificar se o header CORS está configurado
// Em ProductController.java:
@CrossOrigin(origins = "http://localhost:3000")
```

---

## Configuração para Produção

### Back-end

1. **Mudar para MySQL:**

`application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marketplace
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=validate
```

2. **Build JAR:**
```bash
mvn clean package -DskipTests

# Gera: target/marketplace-api-1.0.0.jar
```

3. **Executar:**
```bash
java -jar target/marketplace-api-1.0.0.jar
```

### Front-end

1. **Build para produção:**
```bash
npm run build

# Gera: dist/ (pronto para deploy)
```

2. **Servir com servidor estático:**
```bash
npm install -g serve
serve -s dist
```

---

## Proximas Etapas

1. ✅ Instalação completa
2. 📝 Explorar a documentação em `/docs`
3. 🔍 Testar os endpoints com Postman
4. 🎨 Customizar o visual do front-end
5. 🔒 Implementar autenticação JWT
6. 📊 Adicionar mais funcionalidades

---

## Suporte

- Documentação: Ver `README.md`, `API.md`, `DATABASE.md`
- Logs: Verificar console do back-end e do front-end
- Issues: Procurar nos logs de erro

