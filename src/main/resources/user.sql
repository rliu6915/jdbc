create table if not exists USERS (
    id bigint auto_increment primary key,
    name varchar(255)
);
truncate table USERS;
insert into USERS (name) values ('Alice');
insert into USERS (name) values ('Bob');