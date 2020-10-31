#!/bin/sh
mvn clean package && docker build -t dgauch/restlessjava .
docker rm -f restlessjava || true && docker run -d -p 8080:8080 --name restlessjava dgauch/restlessjava 
