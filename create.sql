create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
create table hibernate_sequence (next_val bigint) type=MyISAM
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table precio (id integer not null, valor double precision not null, tiempo_id integer not null, tipovehiculo_id integer not null, primary key (id)) type=MyISAM
create table registrovehiculo (id integer not null, fechaentrada datetime not null, fechasalida datetime, valorPagado double precision, placa varchar(255) not null, primary key (id)) type=MyISAM
create table tiempo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table tipovehiculo (id integer not null, descripcion varchar(255) not null, primary key (id)) type=MyISAM
create table vehiculo (placa varchar(255) not null, cilindraje integer, tipovehiculo_id integer not null, primary key (placa)) type=MyISAM
alter table precio add constraint FK5u56q7mclfolovumn2jh3gr43 foreign key (tiempo_id) references tiempo (id)
alter table precio add constraint FKebh1ahxbtw9r2ogdg54dx7b7i foreign key (tipovehiculo_id) references tipovehiculo (id)
alter table vehiculo add constraint FKf2e9b9fxxpxexjd1bxe6bd3d7 foreign key (tipovehiculo_id) references tipovehiculo (id)
