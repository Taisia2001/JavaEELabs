

insert into books (id, isbn, title, author)
values (1, 'isbn1', 'Emma', 'Jane Austen')
     , (2, 'isbn2', 'The Idiot', 'Fyodor Dostoyevsky')
     , (3, 'isbn3', 'Game of Thrones', 'George R.R. Martin')
     , (4, 'isbn4', 'Romeo and Juliet', 'William Shakespeare')
     , (5, 'isbn5', 'Mockingjay', 'Suzanne Collins')
     , (6, 'isbn6', 'One Hundred Years of Solitude', 'Gabriel García Márquez')
     , (7, 'isbn7', 'In Cold Blood', 'Truman Capote')
     , (8, 'isbn8', 'Jane Eyre', 'Charlotte Bronte')
     , (9, 'isbn9', 'The Secret History', 'Donna Tartt')
     , (10, 'isbn10', 'The Call of the Wild', 'Jack London')
     , (11, 'isbn11', 'The Chrysalids', 'John Wyndham')
     , (12, 'isbn12', 'Moby-Dick', 'Herman Melville')
     , (13, 'isbn13', 'The Lion, the Witch and the Wardrobe', 'C.S. Lewis')
     , (14, 'isbn14', 'The Death of the Heart', 'Elizabeth Bowen')
     , (15, 'isbn15', 'Frankenstein', 'Mary Shelley')
     , (16, 'isbn16', 'The Master and Margarita', 'Mikhail Bulgakov')
     , (17, 'isbn17', 'The Go-Between', 'L. P. Hartley')
     , (18, 'isbn18', 'Buddenbrooks', 'Thomas Mann')
     , (19, 'isbn19', 'The Grapes of Wrath', 'John Steinbeck')
     , (20, 'isbn20', 'Beloved', 'Toni Morrison');

insert into permissions (id, permission) values
(1, 'VIEW_BOOKS'),
(2, 'ADD_BOOK'),
(3, 'ADD_TO_FAVOURITE'),
(4, 'VIEW_FAVOURITE'),
(5, 'DELETE_FROM_FAVOURITE');

insert into roles (id, role) values
(1, 'ADMIN'),
(2, 'USER');

insert into role_to_permissions (role_id, permission_id) values
(1, (select id from permissions where permission = 'VIEW_BOOKS')),
(1, (select id from permissions where  permission = 'ADD_BOOK')),
(1, (select id from permissions where permission = 'ADD_TO_FAVOURITE')),
(1, (select id from permissions where permission = 'VIEW_FAVOURITE')),
(1, (select id from permissions where permission = 'DELETE_FROM_FAVOURITE')),
(2, (select id from permissions where permission = 'VIEW_BOOKS')),
(2, (select id from permissions where permission = 'ADD_TO_FAVOURITE')),
(2, (select id from permissions where permission = 'VIEW_FAVOURITE')),
(2, (select id from permissions where permission = 'DELETE_FROM_FAVOURITE'));


insert into users(id, login, password, role_id)
values (1, 'user1', 'user1', 2),
       (2, 'user2', 'user2', 2),
       (3, 'admin', 'admin', 1);

insert into user_favourite(user_id, book_id)
values (1, 1),
       (1, 3),
       (1, 5);