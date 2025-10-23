-- ==========================================
--  V1__init.sql
--  초기 테이블 생성
-- ==========================================

CREATE DATABASE IF NOT EXISTS test;
USE test;

CREATE TABLE locations
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    region VARCHAR(255) NOT NULL,
    city   VARCHAR(255) NOT NULL,
    CONSTRAINT uq_locations_region_city UNIQUE (region, city)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE flights
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_id   BIGINT         NOT NULL,
    flight_number VARCHAR(255)   NOT NULL,
    airline       VARCHAR(255)   NOT NULL,
    dept_time     DATETIME       NOT NULL,
    arrival_time  DATETIME       NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_flights_location
        FOREIGN KEY (location_id) REFERENCES locations (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    phone      VARCHAR(50),
    deleted    TINYINT(1)   NOT NULL DEFAULT 0,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
