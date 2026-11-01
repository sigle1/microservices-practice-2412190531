#!/usr/bin/env bash
set -euo pipefail

for service in user-service:8081 pet-service:8082 appointment-service:8083; do
  name="${service%%:*}"
  port="${service##*:}"
  echo "Checking ${name} health endpoint..."
  curl --fail --silent --show-error "http://localhost:${port}/actuator/health"
  echo
done

echo "All service health checks passed."
