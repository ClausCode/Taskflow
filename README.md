# Taskflow

## Задача

```text
Тестовое задание для кандидата:

реализовать 1 сервис(java 21/kotlin, postgresql, kafka, spring boot, spring data, spring kafka)
Рест апи:
- получение задач с пагинацией
- получение задачи по id
- добавление задачи
- назначение исполнителя задаче
- смена статуса задаче


модель данных:
задача(id,  наименование, исполнитель, описание, статус)
пользователь(id, имя, почта)

авторизацию и онбординг пользователей оставить за рамками тестового задания

при назначении исполнителя и создании задачи отправлять событие в кафку

кафку, базу поднять локально через docker

Собрать в docker container



NFT:
до 10к пользователей
до 100к задач


Результат – открытый репозиторий в gitlab/github

```

## Запуск

```bash
docker compose up --build
```

### Сервисы

- App: http://localhost:8080
- Kafka: kafka:9092 (внутри сети Docker)
- Postgres: postgres:5432
- AKHQ: http://localhost:8081

## Остановка

```bash
docker compose down
```

# Taskflow API

Base URL:

```http
http://localhost:8080
```

---

# Tasks

## Get tasks page

```http
GET /tasks
```

### Query params

| Param | Type | Description |
|---|---|---|
| page | Integer | Page number |
| size | Integer | Items per page |
| sort | String | Sort field |

### Response

```java
Page<TaskDto>

TaskDto {
    String id;
    String name;
    String description;
    String executor;
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

---

## Get task by id

```http
GET /tasks/{id}
```

### Path params

| Param | Type |
|---|---|
| id | UUID |

### Response

```java
TaskDto {
    String id;
    String name;
    String description;
    String executor;
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

---

## Create task

```http
POST /tasks
```

### Body

```java
TaskCreateDto {
    String name;
    String description;
}
```

### Response

```java
TaskDto {
    String id;
    String name;
    String description;
    String executor;
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

---

## Change task status

```http
PATCH /tasks/{id}/status
```

### Path params

| Param | Type |
|---|---|
| id | UUID |

### Body

```java
TaskChangeStatusDto {
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

### Response

```java
TaskDto {
    String id;
    String name;
    String description;
    String executor;
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

---

## Set task executor

```http
PATCH /tasks/{id}/executor
```

### Path params

| Param | Type |
|---|---|
| id | UUID |

### Body

```java
TaskSetExecutorDto {
    UUID executorId;
}
```

### Response

```java
TaskDto {
    String id;
    String name;
    String description;
    String executor;
    TaskStatus status;
}

enum TaskStatus {
    PENDING,
    AT_WORK,
    COMPLETED
}
```

---

# Users

## Get users page

```http
GET /users
```

### Query params

| Param | Type | Description |
|---|---|---|
| page | Integer | Page number |
| size | Integer | Items per page |
| sort | String | Sort field |

### Response

```java
Page<UserDto>

UserDto {
    String id;
    String fullName;
    String email;
}
```

---

## Get user by id

```http
GET /users/{id}
```

### Path params

| Param | Type |
|---|---|
| id | UUID |

### Response

```java
UserDto {
    String id;
    String fullName;
    String email;
}
```

---

## Create user

```http
POST /users
```

### Body

```java
UserCreateDto {
    String fullName;
    String email;
}
```

### Response

```java
UserDto {
    String id;
    String fullName;
    String email;
}
```
