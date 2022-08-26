FROM openjdk:8
EXPOSE 2424
ADD target/email-service.jar email-service.jar
ENTRYPOINT ["java","-jar","/email-service.jar"]