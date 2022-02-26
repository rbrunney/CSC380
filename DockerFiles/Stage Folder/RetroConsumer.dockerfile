FROM openjdk:8
COPY RetroConsumer-1.0.jar /usr/local/lib/RetroConsumer.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/RetroConsumer.jar"]