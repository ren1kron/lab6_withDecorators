package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
import general.network.depricated.Request;
import general.network.abstractions.RequestStatus;
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
    public Request apply(Request request) {
        if (!request.getStatus().equals(RequestStatus.KEY_COMMAND))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");


        int key = request.getKey();
        Worker newWorker = (Worker) request.getElement();

        var oldWorker = collectionManager.byKey(key);
        if (oldWorker == null || !collectionManager.isContain(oldWorker)) return new Request("Worker with the specified key does not exist. If you want to add newWorker with this key anyway, use command 'insert key {element}");
        var id = oldWorker.getId();

        newWorker.setId(id);


        if (newWorker.validate() && (newWorker.getSalary() > oldWorker.getSalary())) {
            collectionManager.removeByKey(key);
            collectionManager.add(newWorker);
            return new Request("Worker with inserted id was successfully updated!");
//            return new ExecutionResponse("Worker with inserted id was successfully updated!");
        } else return new Request("Fields of inserted oldWorker are invalid. Worker wasn't updated!");
//        } else return new ExecutionResponse(false, "Fields of inserted oldWorker are invalid. Worker wasn't updated!");
    }
}
