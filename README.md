
## Job-Pe API BACKEND

The proyect have swagger, when you turn on the server you can see all endpoints in this URL:

Microservice business
```
http://localhost:8081/swagger-ui.html#
```
Microservice user
```
http://localhost:8082/swagger-ui.html#
```
Microservice edge
```
http://localhost:8080/swagger-ui.html#
```
---

## Instalation

1- Download the project from the repository.

2- Open each of the Maven projects found inside it on an IDE as IntelliJ.

3- Go into the application.properties files of this repo and replace these lines with your mySQL username and password.
```
'spring.datasource.username=username' and 'spring.datasource.password=password'
```
4- Run each project with the command mvn spring-boot:run, you must run in first place eureka-service, afterwards run the remaining services.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

