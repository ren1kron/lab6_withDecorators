package server.managers;

import server.commandRealization.Command;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages commands
 * @author ren1kron
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return Map of commands
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
