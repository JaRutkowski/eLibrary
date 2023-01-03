FROM openjdk:17-jdk-alpine
MAINTAINER experto.com
VOLUME /tmp
EXPOSE 8080
ADD target/jfs-operations-0.0.1-SNAPSHOT.war jfs-operations.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jfs-operations.war"]