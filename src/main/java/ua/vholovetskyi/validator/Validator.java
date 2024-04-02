package ua.vholovetskyi.validator;

import ua.vholovetskyi.OnlineStoreApplication;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public interface Validator<T> {

    Logger LOG = Logger.getLogger(OnlineStoreApplication.class.getName());

    Map<String, String> validate(T t);

    static <T> boolean validate(Validator<T> validator, T t) {
        if (validator == null) {
            throw new IllegalArgumentException();
        }
        var errors = validator.validate(t);
        LOG.warning(errors
                .entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n")));
        return errors.isEmpty();
    }
}
