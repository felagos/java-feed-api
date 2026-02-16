# Feed API - Docker Setup

This Spring Boot application provides a social feed API with PostgreSQL database, containerized using Docker Compose.

## 🧠 AI Documentation Agent

**¡Nuevo!** El proyecto incluye un agente inteligente para documentar cambios automáticamente.

### Uso Rápido
- **Atajo**: `Cmd+Shift+D` (Mac) / `Ctrl+Shift+D` (Windows/Linux)  
- **Comando**: `Cmd+Shift+P` → "📝 Documentar Cambios"
- **Terminal**: `node .vscode/document-changes.js`

**Genera**: Lista de archivos, resumen de solución, diagrama Mermaid y estadísticas.  
**Salida**: `./docs/changes/YYYY-MM-DD-rama-changes.md`

📖 [Documentación completa del agente](.vscode/README-DocumentationAgent.md)

---

## Prerequisites

- Docker
- Docker Compose
- Make (optional, for simplified commands)

## Quick Start

### Using Makefile (Recommended)

```bash
# Show all available commands
make help

# Start development environment (build + up + logs)
make dev

# Quick start without logs
make up

# Check status
make status

# Test API endpoints
make test-api

# Stop everything
make down
```

### Using Docker Compose Directly

1. **Clone the repository and navigate to the project directory**

2. **Start the services**
   ```bash
   docker-compose up -d
   ```

3. **Check the status**
   ```bash
   docker-compose ps
   ```

4. **View logs**
   ```bash
   # View all logs
   docker-compose logs -f
   
   # View specific service logs
   docker-compose logs -f feed-api
   docker-compose logs -f postgres
   ```

## Services

### PostgreSQL Database
- **Container**: `feed-postgres`
- **Port**: `5432`
- **Database**: `feed_db`
- **User**: `feed_user`
- **Password**: `feed_password`
- **Data Volume**: `postgres_data` (persistent storage)

### Feed API Application
- **Container**: `feed-api`
- **Port**: `8080`
- **Context Path**: `/api`
- **Health Check**: `http://localhost:8080/api/actuator/health`

## API Endpoints

Base URL: `http://localhost:8080/api/feed`

- `POST /posts` - Create a new post
- `GET /timeline` - Get user's feed timeline
- `POST /follow/{followeeId}` - Follow a user

### Example Usage

```bash
# Create a post
curl -X POST http://localhost:8080/api/feed/posts \
  -H "Content-Type: application/json" \
  -H "User-Id: 1" \
  -d '{"content": "Hello, World!"}'

# Get timeline
curl -X GET "http://localhost:8080/api/feed/timeline?page=0&size=20" \
  -H "User-Id: 1"

# Follow a user
curl -X POST http://localhost:8080/api/feed/follow/2 \
  -H "User-Id: 1"
```

## Development Commands

### Makefile Commands (Recommended)

```bash
# Development workflow
make dev              # Start development environment (build + up + logs)
make dev-cycle        # Complete development cycle (clean, build, start, test)
make rebuild          # Rebuild and restart everything

# Service management
make up               # Start all services
make down             # Stop and remove all containers
make restart          # Restart all services
make status           # Show service status

# Logs and monitoring
make logs             # Show all logs
make app-logs         # Show application logs only
make db-logs          # Show database logs only
make health           # Check application health

# Database operations
make db-connect       # Connect to PostgreSQL database
make db-shell         # Open bash shell in PostgreSQL container

# Testing
make test-api         # Test API endpoints

# Cleanup
make clean            # Remove containers, networks, and images
make clean-volumes    # Remove volumes (deletes database data)
make clean-all        # Complete cleanup

# Information
make help             # Show all available commands
make info             # Show project information
```

### Docker Compose Commands

```bash
# Start services
docker-compose up -d

# Rebuild and start (after code changes)
docker-compose up -d --build

# Stop services
docker-compose down

# Stop services and remove volumes (WARNING: This will delete your database data)
docker-compose down -v

# View container logs
docker-compose logs -f feed-api

# Execute commands in containers
docker-compose exec postgres psql -U feed_user -d feed_db
docker-compose exec feed-api bash
```

## Database Connection

To connect to the PostgreSQL database directly:

```bash
# Using docker-compose exec
docker-compose exec postgres psql -U feed_user -d feed_db

# Using external client (while containers are running)
psql -h localhost -p 5432 -U feed_user -d feed_db
```

## Troubleshooting

1. **Port conflicts**: If port 8080 or 5432 is already in use, modify the ports in `docker-compose.yml`

2. **Database connection issues**: 
   - Check if PostgreSQL container is healthy: `docker-compose ps`
   - View database logs: `docker-compose logs postgres`

3. **Application not starting**:
   - Check application logs: `docker-compose logs feed-api`
   - Ensure the database is ready before the app starts (health check should handle this)

4. **Rebuild after changes**:
   ```bash
   docker-compose down
   docker-compose up -d --build
   ```

## Configuration Files

- `docker-compose.yml` - Docker services configuration
- `Dockerfile` - Spring Boot application container
- `application-docker.properties` - Docker-specific application configuration
- `init.sql` - Database initialization script (indexes and optional sample data)