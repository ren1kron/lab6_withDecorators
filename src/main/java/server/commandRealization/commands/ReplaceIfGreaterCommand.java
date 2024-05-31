package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
//import general.network.deprecated.Request;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.ElementRequest;
import general.network.requestDecorators.KeyRequest;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'replace_if_greater key {element}'. Replaces element with specified key if new element bigger than old one
 * @author ren1kron
 */
public class ReplaceIfGreaterCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public ReplaceIfGreaterCommand(Console console, CollectionManager collectionManager) {
        super("replace_if_greater key {element}", "Replaces element with specified key if new element bigger than old one");
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
//        if (!request.getStatus().equals(RequestStatus.KEY_COMMAND))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        KeyRequest keyRequest = (KeyRequest) request;
        int key = keyRequest.key();
        ElementRequest elementRequest = (ElementRequest) request;
        Worker newWorker = (Worker) elementRequest.element();

        if (key <= 0 || !newWorker.validate()) return new Response(false, new Request("Key or fields of inserted worker are invalid. Worker wasn't updated."));

        Worker oldWorker = collectionManager.byKey(key);
        if (oldWorker == null || !collectionManager.isContain(oldWorker)) return new Response(false, new Request("Worker with the specified key does not exist. If you want to add newWorker with this key anyway, use command 'insert key {element}"));
        var id = oldWorker.getId();

        newWorker.setId(id);
        newWorker.setKey(key);


        if (newWorker.validate() && (newWorker.getSalary() > oldWorker.getSalary())) {
            collectionManager.removeByKey(key);
            collectionManager.add(newWorker);
            return new Response(new Request("Worker with selected key was successfully replaced with new Worker!"));
//            return new ExecutionResponse("Worker with inserted id was successfully updated!");
        } else return new Response(false, new Request("Old Worker has bigger salary or fields of new Worker are invalid. Collection was not updated!"));
//        } else return new ExecutionResponse(false, "Fields of inserted oldWorker are invalid. Worker wasn't updated!");
    }
}
