#!/bin/sh
mvn clean package && docker build -t dgauch/restlessjava .
docker rm -f restlessjava || true && docker run -d -p 8080:8080  -p 9990:9990 --name restlessjava dgauch/restlessjava 
