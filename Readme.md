# Spock CDI Example

## CDI implementation
This project supports WELD and OpenWebBeans. It can be changed in [build.gradle.kts](build.gradle.kts) by
commenting in/out the appropriate dependencies.

## Findings

- OpenWebBeans requires that all beans have a no-args constructor, even if fields are injected via constructor
- WELD requires that the beans.xml and the classes are in the same folder, 
but Gradle puts resources and classes into separate folders by default (for test executions).
A workaround is applied in [build.gradle.kts](build.gradle.kts).
- Repeatedly creating the CDI context takes much longer with OpenWebBeans (see [CdiContextConstructionSpec](./src/test/groovy/spock/test/CdiContextConstructionSpec.groovy)).
