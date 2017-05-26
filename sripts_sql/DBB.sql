-- Database: ogzdb

-- DROP DATABASE ogzdb;

CREATE DATABASE ogzdb
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE ogzdb TO postgres;

GRANT ALL ON DATABASE ogzdb TO readinsert;

-- SCHEMA: predeterminado

-- DROP SCHEMA predeterminado ;

CREATE SCHEMA predeterminado
    AUTHORIZATION postgres;

COMMENT ON SCHEMA predeterminado
    IS 'standard public schema';

GRANT ALL ON SCHEMA predeterminado TO postgres;

GRANT ALL ON SCHEMA predeterminado TO readinsert;







-- Table: predeterminado.usuarios

-- DROP TABLE predeterminado.usuarios;

CREATE TABLE predeterminado.usuarios
(
    id integer NOT NULL DEFAULT nextval('predeterminado.usuarios_id_seq'::regclass),
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(12) COLLATE pg_catalog."default" NOT NULL,
    contrasena character varying(32) COLLATE pg_catalog."default",
    CONSTRAINT usuarios_pkey PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email),
    CONSTRAINT nombre UNIQUE (nombre),
    CONSTRAINT uniques UNIQUE (email, nombre)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.usuarios
    OWNER to readinsert;

GRANT ALL ON TABLE predeterminado.usuarios TO readinsert;

-- Table: predeterminado.juegos

-- DROP TABLE predeterminado.juegos;

CREATE TABLE predeterminado.juegos
(
    id integer NOT NULL DEFAULT nextval('predeterminado.juegos_id_seq'::regclass),
    nombre character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT juegos_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.juegos
    OWNER to readinsert;

-- Table: predeterminado.grupos

-- DROP TABLE predeterminado.grupos;

CREATE TABLE predeterminado.grupos
(
    id integer NOT NULL DEFAULT nextval('predeterminado.grupos_id_seq'::regclass),
    nombre character varying(15) COLLATE pg_catalog."default",
    CONSTRAINT grupos_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.grupos
    OWNER to readinsert;

-- Table: predeterminado.eventos

-- DROP TABLE predeterminado.eventos;

CREATE TABLE predeterminado.eventos
(
    id integer NOT NULL DEFAULT nextval('predeterminado.eventos_id_seq'::regclass),
    fecha_inicio timestamp without time zone NOT NULL,
    fecha_fin timestamp without time zone NOT NULL,
    id_juego integer NOT NULL,
    id_grupo integer NOT NULL,
    CONSTRAINT eventos_pkey PRIMARY KEY (id),
    CONSTRAINT id_grupo FOREIGN KEY (id_grupo)
        REFERENCES predeterminado.grupos (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_juego FOREIGN KEY (id_juego)
        REFERENCES predeterminado.juegos (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.eventos
    OWNER to readinsert;

-- Table: predeterminado.evento_usuario

-- DROP TABLE predeterminado.evento_usuario;

CREATE TABLE predeterminado.evento_usuario
(
    id_evento integer NOT NULL,
    id_usuario integer NOT NULL,
    CONSTRAINT id_evento FOREIGN KEY (id_evento)
        REFERENCES predeterminado.eventos (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES predeterminado.usuarios (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.evento_usuario
    OWNER to readinsert;

-- Table: predeterminado.grupo_usuario

-- DROP TABLE predeterminado.grupo_usuario;

CREATE TABLE predeterminado.grupo_usuario
(
    id_grupo integer NOT NULL,
    id_usuario integer NOT NULL,
    CONSTRAINT id_grupo FOREIGN KEY (id_grupo)
        REFERENCES predeterminado.grupos (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES predeterminado.usuarios (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE predeterminado.grupo_usuario
    OWNER to readinsert;