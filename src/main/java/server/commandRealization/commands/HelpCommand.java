package server.commandRealization.commands;

import general.network.depricated.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CommandManager;

import java.util.stream.Collectors;

/**
 * Command 'help'. This command displays help for available commands
 * @author ren1kron
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;
    public HelpCommand(CommandManager commandManager) {
        super("help", "Displays help for available commands");
        this.commandManager = commandManager;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public Request apply(Request request) {
//        if (request.getKey().equals(null)) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");
        if (!request.getStatus().equals(RequestStatus.NORMAL)) return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");
//        System.out.println(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
        return new Request(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
