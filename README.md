
# Vongai´s Search Engine

---

## Prerequisites

Install the following tools **before setup**:


| Tool           | Version  | Install Command / Link                                                   |
|----------------|----------|---------------------------------------------------------------------------|
| Java JDK       | 21+      | [Adoptium](https://adoptium.net/)                                        |
| Maven          | 3.8+     | [Install Guide](https://maven.apache.org/install.html)                   |
| Node.js + npm  | 18+      | [Node.js LTS](https://nodejs.org/)                                       |
| Angular CLI    | Latest   | `npm install -g @angular/cli`                                            |
| Docker         | Latest   | [Docker Desktop](https://www.docker.com/products/docker-desktop/)        |
| Docker Compose | v2+      | Included in Docker Desktop                                               |
| Git            | Latest   | [Git SCM](https://git-scm.com/)                                          |

---

## Project Structure

```
searchengine/
├── backend/               # Spring Boot application
├── frontend/              # Angular app
├── postman-tests/         # Postman collection for API tests
├── Dockerfile             # Backend build file
├── Dockerfile-angular     # Angular build file
├── Dockerfile-postman-cli # Postman CLI runner
├── docker-compose.yml     # All service orchestration
└── README.md
```

---
## Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/vongaihazel/searchengine.git
cd searchengine
```

---

### Build Backend


```bash
cd backend
mvn clean install -DskipTests
cd ..
```

---
### 3. Build Frontend (Optional Local Test)

```bash
cd frontend
npm install
ng build
cd ..
```

> This step is handled in Docker, but useful for local dev.

---

### 4. Start Services with Docker Compose

```bash
docker compose up --build
```

This starts:

- Spring Boot backend: [http://localhost:8080](http://localhost:8080)
- Angular frontend: [http://localhost:4200](http://localhost:4200)
- OpenSearch: [http://localhost:9200](http://localhost:9200)
- OpenSearch Dashboards: [http://localhost:5601](http://localhost:5601)
- PostgreSQL on port 5432
- Postman CLI: runs automated API tests after backend is ready

---

### 5. Monitor Postman Test Logs (Optional)

```bash
docker logs -f postman_cli
```

---

### 6. Run Backend Tests Manually (Optional)

```bash
cd backend
mvn test
```

---

## Angular Build Output (for Docker)

Ensure Angular is configured to output to:

```
dist/searchengine/browser/
```

This path is used in the Dockerfile to serve content with Nginx.

---

## Environment Variables (Auto-set in Docker)

- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/searchdb`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=mysecretpassword`
- `OPENSEARCH_HOSTS=http://opensearch-node:9200`
- `OPENSEARCH_USERNAME=admin`
- `OPENSEARCH_PASSWORD=MyStrongPassword123!`

---

## Docker Cleanup Commands

- Stop and remove all containers + volumes:

```bash
docker compose down --volumes --remove-orphans
```

- Full rebuild without cache:

```bash
docker compose build --no-cache
```

---

## Troubleshooting

- ❗ Use **Java JDK 21+** (`java -version`)
- ❗ Ensure **Docker is running** with ports 5432, 8080, 4200, and 9200 available
- ❗ For CLI test issues, recheck `Dockerfile-postman-cli` logic and Postman binary paths
  ❗ If Angular app shows "Welcome to Nginx", verify Angular output is in `dist/searchengine/browser` and Dockerfile path matches

---

## Author

**Vongai Mazorodze**  
Email: vmazorodze35@gmail.com

---