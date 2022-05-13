FROM openjdk:11-jdk-oracle
COPY ./*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]