cat run.sh
profile=$(ec2-describe-tags --filter "resource-id=$(ec2-metadata -i | cut -d ' ' -f2)" --filter "key=profile" | cut -f5)
echo using profile $profile

export JAVA_HOME=/usr/java/default
export PATH=$JAVA_HOME/bin:$PATH
export MAVEN_HOME=/home/ec2-user/apache-maven-3.1.0
export PATH=$MAVEN_HOME/bin:$PATH



cd spring-environments/profilesweb
git pull
mvn clean install
cd target

java -Dspring.profiles.active=$profile -jar myproject-1.0.0-SNAPSHOT.jar
