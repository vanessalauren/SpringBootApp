FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/ScraybleApp-1.0.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]