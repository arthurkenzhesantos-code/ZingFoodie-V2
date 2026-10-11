FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN mkdir -p /var/data
COPY --from=build /app/target/zingfoodie-campus-1.0.0.jar app.jar
ENV PORT=8080
ENV DB_PATH=/var/data/zingfoodie.db
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar app.jar"]
