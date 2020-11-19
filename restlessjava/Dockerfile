FROM jboss/wildfly:21.0.0.Final
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
RUN sed -i 's/<managed-executor-service /<managed-executor-service max-threads="32" core-threads="32" /g' /opt/jboss/wildfly/standalone/configuration/standalone-microprofile.xml
ADD ./target/restlessjava.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-microprofile.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
