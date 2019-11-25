#!/bin/sh
mvn clean package && docker build -t com.cargotracker.monolith/monolith .
docker rm -f monolith || true && docker run -d -p 9080:9080 -p 9443:9443 --name monolith com.cargotracker.monolith/monolith