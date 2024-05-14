package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'insert'. Adds to collection new worker with inserted key
 * @author ren1kron
 */
public class InsertCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public InsertCommand(Console console, CollectionManager collectionManager) {
        super("insert key {element}", "Adds new element with specified key in collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     * @param request Arguments for applying command
     * @return Command status
     */

    @Override
    public Request apply(Request request) {
        if (!request.getStatus().equals(RequestStatus.ELEMENT_COMMAND))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        int key = request.getKey();
        if (collectionManager.byKey(key) != null) return new Request("Worker with specified key already exist!");
        Worker worker = (Worker) request.getElement();
//        if (worker != null) worker.setId(collectionManager.getFreeId());
        worker.setId(collectionManager.getFreeId());
        if (!worker.validate()) return new Request("Fields of inserted worker are invalid. Worker wasn't added to collection");
//        if (worker != null && worker.validate()) {
        else {
            collectionManager.add(worker);
            return new Request("Worker was successfully added to collection!");
        }
    }
}
