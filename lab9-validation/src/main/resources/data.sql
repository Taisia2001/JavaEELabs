insert into books (id, isbn, title, author)
values (1, 'ISBN-13 978-3-598-21523-0', 'Emma', 'Jane Austen')
     , (2, 'ISBN-13 978-3-598-21519-3', 'The Idiot', 'Fyodor Dostoyevsky')
     , (3, 'ISBN-13 978-3-598-21518-6', 'Game of Thrones', 'George R.R. Martin')
     , (4, 'ISBN-13 978-3-598-21517-9', 'Romeo and Juliet', 'William Shakespeare')
     , (5, 'ISBN-13 978-3-598-21516-2', 'Mockingjay', 'Suzanne Collins')
     , (6, 'ISBN-13 978-3-598-21515-5', 'One Hundred Years of Solitude', 'Gabriel García Márquez')
     , (7, 'ISBN-13 978-3-598-21514-8', 'In Cold Blood', 'Truman Capote')
     , (8, 'ISBN-13 978-3-598-21513-1', 'Jane Eyre', 'Charlotte Bronte')
     , (9, 'ISBN-13 978-3-598-21512-4', 'The Secret History', 'Donna Tartt')
     , (10, 'ISBN-13 978-3-598-21511-7', 'The Call of the Wild', 'Jack London')
     , (11, 'ISBN-13 978-3-598-21510-0', 'The Chrysalids', 'John Wyndham')
     , (12, 'ISBN-13 978-3-598-21509-4', 'Moby-Dick', 'Herman Melville')
     , (13, 'ISBN-13 978-3-598-21508-7', 'The Lion, the Witch and the Wardrobe', 'C.S. Lewis')
     , (14, 'ISBN-13 978-3-598-21507-0', 'The Death of the Heart', 'Elizabeth Bowen')
     , (15, 'ISBN-13 978-3-598-21506-3', 'Frankenstein', 'Mary Shelley')
     , (16, 'ISBN-13 978-3-598-21505-6', 'The Master and Margarita', 'Mikhail Bulgakov')
     , (17, 'ISBN-13 978-3-598-21504-9', 'The Go-Between', 'L. P. Hartley')
     , (18, 'ISBN-13 978-3-598-21503-2', 'Buddenbrooks', 'Thomas Mann')
     , (19, 'ISBN-13 978-3-598-21502-5', 'The Grapes of Wrath', 'John Steinbeck')
     , (20, 'ISBN-13 978-3-598-21501-8', 'Beloved', 'Toni Morrison');

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