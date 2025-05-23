# URL Shortener Service

## How to Run the Project Locally

1. Make sure you have Java 21, Maven, Docker, and MySQL installed.
2. Start MySQL and create the database:
   ```sh
   mysql -u root -p
   CREATE DATABASE shortener_db;
   ```
3. Update `src/main/resources/application.properties` with your MySQL credentials if needed.
4. Build and run the app:
   ```sh
   mvn clean package
   java -jar target/*.jar
   ```
5. Or run with Docker:
   ```sh
   docker build -t shortener-backend .
   docker run -p 8080:8080 --env SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/shortener_db --env SPRING_DATASOURCE_USERNAME=root --env SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD shortener-backend
   ```

## Example API Usage (curl)

- **Shorten a URL:**
  ```sh
  curl -X POST http://localhost:8080/api/shorten -H "Content-Type: application/json" -d '{"url":"https://example.com"}'
  # Response: { "shortCode": "abc123" }
  ```
- **Redirect:**
  ```sh
  curl -v http://localhost:8080/abc123
  # 302 redirect to https://example.com
  ```
- **Analytics:**
  ```sh
  curl http://localhost:8080/api/analytics/abc123
  # Response: { "originalUrl": ..., "shortCode": ..., "redirectCount": ..., "createdAt": ... }
  ```

## How the CI/CD Works

- GitHub Actions workflow runs on every push/PR to `main`.
- Steps: linter (Checkstyle), unit tests, build JAR, build & push Docker image to Docker Hub.
- Uses MySQL service for integration tests in CI.

## How Deployment Works

- Docker image is deployed to Google Cloud Run.
- Cloud Run connects to Cloud SQL (MySQL) using the Cloud SQL Proxy.
- Environment variables for DB connection are set in Cloud Run.
- Service listens on the port provided by the `PORT` environment variable.

## Time Breakdown

- Core API & DB logic: ~4 hours
- Dockerization: ~30 minutes
- CI/CD setup: ~4 hour
- Cloud Run deployment: ~2 hour
- Debugging & edge cases: ~3 hour

## Tradeoffs / Shortcuts

- Used reflection for unit tests to avoid Spring context loading in CI.
- Did not implement full integration tests (could be added for end-to-end validation).
- Used basic URL validation (Java URL class) instead of a more robust validator.
- Secrets for DB and Docker Hub are managed via GitHub Actions and Cloud Run env vars.
- Some test cases and debugging steps were generated or assisted by OpenAI's ChatGPT.
