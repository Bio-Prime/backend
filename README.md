# backend

## Docker

```
docker run --name postgres --publish 127.0.0.1:5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=bioprime -d postgres:9.6.17
```

How to start the backend application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/backend-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

