FROM openjdk:17
WORKDIR /chats-service
COPY build/libs/chats-service-0.0.1-SNAPSHOT.jar chats-service.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "chats-service.jar"]