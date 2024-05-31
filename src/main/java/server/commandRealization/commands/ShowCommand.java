package server.commandRealization.commands;


//import general.network.deprecated.Request;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'show'. Displays all elements of collection
 * @author ren1kron
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "Displays all elements of collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     * @param request Arguments for applying command
     * @return Command status
     */
    @Override
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.NORMAL)) return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        return new Response(new Request(collectionManager.toString()));
    }
}
