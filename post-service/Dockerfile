FROM gradle:8.5.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM openjdk:17-jdk
WORKDIR /post-service
COPY --from=build /home/gradle/src/build/libs/post-service-0.0.1-SNAPSHOT-plain.jar /post-service/post-service.jar
EXPOSE 8080
CMD ["java", "-jar", "/post-service/post-service.jar"]