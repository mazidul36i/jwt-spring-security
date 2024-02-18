# Getting Started

### Pre-requisits
- <b>Java 17+</b> 
- <b>Database</b> : MySQL / PostgreSQL / H2 Database / etc.
- <b>Postman</b> : Need to test the application.
- Create database with name <b>jwt_spring_security</b> (can be changed).


### Application properties
Create an application.properties file to override default properties

```
# Server config
server.port = 8080

# DataSource Configuration
spring.datasource.url = jdbc:mysql://localhost:3306/jwt_spring_security
spring.datasource.username = root
spring.datasource.password = password
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true

# Exception handling
spring.mvc.throw-exception-if-no-handler-found = true
spring.web.resources.add-mappings = false

# Spring security config
JWT_SECRET_KEY = NjQzQUQyQUMtNEYwMi00NURGLUJBMjMtQkY3MzNCOTI3RDBG
auth.public-urls = /api/v1/auth/**, /resources/**
```

## How to access?
### Ping-pong test URL
After starting your spring security microservice, hit the below endpoint to see if the application is working or not. Once you hit the endpoint, you shoud receive a ``Pong`` response.
```
# GET : Ping-pong API
http://localhost:8080/api/v1/auth/ping
```

### Register a new user
Once you verified that your application is running and working, it's time to register a new user into your application.
<br>
To register a new user, hit the below endpoint with a request body having all the user details.
```
# POST : Registration API
http://localhost:8080/api/v1/auth/register
```
```
# Request body
{
    "firstName" : "Mazidul",
    "lastName": "Islam",
    "username" : "mazidul36i",
    "email" : "islammazidul1369@gmail.com",
    "password" : "Pass@123"
}
```
There are validations for all the attributes of request body as follows & these must be followed (you can change as per your requirement by updating regex).
- <b>firstName</b> : Firstname must contain only letters and spaces.
- <b>lastName</b> : Lastname must contain only letters and spaces.
- <b>username</b> : Username must be in the range of 3-20 character. It may only contain letters, numbers, dots, underscores, and hyphens only.
- <b>email</b> : Valid email address.
- <b>password</b> : Password must contain at least one lowercase letter, uppercase letter, digit, one special character(@$!%*?&), and 8 characters long.

### Login with credential
Once you are registered into the application, you can login with the credential to receive JWT token.
<br>
Go to Auth in Postman and select <b style="color:lime">Basic Auth</b>. Enter your username and password. Hit the below API with POST request method.
```
# POST : Login API
http://localhost:8080/api/v1/auth/login
```
<br>

> <span style="color:lime"> When you register a new user or login the registered credential, you will receive 2 type of JWT tokens. Use the JWT token for accessing other endpoints of your application. And use the Refresh JWT token to refresh (regenerate) your JWT token once it expires (after 5 minutes of creation). </span>

> To access any other endpoint than authentication, use JWT token in your auth senction of Postman selecting <b style="color:lime">Bearar Token</b>.


## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#io.validation)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#actuator)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web.security)

### Guides

The following guides illustrate how to use some features concretely:

* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

