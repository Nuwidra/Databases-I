SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

drop database if exists caso_dos;
create database caso_dos;
use caso_dos;

create table persona(
	cedula int unsigned not null auto_increment,
    nombre varchar(50),
    tipo int unsigned not null,
    primary key(cedula),
	key llave_tipo (tipo),
    constraint `tipo_persona` foreign key (tipo) references tipo(identificador_tipo) on delete restrict on update cascade
);

create table persona_fisica(
	cedula int unsigned not null,
	fecha_nacimiento varchar(50),
    key llave_persona_fisica_cedula(cedula),
    constraint `fisica_cedula` foreign key (cedula) references persona(cedula) on delete restrict on update cascade
);

create table persona_juridica(
	cedula int unsigned not null,
	nombre_comercial varchar(50),
    key llave_persona_juridica_cedula(cedula),
    constraint `juridica_cedula` foreign key (cedula) references persona(cedula) on delete restrict on update cascade
);

create table cuenta_por_cobrar(
	numero_cuenta int unsigned not null auto_increment,
    cedula int unsigned not null,
    monto int,
    fecha_vencimiento varchar(50),
	primary key(numero_cuenta),
    key llave_cuenta_con_cedula(cedula),
	constraint `cuenta_con_cedula` foreign key (cedula) references persona(cedula) on delete restrict on update cascade
);

create table persona_cuenta(
	cedula int unsigned not null auto_increment,
    key llave_persona_cedula(cedula),
    constraint `cedula_cuenta_persona` foreign key (cedula) references persona(cedula) on delete restrict on update cascade,
    numero_cuenta int unsigned not null,
    key llave_persona_cuenta(numero_cuenta),
    constraint `cuenta_numero_persona` foreign key (numero_cuenta) references cuenta_por_cobrar(numero_cuenta) on delete restrict on update cascade
);

create table cuenta_abono(
	numero_cuota int unsigned not null auto_increment,
    key llave_persona_cedula(numero_cuenta),
    constraint `cuenta_cobrar_abono` foreign key (numero_cuenta) references cuenta_por_cobrar(numero_cuenta) on delete restrict on update cascade,
    numero_cuenta int unsigned not null,
    key llave_abono(numero_cuota),
    constraint `abono_cuenta` foreign key (numero_cuota) references abono(numero_cuota) on delete restrict on update cascade
);

create table abono(
	numero int unsigned not null,
	numero_cuota int unsigned not null,
    fecha varchar(50),
    monto int,
	primary key (numero_cuota),
    key llave_abono_cuenta(numero_cuota),
    constraint `abono_cuenta_cuota` foreign key (numero_cuota) references cuenta_abono (numero_cuota) on delete restrict on update cascade
);

create table formas_pago(
	codigo int unsigned not null auto_increment,
    nombre varchar(50),
    numero_cuota int unsigned not null,
    primary key(codigo),
	key llave_numero_cuota (numero_cuota),
    constraint `cuota_numero` foreign key (numero_cuota) references abono (numero_cuota) on delete restrict on update cascade
);

-- INSERTAR DATOS --
insert into persona(cedula, nombre, tipo) values(1,'Jorge',1);
insert into persona(cedula, nombre, tipo) values(2,'Mario',1);
insert into persona(cedula, nombre, tipo) values(3,'Ana',1);
insert into persona(cedula, nombre, tipo) values(4,'Maria',1);
insert into persona(cedula, nombre, tipo) values(5,'Lupe',1);
insert into persona(cedula, nombre, tipo) values(6,'Marico',1);
insert into persona(cedula, nombre, tipo) values(7,'Pepe',1);
insert into persona(cedula, nombre, tipo) values(8,'Raul',1);
insert into persona(cedula, nombre, tipo) values(9,'Pedro',1);
insert into persona(cedula, nombre, tipo) values(10,'Pablo',1);
insert into persona(cedula, nombre, tipo) values(11,'Soraka',2);
insert into persona(cedula, nombre, tipo) values(12,'Yasuo',2);
insert into persona(cedula, nombre, tipo) values(13,'Sion',2);
insert into persona(cedula, nombre, tipo) values(14,'Lulu',2);
insert into persona(cedula, nombre, tipo) values(15,'Nunu',2);
insert into persona(cedula, nombre, tipo) values(16,'Willump',2);
insert into persona(cedula, nombre, tipo) values(17,'Garen',2);
insert into persona(cedula, nombre, tipo) values(18,'Darius',2);
insert into persona(cedula, nombre, tipo) values(19,'Malzahar',2);
insert into persona(cedula, nombre, tipo) values(20,'Ahri',2);

select * from persona;

