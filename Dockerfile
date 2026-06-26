FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY shared-domain/pom.xml shared-domain/
COPY catalog/pom.xml catalog/
COPY catalog/catalog-app/pom.xml catalog/catalog-app/
COPY catalog/catalog-core/pom.xml catalog/catalog-core/
COPY catalog/catalog-api-contract/pom.xml catalog/catalog-api-contract/
COPY catalog/catalog-domain-events/pom.xml catalog/catalog-domain-events/
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/catalog/catalog-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
