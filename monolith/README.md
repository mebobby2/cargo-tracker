# Project com.cargotracker.monolith/monolith

Steps to run this project:

1. Start your Docker daemon
2. Execute `./buildAndRun.sh` (Linux/MacOs) or `buildAndRun.bat` (Windows)
3. Wait until Open Liberty is up- and running (e.g. use `docker logs -f CONTAINER_ID`)
4. Visit http://localhost:9080/resources/sample



## Generated
```
mvn archetype:generate \
  -DarchetypeGroupId=de.rieckpil.archetypes \
  -DarchetypeArtifactId=jakartaee8 \
  -DarchetypeVersion=1.0.0 \
  -DgroupId=com.cargotracker.monolith \
  -DartifactId=monolith \
  -DinteractiveMode=false
```
