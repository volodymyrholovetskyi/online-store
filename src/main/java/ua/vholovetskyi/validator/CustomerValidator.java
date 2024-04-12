package ua.vholovetskyi.validator;

import org.apache.commons.lang3.StringUtils;
import ua.vholovetskyi.model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-6
 */
public class CustomerValidator<T> implements Validator<Customer> {
    private static final String EMPTY_FIELD_MESS = "The field cannot be empty!";
    private static final String INVALID_FORMAT = "Invalid field format!";
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\+380\\d{9}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
    private final Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Customer customer) {

        if (customer == null) {
            errors.put("Customer object", "null");
            return errors;
        }

        var id = customer.getId();
        if (id == null) {
            errors.put("ID", EMPTY_FIELD_MESS);
            return errors;
        }

        var firstName = customer.getFirstName();
        if (StringUtils.isBlank(firstName)) {
            errors.put("First name", EMPTY_FIELD_MESS);
            return errors;
        }

        var lastName = customer.getLastName();
        if (StringUtils.isBlank(lastName)) {
            errors.put("Last name", EMPTY_FIELD_MESS);
            return errors;
        }

        var phone = customer.getPhone();
        if (phone == null || !phoneIsValid(phone)) {
            errors.put("Phone", INVALID_FORMAT);
            return errors;
        }

        var email = customer.getEmail();
        if (email == null || !emailIsValid(email)) {
            errors.put("Email", INVALID_FORMAT);
            return errors;
        }

        var street = customer.getStreet();
        if (StringUtils.isBlank(street)) {
            errors.put("Street", EMPTY_FIELD_MESS);
            return errors;
        }

        var city = customer.getCity();
        if (StringUtils.isBlank(city)) {
            errors.put("City", EMPTY_FIELD_MESS);
            return errors;
        }

        var zipCode = customer.getZipCod();
        if (StringUtils.isBlank(zipCode)) {
            errors.put("Zip code", EMPTY_FIELD_MESS);
            return errors;
        }
        return errors;
    }

    private boolean phoneIsValid(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private boolean emailIsValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
