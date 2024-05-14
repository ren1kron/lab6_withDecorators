package server.utility;

import general.network.Request;
import server.managers.CommandManager;

public interface Executor {
    Request execute(Request request);
    CommandManager getCommandManager();
}
