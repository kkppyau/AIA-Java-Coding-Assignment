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

## API Screenshots
