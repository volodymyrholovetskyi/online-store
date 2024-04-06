package ua.vholovetskyi.statistics;

import ua.vholovetskyi.model.AttributeType;

public interface StatisticsHandler {

    /**
     * Checks if the CommandHandler handles the given command
     * @param attributeName Command from the user
     * @return Boolean Return true if the handler can handle the user's command and false if not
     */
    boolean supports(AttributeType attributeName);

    /**
     * This method handles commands entered by the user.
     * @param attribute Contains user data
     * @return SendMessage Information from the handler about the successful processing of the command or vice versa
     */
    Statistics handle(AttributeType attribute);
}
