@echo off
call mvn clean package
call docker build -t com.cargotracker.monolith/monolith .
call docker rm -f monolith
call docker run -d -p 9080:9080 -p 9443:9443 --name monolith com.cargotracker.monolith/monolith