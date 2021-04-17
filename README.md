# BM Project

This is a **Java 11** project showing how to connect with **Microsoft Access database** and expose the data using **
RESTful API**, it's also contain custom session manager to handle `HttpSession`.

The project built using [SpringBoot](https://spring.io/projects/spring-boot)
framework, [Maven](https://maven.apache.org/) as project management, [JUnit5](https://junit.org/junit5/) for
testing, [Jacoco](https://www.eclemma.org/jacoco/) for code coverage and [SonarQube](https://www.sonarqube.org/) for
code quality.

### Setup

Start by cloning the project in your machine and enter it directory

```shell
$ git clone git@github.com:mohammali/bm.git
$ cd bm
```

now, build the project using `maven-wrapper`

```shell
$ ./mvnw clean install -Dmaven.test.skip=true
```

or you can use your own `maven`

```shell
$ mvn clean install -Dmaven.test.skip=true
```

this commands will generate stand-alone jar file, and we can run it on `JVM 11` or higher

You can run the service by

```shell
$ java -jar target/service.jar
```

### APIs

Please use this [Swagger link](http://localhost:8080/)