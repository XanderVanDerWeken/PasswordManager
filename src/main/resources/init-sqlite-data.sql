--drop table if exists t_password_entries;

create table if not exists t_password_entries(
    id integer primary key autoincrement,
    username text not null,
    password text not null,
    platform text not null
);