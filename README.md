# BlogIT
Simple blog about IT. I used Spring Framework, Spring Boot, Hibernate, Thymeleaf, Bootstrap, MySQL local database.

## Getting Started
If you want deploy app in your localhost, you have to specify Database url, password and username. I used MySQL, but you can
use any other database, but you have to import connector dependency to pom.xml, and specify it in application.properties.
Other thing you have to do, it's specify your SSL, or disable SSL in BlogIT, you can to do this in application.properties.
Your own SSL for test you can generate using keytool.

### Sample application.properties

```
spring.jpa.database=mysql                                   - your database type
spring.jpa.show-sql=true
spring.datasource.url=jdbc:mysql://localhost:3306/blogdb    - your database url
spring.datasource.username=root                             - your database username
spring.datasource.password=12345678                         - your database password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver   - classpath to database controller
spring.session.store-type=none
spring.jpa.generate-ddl=true                                - autogenerate database schema when value is true
spring.thymeleaf.cache=false
http.port=80
server.port=443                                             - https port
server.ssl.enabled=true                                  
server.ssl.key-store=c:/keystore.p12                        - your path to your own SSL
server.ssl.key-password=pass                                - password to your SSL key
server.ssl.key-alias=tomcat
server.ssl.key-store-type=PKCS12  
server.ssl.key-store-password=pass
```

### Running BlogIT locally

```
git clone https://github.com/malinowskij/BlogIT.git
cd BlogIT
./mvnw spring-boot:run
```

## A few examples of use BlogIT

### Main Page
![main page](malinowskij/img/blob/master/BlogIT/glowna.jpg)

### Post view
![post view](malinowskij/img/blob/master/BlogIT/postInside.jpg)

### User view for admin
![userr view for admin](malinowskij/img/blob/master/BlogIT/userPanel.jpg)

### Responsive example
![rsponsive](malinowskij/img/blob/master/BlogIT/responsive.jpg)

## Author
 ** Jakub Malinowski **
