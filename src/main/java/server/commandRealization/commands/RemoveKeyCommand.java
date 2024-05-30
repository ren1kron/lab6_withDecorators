package server.commandRealization.commands;


import general.network.depricated.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'remove_key'. Removes element from collection by its key.
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
    public Request apply(Request request) {
        if (!request.getStatus().equals(RequestStatus.KEY_COMMAND))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        int key = request.getKey();
        collectionManager.removeByKey(key);


        return new Request("Element was successfully deleted!");
    }
}
