# 🧑‍💻 Usuário Service - Memelândia

## 📄 Visão Geral

Este microserviço é responsável pelo cadastro e gerenciamento de usuários no ecossistema da **Memelândia**. Ele é parte de um sistema composto por três microsserviços: **Usuários, Categorias e Memes**, além de ferramentas de suporte como Eureka, RabbitMQ, Prometheus e Zipkin.

## 🚀 Tecnologias Utilizadas

* Java 22
* Spring Boot 3.4.5
* Spring Data JPA
* Spring Cloud Stream (RabbitMQ)
* Springdoc OpenAPI (Swagger)
* H2 Database (Desenvolvimento)
* Micrometer + Prometheus (Métricas)
* Zipkin (Tracing)
* Docker

## 🛠️ Como Rodar o Projeto

### ✔️ Rodando Localmente (Sem Docker)

1. Clone o repositório:

   ```bash
   git clone https://github.com/SdneyFernandes/memelandia.git
   cd usuario_service
   ```
2. Compile e execute:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Acesse:

   * API Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   * H2 Database Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

> 🔸 **Observação:** Para integração de eventos, é necessário que um servidor RabbitMQ esteja rodando na porta `5672`.

---

### ✔️ Rodando com Docker (Individual)

1. Gere a imagem Docker:

   ```bash
   docker build -t usuario-service .
   ```
2. Execute o container:

   ```bash
   docker run -p 8080:8080 usuario-service
   ```

> 🔸 **Importante:** Para funcionamento completo, serviços como RabbitMQ, Eureka, Prometheus e Zipkin devem estar rodando. Recomenda-se utilizar o `docker-compose.yml` na raiz do projeto.

---

### ✔️ Rodando com Docker Compose (Recomendado)

1. Na raiz do projeto (`memelandia`), execute:

   ```bash
   docker-compose up --build
   ```
2. O `docker-compose.yml` orquestra todos os microsserviços e dependências (RabbitMQ, Eureka, Prometheus, Zipkin, etc.).

---

## 📂 Endpoints da API

| Método | Endpoint               | Descrição               |
| ------ | ---------------------- | ----------------------- |
| GET    | /usuario\_service      | Lista todos os usuários |
| POST   | /usuario\_service      | Cria um novo usuário    |
| GET    | /usuario\_service/{id} | Busca usuário por ID    |
| DELETE | /usuario\_service/{id} | Deleta usuário por ID   |

---

## 🗂️ Estrutura de Pastas

```
src/main/java/
  br/com/memelandia/config/        # Configurações (Swagger, etc.)
  br/com/memelandia/controller/    # Controllers REST
  br/com/memelandia/entities/      # Entidades JPA
  br/com/memelandia/service/       # Regras de Negócio
  br/com/memelandia/repository/    # Repositórios (Spring Data JPA)
```

---

## 💪 Melhorias Futuras

* ✅ Implementação de camada DTOs para payloads
* ✅ Dockerfile implementado
* ⏳ Testes unitários e de integração (JUnit5 + Mockito)
* ⏳ Implementação de autenticação e segurança

---

## 👥 Contribuições

Sinta-se à vontade para enviar pull requests, reportar bugs ou sugerir melhorias. Toda contribuição é bem-vinda!

---

## 📜 Licença

Este projeto está licenciado sob a **Licença Apache 2.0**.

---

> 🌟 Parte do ecossistema **Memelândia** – o universo mais divertido de memes na internet!
