package server.utility;

import general.network.depricated.Request;
import server.commandRealization.Command;
import server.managers.CommandManager;

public class CommandExecutor implements Executor{
    private final CommandManager commandManager;

    public CommandExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

//    public void interactiveMode() {
//        String[] userInput;
//        while (true) {
////            console.prompt();
//            userInput = (console.readln().trim()).split(" ");
//            if (userInput.length > 1) userInput[1] = userInput[1].trim();
//
//            if (userInput[0].isEmpty() || userInput.length > 1) {
//                console.println("");
//                console.prompt();
//                continue;
//            }
//
//            console.println(commandManager.getCommands().get(userInput[0]).apply(null));
//
//        }
//    }

    @Override
    public Request execute(Request request) {
        Command command = commandManager.getCommands().get(request.getMessage());

        if (command == null) return new Request("Inserted command is not exist or you do not have permission to use it");
        return command.apply(request);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
