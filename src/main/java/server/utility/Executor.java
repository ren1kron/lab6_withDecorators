package server.utility;

//import general.network.depricated.Request;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.Response;
import server.managers.CommandManager;

public interface Executor {
    Response execute(Sendable request);
    CommandManager getCommandManager();
}
