FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY catalog-app/pom.xml catalog-app/
COPY catalog-core/pom.xml catalog-core/
COPY catalog-api-contract/pom.xml catalog-api-contract/
COPY catalog-domain-events/pom.xml catalog-domain-events/
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/catalog-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
