# Spock CDI Example

Example project to demonstrate CDI injection into Spock Specs, including mocking of individual CDI beans per Spec.
There is no need to list all the beans to use in the test.

## CDI implementation
This project supports WELD and OpenWebBeans. It uses WELD by default. 
To run the tests with OpenWebBeans instead, run: `./gradlew build -PuseOpenWebBeans`

## Findings

- OpenWebBeans requires that all beans have a no-args constructor, even if fields are injected via constructor
- WELD requires that the beans.xml and the classes are in the same folder, 
but Gradle puts resources and classes into separate folders by default (for test executions).
A workaround is applied in [build.gradle.kts](build.gradle.kts).
- Repeatedly creating the CDI context takes much longer with OpenWebBeans (see [CdiContextConstructionSpec](./src/test/groovy/spock/test/CdiContextConstructionSpec.groovy)).
