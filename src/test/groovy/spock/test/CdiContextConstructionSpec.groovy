package spock.test

import spock.lang.Specification
import spock.lang.Unroll

import javax.enterprise.inject.se.SeContainer
import javax.enterprise.inject.se.SeContainerInitializer
import javax.inject.Inject

class CdiContextConstructionSpec extends Specification {

    @Inject
    Foo foo

    @Unroll
    def "Test injection #i"() {
        given:
        SeContainer container = SeContainerInitializer.newInstance()
        // Add the current class as a been to be able to use @Produces methods for mocked beans
                .addBeanClasses(getClass())
                .initialize()

        when:
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter { f -> f.isAnnotationPresent(Inject) }
                .forEach { f ->
                    f.setAccessible(true)
                    f.set(this, container.select(f.getType()).get())
                }

        then:
        foo != null
        foo.bar != null
        foo.bar.value == "default"

        and:
        container.close()

        where:
        i << (1..10)
    }
}
