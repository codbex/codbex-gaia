# codbex-gaia

Gaia Edition contains all the available backend standard components except the Web IDE ones.

It is used as a basis platform for products build with and for `codbex` tools and runtimes.


#### Docker

```
docker pull ghcr.io/codbex/codbex-gaia:latest
docker run --name codbex-gaia --rm -p 8080:8080 ghcr.io/codbex/codbex-gaia:latest
```

- For Apple's M1: provide `--platform=linux/arm64` for better performance		

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-gaia-application-1.0.0-SNAPSHOT.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-gaia-application-1.0.0-SNAPSHOT.jar
```
	
#### REST API

```
http://localhost:8080/swagger-ui/index.html
```

