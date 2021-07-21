#!/bin/bash
cd ..
mvn clean install -DskipTests
cd clearing-house-api
if [ "$1" != "" ]; then
    echo "######## Build container for linux/amd64 ###############"
    docker buildx build --platform linux/amd64 -t market4.0/clearing-house .
    echo "######## Export container ###############"
    docker save market4.0/clearing-house > ~/Desktop/clearing-house.tar
  else
    echo "######## Build container for mac/arm64 ###############"
    docker build -t market4.0/clearing-house .
    docker-compose up -d
fi
