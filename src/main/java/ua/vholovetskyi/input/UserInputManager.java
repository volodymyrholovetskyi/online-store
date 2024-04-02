package ua.vholovetskyi.input;

public class UserInputManager {

    private String [] args;
    public UserInputManager(String[] args) {
        this.args = args;
    }

    public UserInputCommand processCommand() {
        return new UserInputCommand(args);
    }
}
