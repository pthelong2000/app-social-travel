FROM java17:latest
WORKDIR /post-service
COPY target/post-service-0.0.1-SNAPSHOT-plain.jar /post-service.jar
EXPOSE 8080
CMD ["java", "-jar", "/post-service.jar"]