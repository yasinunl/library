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
<br />!To use this method, you have to make those columns FULL TEXT in database!
```java
// Repositories
@Query(value = "SELECT * FROM BOOKS " +
         "WHERE MATCH (book_title, book_author, publisher, year_of_publication) AGAINST (:query'*'  IN BOOLEAN MODE)",
         nativeQuery = true)
 List<Books> searchBook(@Param("query") String query); 
```

## Getting Started (Continued)

4. Navigate to the project directory:
    ```bash
    cd library
    ```

5. Build the project using Maven:
    ```bash
    mvn clean install
    ```

6. Run the application:
    ```bash
    mvn spring-boot:run
    ```

Replace `library` with the actual directory where you cloned the project and `library` with the generated JAR file name.

7. The Spring Boot application should now be running. Access the API endpoints at [http://localhost:8080/api/v1.0](http://localhost:8080/api/v1.0/).

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
