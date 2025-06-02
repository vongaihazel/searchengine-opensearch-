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
| jq (Windows)   | 1.6+     | [Download jq.exe](https://github.com/stedolan/jq/releases) → add to PATH |

---

## Project Structure

```
searchengine/
├── backend/               # Spring Boot application
├── frontend/              # Angular app
├── postman-tests/         # Postman collection for API tests
├── data/
│   └── articles.json      # Sample articles to index into OpenSearch
├── import-articles.ps1    # PowerShell script to bulk index sample data
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
git clone https://github.com/vongaihazel/searchengine-opensearch-.git
cd searchengine
```

---

### 2. Start Services with Docker Compose

```bash
docker compose up --build
```

This starts:

- Spring Boot backend: [http://localhost:8080](http://localhost:8080)
- Angular frontend: [http://localhost:4200](http://localhost:4200)
- OpenSearch: [https://localhost:9200](https://localhost:9200)
- OpenSearch Dashboards: [http://localhost:5601](http://localhost:5601)
- PostgreSQL: runs on port 5432
- Postman CLI: runs automated API tests

---

### 3. Build Backend

```bash
cd backend
mvn clean install -DskipTests
cd ..
```

---

### 4. Build Frontend (Optional Local Test)

```bash
cd frontend
npm install
ng build
cd ..
```

> This step is handled in Docker, but useful for local dev.


---

### 5. Import Sample Articles into OpenSearch

> This replaces the old `DataLoader.java`.

#### A. (Recommended) Use PowerShell Script (Windows only)

```powershell
cd C:\Projects\searchengine
.\import-articles.ps1
```

Make sure `jq.exe` is in your system PATH.

#### B. (Alternative) Use Postman

1. Open Postman.
2. Send POST requests to:
```
POST http://localhost:8080/search/api/articles
```

Example body:
```json
{
  "id": "1",
  "title": "Spring Boot Basics",
  "content": "Spring Boot helps build Java apps quickly"
}
```

Repeat for multiple articles.

---

### 6. Search via Angular Frontend

Open [http://localhost:4200](http://localhost:4200)  
Search for terms like:

- `Spring Boot`
- `OpenSearch`
- `Docker`

---

### 7. Monitor Logs (Optional)

```bash
docker logs -f postman_cli
```

---

### 8. Run Backend Tests (Optional)

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
- `OPENSEARCH_HOSTS=https://opensearch-node:9200`
- `OPENSEARCH_USERNAME=admin`
- `OPENSEARCH_PASSWORD=admin`

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
- ❗ Ensure **Docker** is running and ports 5432, 8080, 4200, 9200 are free
- ❗ If frontend shows "Welcome to Nginx", ensure Angular build is in `dist/searchengine/browser`
- ❗ If search fails: confirm articles are indexed and the `articles` index exists in OpenSearch

---

## Author

**Vongai Mazorodze**  
Email: vmazorodze35@gmail.com

---
