#!/usr/bin/env bash
# Sobe o ambiente de desenvolvimento completo: banco (Docker), backend e frontend.
# Ctrl+C encerra backend e frontend; o banco continua rodando (dados persistem no volume).
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "==> Subindo banco de dados (Postgres via Docker Compose)..."
docker compose -f "$ROOT_DIR/docker-compose.yml" up -d

if [ ! -d "$ROOT_DIR/frontend/node_modules" ]; then
  echo "==> Instalando dependências do frontend (primeira vez)..."
  (cd "$ROOT_DIR/frontend" && npm install)
fi

cleanup() {
  echo
  echo "==> Encerrando backend e frontend..."
  kill "${BACKEND_PID:-}" "${FRONTEND_PID:-}" 2>/dev/null || true
  wait "${BACKEND_PID:-}" "${FRONTEND_PID:-}" 2>/dev/null || true
}
trap cleanup EXIT INT TERM

echo "==> Iniciando backend (Spring Boot)..."
(cd "$ROOT_DIR/backend" && ./mvnw spring-boot:run) &
BACKEND_PID=$!

echo "==> Iniciando frontend (Vite)..."
(cd "$ROOT_DIR/frontend" && npm run dev) &
FRONTEND_PID=$!

wait
