FROM openjdk:11.0.7-slim
LABEL maintainer="julianalejandrosanchez@hotmail.com"
ARG JAR_FILE
ADD target/${JAR_FILE} ms-usuarios-app.jar
RUN echo ${JAR_FILE}
ENTRYPOINT [ "java", "-jar", "/ms-usuarios-app.jar" ]