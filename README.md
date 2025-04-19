![img.png](img.png)

# Cucumber
In this article I would like to discuss the acceptance test framework like Cucumber that is Java based and how does this help for testing micro services from business stand point.
What All you need?
Provided you are already developed an API in Java. I am limiting my discussion to Java/Spring based API’s only.
Cucumber dependencies baked in to pom file.
# You will need the below Maven POM File.
<code>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>cucumber</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <java.version>17</java.version> <!-- Updated to Java 17 -->
        <projectreactor.version>3.4.20</projectreactor.version> <!-- Updated to latest Reactor version -->
        <junit.jupiter.version>5.9.0</junit.jupiter.version> <!-- Updated to latest JUnit Jupiter version -->
        <cucumber.version>7.0.0</cucumber.version>
    </properties>
    <dependencies>
        <!-- Spring WebFlux for Reactive Microservice -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
            <version>2.6.3</version>
        </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <version>3.2.2</version>
                <scope>test</scope>
            </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>


        <!-- Cucumber for BDD Testing -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.30</version>
        </dependency>
        <!-- Override Spring Data release train provided by Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.2.2</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Other necessary dependencies (e.g., JUnit) -->
    </dependencies>

    <build>
        <plugins>
            <!-- Spring Boot Maven Plugin -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
</code>



# API that we want to test:
Let's Look at the below example API that we want to test. This API just returns the message. It's reactive service only returning the String as a response.
package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CucumberExample {

    @GetMapping("/cucumber")
    public Mono<String> cucumberTestResponse() {
        return Mono.just("Hello, Cucumber Acceptance Testing!");
    }
}
Now we need to write a feature file and put that in sr/resources folder. I will be sharing the folder structure as well.
<Code>Feature File.
Feature: Hello feature
Scenario: User calls cucumber
When user calls /cucumber
Then the response is "Hello, Cucumber Acceptance Testing!"
Step Definition File:
We also need to write a step definition file that will be executed once you run the feature file.
package cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitions {

    @Autowired
    private WebTestClient webTestClient;

    private String responseBody;

    @When("user test cuccumber")
    public void user_test_cucumber() {
        responseBody = webTestClient.get().uri("/api/cucumber")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    @Then("the response is {string}")
    public void the_response_is(String expectedResponse) {
        assertEquals(expectedResponse, responseBody);
    }

}
</code>
# Now look at what’s happening.
When you call the end point /api/cucumber , the returned message is
Hello, Cucumber Acceptance Testing!
And that’s the assertion done by the feature file in the most simplistic way.
you can run below command to run the acceptance test.
mvn test
Directory Structure:

Above is a very simple example and you can write any complex feature file to test even more complex API’s for example. However this is a beginners guide I wanted to keep that very simple. Thanks for sharing and please clap if you liked my content.
