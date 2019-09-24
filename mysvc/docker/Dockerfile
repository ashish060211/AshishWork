FROM java:8
MAINTAINER Igor Moochnick "igor@igorshare.com"
VOLUME /tmp
EXPOSE 8100
#Defined by the Maven Build Arguments in POM File
ARG JAR_FILE
#ADD mysvc.jar mysvc.jar
ADD ${JAR_FILE} ${JAR_FILE}
# RUN bash -c 'touch /${project.name}.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom" ,"-jar", "/${JAR_FILE}" ]