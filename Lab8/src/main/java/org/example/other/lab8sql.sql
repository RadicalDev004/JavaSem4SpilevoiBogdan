DROP TABLE IF EXISTS sister_cities;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS continents;

CREATE TABLE continents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL UNIQUE,
    continent_id INTEGER NOT NULL
);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capital BOOLEAN NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    country VARCHAR(100) NOT NULL
);

CREATE TABLE sister_cities (
    city1_id INT REFERENCES cities(id),
    city2_id INT REFERENCES cities(id),
    PRIMARY KEY (city1_id, city2_id),
    CHECK (city1_id <> city2_id)
);
