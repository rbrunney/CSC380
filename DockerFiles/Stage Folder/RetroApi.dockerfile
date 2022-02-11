FROM tomcat:8-jdk8-openjdk

ENV DB_HOST=csc380mysql
ENV DB_PORT=3306
ENV DB_USER=root
ENV DB_PASSWORD=testtest1

COPY  RetroVideoGameExchangeAPI-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/RetroExchangeAPI.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/tomcat/webapps/RetroExchangeAPI.jar"]