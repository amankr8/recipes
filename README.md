# Recipes Project

This project consists of a **Spring Boot backend API** and an **Angular frontend**.

---

## Backend API (Spring Boot)

The backend is a RESTful API built with Spring Boot. It provides endpoints for fetching recipes and allowing search with keywords.

### Prerequisites
- Java 21
- Maven
- Docker (for running with Docker Compose)

### Running the Backend (Standalone)

1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Build the project:
   ```bash
   ./mvnw clean package
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The backend will start on `http://localhost:8080/` by default.

### Running with Docker Compose

To run the backend using Docker Compose:

1. From the project root, run:
   ```bash
   docker-compose up --build
   ```
2. The backend API will be available at `http://localhost:8080/`.

---

## API Endpoints (Backend)

The backend exposes REST endpoints for managing recipes. Example endpoints:

- `GET /api/recipes` — List all recipes
- `GET /api/recipes/{id}` — Get a recipe by ID
- `GET /api/recipes/search?query=...` — Search recipes by a query string (recipe name)

---

## Frontend (Angular)

The frontend is built with Angular CLI version 19.1.0.

### Prerequisites
- Node.js (v18 or later recommended)
- npm

### Development server

1. Navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```
4. Open your browser and go to `http://localhost:4200/`.

The application will automatically reload whenever you modify any of the source files.

### Building the Frontend

To build the project for production, run:
```bash
ng build
```
The build artifacts will be stored in the `dist/` directory.
