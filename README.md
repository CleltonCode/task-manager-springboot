# Projeto: Task Manager API

## Visão Geral
Bem-vindo ao repositório da Task Manager API, uma aplicação desenvolvida em Spring Boot para gerenciamento eficiente de tarefas.

## Features
- Adição de novas tarefas
- Recuperação de todas as tarefas paginadas
- Recuperação de uma tarefa específica por ID
- Exclusão de uma tarefa por ID
- Edição de uma tarefa, marcando-a como concluída

## Como Utilizar
Siga as instruções abaixo para integrar a API ao seu projeto:

1. Clone o repositório: `git clone https://github.com/seuusuario/task-manager-api.git`
2. Abra o projeto em sua IDE favorita.
3. Execute a aplicação Spring Boot.

### Alterando Configurações CORS
Se necessário, você pode ajustar as configurações CORS no projeto. As configurações estão localizadas na classe `CorsConfig` no pacote de configuração(config/CorsConfig.java). Certifique-se de revisar e ajustar as configurações de acordo com as necessidades do seu ambiente.  

## Endpoints
### 1. Adicionar Tarefa
- **Endpoint:** `POST /api/tasks`
- **Descrição:** Adiciona uma nova tarefa.

### 2. Recuperar Todas as Tarefas
- **Endpoint:** `GET /api/tasks`
- **Descrição:** Recupera uma lista paginada de todas as tarefas.

### 3. Recuperar Tarefa por ID
- **Endpoint:** `GET /api/tasks/{id}`
- **Descrição:** Recupera uma tarefa específica pelo seu ID.

### 4. Excluir Tarefa
- **Endpoint:** `DELETE /api/tasks/{id}`
- **Descrição:** Exclui uma tarefa pelo seu ID.

### 5. Editar Tarefa
- **Endpoint:** `PUT /api/tasks/{id}`
- **Descrição:** Edita uma tarefa, marcando-a como concluída.

## Registro e Colaboração
- Registre logs usando o framework Log4j.
- Contribua para o desenvolvimento e melhoria contínua do projeto.

## Autor
[Clelton Henrique]
[cleltonh@gmail.com]
[[Link para seu Perfil](https://www.linkedin.com/in/clelton-henrique)]

Sinta-se à vontade para explorar, contribuir e utilizar a Task Manager API! 😊
