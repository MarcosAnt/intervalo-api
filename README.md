# Intervalo API

Esta é uma API RESTful de demonstração construída com **Java**, **Spring Boot 3**, **Spring Data JPA** e **H2 Database**.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3+
* **Persistência:** Spring Data JPA
* **Banco de Dados:** H2 Database (em memória)
* **Testes:** JUnit 5, MockMvc
* **Swagger:** 2.3.0

## ⚙️ Pré-requisitos

* **JDK:** Versão 17 ou superior.
* **Maven:** versão 3.9.11
* **IDE:** IntelliJ IDEA, VS Code ou Eclipse.

## Como Rodar a Aplicação

### 1. Clonar o Repositório

```bash
git clone https://github.com/MarcosAnt/intervalo-api.git
cd intervalo-api
````

### 2. Rodar a aplicação

```bash
# Executa a compilação e sobe a aplicação na porta padrão (8080)
mvn clean install
mvn spring-boot:run
```

### 3. Consultar a documentação Swagger

```bash
http://[host]/swagger-ui.html
```
Padrão no ambiente local:
```bash
http://localhost:8080/swagger-ui.html
```

### 4. Rodar os testes

```bash
mvn test
```
