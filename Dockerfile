FROM openjdk:15
ADD target/docker-spring-boot.jar docker-spring-boot.jar
EXPOSE 8070/tcp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]

