-- ==========================================
--  V2__import_flights_from_csv.sql
--  flights_data_updated.csv → flights 테이블 적재
-- ==========================================

USE test;

-- 1. 임시 테이블 생성
CREATE TABLE IF NOT EXISTS temp_flights
(
    flight_number VARCHAR(255),
    airline       VARCHAR(255),
    dept_time     VARCHAR(255),
    arrival_time  VARCHAR(255),
    price         DECIMAL(10, 2),
    destination   VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- 2. CSV 파일 로드
LOAD DATA LOCAL INFILE 'src/main/resources/db/import/flights_data_updated.csv'
    INTO TABLE temp_flights
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (flight_number, airline, dept_time, arrival_time, price, destination);

-- 3. flights 테이블로 데이터 삽입
INSERT INTO flights (location_id, flight_number, airline, dept_time, arrival_time, price)
SELECT l.id,
       t.flight_number,
       t.airline,
       STR_TO_DATE(t.dept_time, '%Y-%m-%d %H:%i:%s'),
       STR_TO_DATE(t.arrival_time, '%Y-%m-%d %H:%i:%s'),
       t.price
FROM temp_flights t
         JOIN locations l ON l.city = t.destination;

-- 4. 임시 테이블 삭제
DROP TABLE temp_flights;
