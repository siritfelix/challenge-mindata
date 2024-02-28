FROM openjdk:17-alpine

EXPOSE 8080
ADD target/challenge-0.0.1-SNAPSHOT.jar /app/challenge.jar
WORKDIR /app
ENV DB_PLATFORM org.hibernate.dialect.H2Dialect
ENV DB_URL jdbc:h2:mem:testdb
ENV DB_USER root
ENV DB_ROOT_PASSWORD password
ENV SPRING_PROFILES_ACTIVE dev
CMD java -jar challenge.jar