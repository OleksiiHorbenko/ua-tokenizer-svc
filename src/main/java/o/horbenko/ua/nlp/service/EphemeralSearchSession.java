package o.horbenko.ua.nlp.service;

import java.util.Collection;

public interface EphemeralSearchSession extends AutoCloseable {

    Double match(String toMatch);

    Double match(Collection<String> toMatch);
}
