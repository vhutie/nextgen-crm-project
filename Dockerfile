FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:///dev/./urandom -XX:+HeapDumpOnOutOfMemoryError "
ENV TZ Africa/Johannesburg
ENV dynamic_port 5200
# NOT USE YET -XX:+UseG1GC
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar --server.port=${dynamic_port}" ]