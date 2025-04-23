### English version: [README-en.md](https://github.com/ImmortalIdiot/Sharding/blob/master/README-en.md)

___

# 📝 Описание

Это приложение использует **шардинг на уровне приложения** для распределения данных между 3 шардами. Каждый шард хранит таблицу `users` в базе данных PostgreSQL. Таблица имеет следующие поля:

| Поле   | Тип     | Описание              |
|--------|---------|-----------------------|
| `id`   | UUID    | Уникальный идентификатор (первичный ключ) |
| `name` | TEXT    | Имя пользователя (не может быть пустым) |
| `email`| TEXT    | Электронная почта пользователя (не может быть пустым) |

---

# 🛠️ Стек

- **Kotlin** — современный язык программирования.
- **Ktor** — фреймворк для создания асинхронных серверных приложений на Kotlin.
- **Exposed** — библиотека для работы с базами данных в стиле Kotlin DSL.
- **PostgreSQL** — реляционная база данных для хранения данных.

Приложение поддерживает **3 шарда**.

---

# 🖥️ Необходимые IDE и инструменты

Для разработки и запуска проекта понадобятся следующие инструменты:

- **IDE**:
  - IntelliJ IDEA (рекомендуемая IDE для Kotlin).
  - Другие IDE с поддержкой Kotlin могут работать, но для лучшей совместимости рекомендуется использовать IntelliJ IDEA.
- **Docker**:
  - Docker для контейнеризации PostgreSQL.
  - Docker Compose для управления многоконтейнерными приложениями.
- **HTTP клиент**:
  - Postman или любой другой HTTP клиент для тестирования API.

---

# 🚀 Установка и использование

## Требования

Для работы с проектом вам понадобятся:

- **Docker**
- **Docker Compose**
- **Postman** или любой HTTP клиент
- **IDE** для запуска проекта (рекомендуется использовать IntelliJ IDEA)

## Шаги:

1. **Склонируйте репозиторий:**

    ```bash
    git clone https://github.com/ImmortalIdiot/Sharding
    ```

2. **Запустите Docker контейнеры:**

    В корне проекта откройте терминал и выполните команду:

    ```bash
    docker-compose up
    ```

    Если вы используете Docker Desktop, убедитесь, что он запущен.

3. **Создание таблиц в каждом шарде:**

    После того как контейнеры будут запущены, откройте терминал и выполните следующие команды для создания таблиц в каждом шарде:

    Для первого шарда:
    ```bash
    docker exec -it sharding-shard1-1 psql -U postgres -d shard1
    ```

    В psql выполните команду:

    ```sql
    CREATE TABLE users(
        id UUID PRIMARY KEY,
        name TEXT NOT NULL,
        email TEXT NOT NULL
    );
    ```

    Для выхода из psql выполните:

    ```bash
    \q
    ```

    Повторите эти шаги для второго и третьего шарда:

    Для второго шарда:
    ```bash
    docker exec -it sharding-shard2-1 psql -U postgres -d shard2
    ```

    Для третьего шарда:
    ```bash
    docker exec -it sharding-shard3-1 psql -U postgres -d shard3
    ```

4. **Запустите проект:**

    Откройте проект в вашей IDE (например, IntelliJ IDEA), найдите файл `Application.kt` и запустите метод `main`.

5. **Проверка работы API с помощью Postman:**

    Откройте Postman или любой другой HTTP клиент для тестирования API.

    - **POST-запрос:**

        - Установите метод запроса: `POST`
        - Введите эндпоинт: `http://localhost:8080/users`
        ![изображение](https://github.com/user-attachments/assets/91803141-9d63-4521-91b3-2c8bdfe816d3)

        - Перейдите во вкладку **Headers**:
            - В поле **key** укажите `Content-Type`
            - В поле **value** укажите `application/json`.
            ![изображение](https://github.com/user-attachments/assets/6806a4e6-04ec-4636-9b6e-bc928764a472)

        - Перейдите во вкладку **Body**, выберите `raw`, `JSON` и вставьте json. Пример:

            ```json
            {
                "name": "John Doe",
                "email": "johndoe@example.com"
            }
            ```
        ![изображение](https://github.com/user-attachments/assets/7a5f0058-116d-4b9c-b69c-3d96c2d866bd)

        - Нажмите **Send**.

      Результат отправленного post-запроса:
      ![изображение](https://github.com/user-attachments/assets/2f81a798-7883-4920-93d6-b3b2e56c96d1)

    - **GET-запрос:**

        - Установите метод запроса: `GET`
        - Введите эндпоинт: `http://localhost:8080/users/{id}`
        - Замените `{id}` на id пользователя. Пример полного эндпоинта:

            ```
            http://localhost:8080/users/cd3fce2c-4abd-41d7-ac2d-896454fd03e4
            ```
        ![изображение](https://github.com/user-attachments/assets/b7a80bc8-20aa-495d-91d3-f9430b50fce3)

        - Нажмите **Send**.
     
      Результат get-запроса:
      ![изображение](https://github.com/user-attachments/assets/73f5f3b6-ccb7-4eec-9edf-6e9a3c2aed0d)

В консоли во вкладке `Run` можно увидеть логи запросов:
![изображение](https://github.com/user-attachments/assets/b3d14b36-0cdc-4d97-a0f9-537912614714)
