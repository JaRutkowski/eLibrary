FROM openjdk:17-jdk-alpine
MAINTAINER experto.com
VOLUME /tmp
EXPOSE 8080
ADD target/elibrary-micro-0.0.1-SNAPSHOT.war elibrary-micro.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/elibrary-micro.war"]