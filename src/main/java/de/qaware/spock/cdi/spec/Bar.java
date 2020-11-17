package de.qaware.spock.cdi.spec;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Data
@Accessors(chain = true)
public class Bar {
    String value = "default";
}
