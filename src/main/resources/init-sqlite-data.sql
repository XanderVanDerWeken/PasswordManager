drop table if exists t_password_entries;

create table t_password_entries(
    id integer primary key autoincrement,
    username text not null,
    password text not null,
    platform text not null
);

insert into t_password_entries(username, password, platform)
values ('xa3687va', 'Asselborn1', 'moodle')