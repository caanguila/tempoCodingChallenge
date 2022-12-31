# Code Challenge Tempo

Create a new Roles service that enhances the Users and Teams services, by giving us the

concept of team roles and the ability to associate them with team members.

In order to achieve the role creation service an integration layer was implemented using resttemplate to communicate with existing

services (User and team) 


---

### Prerequisites 📋

In order to use the project you must have the following tools:

- Docker.


### Installation 🔧

- Unzip the project folder
- Run command prompt on the project folder location
- Write the next line code "docker-compose up"  

### Deploy ⚙️

1. After all docker containers are up 
2. we can verify http://localhost:8080/actuator/health
3. Endpoints details at : http://localhost:8080/swagger-ui.html

---

## Technology Stack 🛠️

* [Spring Boot](https://spring.io/projects/spring-boot/) - The web framework
* [Maven](https://maven.apache.org/) - Dependency handler
* [Swagger](https://swagger.io/) - API documentation
* [Junit](https://junit.org/junit5/) - The unit testing framework
* [MySql] (https://www.mysql.com) - database engine


Github url https://github.com/caanguila/tempoCodingChallenge





