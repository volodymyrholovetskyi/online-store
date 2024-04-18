package ua.vholovetskyi.input;

import org.apache.commons.lang3.StringUtils;
import ua.vholovetskyi.model.OrderStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.vholovetskyi.utils.Attributes.STATUS;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-05
 */
public class UserInputArgument {

    private static final Logger LOG = Logger.getLogger(UserInputArgument.class.getName());
    private final Set<String> files = new HashSet<>();
    private final String attributeName;
    private final Set<OrderStatus> statusParams = new HashSet<>();

    /**
     * Validation fields and create object
     *
     * @param args arguments from the user
     */
    public UserInputArgument(String[] args) {
        if (StringUtils.isAllBlank(args) || args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments entered!. Argument order: <folder_name> <attribute_name> ...");
        }

        var dir = args[0];
        if (!Files.exists(Paths.get(dir))) {
            throw new IllegalArgumentException("Folder [%s] does not exist!".formatted(dir));
        }

        extractFilesFromFolder(dir);
        this.attributeName = args[1];

        if (args.length > 2) {
            if (STATUS.equalsIgnoreCase(attributeName)) {
                var statuses = Arrays.copyOfRange(args, 2, args.length);
                extractStatusParams(statuses);
            } else {
                throw new IllegalArgumentException("Sorry, but the application only handles parameters with the [%s] argument!"
                        .formatted(STATUS));
            }
        }
    }

    private void extractFilesFromFolder(String dir) {
        try (Stream<Path> listFiles = Files.list(Paths.get(dir))) {
            files.addAll(listFiles
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toSet()));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error in extractFilesFromFolder() method!");
        }
    }

    private void extractStatusParams(String[] statuses) {
        for (String status : statuses) {
            statusParams.add(OrderStatus.parseString(status));
        }
    }

    public Set<String> getFiles() {
        return files;
    }

    public String getAttribute() {
        return attributeName;
    }

    public Set<OrderStatus> getStatusParams() {
        return statusParams;
    }
}
