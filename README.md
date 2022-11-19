# Assignment

A JVM based backend application using REST that manages medical information entity.

## How to build?

### Dependencies

1. `Java 11`
2. `Docker`

### How to build the project

This command will make an executable `jar` file which you can run it on your system.

```shell
$ mvn clean package
```

After build successfully done run these commands.

```shell
$ cd targets
$ java -jar assignment-0.0.1-SNAPSHOT.jar
```

If you want to run this project by docker you can build it like this
run this command in the root of project where `docker-compose.yml` is.

```shell
$ docker-compose build 
$ docker-compose up -d
$ docker-compose logs
```

## Documentation

After running the application you can see the api documentation in link below

[Open in Browser - http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Monitoring

This app expose `actuator` and `prometheus` metrics and can be use for different usages.
right now these endpoints doesn't have any authentication which should be harmful and exposing sensitive data and
access.

1. [Actuator](http://localhost:8080/actuator/)
2. [Prometheus](http://localhost:8080/actuator/prometheus)