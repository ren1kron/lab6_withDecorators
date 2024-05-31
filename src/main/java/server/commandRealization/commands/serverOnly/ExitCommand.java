package server.commandRealization.commands.serverOnly;


import general.console.Console;
import general.network.abstractions.Sendable;
//import general.network.deprecated.Request;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.commandRealization.interfaces.ServerCommand;

/**
 * Command 'exit'. Closes the application without saving the collection to csv-file.
 * @author ren1kron
 */
public class ExitCommand extends Command implements ServerCommand {
    private final Console console;
    public ExitCommand(Console console) {
        // TODO повторение истории из save
//        super("exit", "Closes the application without saving the collection to csv-file");
        super("exit", "If you client - disconnects you from server. If you server - closes server without saving collection.");
        this.console = console;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */

    @Override
    public Response apply(Sendable request) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        console.println("Closing server...");
        System.exit(1);
        return null;
    }
}
