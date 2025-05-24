# 🖼️ Meme Service - Memelândia 🌟

## 📄 Visão Geral

Este microserviço é responsável pelo cadastro, gerenciamento e seleção de **memes** dentro do ecossistema da **Memelândia**.

O **Meme Service** depende dos microsserviços de **Usuários** e **Categorias** para referenciar corretamente o **autor** e a **classificação** de cada meme.

---

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

---

## 🛠️ Como Rodar o Projeto

### ✔️ Rodando Localmente (Sem Docker)

1. Clone o repositório:

   ```bash
   git clone https://github.com/SdneyFernandes/memelandia.git
   cd meme_service
   ```
2. Compile e execute:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Acesse:

   * API Swagger UI: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
   * H2 Database Console: [http://localhost:8082/h2-console](http://localhost:8082/h2-console)

> 🔸 **Observação:** Para funcionamento completo, é necessário que os microsserviços de **Usuários** e **Categorias**, além de um servidor **RabbitMQ**, estejam rodando.

---

### ✔️ Rodando com Docker (Individual)

1. Gere a imagem Docker:

   ```bash
   docker build -t meme-service .
   ```
2. Execute o container:

   ```bash
   docker run -p 8082:8082 meme-service
   ```

> 🔸 **Importante:** O serviço espera que RabbitMQ e os outros microsserviços estejam rodando. Recomenda-se usar `docker-compose`.

---

### ✔️ Rodando com Docker Compose (Recomendado)

1. Na raiz do projeto `memelandia`, execute:

   ```bash
   docker-compose up --build
   ```
2. Todos os microsserviços, juntamente com RabbitMQ, Eureka, Prometheus, Zipkin e outros, serão executados automaticamente.

---

## 📂 Endpoints da API

| Método | Endpoint                   | Descrição                 |
| ------ | -------------------------- | ------------------------- |
| GET    | /meme\_service             | Lista todos os memes      |
| POST   | /meme\_service             | Cria um novo meme         |
| GET    | /meme\_service/{id}        | Busca meme por **ID**     |
| DELETE | /meme\_service/{id}        | Deleta meme por **ID**    |
| GET    | /meme\_service/nome/{nome} | Busca meme por **nome**   |
| DELETE | /meme\_service/nome/{nome} | Deleta meme por **nome**  |
| GET    | /meme\_service/meme-do-dia | Retorna um meme aleatório |

---

## 🗂️ Estrutura de Pastas

```
src/main/java/
  br/com/memelandia/config/        # Configurações gerais (Swagger, etc)
  br/com/memelandia/controller/    # Controllers REST
  br/com/memelandia/entities/      # Entidades JPA
  br/com/memelandia/service/       # Regras de Negócio
  br/com/memelandia/repository/    # Repositórios (Spring Data JPA)
```

---

## 💪 Melhorias Futuras

* ✅ Dockerfile implementado
* ✅ DTO implementado
* ⏳ Implementação de testes unitários (JUnit5 + Mockito)
* ⏳ Implementação de testes de integração
* ⏳ Implementação de autenticação e segurança

---

## 👥 Contribuições

Sinta-se livre para enviar **pull requests**, reportar **bugs** ou sugerir **melhorias**! Sua colaboração é muito bem-vinda para deixar a **Memelândia** ainda mais divertida. 😄

---

## 📜 Licença

Este projeto está licenciado sob a **Licença Apache 2.0**.

---

> 🌟 Parte do ecossistema **Memelândia** – o universo mais divertido de memes na internet!
