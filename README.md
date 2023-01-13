# Bookstore

REST API paths on /api/** needs to be authenticated:
2 types of user:
- username: user, password: userPass (cannot delete book, but all /api operationss ok)
- username: admin, password: adminPass (can delete book

Data are persisted only in In-memory Database: H2

--------------------------------
Execute Test cases with Postman:
--------------------------------
Setup:

Open Postman, import following json file in this app:
"bookstore.postman_collection.json"
Run following test cases by the imported script in postman:

TC1) Get All Books
1) Execute "Get All Books" in the postman script
   Expected: it should return 2 books with IDs: "ISBN-BOOK1", "ISBN-BOOK2"

TC2) Add New Book
1) Execute "Add New Book" in the postman script
2) Execute "Get All Books" in the postman script
   Expected: it should return 3 books, a new book with: "ISBN-BOOK3" is added to the result

TC2) Update Book
1) Execute "Update Book" in the postman script
2) Execute "Get All Books" in the postman script
   Expected: Book with ISBN: "ISBN-BOOK3", has updated some fields

TC3) Search by Title
1) Execute "Find by Title" in the postman script
   Expected: Book with ISBN "ISBN-BOOK1" is returned as result

TC4) Search by Author
1) Execute "Find by Title" in the postman script
   Expected: Book with ISBN "ISBN-BOOK1" is returned as result

TC5) Delete by User
1) Execute "Delete by User" in the postman script
   Expected: HTTP Error 403

TC6) Delete by Admin
1) Execute "Delete by Admin" in the postman script
   Expected: HTTP Status 200
2) Run "Get All Books"
   Expected: "ISBN-BOOK3" is removed from the list

