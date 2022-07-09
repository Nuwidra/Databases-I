-- crear base de datos

show databases;

-- bases de datos

drop database if exists universidad;
create database universidad;
use universidad;


drop table if exists departamento;
drop table if exists profesor;
drop table if exists estudiante;

-- tablas

create table departamento(
	nombre varchar(20),
    edificio varchar(20),
    presupuesto int check (presupuesto > 0),
    primary key (nombre)
);

create table curso(
	id int,
    nombre varchar(60),
	creditos int,
    departamento varchar(40),
    primary key (id)
);

create table profesor(
	id int primary key,
    nombre varchar(30),
    apellido varchar(30),
    ciudad varchar(50)
);


create table estudiante(
	id int primary key,
    nombre varchar(30),
    apellido varchar(30),
    total_creditos int check (total_creditos > 0),
    departamento varchar(20),
    foreign key (departamento) references departamento(nombre)
);

create table tutor(
	estudiante_id int,
    profesor_id int, 
	foreign key(estudiante_id) references estudiante(id),
    foreign key(profesor_id) references profesor(id)
);

-- ingresar datos

insert into departamento(nombre, edificio, presupuesto) values ('Computacion', 'Exactas', 120000);
insert into departamento(nombre, edificio, presupuesto) values ('Biologia', 'Ciencias', 100000);
insert into departamento(nombre, edificio, presupuesto) values ('Quimica', 'Exactas', 110000);
insert into departamento(nombre, edificio, presupuesto) values ('Ing Civil', 'Ingenieria', 120000);
insert into departamento(nombre, edificio, presupuesto) values ('Filosofia', 'Sociales', 1000);
insert into departamento(nombre, edificio, presupuesto) values ('Fisica', 'Exactas', 10000);

-- ingresar profesores

insert into profesor(id, nombre, apellido, ciudad) values(1, 'Martin', 'Flores', 'San Ramon');
insert into profesor(id, nombre, apellido, ciudad) values(2, 'Allan', 'Cascante', 'Palmares');
insert into profesor(id, nombre, apellido, ciudad) values(3, 'Albert', 'Einstein', 'San Ramon');
insert into profesor(id, nombre, apellido, ciudad) values(4, 'Marco', 'Calvo', 'Palmares');
insert into profesor(id, nombre, apellido, ciudad) values(5, 'Jose', 'Herrera', 'San Ramon');
insert into profesor(id, nombre, apellido, ciudad) values(6, 'Carolina', 'Lizano', 'Palmares');
insert into profesor(id, nombre, apellido, ciudad) values(7, 'Raquel', 'Rodriguez', 'San Ramon');


-- ingresa estudiantes

insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(1, 'Steven', 'Alvarado', 8, 'Computacion');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(2, 'Lermith', 'Biarreta', 8, 'Biologia');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(3, 'Maria', 'Biarreta', 8, 'Biologia');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(4, 'Valeria', 'Calderon', 12, 'Biologia');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(5, 'Sebastian', 'Campos', 4, 'Quimica');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(6, 'Josue', 'Castro', 11, 'Ing Civil');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(7, 'Susana', 'Cen', 16, 'Filosofia');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(8, 'Johan', 'Echeverria', 8, 'Fisica');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(9, 'Junior', 'Herrera', 8, 'Computacion');
insert into estudiante(id, nombre, apellido, total_creditos, departamento) values(10, 'Alejandro', 'Loaiza', 4, 'Fisica');

-- ingresar cursos

insert into curso(id, nombre, creditos, departamento) values (1, 'Programacion I', 4, 'Ingenieria en Computacion');
insert into curso(id, nombre, creditos, departamento) values (2, 'Programacion II', 4, 'Ingenieria en Computacion');
insert into curso(id, nombre, creditos, departamento) values (3, 'Programacion III', 1, 'Ingenieria en Computacion');
insert into curso(id, nombre, creditos, departamento) values (4, 'Programacion IV', 4, 'Ingenieria en Computacion');
insert into curso(id, nombre, creditos, departamento) values (5, 'Programacion V', 5, 'Ingenieria en Computacion');

select * from departamento;
select * from profesor;

-- Se crea una nueva columna
alter table estudiante add column fecha_nacimiento timestamp not null after total_creditos;

-- Le pone el mismo valor a la columna agregada
update estudiante set fecha_nacimiento = '2000-08-08' where id in (1,2,3,4,5,6,7,8,9, 10);
select * from estudiante;
-- ===================================================================================
--                                PROYECTO PROGRAMADO 2                                                            
-- ===================================================================================
--                                                              Jonathan Quesada Salas
--                                                                          2020023583
-- ===================================================================================

-- ===================================================================================
--                                                       PROCEDIMIENTOS DE ESTUDIANTES
-- ===================================================================================

