package server.commandRealization.commands.serverOnly;


import general.console.Console;
import general.network.Request;
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
        super("exit", "Disconnects client");
        this.console = console;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */

    @Override
    public Request apply(Request request) {
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
