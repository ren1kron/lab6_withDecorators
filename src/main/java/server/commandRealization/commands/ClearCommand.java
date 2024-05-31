package server.commandRealization.commands;


//import general.network.depricated.Request;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'clear'. This command clears collection
 * @author ren1kron
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "Clears the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     *
     * @param request Arguments for applying command
     * @return Command status
     */
    @Override
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.NORMAL)) return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");
        collectionManager.clear();
        return new Response(new Request("Collection was cleared!"));
    }
}
