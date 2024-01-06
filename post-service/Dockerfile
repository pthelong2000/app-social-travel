FROM openjdk:17
WORKDIR /post-service
COPY build/libs/post-service-0.0.1-SNAPSHOT-plain.jar /post-service.jar
EXPOSE 8080
CMD ["java", "-jar", "/post-service.jar"]