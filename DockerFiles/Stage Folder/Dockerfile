FROM tomcat:8-jdk8-openjdk

ENV DB_HOST=csc380mysql
ENV DB_PORT=3306
ENV DB_USER=root
ENV DB_PASSWORD=testest1

#RUN apt-get install procps

# COPY dist-sys-data-rest-demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/RetroExchangeAPI.war
COPY RetroVideoGameExchangeAPI-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/RetroExchangeAPI.jar