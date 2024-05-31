package server.commandRealization.commands;


import general.console.Console;
import general.models.Position;
//import general.network.deprecated.Request;
import general.models.Worker;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.Response;
import general.network.requestDecorators.PositionRequest;
import server.commandRealization.Command;
import server.managers.CollectionManager;

import java.util.stream.Collectors;

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
     *
     * @param request Arguments for applying command
     * @return Command status
     */
    @Override
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.POSITION_COMMAND))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        console.println("* Entering position to compare...");
//        var position = Asker.askPosition(console);

        PositionRequest positionRequest = (PositionRequest) request;
        Position position = positionRequest.getPosition();

        var result = collectionManager.getKeyMap().values().stream()
                .filter(worker -> worker.getPosition().equals(position))
                .map(Worker::toString)
                .collect(Collectors.joining("\n", "* Workers with selected position", ""));
        Request response_build = new Request(result);
        return new Response(response_build);
//                .sorted(Map.Entry.comparingByValue((w1, w2) -> Float.compare(w2.getSalary(), w1.getSalary())))
////                .forEach(entry -> stringBuilder.append(entry.getValue().getSalary())
//////                        .append("\n"));
////                        .append("; "));
//                .map(entry -> String.valueOf(entry.getValue().getSalary()))
//                .collect(Collectors.joining("; ", "* Salaries of workers in descending order: ", ""));

//        var stringBuilder = new StringBuilder();
//        for (var e : collectionManager.getKeyMap().values()) if (e.getPosition().equals(position)) stringBuilder.append(e);
//        return new Request(stringBuilder.toString());
    }
}
