#!/bin/bash
cd ..
mvn clean install -DskipTests
cd clearing-house-api
docker build -t market4.0/clearing-house .
docker-compose up -d
if [ "$1" != "" ]; then
	echo "######## Export container ###############"
  docker save market4.0/clearing-house > ../docker/clearing-house.tar
fi
