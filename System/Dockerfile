FROM java:8-jre

ADD ./target/mail-0.0.1-SNAPSHOT.jar /app/
ADD ./src/main/resources/application.properties /app/


CMD java -Dspring.config.location=/app/application.properties -Dserver.port=$serverPort -Dscheduled.resend.rate=$resendRate -Dthreads.count=$threadsCount -DdbServer=$dbServer -jar /app/mail-0.0.1-SNAPSHOT.jar
