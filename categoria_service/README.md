# Categoria Service - Memelândia 🌟

## 📄 Visão Geral
Este microserviço é responsável pelo cadastro e gerenciamento de **categorias** no ecossistema da **Memelândia**. Ele é utilizado para classificar memes dentro do sistema.

## 🚀 Tecnologias Utilizadas
- Java 22
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Cloud Stream (RabbitMQ)
- Springdoc OpenAPI (Swagger)
- H2 Database (Desenvolvimento)
- Micrometer + Prometheus (Métricas)
- Zipkin (Tracing)

## 🛠️ Como Rodar o Projeto

### Ambiente de Desenvolvimento:
1. Clone o repositório:
   ```bash
   git clone https://github.com/SdneyFernandes/memelandia.git
   cd categoria_service
   ```
2. Compile e rode:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Acesse:
   - API Swagger UI: http://localhost:8081/swagger-ui.html
   - H2 Database Console: http://localhost:8081/h2-console


### Importante:
- O banco utilizado é um banco H2 em memória.
- Para integração de eventos, é esperado que um servidor RabbitMQ esteja rodando localmente na porta 5672.

## 📂 Endpoints da API

| Método | Endpoint | Descrição |
|:--------|:---------|:-----------|
| GET     | /categoria_service        | Lista todas as categorias |
| POST    | /categoria_service        | Cria uma nova categoria |
| GET     | /categoria_service/{id}   | Busca categoria por ID |
| DELETE  | /categoria_service/{id}   | Deleta categoria por ID |


## 🛂 Estrutura de Pastas

```
src/main/java/
  br/com/memelandia/config/        # Configurações (Swagger, etc)
  br/com/memelandia/controller/    # Controllers REST
  br/com/memelandia/entities/      # Entidades JPA
  br/com/memelandia/service/       # Regras de Negócio
  br/com/memelandia/repository/    # Repositórios (Spring Data JPA)
```


## 💪 Futuras Melhorias
-Testes unitários e integração (JUnit5 + Mockito)
-Dockerfile + docker-compose

## 👥 Contribuições
Sinta-se livre para enviar pull requests, reportar bugs ou sugerir melhorias!


## 📜 Licença
Este projeto está licenciado sob a licença Apache 2.0.

---

> Parte do ecossistema Memelândia 🌟 - o universo mais divertido de memes na internet!

