CREATE DATABASE "sistema-comercial"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.categoria(
	id SERIAL PRIMARY KEY,
	nome varchar(80)
);

CREATE TABLE public.produto(
	id SERIAL PRIMARY KEY,
	nome varchar(80),
	marca varchar (80),
	codigo_barras varchar(30),
	unidade_medida varchar(2),
	categoria_id int,
	FOREIGN KEY(categoria_id) REFERENCES categoria(id)
);