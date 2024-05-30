package server.commandRealization.commands;


import general.console.Console;
import general.models.Position;
import general.network.depricated.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

/**
 * Command 'filter_by_position'. Displays elements whose position equals to the specified one
 * @author ren1kron
 */
public class FilterByPosition extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public FilterByPosition(Console console, CollectionManager collectionManager) {
        super("filter_by_position position", "Displays elements whose position equals to the specified one");
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
        if (!request.getStatus().equals(RequestStatus.POSITION_COMMAND))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        console.println("* Entering position to compare...");
//        var position = Asker.askPosition(console);
        Position position = request.getPosition();
        var stringBuilder = new StringBuilder();
        for (var e : collectionManager.getKeyMap().values()) if (e.getPosition().equals(position)) stringBuilder.append(e);
        return new Request(stringBuilder.toString());
    }
}
