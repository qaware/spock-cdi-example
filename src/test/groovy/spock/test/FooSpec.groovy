package spock.test


import javax.annotation.Priority
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Produces
import javax.inject.Inject

/**
 * @Priority is required for @Produces @Alternative methods to overwrite the productive CDI bean
 */
@Priority(1000)
class FooSpec extends CdiSpecification {

    // The fields will be injected by CdiSpecification, not by CDI itself.
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
