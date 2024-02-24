
CREATE SCHEMA IF NOT EXISTS FILMORATE;

CREATE TABLE IF NOT EXISTS filmorate.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS filmorate.mpa (
    id SERIAL PRIMARY KEY,
    name VARCHAR(5) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS filmorate.films_genre (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS filmorate.films (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(200),
    release_date DATE,
    duration INT,
    rate int,
    mpa_id BIGINT,
    FOREIGN KEY (mpa_id) REFERENCES filmorate.mpa(id)
);

CREATE TABLE IF NOT EXISTS filmorate.friendship (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES filmorate.users(id),
    FOREIGN KEY (friend_id) REFERENCES filmorate.users(id)
);

CREATE TABLE IF NOT EXISTS filmorate.films_like (
    user_id BIGINT NOT NULL,
    film_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, film_id),
    FOREIGN KEY (user_id) REFERENCES filmorate.users(id),
    FOREIGN KEY (film_id) REFERENCES filmorate.films(id)
);

CREATE TABLE IF NOT EXISTS filmorate.film_film_genre (
    film_id BIGINT NOT NULL,
    film_genre_id BIGINT NOT NULL,
    PRIMARY KEY (film_id, film_genre_id),
    FOREIGN KEY (film_id) REFERENCES filmorate.films(id),
    FOREIGN KEY (film_genre_id) REFERENCES filmorate.films_genre(id)
);

INSERT INTO filmorate.films_genre (name) VALUES
    ('Комедия'),
    ('Драма'),
    ('Мультфильм'),
    ('Триллер'),
    ('Документальный'),
    ('Боевик');

insert into filmorate.mpa (name) values
    ('G'),
    ('PG'),
    ('PG-13'),
    ('R'),
    ('NC-17');






