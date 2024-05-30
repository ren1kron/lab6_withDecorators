package server.utility;

import general.network.depricated.Request;
import server.managers.CommandManager;

public interface Executor {
    Request execute(Request request);
    CommandManager getCommandManager();
}
