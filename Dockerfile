FROM java:8
LABEL maintainer="kevin.jin01@sap.com"

WORKDIR /customer-service
EXPOSE 8090

ARG JAR_FILE
ADD target/${JAR_FILE} ./customer-service.jar
ENTRYPOINT ["java", "-jar","customer-service.jar"]