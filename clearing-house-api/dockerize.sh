#!/bin/bash
mvn clean install -DskipTests
docker build -t market4.0/clearing-house .
docker-compose up -d
if [ "$1" != "" ]; then
	echo "######## Export container ###############"
  docker save market4.0/clearing-house > ../docker/clearing-house.tar
fi
