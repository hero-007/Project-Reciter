FROM openjdk:17.0.1-oracle
EXPOSE 8080
ADD target/reciterproject-0.0.1-SNAPSHOT.jar reciterproject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/reciterproject-0.0.1-SNAPSHOT.jar"]