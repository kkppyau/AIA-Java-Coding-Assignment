# AIA-Java-Coding-Assignment

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [API Screenshots](#api-screenshots)

## Prerequisites

To run this application, you'll need the following:

- Java 17 or higher
- MySQL

## Installation

1. Clone the repository:

```
git clone https://github.com/kkppyau/AIA-Java-Coding-Assignment
```

2. Navigate to the project directory:

```
cd aia-coding-assignment
```

3. Update the datasource url, username & password on `src/main/resources/application.properties`


4. Build the project using Maven:

```
mvn clean install
```

## Running the Application

1. Start the application:

```
mvn spring-boot:run
```

2. The application will be available at `http://localhost:8080`.

## API Endpoints

The following API endpoints are available:

- `POST /book/create`: Creates a new book.
- `GET /book/all`: Retrieves a list of all books.
- `DELETE /book/delete/{id}`: Deletes a book by its ID.

## Testing

To run the unit tests:

```
mvn clean test
```

<img width="1105" alt="test-result" src="https://github.com/user-attachments/assets/76e5eb81-9042-47c2-b98c-a0a4783abd1d">

<img width="765" alt="coverage" src="https://github.com/user-attachments/assets/528b62b9-ad61-427f-9b43-16fc5fac0e25">


## API Screenshots

#### POST `/book/create` API

| Success  | Failure |
| ------------- | ------------- |
| <img width="1392" alt="create-success" src="https://github.com/user-attachments/assets/1154f1e0-8d52-4c27-a4ef-7f4b9de5f37e">  | <img width="1392" alt="create-failure" src="https://github.com/user-attachments/assets/6750e00c-f50a-47d2-be1e-a59627ae9b5a"> |

#### GET `/book/all` API

<img width="1392" alt="find-all-success" src="https://github.com/user-attachments/assets/ab4bd168-ebae-43cd-9ad0-07d3c99769fc">

#### DELETE `book/delete/{id}` API

| Success  | Failure |
| ------------- | ------------- |
| <img width="1392" alt="delete-success" src="https://github.com/user-attachments/assets/c7e6ae70-d74d-456d-abd9-69b8d803e911"> | <img width="1392" alt="delete-failure" src="https://github.com/user-attachments/assets/838c4ffa-0acf-47b2-8dcf-3f469a201730"> |
