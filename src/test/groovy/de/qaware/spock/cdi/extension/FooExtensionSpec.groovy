package de.qaware.spock.cdi.extension

import de.qaware.spock.cdi.spec.Bar
import de.qaware.spock.cdi.spec.Foo
import spock.lang.Specification

import javax.annotation.Priority
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Produces
import javax.inject.Inject

@Cdi
@Priority(1000)
class FooExtensionSpec extends Specification {

    /**
     * The fields will be injected by CDI extension, not by CDI itself.
     */
    @Inject
    Foo foo

    /**
     * Provide an alternative (mocked) bean for Bar.
     * @return
     */
    @Produces
    @Alternative
    Bar mockBar() {
        def mock = Mock(Bar)
        mock.getValue() >> "mocked"
        return mock
    }

    def "Test injection"() {
        expect:
        foo != null
        foo.bar != null
        foo.bar.value == "mocked"
    }
}
