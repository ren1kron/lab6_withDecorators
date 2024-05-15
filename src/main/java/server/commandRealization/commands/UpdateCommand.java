package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

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
    public Request apply(Request request) {
        if (!request.getStatus().equals(RequestStatus.KEY_ELEMENT_COMMAND))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");


        int id = request.getKey();

        var oldWorker = collectionManager.byId(id);
        if (oldWorker == null || !collectionManager.isContain(oldWorker)) return new Request("Worker with the specified ID does not exist!");
        var key = oldWorker.getKey();
        Worker worker = (Worker) request.getElement();

        if (worker != null && worker.validate()) {
            collectionManager.removeByKey(key);
            collectionManager.add(worker);
            return new Request("Worker with inserted id was successfully updated!");
        } else return new Request("Fields of inserted old Worker are invalid. Worker wasn't updated!");
    }
}
