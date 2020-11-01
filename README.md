# RestlessJava

Provides REST APIs to produce some load. A common use case is to use multiple containers based on this project to generate some load in a Kubernetes cluster.

## Build and run

```bash
(cd restlessjava; ./buildAndRun.sh)
```

## Use it 

Use MP health check to see whether the server is already up and running: http://localhost:9990/health.

Burn cold (i.e. threads go sleeping while waiting)
```bash
curl --head 'localhost:8080/restlessjava/api/load/burn-cold?minThreads=1&maxThreads=4&min=100&max=1000'
```

Burn hot (i.e. threads will stay active)
```bash
curl --head 'localhost:8080/restlessjava/api/load/burn-hot?minThreads=1&maxThreads=4&min=100&max=1000'
```

## Tests

Make sure that a server is running at http://localhost:8080.

```bash
(cd restlessjava-st; mvn compile failsafe:integration-test)
```