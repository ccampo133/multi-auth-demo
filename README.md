#multi-auth-demo

A small Spring Boot demo application that demonstrates how to set up multiple HTTP security realms using Spring 
Security.

This represents a simple web service serving JSON over HTTP (REST, if you will). There are 3 endpoints, `/`, `/api`, and
`/random`. There are two users, `user` (with password `pw`), and `apiuser` (with password `apipw`). The user `user` only 
has access to the `/` and `/random` endpoints. The user `apiuser` only has access to the `/api` endpoint.

See the integration tests in `src/test/java` which validate this behavior.

Requires Java 8

## To Build

    ./gradlew build

## To Run
 
    ./gradlew bootRun

OR

    java -jar multi-auth-demo-0.0.1.jar

using the built jar file (located in `build/libs`).

The service will default to running on port 8080, so you can access it via `http://localhost:8080`