-- ===================================================================================
-- Procedimiento almacenado para seleccionar todos los estudiantes
-- ===================================================================================
drop procedure if exists selecionar_todos_estudiantes;
delimiter $$
create procedure selecionar_todos_estudiantes()
begin
	start transaction;
	select id, nombre, apellido, fecha_nacimiento, total_creditos from estudiante;
    commit;
end
$$
delimiter ;
-- call selecionar_todos_estudiantes();

-- ===================================================================================
-- Procedimiento almacenado para encontrar a un estudiante por su ID
-- ===================================================================================
drop procedure if exists encontrar_estudiante_por_id;
delimiter $$
create procedure encontrar_estudiante_por_id(in id_estudiante int)
begin
	start transaction;
	select id, nombre, apellido, fecha_nacimiento, total_creditos from estudiante where id = id_estudiante;
    commit;
end
$$
delimiter ;
-- call encontrar_estudiante_por_id(1);

-- ===================================================================================
-- Procedimiento almacenado para agregar un nuevo estudiante
-- ===================================================================================
drop procedure if exists agregar_nuevo_estudiante;
delimiter $$
create procedure agregar_nuevo_estudiante(in estudiante_id int, nombre_estudiante text, apellido_estudiante text, fecha_nacimiento timestamp, total_creditos int)
begin
	start transaction;
    insert into estudiante (id, nombre, apellido, fecha_nacimiento, total_creditos) values (estudiante_id, nombre_estudiante, apellido_estudiante, fecha_nacimiento, total_creditos);
    commit;
end
$$
delimiter ;
-- call agregar_nuevo_estudiante(100, 'Jonathan', 'Quesada', '2001-07-09', 15);

-- ===================================================================================
-- Procedimiento almacenado para actualizar un estudiante existente
-- ===================================================================================
drop procedure if exists actualizar_estudiante_existente;
delimiter $$
create procedure actualizar_estudiante_existente(in estudiante_id int, nombre_estudiante text, apellido_estudiante text, fecha_nacimiento_de_estudiante timestamp, total_creditos_de_estudiante int)
begin
	start transaction;
    update estudiante set nombre = nombre_estudiante, apellido = apellido_estudiante, fecha_nacimiento = fecha_nacimiento_de_estudiante, total_creditos = total_creditos_de_estudiante where id = estudiante_id;
    commit;
end
$$
delimiter ;
-- call actualizar_estudiante_existente(2, 'Nuwidra', 'UwU', '2020-01-01', 30);

-- ===================================================================================
-- Procedimiento almacenado para eliminar un estudiante existente
-- ===================================================================================
drop procedure if exists eliminar_estudiante;
delimiter $$
create procedure eliminar_estudiante(in estudiante_id int)
begin
	start transaction;
    delete from estudiante where id = estudiante_id;
    commit;
end
$$
delimiter ;
-- call eliminar_estudiante(1);

-- ===================================================================================
-- Procedimiento almacenado para encontrar a un estudiante por su apellido
-- ===================================================================================
drop procedure if exists find_by_last_name_student;
delimiter $$
create procedure find_by_last_name_student(in apellido_estudiante text)
begin
	start transaction;
    select id, nombre, apellido, fecha_nacimiento, total_creditos from estudiante where apellido = apellido_estudiante;
    commit;
end
$$
delimiter ;
-- call find_by_last_name_student('Cen');

-- ===================================================================================
-- Procedimiento almacenado para ordenar alfabeticamente el apellido del estudiante
-- ===================================================================================
drop procedure if exists order_by_last_name_student;
delimiter $$
create procedure order_by_last_name_student()
begin
	start transaction;
    select id, nombre, apellido, fecha_nacimiento, total_creditos from estudiante order by apellido asc;
    commit;
end
$$
delimiter ;
-- call order_by_last_name_student();

-- ===================================================================================
--                                                             PROCEDIMIENTOS DE CURSO
-- ===================================================================================

-- ===================================================================================
-- Procedimiento almacenado para seleccionar todos los cursos
-- ===================================================================================
drop procedure if exists seleccionar_todos_cursos;
delimiter $$
create procedure seleccionar_todos_cursos()
begin
	start transaction;
	select id, nombre, creditos, departamento from curso;
    commit;
end
$$
delimiter ;
-- call seleccionar_todos_cursos();

-- ===================================================================================
-- Procedimiento almacenado para encontrar a un curso por su ID
-- ===================================================================================
drop procedure if exists encontrar_curso_por_id;
delimiter $$
create procedure encontrar_curso_por_id(in id_curso int)
begin
    start transaction;
	select id, nombre, creditos, departamento from curso where id = id_curso;
    commit;
end
$$
delimiter ;
-- call encontrar_curso_por_id(1);

-- ===================================================================================
-- Procedimiento almacenado para agregar un nuevo curso
-- ===================================================================================
drop procedure if exists agregar_nuevo_curso;
delimiter $$
create procedure agregar_nuevo_curso(in curso_id int, nombre_curso text, creditos_curso int, departamento_curso text)
begin
	start transaction;
    insert into curso (id, nombre, creditos, departamento) values (curso_id, nombre_curso, creditos_curso, departamento_curso);
    commit;
