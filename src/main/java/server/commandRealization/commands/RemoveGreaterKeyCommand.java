package server.commandRealization.commands;


import general.console.Console;
import general.network.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'remove_greater_key key'. Removes all elements with key greater than the specified one from collection.
 * @author ren1kron
 */
public class RemoveGreaterKeyCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public RemoveGreaterKeyCommand(Console console, CollectionManager collectionManager) {
        super("remove_greater_key key", "Removes all elements with key greater than the specified one from collection");
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

//        int key = Integer.parseInt(arguments[1].trim());
        int key = request.getKey();

        // IDE recommended way
        collectionManager.getKeyMap().keySet().removeIf(integer -> key < integer);
        // harder way
//            Iterator<Integer> iterator = collectionManager.getKeyMap().keySet().iterator();
//            while (iterator.hasNext()) {
//                if (key < iterator.next()) iterator.remove();
//            }
        // easy way
//            List<Integer> list = new LinkedList<>();
//            for (Integer e : collectionManager.getKeyMap().keySet()) {
//                if (key < e) list.add(e);
//            }
//            for (Integer i : list) collectionManager.removeByKey(i);
        return new Request("All elements with key greater than specified one has been successfully removed from collection!");
    }
}
