DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS MPA
(
    MPA_ID   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    MPA_NAME CHARACTER VARYING(20) not null
);

CREATE TABLE IF NOT EXISTS DIRECTORS
(
    DIRECTOR_ID INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    DIRECTOR_NAME CHARACTER VARYING(50) not null
);

CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_NAME    CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200) not null,
    RELEASE_DATE DATE                   not null,
    DURATION     INTEGER                not null,
    MPA_ID       INTEGER,
    DIRECTOR_ID  INTEGER,

    CONSTRAINT FK_MPA_ID FOREIGN KEY (MPA_ID) REFERENCES MPA(MPA_ID),
    CONSTRAINT FK_DIRECTOR_ID FOREIGN KEY (DIRECTOR_ID) REFERENCES DIRECTORS(DIRECTOR_ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS FILMS_ID_UNQ
    on FILMS (FILM_ID);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_NAME CHARACTER VARYING(100) not null,
    LOGIN     CHARACTER VARYING(50)  not null,
    EMAIL     CHARACTER VARYING(200) not null,
    BIRTHDAY  DATE
);

CREATE UNIQUE INDEX IF NOT EXISTS USERS_ID_UNQ
    on USERS (USER_ID);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    USER_ID INT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FRIEND_ID INT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    UNIQUE  (USER_ID, FRIEND_ID)
);

CREATE TABLE IF NOT EXISTS GENRES
(
    GENRE_ID   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME CHARACTER VARYING(20) not null
);

CREATE TABLE IF NOT EXISTS LIKES
(
    USER_ID INT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FILM_ID INT REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    UNIQUE  (FILM_ID, USER_ID)
);

CREATE TABLE IF NOT EXISTS FILM_GENRES
(
    FILM_ID  INT REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    GENRE_ID INT REFERENCES GENRES (GENRE_ID) ON DELETE CASCADE
);