FROM openjdk:11 as build

RUN apk --no-cache update && \
    apk --no-cache upgrade && \
    apk add --update tzdata && \
    rm -rf /var/cache/apk/*
    
RUN echo ${TZ} > /etc/timezone
    
EXPOSE 80

ADD /target/adote-um-amanha-0.0.1-SNAPSHOT.jar adote-uma-amanha-api.jar

ENTRYPOINT ["java","-jar","adote-uma-amanha-api.jar", "--server.port=80"]