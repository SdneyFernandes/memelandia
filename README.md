# 📚 Projeto Memelandia - Sistema de Microsserviços

Este projeto é uma arquitetura de microsserviços em Spring Boot para gestão de memes, usuários e categorias.

## 🏗 Estrutura dos Microsserviços

- **usuario_service** — Gerencia usuários (cadastro, busca, exclusão).
- **categoria_service** — Gerencia categorias (cadastro, busca, exclusão).
- **meme_service** — Gerencia memes (criação, consulta, remoção), integrando usuários e categorias via chamadas REST.

---

## 📦 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.x
- Spring Data JPA
- H2 Database (memória)
- Eureka Server (Service Discovery)
- OpenAPI/Swagger 3 (Documentação de APIs)
- Spring Cloud Stream (Comunicação de eventos)
- Micrometer + Prometheus (Métricas)
- Zipkin (Tracing distribuido )
- RabbitMQ (Mensageria + eventos)

---

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos
- Java 21+
- Maven 3.8+

### 2. Subir os Microsserviços

Execute os projetos na seguinte ordem:

1. **Eureka Server** (`localhost:8761`)
2. **usuario_service** (`localhost:8080`)
3. **categoria_service** (`localhost:8081`)
4. **meme_service** (`localhost:8082`)

> Cada serviço pode ser executado via `mvn spring-boot:run`.

---

## 📖 Endpoints Principais

### usuario_service (localhost:8080)
- `GET /usuario_service` → Listar todos usuários
- `POST /usuario_service` → Criar novo usuário
- `GET /usuario_service/{id}` → Buscar usuário por ID
- `GET /usuario_service/nome/{nome}` → Buscar usuário por Nome
- `DELETE /usuario_service/{id}` → Deletar usuário por ID
- `DELETE /usuario_service/nome/{nome}` → Deletar usuário por Nome

### categoria_service (localhost:8081)
- `GET /categoria_service` → Listar todas categorias
- `POST /categoria_service` → Criar nova categoria
- `GET /categoria_service/{id}` → Buscar categoria por ID
- `GET /categoria_service/nome/{nome}` → Buscar categoria por Nome
- `DELETE /categoria_service/{id}` → Deletar categoria por ID
- `DELETE /categoria_service/nome/{nome}` → Deletar categoria por Nome

### meme_service (localhost:8082)
- `GET /meme_service` → Listar todos memes
- `POST /meme_service` → Criar novo meme
- `GET /meme_service/{id}` → Buscar meme por ID
- `GET /meme_service/nome/{nome}` → Buscar meme por Nome
- `DELETE /meme_service/{id}` → Deletar meme
- `DELETE /meme_service/nome/{nome}` → Deletar meme por Nome
- `DELETE /meme_service/meme-d-dia` → Meme do dia de forma randomica

---

## 📋 Observações Importantes

- Ao criar um novo **Meme**, o serviço verifica se o **Usuário** e a **Categoria** existem através de chamadas REST para os respectivos microsserviços.
- Todos os dados são armazenados em bancos de dados H2 em memória.
- Métricas são expostas no `/actuator/metrics` para cada serviço.

---

## 📚 Documentação da API

Swagger UI disponível em cada microsserviço:
- `http://localhost:8080/swagger-ui.html` (Usuários)
- `http://localhost:8081/swagger-ui.html` (Categorias)
- `http://localhost:8082/swagger-ui.html` (Memes)

---

## 🤝 Contribuição

Sinta-se livre para sugerir melhorias, abrir issues ou enviar pull requests!

---

## 📃 Licença

Projeto sob licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
