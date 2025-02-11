drop table if exists user cascade;
drop table if exists schedule cascade;
drop table if exists comment cascade;

create table user
(
    id         bigint       not null auto_increment primary key,
    email      varchar(255) not null unique,
    password   varchar(80)  not null,
    created_at datetime     not null default CURRENT_TIMESTAMP,
    updated_at datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table schedule
(
    id         bigint        not null auto_increment primary key,
    user_id    bigint        not null,
    title      varchar(200)  not null,
    content    varchar(3500) null,
    created_at datetime      not null default CURRENT_TIMESTAMP,
    updated_at datetime      not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

    constraint schedule_ibfk_1 foreign key (user_id) references user (id)
);

create table comment
(
    id          bigint       not null auto_increment primary key,
    user_id     bigint       not null,
    schedule_id bigint       not null,
    title       varchar(650) not null,
    created_at  datetime     not null default CURRENT_TIMESTAMP,
    updated_at  datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

    constraint comment_ibfk_1 foreign key (user_id) references user (id),
    constraint comment_ibfk_2 foreign key (schedule_id) references schedule (id)
);