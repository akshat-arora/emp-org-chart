FROM openjdk:8
EXPOSE 8080
ADD target/emp-management-system.jar emp-management-system.jar
ENTRYPOINT ["java","-jar","emp-management-system.jar"]
