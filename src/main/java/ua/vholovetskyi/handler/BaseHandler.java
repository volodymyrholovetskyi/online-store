package ua.vholovetskyi.handler;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public abstract class BaseHandler implements StatisticsHandler {

    @Override
    public boolean supports(String attributeName) {
        return getArgumentName().equalsIgnoreCase(attributeName);
    }

    protected abstract String getArgumentName();

}
