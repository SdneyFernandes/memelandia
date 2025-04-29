# Meme Service - Memelândia 🌟

## 📄 Visão Geral
Este microserviço é responsável pelo cadastro, gerenciamento e seleção de **memes** dentro do ecossistema da **Memelândia**.

O Meme Service depende dos microsserviços de **Usuários** e **Categorias** para referenciar corretamente o autor e a classificação do meme.

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
   cd meme_service
   ```
2. Compile e rode:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Acesse:
   - API Swagger UI: http://localhost:8082/swagger-ui.html
   - H2 Database Console: http://localhost:8082/h2-console


### Importante:
- O banco utilizado é um banco H2 em memória.
- Para integração de eventos, é esperado que um servidor RabbitMQ esteja rodando localmente na porta 5672.
- Este serviço escuta eventos de criação de **usuários** e **categorias**.


## 📂 Endpoints da API

| Método | Endpoint | Descrição |
|:--------|:---------|:-----------|
| GET     | /meme_service               | Lista todos os memes |
| POST    | /meme_service               | Cria um novo meme |
| GET     | /meme_service/{id}          | Busca meme por ID |
| DELETE  | /meme_service/{id}          | Deleta meme por ID |
| GET     | /meme_service/meme-do-dia   | Retorna um meme aleatório |


## 🛂 Estrutura de Pastas

```
src/main/java/
  br/com/memelandia/config/        # Configurações gerais (Swagger, etc)
  br/com/memelandia/controller/    # Controllers REST
  br/com/memelandia/entities/      # Entidades JPA
  br/com/memelandia/service/       # Regras de Negócio
  br/com/memelandia/repository/    # Repositórios (Spring Data JPA)
```


## 💪 Futuras Melhorias
- Implementação de testes unitários (JUnit5 + Mockito)
- Implementação de testes de integração
- Dockerização para ambiente de produção


## 👥 Contribuições
Sinta-se livre para enviar pull requests, reportar bugs ou sugerir melhorias!

## 📜 Licença
Este projeto está licenciado sob a licença Apache 2.0.

---

> Parte do ecossistema **Memelândia** 🌟 - o universo mais divertido de memes na internet!

