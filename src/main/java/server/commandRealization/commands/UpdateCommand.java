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
import server.commandRealization.Command;
import server.managers.CollectionManager;

import java.util.NoSuchElementException;

/**
 * Command 'update id {element}'. This command updates element with inserted id
 * @author ren1kron
 */
public class UpdateCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public UpdateCommand(Console console, CollectionManager collectionManager) {
        super("update id {element}", "Update element of collection with inserted id");
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


        ElementRequest elementRequest = (ElementRequest) request;
        Worker worker = (Worker) elementRequest.element();
//        KeyRequest keyRequest = (KeyRequest) request;
//        int id = keyRequest.key();
        try {
            int id = elementRequest.key();

            if (!(id > 0)) return new Response(false, new Request("Selected id is invalid!"));

            var oldWorker = collectionManager.byId(id);
            if (oldWorker == null || !collectionManager.isContain(oldWorker))
                return new Response(false, new Request("Worker with the specified ID does not exist!"));
            var key = oldWorker.getKey();

//        Worker worker = (Worker) request.getElement();

            if (worker != null && worker.validate()) {
                collectionManager.removeByKey(key);
                collectionManager.add(worker);
                return new Response(new Request("Worker with inserted id was successfully updated!"));
            } else
                return new Response(false, new Request("Fields of inserted old Worker are invalid. Worker wasn't updated!"));
        } catch (NoSuchElementException e) {
            return new Response(false, new Request("The request received from the client is incorrect. There is no key in it!"));
        }
    }
}
