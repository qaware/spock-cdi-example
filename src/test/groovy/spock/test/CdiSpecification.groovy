package spock.test

import spock.lang.Shared
import spock.lang.Specification

import javax.enterprise.inject.se.SeContainer
import javax.enterprise.inject.se.SeContainerInitializer
import javax.inject.Inject

class CdiSpecification extends Specification {

    @Shared
    SeContainer container = SeContainerInitializer.newInstance()
    // Add the current class as a been to be able to use @Produces methods for mocked beans
            .addBeanClasses(getClass())
            .initialize()


    /**
     * Called before every feature method. Initializes the @Inject fields of a specification class.
     */
    def setup() {
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter { f -> f.isAnnotationPresent(Inject) }
                .forEach { f ->
                    f.setAccessible(true)
                    f.set(this, container.select(f.getType()).get())
                }
    }

    /**
     * Called after the spec is done. Shutdown of the CDI container.
     */
    def cleanupSpec() {
        container.close()
    }
}
