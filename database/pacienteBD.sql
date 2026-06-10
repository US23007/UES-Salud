create database UES_SALUD;

use ues_salud;

create table paciente(
	id_paciente int primary key,
    nombre_paciente varchar(100) not null,
    apellido_paciente varchar(100) not null,
    carnet varchar(7) unique,
    sintomas text not null,
    fecha_nacimiento date not null
);