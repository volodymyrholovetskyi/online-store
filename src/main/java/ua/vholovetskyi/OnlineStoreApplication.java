package ua.vholovetskyi;

import ua.vholovetskyi.input.UserInputManager;

import java.util.logging.Logger;

public class OnlineStoreApplication {
    private static Logger LOG = Logger.getLogger(OnlineStoreApplication.class.getName());

    public static void main(String[] args) {
        new OnlineStoreApplication().start(args);
    }

    private void start(String[] args) {
        LOG.info("Start app...");

        var userInputManager = new UserInputManager(args);
        var userInputArgs = userInputManager.processArgs();

    }
}