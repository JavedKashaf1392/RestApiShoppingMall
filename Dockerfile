FROM java:8
ADD target/ProspectaOnlineShopping-0.0.1-SNAPSHOT.jar ProspectaOnlineShopping-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ProspectaOnlineShopping-0.0.1-SNAPSHOT.jar"]