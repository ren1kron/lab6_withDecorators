package server.commandRealization.commands;


import general.network.abstractions.Sendable;
//import general.network.deprecated.Request;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import general.network.requestDecorators.KeyRequest;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'remove_key key'. Removes element from collection by its key.
 * @author ren1kron
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key key", "Removes element from collection by key");
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     * @param request Arguments for applying command
     * @return Command status
     */
    @Override
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.KEY_COMMAND))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        KeyRequest keyRequest = (KeyRequest) request;
        int key = keyRequest.key();

        System.out.println(key);

        if (collectionManager.removeByKey(key)) return new Response(new Request("Element was successfully deleted!"));
        else return new Response(false, new Request("Element with selected key is not exist"));
    }
}
