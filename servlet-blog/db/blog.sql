drop database if exists blogdemo;
create database blogdemo default charset utf8mb4;

use blogdemo;



drop table IF EXISTS user;
create table user(
    id int primary key auto_increment,
    name varchar(20),
    create_time timestamp
);

drop table IF EXISTS article;
create table article(
                        id int primary key auto_increment,
                        title varchar(50) not null,
                        content mediumtext not null,
                        user_id int,
                        create_time timestamp,
                        foreign key(user_id) references user(id)
);

insert into user(name, create_time) VALUE ('abc', CURRENT_TIMESTAMP);

insert into article(title, content, user_id, create_time) VALUES ('标题1', '内容2', 1, CURRENT_TIMESTAMP);
insert into article(title, content, user_id, create_time) VALUES ('标题2', '内容2', 1, CURRENT_TIMESTAMP);
insert into article(title, content, user_id, create_time) VALUES ('标题3', '内容3', 1, CURRENT_TIMESTAMP);