package ua.vholovetskyi.input;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public class UserInputManager {
    private final String[] args;

    public UserInputManager(String[] args) {
        this.args = args;
    }

    public UserInputArgument processArgs() {
        return new UserInputArgument(args);
    }
}
