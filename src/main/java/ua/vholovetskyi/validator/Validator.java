package ua.vholovetskyi.validator;

import ua.vholovetskyi.OnlineStoreApplication;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-6
 */
public interface Validator<T> {

    Logger LOG = Logger.getLogger(OnlineStoreApplication.class.getName());

    Map<String, String> validate(T t);

    static <T> boolean validate(Validator<T> validator, T t) {
        if (validator == null) {
            throw new IllegalArgumentException();
        }
        var errors = validator.validate(t);
        if (!errors.isEmpty()) {
            String errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining("\n"));
            LOG.log(Level.WARNING, errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return errors.isEmpty();
    }
}
