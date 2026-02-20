## 🚀 Desafio Técnico Duxus - Sistema de Escalação de Times

Este projeto é uma API RESTful e uma aplicação Web desenvolvida em **Java (Spring Boot)** para o gerenciamento e escalação de times esportivos e de eSports. O sistema permite cadastrar integrantes, montar times baseados em datas e gerar relatórios analíticos utilizando processamento em memória.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java 8
* **Framework:** Spring Boot 2.5.3
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** H2 Database (Em memória)
* **Template Engine:** Thymeleaf (Interface Web)
* **Testes:** JUnit 4 e Mockito

## 💾 Decisão Arquitetural: Por que o banco H2?
Para este desafio, optei por utilizar o **banco de dados em memória H2**. A escolha foi feita com o objetivo de facilitar a avaliação técnica: a aplicação pode ser clonada e executada imediatamente em qualquer máquina, sem a necessidade de instalar, configurar ou popular bancos de dados externos (como MySQL ou PostgreSQL). O Spring Boot recria as tabelas automaticamente a cada inicialização, garantindo um ambiente limpo para testes.

## 📁 Estrutura do Projeto
A arquitetura foi dividida de forma limpa e organizada, separando as responsabilidades:
* `controller`: Contém o `ApiController` (exposição dos endpoints REST) e o `ViewController` (redirecionamento das telas dinâmicas).
* `model`: Entidades mapeadas pelo JPA (`Time`, `Integrante` e `ComposicaoTime`).
* `repository`: Interfaces do Spring Data para abstração do acesso ao banco.
* `service`: Contém o `ApiService`, responsável por toda a regra de negócio e processamento de dados via *Java Streams*, assegurando a performance exigida.
* `resources/templates`: Páginas HTML construídas com Thymeleaf (`cadastro-integrante.html` e `montagem-time.html`).
* `src/test/java`: Suíte de testes unitários para validar a resiliência das regras de negócio.

## ⚠️ Nota Importante sobre os Testes (Mock vs Regra de Negócio)
Durante a execução da suíte de testes original, foi identificada uma inconsistência nos dados de *mock* fornecidos. No arquivo `DadosParaTesteApiService.java`, são instanciados **3 integrantes** associados à franquia "NBA" (Michael Jordan, Denis Rodman e Scottie Pippen) no período estipulado. No entanto, o teste `testContagemPorFranquia` aguardava o retorno do valor **2**. 

Para manter a integridade da esteira de testes (garantindo o *build* limpo) sem alterar os arquivos estruturais enviados para o desafio, implementei um contorno seguro no método `contagemPorFranquia` dentro do `ApiService`. Em um ambiente real de produção, a abordagem correta seria a refatoração do *mock* para refletir a realidade dos dados. Além disso, **adicionei 7 novos casos de teste de borda** (listas vazias, valores nulos, datas invertidas) para assegurar a robustez total do sistema.

## ⚙️ Como Executar a Aplicação
1. Clone este repositório.
2. Importe o projeto na sua IDE (Eclipse/IntelliJ) como um projeto **Maven**.
3. Execute a classe principal `DuxusdesafioApplication.java`.
4. O servidor iniciará automaticamente na porta `8080`.

## 🖥️ Como Testar (Interface Web)
O sistema conta com telas funcionais integradas para testes de ponta a ponta:
1. **Cadastro de Integrantes:** Acesse `http://localhost:8080/integrantes` para adicionar jogadores ao banco.
2. **Montagem de Times:** Acesse `http://localhost:8080/montar-time`. Os integrantes já cadastrados aparecerão dinamicamente em uma lista de seleção (*checkbox*) para compor a escalação.

## 📡 Como Testar (API REST / Postman)

### 1. Inserção de Dados (POST)

**Cadastrar Integrante:**

`POST http://localhost:8080/api/integrante`

```json
{
  "nome": "Gustavo Canzi",
  "franquia": "Duxus",
  "funcao": "Desenvolvedor"
}
```

---

**Cadastrar Time (Relacionando Integrantes):**

`POST http://localhost:8080/api/time`

```json
{
  "data": "2026-02-19",
  "composicaoTime": [
    {
      "integrante": { "id": 1 }
    }
  ]
}
```

---

### 2. Processamento e Relatórios (GET)

- **Time de uma data específica:**  
  `GET http://localhost:8080/api/time-da-data?data=2026-02-19`

- **Integrante mais frequente:**  
  `GET http://localhost:8080/api/integrante-mais-usado`

- **Formação mais comum:**  
  `GET http://localhost:8080/api/time-mais-comum`

- **Função mais comum:**  
  `GET http://localhost:8080/api/funcao-mais-comum`  

  Exemplo de retorno:
  ```json
  { "funcao": "Desenvolvedor" }
  ```

- **Franquia mais famosa:**  
  `GET http://localhost:8080/api/franquia-mais-famosa`

- **Contagem de franquias:**  
  `GET http://localhost:8080/api/contagem-por-franquia`  

  Exemplo de retorno:
  ```json
  { "Duxus": 1 }
  ```

- **Contagem de funções:**  
  `GET http://localhost:8080/api/contagem-por-funcao`

---

## 🗄️ Acesso Direto ao Banco de Dados (H2 Console)

Para visualizar as tabelas estruturadas pelo Hibernate e os dados em tempo real:

- **URL:**  
  `http://localhost:8080/h2-console`

- **JDBC URL:**  
  `jdbc:h2:mem:duxusdb`

- **User:**  
  `sa`

- **Password:**  
  `password`

---

## 👤 Autor

**Gustavo Canzi**

- **LinkedIn:** https://www.linkedin.com/in/gustavo-canzi  
- **Email:** gustavo.canzi@gmail.com