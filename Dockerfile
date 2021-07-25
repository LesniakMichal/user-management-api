FROM openjdk:11
EXPOSE 8080
ADD target/user-management-api-0.0.1-SNAPSHOT.jar user-management-api.jar
ENTRYPOINT ["java", "-jar", "/user-management-api.jar"]