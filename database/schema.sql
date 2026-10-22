-- Student Management - MySQL setup script
-- Run:  mysql -u YOUR_USERNAME -p < database/schema.sql
-- NOTE: This is OPTIONAL. The app also creates the database and tables
-- automatically (createDatabaseIfNotExist=true + ddl-auto=update).

CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

CREATE TABLE IF NOT EXISTS users (
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL           -- BCrypt hash
);

CREATE TABLE IF NOT EXISTS students (
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    branch     VARCHAR(255),
    study_year INT
);
