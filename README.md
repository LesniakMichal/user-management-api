# User-Management-Api

Application for adding employees and managing their State, wrote in hexagonal architecture.
 
It simulates state machine based on Enum. 

**State route:**
**ADDED -> IN_CHECK -> APPROVED -> ACTIVE**

## Requirements

- Java 11


## Running application

There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the main method in the com.pplflw.usermanagementapi.UserManagementApiApplication class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Or via docker:
```shell
docker-compose.yml up
```

##Other infomation
This api provides openApi documentation: [localhost:8080/swagger-ui](localhost:8080/swagger-ui).
Default profile provides H2 in-memory database: [localhost:8080/h2-console](localhost:8080/h2-console).