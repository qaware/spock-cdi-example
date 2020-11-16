package spock.test


import javax.annotation.Priority
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Produces
import javax.inject.Inject

/**
 * Another spec that produces another mock for Bar.
 * The specs must not be detected by the bean discovery, else their @Produces methods would conflict with each other
 */
@Priority(1000)
class FooBarSpec extends CdiSpecification {

    @Inject
    Foo foo

    @Produces
    @Alternative
    Bar mockBar() {
        def mock = Mock(Bar)
        mock.getValue() >> "another mock"
        return mock
    }

    def "Test injection"() {
        expect:
        foo != null
        foo.bar != null
        foo.bar.value == "another mock"
    }
}
