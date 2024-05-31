package server.commandRealization.commands;


import general.console.Console;
import general.models.Worker;
import general.network.abstractions.Sendable;
//import general.network.deprecated.Request;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import general.network.requestDecorators.ElementRequest;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command 'remove_lower {element}'. Remove from collection all elements smaller than specified one.
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
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.ELEMENT_COMMAND))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        console.println("* Getting the worker to compare...");
        ElementRequest elementRequest = (ElementRequest) request;
        Worker worker = (Worker) elementRequest.element();
//        try {
//            var key = Asker.askKey(console);
//            worker = Asker.askWorker(console, key, collectionManager.getFreeId());
//            if (worker == null) throw new NullPointerException();
//        } catch (AskExitExecption e) {
//            return new ExecutionResponse(false, "Abort the operation...");
//        } catch (NullPointerException e) {
//            return new ExecutionResponse(false, "This is not a worker! Abort the operation...");
//        }
//        for (var e : collectionManager.getKeyMap().values()) {
////            if (worker.compareTo(e) < 0) collectionManager.removeByKey(e.getKey());
//            if (worker.getSalary() > e.getSalary()) collectionManager.removeByKey(e.getKey());
//        }
        collectionManager.getKeyMap().entrySet().stream()
                .filter(element -> element.getValue().getSalary() < worker.getSalary())
                .map(Map.Entry::getKey)
                .toList()
                .forEach(collectionManager::removeByKey);
//        return new ExecutionResponse("All workers with ID lower than specified was deleted!");
        return new Response(new Request("All worker's with salary lower than specified was deleted!"));
    }
}
