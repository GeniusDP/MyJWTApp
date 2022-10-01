drop table if exists app_users, roles, app_user_roles;

create table if not exists app_users
(
    id       bigserial primary key,
    username text unique not null,
    password text not null,
    refresh_token text
);

create table if not exists roles
(
    id   bigserial primary key,
    name text unique not null
);

insert into roles (name)
values ('ROLE_USER'), ('ROLE_ADMIN');

create table if not exists app_user_roles
(
    id   bigserial primary key,
    app_user_id bigint references app_users(id),
    role_id bigint references roles(id)
);


insert into app_users (username, password)
values ('bogdan', 'zaranik'), ('max', 'borisov');

insert into app_user_roles (app_user_id, role_id)
values (1, 1), (1, 2), (2, 1);