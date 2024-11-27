FROM maven:latest AS build
WORKDIR /app
COPY ["pom.xml", "."]
COPY [".", "./"]

FROM openjdk:21
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/target/*.jar ./api.jar
CMD ["/usr/bin/java", "-jar", "api.jar"]