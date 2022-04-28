FROM openjdk:17
ADD /target/InsideJWT-0.0.1-SNAPSHOT.jar inside-jwt-v1.jar
ENTRYPOINT ["java", "-jar", "inside-jwt-v1.jar"]