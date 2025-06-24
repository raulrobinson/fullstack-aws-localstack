<p align="center">
  <img src="https://raw.githubusercontent.com/localstack/localstack/master/docs/localstack-readme-banner.svg" alt="LocalStack - A fully functional local cloud stack">
</p>

# Fullstack AWS LocalStack

Development Services System for AWS LocalStack 

## Features

- LocalStack for AWS services emulation
- Docker Compose for service orchestration
- Java Spring Boot for backend development
- Angular for frontend development

## Getting Started

### Prerequisites

- Docker and Docker Compose installed
- Java Development Kit (JDK) installed
- Node.js and npm installed
- Angular CLI installed
- Maven installed
- Git installed
- Postman or any API testing tool
- IDE (IntelliJ IDEA, Visual Studio Code, etc.)
- AWS CLI installed (optional, for AWS interactions)
- LocalStack CLI installed (optional, for LocalStack interactions)
- Docker Desktop or Docker Engine running
- LocalStack running (can be started via Docker Compose)

### Installation

1. Clone the repository:
   ```bash
   git clone
   ```
   
2. Navigate to the project directory:
   ```bash
    cd fullstack-aws-localstack
    ```
   
3. Start LocalStack using Docker Compose:
    ```bash
    docker-compose up -d
    ```
   
4. Build the backend service:
    ```bash
    cd backend
    gradlew clean build
    ```
   
5. Start the backend service:
    ```bash
    ./gradlew bootRun
    ```
   
6. Build the frontend service:
    ```bash
    cd dashbaord-ui
    npm install
    ng build --configuration=development
    ```
   
7. Start the frontend service:
    ```bash
    npm run start
    ```
   
8. Access the application:
   Open your web browser and navigate to `http://localhost:4200`.


9. Access the backend API:
   Use Postman or any API testing tool to access the backend API at `http://localhost:9800/webjars/swagger-ui/index.html`.


### DynamoDB Admin 

To manage DynamoDB tables, you can use the DynamoDB Admin UI. Follow these steps:

1. Open your web browser and navigate to `http://localhost:8001`.

### Minio Console

To manage Minio buckets, you can use the Minio Console. Follow these steps:

1. Open your web browser and navigate to `http://localhost:9001/login`.

2. Use the following credentials to log in:
   - **Username**: `admin`
   - **Password**: `admin123`


### Author: [Raul Bolivar Navas](https://github.com/raulrobinson)

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.