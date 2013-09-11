mvn clean install ;
cd target ; 
java -Dspring.profiles.active=cat -jar myproject-1.0.0-SNAPSHOT.jar  ;
