CREATE DATABASE transport_service;
use transport_service;

CREATE TABLE drivers_repo (
                              id integer auto_increment primary key ,
                              name varchar(30) not null,
                              surname varchar(30) not null ,
                              phone_number varchar(30) not null ,
                              qualification varchar(30) not null
);

CREATE TABLE transports_repo (
                                 id integer auto_increment primary key ,
                                 brand varchar(30) not null ,
                                 numbers_passengers integer not null ,
                                 qualification varchar(30) not null ,
                                 driver_id integer,
                                 enum_of_transport varchar(30),
                                 type varchar(30),
                                 numbers_of_doors integer,
                                 numbers_of_railcar integer,
                                 foreign key (driver_id) references drivers_repo(id)
);

CREATE TABLE routes_repo (
                             id integer auto_increment primary key ,
                             start_way varchar(30) not null ,
                             end_way varchar(30) not null ,
                             drivers_id integer,
                             transports_id integer,
                             foreign key (drivers_id) references transports_repo (id)
);

