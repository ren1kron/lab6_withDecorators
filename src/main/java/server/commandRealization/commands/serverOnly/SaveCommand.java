package server.commandRealization.commands.serverOnly;


import general.network.abstractions.Sendable;
//import general.network.deprecated.Request;
//import general.network.abstractions.RequestStatus;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.commandRealization.interfaces.ServerCommand;
import server.managers.CollectionManager;

/**
 * Command 'save'. Saves the collection changes that the user made in the last session.
 * !!! SERVER ONLY
 * @author ren1kron
 */
public class SaveCommand extends Command implements ServerCommand {
    private final CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager) {
        // TODO кринжуха – я просто делаю записи пустыми. Надо придумать, как это исправить
//        super("save", "Saves collection to file");
        super("save", "Saves collection to file. Available only on server.");
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

        collectionManager.saveMap();
        return null;
    }
}
