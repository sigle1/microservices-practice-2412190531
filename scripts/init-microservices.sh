#!/usr/bin/env bash
set -eu

mysql -uroot -p"${MYSQL_ROOT_PASSWORD}" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE DATABASE IF NOT EXISTS pet_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE DATABASE IF NOT EXISTS appointment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

    CREATE USER IF NOT EXISTS 'user_app'@'%' IDENTIFIED BY '${USER_DB_PASSWORD}';
    CREATE USER IF NOT EXISTS 'pet_app'@'%' IDENTIFIED BY '${PET_DB_PASSWORD}';
    CREATE USER IF NOT EXISTS 'appointment_app'@'%' IDENTIFIED BY '${APPOINTMENT_DB_PASSWORD}';

    GRANT ALL PRIVILEGES ON user_db.* TO 'user_app'@'%';
    GRANT ALL PRIVILEGES ON pet_db.* TO 'pet_app'@'%';
    GRANT ALL PRIVILEGES ON appointment_db.* TO 'appointment_app'@'%';
    FLUSH PRIVILEGES;
EOSQL
