-- --------------------------------------
--              QUIZ-4
-- --------------------------------------
-- Nombre: Jonathan Quesada Salas
-- --------------------------------------
-- Carnet: 2020023583
-- --------------------------------------

-- SCHEMA MOVIES
drop schema if exists movies;

create schema if not exists movies default character set utf8;
use movies;

-- --------------------------------------------------
-- TABLAS DEL DIAGRAMA Y SUS INDICES
-- --------------------------------------------------
drop table if exists category;

create table if not exists category(
	id int not null auto_increment,
    category_name varchar(100) not null,
    primary key(id)
);

create index category_name_idx on category (category_name asc);

-- --------------------------------------------------
drop table if exists movie;

create table if not exists movie(
	id int not null auto_increment,
    title varchar(100) not null,
    release_date datetime,
    category_id int not null,
    primary key(id),
    constraint category_fk foreign key (category_id) references category(id)
);

create index title_idx on movie (title asc);

-- --------------------------------------------------
drop table if exists users;

create table if not exists users(
	id int not null auto_increment,
    user_name varchar(100) not null,
    first_name varchar(100),
    last_name varchar(100),
    primary key(id)
);

alter table users add fulltext(first_name);
alter table users add fulltext(last_name);

-- --------------------------------------------------
drop table if exists rating;

create table if not exists rating(
	movie_id int not null,
    score int not null,
    review varchar(200),
    user_id int not null,
    constraint movie_fk foreign key (movie_id) references movie(id),
	constraint user_fk foreign key (user_id) references users(id)
);

create index movie_idx on rating (movie_id asc);
create index user_idx on rating (user_id asc);

-- --------------------------------------------------
-- INSERTAR LOS DATOS EN LAS TABLAS CORRESPONDIENTES
-- --------------------------------------------------
insert into category(category_name) values ('Comedia');
insert into category(category_name) values ('Accion');
insert into category(category_name) values ('Drama');
insert into category(category_name) values ('Suspenso');
insert into category(category_name) values ('Terror');

insert into users(user_name, first_name, last_name) values ('Nuwidra','Jonathan','Quesada');
insert into users(user_name, first_name, last_name) values ('Nuwidra','Jonathan','Quesada');
insert into users(user_name, first_name, last_name) values ('Nuwidra','Jonathan','Quesada');
insert into users(user_name, first_name, last_name) values ('Nuwidra','Jonathan','Quesada');
insert into users(user_name, first_name, last_name) values ('Nuwidra','Jonathan','Quesada');

insert into movie(title, release_date, category_id) values ('Godzilla vs Kong','2021-01-01',1);
insert into movie(title, release_date, category_id) values ('Ruega por nosotros','2021-01-01',1);
insert into movie(title, release_date, category_id) values ('Demon Slayer','2021-01-01',1);
insert into movie(title, release_date, category_id) values ('Moana','2021-01-01',1);
insert into movie(title, release_date, category_id) values ('La Sirenita','2021-01-01',1);

insert into rating(movie_id, user_id, score, review) values (1,1,1,'Esperaba mas de la pelicula');
insert into rating(movie_id, user_id, score, review) values (2,2,2,'Muy intensa la pelicula');
insert into rating(movie_id, user_id, score, review) values (3,3,3,'Una pelicula demasiado epica');
insert into rating(movie_id, user_id, score, review) values (4,4,4,'La porqueria mas grande del cine');
insert into rating(movie_id, user_id, score, review) values (5,5,5,'Muy buena pelicula');

select * from category;
select * from users;
select * from movie;
select * from rating;
-- --------------------------------------------------
-- Parte A
-- --------------------------------------------------
-- a. Seleccione todas las categorías (id, category_name) que NO tienen asignadas películas.
select sector_A.id, sector_A.category_name from category as sector_A left join movie as sector_B on sector_B.id = sector_B.category_id where sector_B.category_id is null;

-- b. Seleccione la cantidad de calificaciones (reviews) que se hayan hecho en las categorías.
select sector_A.id, sector_A.category_name, count(sector_B.movie_id = sector_C.id) from category as sector_A inner join movie as sector_C on sector_A.id = sector_C.category_id inner join rating as sector_B on sector_C.id = sector_B.movie_id group by sector_A.id; 

-- c. Seleccione únicamente los nombres de las categorías en donde el usuario con ID = 1 ha puesto calificaciones (ratings).
select category_name from category as sector_A inner join movie as sector_B on sector_A.id = sector_B.category_id inner join rating as sector_C on sector_B.id = sector_C.movie_id where sector_C.user_id = 2;

-- d. Seleccione las películas que NO tienen calificaciones.
select * from movie as sector_A left join rating as sector_B on sector_A.id = sector_B.movie_id where sector_B.movie_id is null;

-- --------------------------------------------------
-- Parte B
-- --------------------------------------------------
-- a. Implemente una función llamada “number_of_ratings” que retorne la cantidad de calificaciones de una película a partir del ID de la película (movie_id)
drop function if exists number_of_ratings;
delimiter $$
create function number_of_ratings(id int) returns int reads sql data
begin
    declare cantidad int;
    select count(sector_1.review) into cantidad from rating as sector_1 where sector_1.movie_id = id;
    return(cantidad);
end $$
delimiter ;

select number_of_ratings(1);

-- b.  En esta base de datos se desea que no existan valores nulos a pesar que existen campos en las tablas movie y rating que aceptan nulos: release_date y review respectivamente. Implemente un procedimiento almacenado llamado “defaults” que reciba el nombre de la tabla a la que desea aplicar valores por default y de esta forma eliminar los nulos.
drop procedure if exists defaults;
delimiter $$
create procedure defaults(
	in nombre varchar(50)
)
begin
	case nombre
		when 'movie' then update movie set release_date = now() where release_date is null;
        when 'rating' then update rating set review = '' where review is null;
        else select 'No se encontro la tabla';
	end case;
end $$
delimiter ;
CALL defaults('category');

-- c. Implemente un procedimiento almacenado o una función llamada movie_rating_avg que retorne el promedio de las calificaciones de una película.
drop function if exists movie_rating_avg;
DELIMITER $$
create function movie_rating_avg(id int) returns int reads sql data
begin
	declare promedio int;
    select round(avg(sector_A.score)) into promedio from rating as sector_A where sector_A.movie_id = id;
    return promedio;
end$$
DELIMITER ;

select movie_rating_avg(1);