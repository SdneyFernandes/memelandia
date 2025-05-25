# 📦 Categoria Service - Memelândia 🌟

## 📄 Visão Geral

Este microserviço é responsável pelo cadastro e gerenciamento de **categorias** no ecossistema da **Memelândia**. Ele permite classificar memes dentro do sistema, facilitando sua organização e busca.

## 🚀 Tecnologias Utilizadas

* Java 22
* Spring Boot 3.4.5
* Spring Data JPA
* Spring Cloud Stream (RabbitMQ)
* Springdoc OpenAPI (Swagger)
* H2 Database (Desenvolvimento)
* Micrometer + Prometheus (Métricas)
* Zipkin (Tracing)

---

## 🛠️ Como Rodar o Projeto

### ✔️ Rodando Localmente (Sem Docker)

1. Clone o repositório:

   ```bash
   git clone https://github.com/SdneyFernandes/memelandia.git
   cd categoria_service
   ```
2. Compile e execute:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Acesse:

   * API Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
   * H2 Database Console: [http://localhost:8081/h2-console](http://localhost:8081/h2-console)


> 🔸 **Importante:** Este serviço espera que serviços como RabbitMQ e Eureka estejam ativos. 

---


## 📂 Endpoints da API

| Método | Endpoint                        | Descrição                     |
| ------ | ------------------------------- | ----------------------------- |
| GET    | /categoria\_service             | Lista todas as categorias     |
| POST   | /categoria\_service             | Cria uma nova categoria       |
| GET    | /categoria\_service/{id}        | Busca categoria por **ID**    |
| DELETE | /categoria\_service/{id}        | Deleta categoria por **ID**   |
| GET    | /categoria\_service/nome/{nome} | Busca categoria por **nome**  |
| DELETE | /categoria\_service/nome/{nome} | Deleta categoria por **nome** |

---

## 🗂️ Estrutura de Pastas

```
src/main/java/
  br/com/memelandia/config/        # Configurações (Swagger, etc.)
  br/com/memelandia/controller/    # Controllers REST
  br/com/memelandia/entities/      # Entidades JPA
   br/com/memelandia/dto/          # DTO
  br/com/memelandia/service/       # Regras de Negócio
  br/com/memelandia/repository/    # Repositórios (Spring Data JPA)
```

---

## 💪 Melhorias Futuras

* ⏳ Dockerfile 
* ✅ DTO implementado
* ⏳ Testes unitários e de integração (JUnit5 + Mockito)
* ⏳ Implementação de autenticação e segurança

---

## 👥 Contribuições

Sinta-se livre para enviar pull requests, reportar bugs ou sugerir melhorias! Toda contribuição é bem-vinda para tornar a **Memelândia** ainda mais incrível.

---

## 📜 Licença

Este projeto está licenciado sob a **Licença Apache 2.0**.

---

> 🌟 Parte do ecossistema **Memelândia** – o universo mais divertido de memes na internet!
