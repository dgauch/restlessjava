# Build
mvn clean package && docker build -t dgauch/restlessjava .

# RUN

docker rm -f restlessjava || true && docker run -d -p 8080:8080 --name restlessjava dgauch/restlessjava 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test