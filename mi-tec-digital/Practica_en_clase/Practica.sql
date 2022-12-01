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

create unique index category_name_idx on category (category_name asc);

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

create unique index title_idx on movie (title asc);

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

create unique index movie_idx on rating (movie_id asc);
create unique index user_idx on rating (user_id asc);

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