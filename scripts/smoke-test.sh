#!/usr/bin/env bash
set -euo pipefail

nacos_console="${NACOS_CONSOLE_URL:-http://localhost:18080}"
failed=0

check_nacos_console() {
  local code
  echo "Checking Nacos console at ${nacos_console}/ ..."
  if ! code="$(curl --location --silent --show-error --max-time 10 \
      --output /dev/null --write-out '%{http_code}' "${nacos_console}/")"; then
    echo "FAIL: Nacos console is not reachable." >&2
    failed=1
    return
  fi
  if [[ "${code}" != 2* && "${code}" != 3* ]]; then
    echo "FAIL: Nacos console returned HTTP ${code}." >&2
    failed=1
    return
  fi
  echo "OK: Nacos console returned HTTP ${code}."
}

check_health() {
  local name="$1"
  local url="$2"
  local body
  echo "Checking ${name} health at ${url}/actuator/health ..."
  if ! body="$(curl --fail --silent --show-error --max-time 10 "${url}/actuator/health")"; then
    echo "FAIL: ${name} health endpoint is not reachable or is not healthy." >&2
    failed=1
    return
  fi
  if [[ "${body}" != *'"status":"UP"'* ]]; then
    echo "FAIL: ${name} returned an unhealthy body: ${body}" >&2
    failed=1
    return
  fi
  echo "OK: ${name} health status is UP."
}

check_nacos_console
check_health "user-service" "${USER_URL:-http://localhost:8081}"
check_health "pet-service" "${PET_URL:-http://localhost:8082}"
check_health "appointment-service" "${APPOINTMENT_URL:-http://localhost:8083}"

if (( failed != 0 )); then
  echo "Smoke test failed. Nacos console reachability is not a substitute for a business call." >&2
  exit 1
fi

echo "All smoke checks passed (Nacos console plus 3 service health endpoints)."
