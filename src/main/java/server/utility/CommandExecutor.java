package server.utility;

import general.network.Request;
import server.commandRealization.Command;
import server.managers.CommandManager;

public class CommandExecutor implements Executor{
    private final CommandManager commandManager;

    public CommandExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public Request execute(Request request) {
        Command command = commandManager.getCommands().get(request.getMessage());
        if (command == null) return new Request("Inserted command is not exist or you do not have permission to use it");
        return command.apply(request);
    }
}
