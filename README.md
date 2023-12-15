# Log Routing App
An application capable of selectively routing specific logs to syslog and others to the console. The application should offer precise and customisation logging functionality.

## Prerequisites
Make sure you have the following software installed on your machine:

- Java Development Kit (JDK 17+): The application is built on Java, so make sure you have the appropriate version of the JDK installed.
- Docker: Docker is required for containerization, enabling easy deployment and management.

## Log4j2 Integration
Log4j2 is the latest version of Log4j, a common choice for high-performance logging used in many production applications.

### Step 1:  Maven Dependency
Add the Log4j2 Maven dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
    <version>2.5.2</version>
</dependency>
```

### Step 2: Exclude Logback Logging
Exclude the default Logback logging framework from the spring-boot-starter-web dependency to ensure smooth Log4j2 integration. Add the following exclusion to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
To configure Log4j2 in a Spring Boot application, exclude the default Logback logging framework from the spring-boot-starter-web dependency
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

## Configure the Log4j2 framework for your Spring Boot application.

### Step 1: Create Log4j2 Configuration File
Create a `log4j2-spring.xml` file in the `resource directory`. This file defines the logging behavior of your application.

### log4j2-spring.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <!-- Create a Properties to maintain single config file -->
    <Properties>
        <Property name="syslog.host">${bundle:application:syslog.host}</Property>
        <Property name="syslog.port">${bundle:application:syslog.port}</Property>
    </Properties>
    
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout
                    pattern="%style{%d{ISO8601}}{bright,blue} %highlight{%-5level }[%style{%t}{bright,blue}] %style{%C{1.}}{bright,yellow}: %msg %n"/>
        </Console>
        <Syslog name="Syslog" format="RFC5424" host="${syslog.host}" port="${syslog.port}"
                protocol="TCP" appName="LogRouter" facility="LOCAL0" newLine="true"/>

    </Appenders>

    <Loggers>
        <!-- Create a specific Logger for logs you want to send to Syslog -->
        <Logger name="com.hrithik.logrouter.service.LogService" level="info">
            <AppenderRef ref="Syslog"/>
        </Logger>

        <!-- LOG everything at INFO level -->
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>

    </Loggers>

</Configuration>
```

### Step 2: Disable Debugging
In the `log4j2.system.properties` file, set debugging to false:
```properties
log4j2.debug=false
```

### Step 3: Configure Application Properties
Update the `application-log4j2-extension.properties` and `application.properties` files with the following configurations:

### application-log4j2-extension.properties
```properties
logging.config=classpath:log4j2-spring.xml
spring.application.name=log4j2-extension
```

### application.properties
```properties
server.port=8080
server.error.include-message=always
spring.application.name=LogRoutingExample
syslog.host=<Your-Log-Server_IP>
syslog.port=<Your-Log-Server_Port>
```


## Post Call
After setting up the Log Routing application, test it with a POST request:
```bash
curl --location 'http://localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "Test":"Testing",
    "Client": "Hrithk"
}'
```


## References
- [SyslogAppender](https://logging.apache.org/log4j/2.x/manual/appenders.html#syslogappender)
