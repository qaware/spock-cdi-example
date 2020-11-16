package spock.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Getter
@NoArgsConstructor // required by openwebbeans...
public class Foo {
    Bar bar;

    @Inject
    public Foo(Bar bar) {
        this.bar = bar;
    }
}
