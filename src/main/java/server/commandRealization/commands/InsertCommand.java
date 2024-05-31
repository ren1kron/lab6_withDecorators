package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
//import general.network.deprecated.Request;
import general.models.abstractions.Element;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.ElementRequest;
import general.network.requestDecorators.KeyRequest;
import general.network.requestDecorators.Response;
import general.network.requestDecorators.abstractions.SendableDecorator;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'insert key {element}'. Adds to collection new worker with inserted key
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
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.KEY_ELEMENT_COMMAND))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");
        SendableDecorator request1 = (SendableDecorator) request;
        ElementRequest elementRequest = (ElementRequest) request;
        Element element = elementRequest.element();

        KeyRequest keyRequest = (KeyRequest) request1;
        int key = keyRequest.key();


        if (!(key > 0)) return new Response(false, new Request("Not valid or empty key"));
        if (element == null) return new Response(false, new Request("Empty element"));

        Worker worker = (Worker) element;
//        int key = request.getKey();
        if (collectionManager.byKey(key) != null) return new Response(false, new Request("Worker with specified key already exist!"));
//        Worker worker = (Worker) request.getElement();
//        if (worker != null) worker.setId(collectionManager.getFreeId());
        worker.setId(collectionManager.getFreeId());
        worker.setKey(key);
        if (!worker.validate()) return new Response(false, new Request("Fields of inserted worker are invalid. Worker wasn't added to collection"));
//        if (worker != null && worker.validate()) {
        else {
            collectionManager.add(worker);
            return new Response(new Request("Worker was successfully added to collection!"));
        }
    }
}
