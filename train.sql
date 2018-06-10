create database train;
use train;
create table users(
    id int primary key auto_increment,
    user_name varchar(50) not null unique,
    password varchar(50) not null,
    telphone varchar(13),
    register_time timestamp default now(),
    last_login_time timestamp,
    last_login_ip varchar(50),
    status int default 1,
    remark varchar(500)
)engine=innoDB default charset=utf8;
create table role(
        id int primary key auto_increment,
        name varchar(50) not null,
        remark varchar(500)
)engine=innoDB default charset=utf8;

create table user_role(
    user_id int,
    role_id int,
    constraint fk_user_id foreign key(user_id) references users(id),
    constraint fk_role_id foreign key(role_id) references role(id)
)engine=innoDB default charset=utf8;

create table contact(
        id int primary key auto_increment,
    user_id int not null,
    name varchar(124) not null,
    telphone varchar(124),
    identity_card char(35) not null,
    CONSTRAINT contact_user_id FOREIGN KEY (user_id) REFERENCES users(id)
)engine=innoDB default charset=utf8;

create table train(
        id int primary key auto_increment,
        train_serial varchar(128) not null,
        train_site_detail varchar(1024) not null,
        status int default 1
)engine=innoDB default charset=utf8;

create table train_site(
        id int primary key auto_increment,
        train_id int not null,
        site int not null,
        constraint fk_train_id foreign key(train_id) references train(id)
)engine=innoDB default charset=utf8;

create table train_detail(
        id int primary key auto_increment,
        train_id int not null,
        time timestamp not null,
    seat_number int not null,
    status int default 1,
        constraint fk_train_id_detail foreign key(train_id) references train(id)
)engine=innoDB default charset=utf8;

create table orders(
        id int primary key auto_increment,
        user_id int not null,
        time timestamp default now(),
        begin_time timestamp,
        end_time timestamp,
        begin_site varchar(64),
        end_site varchar(64),
        begin_index int,
        end_index int,
        use_time varchar(64),
        price float,
        train_detail_id int not null,
        status int default 1,
    CONSTRAINT contact_user_id_order FOREIGN KEY (user_id) REFERENCES users(id)
)engine=innoDB default charset=utf8;


create table orders_detail(
        id int primary key auto_increment,
        order_id int not null,
       contact_id int not null,
        seat_serial varchar(64),
    CONSTRAINT fk_contact_id FOREIGN KEY (contact_id) REFERENCES contact(id),
 CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id)
)engine=innoDB default charset=utf8;


create table sites(
        id int primary key auto_increment,
        site varchar(50) not null,
        insert_time timestamp default now(),
        province varchar(50),
        remark varchar(200)
)engine=innoDB default charset=utf8;


create table notify(
        id int primary key auto_increment,
        title varchar(1024) not null default "",
        content varchar(1024) not null default "",
insert_time timestamp default now(),
        expire_time timestamp
)engine=innoDB default charset=utf8;

create table advices(
        id int primary key auto_increment,
        username varchar(16),
        email varchar(32),
        content varchar(1024),
        insert_time timestamp
)engine=innoDB default charset=utf8;


insert into users(user_name,password,telphone) values("admin","1234","1211111111")
insert into users(user_name,password,telphone) values("user","1234","1211111111")

insert into role(name,remark) value("ROLE_ADMIN","admin")
insert into role(name,remark) value("ROLE_USER","user")

insert into user_role value(1,1)
insert into user_role value(2,2)

INSERT into sites(site,province) values('福州站','福建省');
INSERT into sites(site,province) values('福州南站','福建省');
INSERT into sites(site,province) values('福清站','福建省');
INSERT into sites(site,province) values('莆田站','福建省');
INSERT into sites(site,province) values('涵江站','福建省');
INSERT into sites(site,province) values('泉州站','福建省');
INSERT into sites(site,province) values('晋江站','福建省');
INSERT into sites(site,province) values('厦门北站','福建省');
INSERT into sites(site,province) values('厦门站','福建省');
INSERT into sites(site,province) values('漳州站','福建省');
INSERT into sites(site,province) values('龙岩站','福建省');
INSERT into sites(site,province) values('宁德站','福建省');
INSERT into sites(site,province) values('三明站','福建省');

alter table train_detail modify column seat_number VARCHAR(124);
alter table orders add column begin_index int;
alter table orders add column end_index int;
