### Русская версия: [README-ru.md](https://github.com/ImmortalIdiot/Sharding/blob/master/README-ru.md)

___

## 📝 Overview

This application uses **sharding at the application level** to distribute data across 3 shards. Each shard stores a `users` table in PostgreSQL. The table has the following fields:

| Field   | Type     | Description                               |
|---------|----------|-------------------------------------------|
| `id`    | UUID     | Unique identifier (primary key)          |
| `name`  | TEXT     | User's name              |
| `email` | TEXT     | User's email             |

---

## 🛠️ Tech Stack

- **Kotlin** — A modern programming language.
- **Ktor** — A framework for building asynchronous server applications in Kotlin.
- **Exposed** — A Kotlin DSL library for working with databases.
- **PostgreSQL** — A relational database to store data.

The application supports **3 shards**.

---

## 🖥️ Required IDE and Tools

To develop and run this project, you will need the following tools:

- **IDE**:
  - IntelliJ IDEA (recommended for Kotlin development).
  - Other IDEs supporting Kotlin might work, but for best compatibility, IntelliJ IDEA is recommended.
- **Docker**:
  - Docker to containerize PostgreSQL.
  - Docker Compose to manage multi-container applications.
- **HTTP Client**:
  - Postman or any other HTTP client to test the API.

---

## 🚀 Getting Started

### Requirements

To run the project, you'll need:

- **Docker**
- **Docker Compose**
- **Postman** or any HTTP client
- **IDE** to run the project (IntelliJ IDEA is recommended)

### Steps:

1. **Clone the repository:**

    ```bash
    git clone https://github.com/ImmortalIdiot/Sharding
    ```

2. **Start Docker containers:**

    In the project root directory, open a terminal and run the following command:

    ```bash
    docker-compose up
    ```

    If you are using Docker Desktop, make sure it is running.

3. **Create tables in each shard:**

    After the containers are up, open the terminal and run the following commands to create tables in each shard:

    For the first shard:
    ```bash
    docker exec -it sharding-shard1-1 psql -U postgres -d shard1
    ```

    In psql, execute the following command:

    ```sql
    CREATE TABLE users(
        id UUID PRIMARY KEY,
        name TEXT NOT NULL,
        email TEXT NOT NULL
    );
    ```

    To exit psql, run:

    ```bash
    \q
    ```

    Repeat these steps for the second and third shards:

    For the second shard:
    ```bash
    docker exec -it sharding-shard2-1 psql -U postgres -d shard2
    ```

    For the third shard:
    ```bash
    docker exec -it sharding-shard3-1 psql -U postgres -d shard3
    ```

4. **Run the project:**

    Open the project in your IDE (e.g., IntelliJ IDEA), find the `Application.kt` file, and run the `main` method.

5. **Test the API using Postman:**

    Open Postman or any other HTTP client to test the API.

    - **POST request:**

        - Set the request method to `POST`
        - Enter the endpoint: `http://localhost:8080/users`
        ![image](https://github.com/user-attachments/assets/91803141-9d63-4521-91b3-2c8bdfe816d3)

        - Go to the **Headers** tab:
            - In the **key** field, enter `Content-Type`
            - In the **value** field, enter `application/json`
            ![image](https://github.com/user-attachments/assets/6806a4e6-04ec-4636-9b6e-bc928764a472)

        - Go to the **Body** tab, select `raw`, `JSON` and enter the JSON. Example:

            ```json
            {
                "name": "John Doe",
                "email": "johndoe@example.com"
            }
            ```
        ![image](https://github.com/user-attachments/assets/7a5f0058-116d-4b9c-b69c-3d96c2d866bd)

        - Click **Send**.

      The result of the sent POST request:
      ![image](https://github.com/user-attachments/assets/2f81a798-7883-4920-93d6-b3b2e56c96d1)

    - **GET request:**

        - Set the request method to `GET`
        - Enter the endpoint: `http://localhost:8080/users/{id}`
        - Replace `{id}` with the user ID. Example of the complete endpoint:

            ```
            http://localhost:8080/users/cd3fce2c-4abd-41d7-ac2d-896454fd03e4
            ```
        ![image](https://github.com/user-attachments/assets/b7a80bc8-20aa-495d-91d3-f9430b50fce3)

        - Click **Send**.
     
      The result of the GET request:
      ![image](https://github.com/user-attachments/assets/73f5f3b6-ccb7-4eec-9edf-6e9a3c2aed0d)

In the **Run** tab of the console, you can see the request logs:
![image](https://github.com/user-attachments/assets/b3d14b36-0cdc-4d97-a0f9-537912614714)

