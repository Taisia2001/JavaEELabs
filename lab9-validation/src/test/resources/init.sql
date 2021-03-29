create table users
(
    id       int primary key auto_increment,
    login    varchar(30) not null,
    password varchar(40) not null,
    role_id      integer     not null,
    unique uniq_login (login)
        constraint fk_user_role_id
        foreign key (role_id)
        references roles(id)
);

create table roles
(
    id         int primary key auto_increment,
    role varchar(30) not null,
    unique uniq_role (role)
);

create table permissions
(
    id         int primary key auto_increment,
    permission varchar(30) not null,
    unique uniq_permission (permission)
);

create table role_to_permissions (
                                     role_id int not null,
                                     permission_id int not null,
                                     constraint fk_user_to_permission_user foreign key (role_id) references roles(id),
                                     constraint fk_user_to_permission_permission foreign key (permission_id) references permissions(id)
);
create table books
(
    id  int primary key auto_increment,
    isbn  varchar(8)    not null,
    title varchar(200)  not null,
    author varchar(200) not null
);

create table user_favourite
(
    user_id integer not null,
    book_id   varchar(8) not null,
    constraint fk_user_favourite_user_id
        foreign key (user_id)
            references users(id),
    constraint fk_client_has_favourite_book_book_id
        foreign key (book_id)
            references books(isbn),
    constraint user_favourite_pk
        primary key (user_id, book_id)
);

insert into books (id, isbn, title, author)
values (1, 'isbn1', 'Emma', 'Jane Austen')
     , (2, 'isbn2', 'The Idiot', 'Fyodor Dostoyevsky')
     , (3, 'isbn3', 'Game of Thrones', 'George R.R. Martin')
     , (4, 'isbn4', 'Romeo and Juliet', 'William Shakespeare')
     , (5, 'isbn5', 'Mockingjay', 'Suzanne Collins');

insert into permissions (permission) values
('VIEW_BOOKS'),
('ADD_BOOK'),
('ADD_TO_FAVOURITE'),
('VIEW_FAVOURITE'),
('DELETE_FROM_FAVOURITE');

insert into roles (id, role) values
(1, 'ADMIN'),
(2, 'USER');

insert into role_to_permissions (role_id, permission_id) values
(1, (select id from permissions where permission = 'VIEW_BOOKS' or permission = 'ADD_BOOK')),
(2, (select id from permissions where permission = 'VIEW_BOOKS' or permission = 'ADD_TO_FAVOURITE'
                                      or permission = 'VIEW_FAVOURITE' or permission = 'DELETE_FROM_FAVOURITE'));


insert into users(id, login, password, role_id)
values (1, 'client1', 'client1', 2),
       (2, 'client2', 'client2', 2),
       (3, 'admin', 'admin', 1);

insert into user_favourite(user_id, book_id)
values (1, 1),
       (1, 3),
       (1, 5);

