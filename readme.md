### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.


#### Restrictions
- use java 8

## API Endpoints

**Retrieve All Employees**
- **URL**: `/api/v1/employees`
- **Method**: `GET`
- **Description**: Retrieves a list of all employees.
- **Response**: `200 OK`

**Retrieve Employee by ID**
- **URL**: `/api/v1/employees/{employeeId}`
- **Method**: `GET`
- **Description**: Retrieves an employee by their ID.
- **Response**: `200 OK` or `404 Not Found` if employee is not found.

**Add New Employee**
- **URL**: `/api/v1/employees`
- **Method**: `POST`
- **Description**: Adds a new employee.
- **Request Body**: JSON representation of the employee.
- **Response**: `201 Created`

**Update Employee**
- **URL**: `/api/v1/employees/{employeeId}`
- **Method**: `PUT`
- **Description**: Updates an existing employee.
- **Request Body**: JSON representation of the updated employee.
- **Response**: `200 OK` or `404 Not Found` if employee is not found.

**Delete Employee**
- **URL**: `/api/v1/employees/{employeeId}`
- **Method**: `DELETE`
- **Description**: Deletes an employee by their ID.
- **Response**: `200 OK` or `404 Not Found` if employee is not found.

**Search Employees**
- **URL**: `/api/v1/employees/search`
- **Method**: `GET`
- **Description**: Searches for employees by name, salary, and department.
- **Response**: `200 OK`


##What I did

-Add Search Employees
 - **URL**: `/api/v1/employees/search`
 - **Method**: `GET`
 - **Description**: Searches for employees by name, salary, and department.
 - **Response**: `200 OK`

-Add Exception Handling

　　The application includes a global exception handler that manages the following scenarios:

- **EmployeeNotFoundException**: Thrown when an employee is not found. Returns a `404 Not Found` response.
- **Generic Exceptions**: Catches other exceptions and returns a `500 Internal Server Error` response.

-Add comprehensive comments to code for readability and maintainability.

-Implemented comprehensive test cases for the EmployeeController class, ensuring robustness and reliability, as demonstrated in the EmployeeControllerTest class

-Caching Database Calls
  To improve performance, caching has been implemented for database calls using Spring's caching abstraction. The `EmployeeService` class utilizes caching annotations (`@Cacheable`, `@CachePut`, `@CacheEvict`) to cache method.

#### My experience in Java

Please let us know more about your Java experience in a few sentences. 

- I have over 5 years of experience working with Java, particularly in developing applications using Spring Boot and Spring MVC.
- I have a deep understanding of Spring Boot, having utilized it extensively in several projects to create robust and scalable applications.
- My expertise includes designing RESTful APIs, implementing security features, and integrating various services, which has allowed me to deliver high-quality software solutions effectively.
