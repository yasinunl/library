# Spring Boot Library API

## Overview
This Spring Boot project serves as a library API, facilitating the management of books, students, borrow history(borrowings), and user authentication. The API includes controllers for adding, updating, and deleting books, students, and borrow history. It incorporates security measures by requiring tokens for authorization, distinguishing between admin and user roles.

## Project Structure
The project comprises four database tables: `books`, `students`, `borrow_history`, and `user`.

### Books
The `books` table includes attributes such as `total_quantity` and `available_quantity`. Special controllers are implemented for book services, allowing for book deletion (by decrementing quantities) and bulk deletion. Additionally, an enhanced add method dynamically adjusts quantities based on existing book names.

### Borrow History
The `borrow_history` service records user, book, and student associations. Tokens are used for authentication, ensuring that only authorized users can add, update, or delete entries. Admin privileges are required for deletions, while both admin and user tokens are permitted for additions and updates.

### Security
- **Token Authentication**: Users must obtain and use tokens for authentication.
- **Authorization Levels**: Admins have exclusive rights to delete entries, while both admins and users can add and update records.
- **Access Levels**: The GET method is available to all users without the need for login.

## Search Functionality
A search method is implemented for each table, leveraging example matchers. Additionally, a special search method using a native SQL query is provided for the `books` table, enabling advanced search capabilities. Uncomment the following code to use the custom search method:
<br />!To use this method, you have to make those columns FULL TEXT in database!<br>
http://localhost:8080/api/v1.0/.../search?query= 
```java
// Repositories
@Query(value = "SELECT * FROM BOOKS " +
         "WHERE MATCH (book_title, book_author, publisher, year_of_publication) AGAINST (:query'*'  IN BOOLEAN MODE)",
         nativeQuery = true)
 List<Books> searchBook(@Param("query") String query); 
```

## Getting Started (Continued)

4. Create database tables:
    ```sql
    CREATE TABLE `books` (
   `id` int NOT NULL AUTO_INCREMENT,
   `book_title` varchar(255) NOT NULL,
   `book_author` varchar(255) NOT NULL,
   `year_of_publication` varchar(8) NOT NULL,
   `publisher` varchar(255) DEFAULT NULL,
   `available_quantity` int NOT NULL DEFAULT '1',
   `total_quantity` int NOT NULL DEFAULT '1',
   PRIMARY KEY (`id`),
   FULLTEXT KEY `content` (`publisher`,`book_title`,`book_author`,`year_of_publication`)
   ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

   CREATE TABLE `borrowings` (
   `id` int NOT NULL AUTO_INCREMENT,
   `students_id` int DEFAULT NULL,
   `books_id` int DEFAULT NULL,
   `user_id` int DEFAULT NULL,
   `borrow_date` date DEFAULT NULL,
   `return_date` date DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `students_id` (`students_id`),
   KEY `books_id` (`books_id`),
   KEY `user_id` (`user_id`),
   CONSTRAINT `borrowings_ibfk_1` FOREIGN KEY (`students_id`) REFERENCES `students` (`id`),
   CONSTRAINT `borrowings_ibfk_2` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`),
   CONSTRAINT `borrowings_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

   CREATE TABLE `students` (
   `id` int NOT NULL AUTO_INCREMENT,
   `first_name` varchar(255) NOT NULL,
   `last_name` varchar(255) NOT NULL,
   `email` varchar(255) NOT NULL,
   `borrowed_books` int DEFAULT '0',
   PRIMARY KEY (`id`),
   UNIQUE KEY `email` (`email`) /*!80000 INVISIBLE */,
   FULLTEXT KEY `content` (`first_name`,`last_name`,`email`),
   CONSTRAINT `CHK_BORROWED_BOOKS` CHECK ((`borrowed_books` >= 0))
   ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

   CREATE TABLE `user` (
   `id` int NOT NULL AUTO_INCREMENT,
   `first_name` varchar(15) DEFAULT NULL,
   `last_name` varchar(15) DEFAULT NULL,
   `email` varchar(25) DEFAULT NULL,
   `password` varchar(155) DEFAULT NULL,
   `role` enum('USER','ADMIN') DEFAULT NULL,
   PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

    ```

5. Navigate to the project directory:
    ```bash
    cd library
    ```

6. Build the project using Maven:
    ```bash
    mvn clean install
    ```
7. Run the application:
    ```bash
    mvn spring-boot:run
    ```

Replace `library` with the actual directory where you cloned the project and `library` with the generated JAR file name.

8. The Spring Boot application should now be running. Access the API endpoints at [http://localhost:8080/api/v1.0](http://localhost:8080/api/v1.0/).

## API Endpoints

## Books API Endpoints

| Method | URL                                                            | Description                                                                   | Authorization Required |
|--------|----------------------------------------------------------------|-------------------------------------------------------------------------------|-----------------------|
| GET    | `/books`<br/>`/students`  <br/>`/borrowings`                   | Retrieve all data.                                                            | No                    |
| GET    | `/books/{id}` <br/>`/students/{id}`    <br/>`/borrowings/{id}` | Retrieve a specific data by ID.                                               | No                    |
| POST   | `/books`  <br/> `/students` <br/>`/borrowings`                 | Add a new data.                                                               | Yes                   |
| PUT    | `/books` <br/>    `/students` <br/>`/borrowings`               | Update a data.                                                                | Yes                   |
| DELETE | `/books/{id}` <br/> `/students/{id}` <br/> `/books/{id}/`      | Delete a data by ID.   <br/> Decreases total and available quantity for book. | Yes      |
| DELETE | `/books/delete-all/{id}/`                                      | Delete a book by ID.                                                          | Yes      |
| POST   | `/auth/login`                                                  | User login.                                                                   | No                    |
| POST   | `/auth/register`                                               | Add a new user.                                                               | Yes (Admin only)      |
