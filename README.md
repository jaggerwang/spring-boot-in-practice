# Spring Boot in Practice

This project can be used as a starter for spring boot api service development, it is also a reference implementation of [Clean Architecture](https://blog.jaggerwang.net/clean-architecture-in-practice/). This api service can be used as the backend api service for this flutter app [Flutter in Practice](https://github.com/jaggerwang/flutter-in-practice). There is also an article [Spring Boot API 服务开发指南](https://blog.jaggerwang.net/spring-boot-api-service-develop-tour/) for learning this project.

## Branches

1. `mybatis` Using MyBatis to implement DAO(Data Access Object)
1. `mybatis-plus` Using MyBatis-Plus to implement DAO(Data Access Object)

## Dependent frameworks and packages

1. [Spring Boot](https://spring.io/projects/spring-boot) Web framework and server
1. [Spring Data JPA](https://spring.io/projects/spring-data-jpa) Access database
1. [Querydsl JPA](https://github.com/querydsl/querydsl/tree/master/querydsl-jpa) Type safe dynamic sql builder
1. [MyBatis](https://mybatis.org/) SQL Mapping Framework
1. [MyBatis-Plus](https://baomidou.com/) An powerful enhanced toolkit of MyBatis for simplify development
1. [Spring Data Redis](https://spring.io/projects/spring-data-redis) Cache data
1. [Spring Security](https://spring.io/projects/spring-security) Authenticate and authrorize
1. [Spring Session](https://spring.io/projects/spring-session) Manage session
1. [Flyway](https://flywaydb.org/) Database migration
1. [Swagger](https://swagger.io/) Api documentation

## APIs

### Rest

| Path  | Method | Description |
| ------------- | ------------- | ------------- |
| /auth/login | POST | Login |
| /auth/logout | GET | Logout |
| /auth/logged | GET | Get logged user |
| /user/register | POST | Register |
| /user/modify | POST | Modify logged user |
| /user/info | GET | Get user info |
| /user/follow | POST | Follow user |
| /user/unfollow | POST | Unfollow user |
| /user/following | GET | Following users of someone |
| /user/follower | GET | Fans of some user |
| /user/sendMobileVerifyCode | POST | Send mobile verify code |
| /post/publish | POST | Publish post |
| /post/delete | POST | Delete post |
| /post/info | GET | Get post info |
| /post/published | GET | Get published posts of some user |
| /post/like | POST | Like post |
| /post/unlike | POST | Unlike post |
| /post/liked | GET | Liked posts of some user |
| /post/following | GET | Posts of following users of someone |
| /file/upload | POST | Upload file |
| /file/info | GET | Get file meta info |

This project uses [Swagger](https://swagger.io/) to auto generate api documentation. After started the api service, you can browse all apis at `http://localhost:8080/swagger-ui.html`.

## How to run

This project need java v11+.

### By local environment

#### Clone repository

```bash
git clone https://github.com/jaggerwang/spring-boot-in-practice.git && cd spring-boot-in-practice
```

#### Prepare mysql and redis service

Install mysql and redis server, and start them. After mysql started, create a database for this project, and a user to access this database.

```sql
CREATE DATABASE `sbip`;
CREATE USER 'sbip'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON `sbip`.* TO 'sbip'@'%';
```

#### Configure application

Change configs in `src/main/resources/application.yml` as your need, especially mysql, redis and path related configs. You can also change configs by environment variables, you need add `SBIP_` prefix to each config you want to change. You should make sure the directories you configured is existing.

#### Start server

```bash
./mvnw spring-boot:run
```

The running main class is `net.jaggerwang.sbip.adapter.api.Application`. When first start server, it will auto create tables, we use flyway to migrate database changes.

After started, the api service's endpoint is `http://localhost:8080/`.

### By docker compose

You need install [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) at first.

#### Configure compose

Change the content of `docker-compose.yml` as your need, especially the host path of mounted volumes.

#### Start all services

```bash
docker-compose up -d
```

It will start server, mysql and redis services. If you need to stop and remove all services, you can execute command `docker-compose down`. The container port `8080` is mapping to the same port on local host, so the endpoint of api service is same as previous.

When first start mysql, it will auto create a database `sbip` and a user `sbip` with password `123456` to access this database. The password of `root` user is also `123456`.

## How to test

By default it will not run any tests when run maven `test` or `package` task. You can specify corresponding system property to enable each test.

### By local environment

#### Test repositories

```bash
./mvnw -Dtest.dao.enabled=true test
```

#### Test usecases

```bash
./mvnw -Dtest.usecase.enabled=true test
```

Usecase tests are unit tests, it not dependent on outside mysql or redis service.

Repository tests are integration tests, but it use an embedded H2 database, so there is no need to start a mysql service.

#### Test apis

```bash
./mvnw -Dtest.api.enabled=true test
```

Api tests are integration tests, it do need mysql and redis service. You can config the services for testing in `application-test.yml`.
                                                                    
> Be careful, api integration tests will init and clean data in database, do not connect to the real using database.

### By docker compose

To reduce the work of preparing test environment, especilly api integration tests, we can use docker container to run tests. You can use the following commands to run corresponding tests in docker container.

```bash
docker-compose -p spring-boot-in-practice-usecase-test -f docker-compose.usecase-test.yml up
docker-compose -p spring-boot-in-practice-repository-test -f docker-compose.repository-test.yml up
docker-compose -p spring-boot-in-practice-api-test -f docker-compose.api-test.yml up
```

After running tests, you can run the following commands to get the corresponding test result. The result of `0` means success, others means failure. 

```bash
docker inspect spring-boot-in-practice-usecase-test_server_1 --format='{{.State.ExitCode}}'
docker inspect spring-boot-in-practice-repository-test_server_1 --format='{{.State.ExitCode}}'
docker inspect spring-boot-in-practice-api-test_server_1 --format='{{.State.ExitCode}}'
```