end
$$
delimiter ;
-- call agregar_nuevo_curso(6, 'Programacion VI', 6, 'Ingenieria en Computacion');

-- ===================================================================================
-- Procedimiento almacenado para actualizar un curso existente
-- ===================================================================================
drop procedure if exists actualizar_curso_existente;
delimiter $$
create procedure actualizar_curso_existente(in curso_id int, nombre_curso text, creditos_curso int, departamento_curso text)
begin
	start transaction;
    update curso set nombre = nombre_curso, creditos = creditos_curso, departamento = departamento_curso where id = curso_id;
    commit;
end
$$
delimiter ;
-- call actualizar_curso_existente(1, 'Programacion X', 8, 'Ingenieria en Computacion');

-- ===================================================================================
-- Procedimiento almacenado para eliminar un curso existente
-- ===================================================================================
drop procedure if exists eliminar_curso;
delimiter $$
create procedure eliminar_curso(in curso_id int)
begin
	start transaction;
    delete from curso where id = curso_id;
    commit;
end
$$
delimiter ;
-- call eliminar_curso(1);

-- ===================================================================================
-- Procedimiento almacenado para encontrar un departamento en especifico
-- ===================================================================================
drop procedure if exists find_by_department_course;
delimiter $$
create procedure find_by_department_course(in departamento_curso text)
begin
	start transaction;
    select id, nombre, creditos, departamento from curso where departamento = departamento_curso;
    commit;
end
$$
delimiter ;
-- call find_by_department_course('Ingenieria en Computacion');

-- ===================================================================================
--                                                          PROCEDIMIENTOS DE PROFESOR
-- ===================================================================================

-- ===================================================================================
-- Procedimiento almacenado para seleccionar todos los profesores
-- ===================================================================================
drop procedure if exists seleccionar_todos_profesores;
delimiter $$
create procedure seleccionar_todos_profesores()
begin
	start transaction;
	select id, nombre, apellido, ciudad from profesor;
    commit;
end
$$
delimiter ;
-- call seleccionar_todos_profesores();

-- ===================================================================================
-- Procedimiento almacenado para encontrar un profesor por su ID
-- ===================================================================================
drop procedure if exists encontrar_profesor_por_id;
delimiter $$
create procedure encontrar_profesor_por_id(in profesor_id int)
begin
	start transaction;
	select id, nombre, apellido, ciudad from profesor where id = profesor_id;
    commit;
end
$$
delimiter ;
-- call encontrar_profesor_por_id(1);

-- ===================================================================================
-- Procedimiento almacenado para agregar un nuevo profesor
-- ===================================================================================
drop procedure if exists agregar_nuevo_profesor;
delimiter $$
create procedure agregar_nuevo_profesor(in profesor_id int, nombre_profesor text, apellido_profesor text, ciudad_profesor text)
begin
	start transaction;
    insert into profesor (id, nombre, apellido, ciudad) values (profesor_id, nombre_profesor, apellido_profesor, ciudad_profesor);
    commit;
end
$$
delimiter ;
-- call agregar_nuevo_profesor(100, 'Jonathan', 'Quesada', 'Santiago');

-- ===================================================================================
-- Procedimiento almacenado para actualizar un profesor existente
-- ===================================================================================
drop procedure if exists actualizar_profesor_existente;
delimiter $$
create procedure actualizar_profesor_existente(in profesor_id int, nombre_profesor text, apellido_profesor text, ciudad_profesor text)
begin
	start transaction;
    update profesor set nombre = nombre_profesor, apellido = apellido_profesor, ciudad = ciudad_profesor where id = profesor_id;
    commit;
end
$$
delimiter ;
-- call actualizar_profesor_existente(100, 'Nuwidra', 'UwU', 'San Ramon');

-- ===================================================================================
-- Procedimiento almacenado para eliminar un profesor existente
-- ===================================================================================
drop procedure if exists eliminar_profesor;
delimiter $$
create procedure eliminar_profesor(in profesor_id int)
begin
	start transaction;
    delete from profesor where id = profesor_id;
    commit;
end
$$
delimiter ;
-- call eliminar_profesor(100);

-- ===================================================================================
-- Procedimiento almacenado para encontrar un profesor con una ciudad
-- ===================================================================================
drop procedure if exists find_by_city_professor;
delimiter $$
create procedure find_by_city_professor(in ciudad_profesor text)
begin
	start transaction;
    select id, nombre, apellido, ciudad from profesor where ciudad = ciudad_profesor;
    commit;
end
$$
delimiter ;
-- call find_by_city_professor('Palmares');

-- ===================================CREAR EL USUARIO===================================
use universidad;
drop user if exists 'universidad_user';
create user 'universidad_user' identified by 'universidad_pass';
grant insert, delete, update on universidad.* to 'universidad_user';
grant execute on universidad.* to 'universidad_user';
flush privileges;
-- ======================================================================================
