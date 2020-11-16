package spock.test;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Data
@Accessors(chain = true)
public class Bar {
    String value = "default";
}