insert into persona_fisica(cedula, fecha_nacimiento) values (1,'1 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (2,'2 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (3,'3 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (4,'4 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (5,'5 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (6,'6 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (7,'7 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (8,'8 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (9,'9 de enero de 2021');
insert into persona_fisica(cedula, fecha_nacimiento) values (10,'10 de enero de 2021');

select * from persona_fisica;

insert into persona_juridica(cedula, nombre_comercial) values (11,'Destroza mundosos');
insert into persona_juridica(cedula, nombre_comercial) values (12,'Destroza años');
insert into persona_juridica(cedula, nombre_comercial) values (13,'El pepe');
insert into persona_juridica(cedula, nombre_comercial) values (14,'Grillo');
insert into persona_juridica(cedula, nombre_comercial) values (15,'Manteca');
insert into persona_juridica(cedula, nombre_comercial) values (16,'Los testigos de Goku');
insert into persona_juridica(cedula, nombre_comercial) values (17,'Los UwUs');
insert into persona_juridica(cedula, nombre_comercial) values (18,'Otakus');
insert into persona_juridica(cedula, nombre_comercial) values (19,'Loleros');
insert into persona_juridica(cedula, nombre_comercial) values (20,'Pecadores');

select * from persona_juridica;

insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (1,1,100, '1 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (2,2,200, '2 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (3,3,300, '3 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (4,4,400, '4 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (5,5,500, '5 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (6,6,600, '6 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (7,7,700, '7 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (8,8,800, '8 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (9,9,900, '9 de julio del 2021');
insert into cuenta_por_cobrar(numero_cuenta, cedula, monto, fecha_vencimiento) values (10,10,1000, '10 de julio del 2021');

select * from cuenta_por_cobrar;

insert into abono(numero, numero_cuota, fecha, monto) values (1,1,'1 de diciembre',100);
insert into abono(numero, numero_cuota, fecha, monto) values (2,2,'2 de diciembre',200);
insert into abono(numero, numero_cuota, fecha, monto) values (3,3,'3 de diciembre',300);
insert into abono(numero, numero_cuota, fecha, monto) values (4,4,'4 de diciembre',400);
insert into abono(numero, numero_cuota, fecha, monto) values (5,5,'5 de diciembre',500);
insert into abono(numero, numero_cuota, fecha, monto) values (6,6,'6 de diciembre',600);
insert into abono(numero, numero_cuota, fecha, monto) values (7,7,'7 de diciembre',700);
insert into abono(numero, numero_cuota, fecha, monto) values (8,8,'8 de diciembre',800);
insert into abono(numero, numero_cuota, fecha, monto) values (9,9,'9 de diciembre',900);
insert into abono(numero, numero_cuota, fecha, monto) values (10,10,'10 de diciembre',1000);

select * from abono;

insert into formas_pago(codigo, nombre, numero_cuota) values(1,'Tarjeta',1);
insert into formas_pago(codigo, nombre, numero_cuota) values(2,'Efectivo',2);
insert into formas_pago(codigo, nombre, numero_cuota) values(3,'Tarjeta',3);
insert into formas_pago(codigo, nombre, numero_cuota) values(4,'Efectivo',4);
insert into formas_pago(codigo, nombre, numero_cuota) values(5,'Tarjeta',5);
insert into formas_pago(codigo, nombre, numero_cuota) values(6,'Efectivo',6);
insert into formas_pago(codigo, nombre, numero_cuota) values(7,'Tarjeta',7);
insert into formas_pago(codigo, nombre, numero_cuota) values(8,'Efectivo',8);
insert into formas_pago(codigo, nombre, numero_cuota) values(9,'Tarjeta',9);
insert into formas_pago(codigo, nombre, numero_cuota) values(10,'Efectivo',10);

select * from formas_pago;

insert into persona_cuenta(cedula, numero_cuenta) values(1,1);
insert into persona_cuenta(cedula, numero_cuenta) values(2,2);
insert into persona_cuenta(cedula, numero_cuenta) values(3,3);
insert into persona_cuenta(cedula, numero_cuenta) values(4,4);
insert into persona_cuenta(cedula, numero_cuenta) values(5,5);
insert into persona_cuenta(cedula, numero_cuenta) values(6,6);
insert into persona_cuenta(cedula, numero_cuenta) values(7,7);
insert into persona_cuenta(cedula, numero_cuenta) values(8,8);
insert into persona_cuenta(cedula, numero_cuenta) values(9,9);
insert into persona_cuenta(cedula, numero_cuenta) values(10,10);

select * from persona_cuenta;


insert into cuenta_abono(numero_cuota, numero_cuenta) values(1,1);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(2,2);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(3,3);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(4,4);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(5,5);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(6,6);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(7,7);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(8,8);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(9,9);
insert into cuenta_abono(numero_cuota, numero_cuenta) values(10,10);

select * from cuenta_abono;