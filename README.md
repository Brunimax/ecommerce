
# Sistema de E-commerce

Este projeto é uma plataforma de e-commerce completa, com backend em Spring Boot e frontend em Vue.js. Ele permite gerenciar produtos, pedidos, clientes e integra mensageria com RabbitMQ para notificações assíncronas.

## Estrutura do Projeto

```plaintext
ecommerce/
├── backend/               # Aplicação Spring Boot
│   ├── src/               # Código-fonte Java
│   ├── docker-compose.yml # Configuração Docker (PostgreSQL, RabbitMQ)
│   └── pom.xml            # Dependências Maven
│
└── frontend/              # Aplicação Vue.js
    ├── src/               # Código-fonte Vue
    ├── public/            # Assets estáticos
    └── package.json       # Dependências npm
```

## Tecnologias Utilizadas

### Backend (Spring Boot)
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (PostgreSQL)
- **Spring Security** (CORS)
- **Spring Web** (REST API)
- **Spring AMQP** (RabbitMQ)
- **Liquibase** (Migrações de banco de dados)
- **PostgreSQL 15** (Banco de dados)
- **RabbitMQ** (Mensageria)
- **JUnit 5 + Mockito** (Testes)

### Frontend (Vue.js)
- **Vue 2.6.14** (Composition API)
- **Vuetify 2.7** (UI Framework)
- **Vue Router** (Navegação)
- **Axios** (HTTP Client)
- **Vue The Mask** (Máscaras de formulário)

## Pré-requisitos

- **Java 11+**
- **Maven**
- **Node.js 18+**
- **Docker**
- **Postman** (Opcional)

## Configuração do Ambiente

### 1. Banco de Dados e Mensageria (Docker)

Suba os serviços em segundo plano:

```bash
cd backend
docker-compose up -d
```

Isso criará:

- **PostgreSQL** em `localhost:5432`
- **RabbitMQ** em `localhost:5672` (Interface de gerenciamento: [http://localhost:15672](http://localhost:15672))

### 2. Backend (Spring Boot)

Inicie o backend:

```bash
cd backend
mvn spring-boot:run
```

A API estará disponível em [http://localhost:8080/api](http://localhost:8080/api).

### 3. Frontend (Vue.js)

Inicie o frontend:

```bash
cd frontend
npm install       # Instala dependências
npm run dev       # Inicia servidor de desenvolvimento
```

O frontend estará disponível em [http://localhost:5173](http://localhost:5173).

## Funcionalidades Principais

### Backend
- CRUD de **produtos** e **pedidos**
- Gestão de **status de pedidos** (CRIADO, PROCESSANDO, ENTREGUE)
- Integração com **RabbitMQ** para notificações
- Validação de dados e tratamento de erros

### Frontend
- Catálogo de **produtos**
- Criação de **pedidos** com múltiplos produtos
- Visualização detalhada de **pedidos**
- Busca de endereço por **CEP** (ViaCEP)
- Atualização em tempo real via **WebSocket** (Opcional)

## Endpoints da API (Backend)

| Método | Endpoint                                    | Descrição                                           |
|--------|---------------------------------------------|-----------------------------------------------------|
| POST   | `/api/pedidos`                             | Cria um novo pedido com produtos                    |
| GET    | `/api/pedidos`                             | Lista todos os pedidos                              |
| GET    | `/api/pedidos/pedidosProdutos/{id}`        | Busca pedido com detalhes dos produtos              |
| PATCH  | `/api/pedidos/{id}/status`                 | Atualiza o status de um pedido                      |

## Testes

### Backend

Execute os testes:

```bash
cd backend
mvn test
```

Cobertura de testes:

- Serviços (PedidoService, ProdutoService)
- Controladores (PedidoController, ProdutoController)
- Integração com banco de dados

```

## Arquitetura do Sistema

```plaintext
graph TD
    A[Frontend Vue.js] -->|HTTP| B[Backend Spring Boot]
    B -->|JDBC| C[(PostgreSQL)]
    B -->|AMQP| D[RabbitMQ]
    D -->|Eventos| E[Microserviço de Notificações]
```

## Configurações Adicionais

### Variáveis de Ambiente (Frontend)

Crie um arquivo `.env` no diretório `frontend` com as seguintes variáveis:

```plaintext
VITE_API_URL=http://localhost:8080/api
```

### Banco de Dados (Liquibase)

As migrações de banco de dados estão localizadas em:

```plaintext
backend/src/main/resources/db/changelog/
```

## Deploy em Produção

### Backend

Compile e empacote o backend:

```bash
cd backend
mvn clean package
java -jar target/ecommerce-backend.jar
```

### Frontend

Compile o frontend:

```bash
cd frontend
npm run build
```

O build será gerado em `frontend/dist`.

## Contribuição

1. Faça um **fork** do repositório.
2. Crie uma **branch**: `git checkout -b minha-feature`.
3. Commit suas mudanças: `git commit -m 'Minha nova feature'`.
4. Envie para o repositório: `git push origin minha-feature`.
5. Abra um **Pull Request**.

## Licença

Distribuído sob a licença MIT. Veja [LICENSE](LICENSE) para mais detalhes.

---

Este README fornece todas as instruções necessárias para configurar, executar e contribuir com o projeto full-stack! 🚀
