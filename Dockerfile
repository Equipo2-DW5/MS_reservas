FROM openjdk:17-jdk-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
CMD gunicorn --bind 0.0.0.0:$PORT wsgi