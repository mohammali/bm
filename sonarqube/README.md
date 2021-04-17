# SonarQube Docker

You can run **SonarQube** locally in your machine by this configuration

### Create directories

Run this commands to create all directories we need

```shell
$ mkdir "~/dev/sonarqube/temp" \
  mkdir "~/dev/sonarqube/logs" \
  mkdir "~/dev/sonarqube/extensions" \
  mkdir "~/dev/sonarqube/data" \
  mkdir "~/dev/sonarqube/postgresql" \
  mkdir "~/dev/sonarqube/postgresql/data"
```

### Run docker compose

Start the **SonarQube** and **Postgresql** by

```shell
$ docker-compose up
```

### Push project to SonarQube

After you config the SonarQube and crete new project use this command to push the code into your project

```shell
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=PROJECT_KEY \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=LOGIN_KEY \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```