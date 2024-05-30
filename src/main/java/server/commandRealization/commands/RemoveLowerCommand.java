package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
import general.network.depricated.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'remove_lower'. Remove from collection all elements smaller than specified one.
 * @author ren1kron
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    public RemoveLowerCommand(Console console, CollectionManager collectionManager) {
        super("remove_lower {element}", "Remove from collection all elements smaller than specified one");
        this.collectionManager = collectionManager;
        this.console = console;
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

//        console.println("* Getting the worker to compare...");
        Worker worker = (Worker) request.getElement();
//        try {
//            var key = Asker.askKey(console);
//            worker = Asker.askWorker(console, key, collectionManager.getFreeId());
//            if (worker == null) throw new NullPointerException();
//        } catch (AskExitExecption e) {
//            return new ExecutionResponse(false, "Abort the operation...");
//        } catch (NullPointerException e) {
//            return new ExecutionResponse(false, "This is not a worker! Abort the operation...");
//        }
        for (var e : collectionManager.getKeyMap().values()) {
//            if (worker.compareTo(e) < 0) collectionManager.removeByKey(e.getKey());
            if (worker.getSalary() < e.getSalary()) collectionManager.removeByKey(e.getKey());
        }
//        return new ExecutionResponse("All workers with ID lower than specified was deleted!");
        return new Request("All worker's with salary lower than specified was deleted!");
    }
}
