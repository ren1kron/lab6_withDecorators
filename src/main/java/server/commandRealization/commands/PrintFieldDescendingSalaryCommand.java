package server.commandRealization.commands;


import general.network.abstractions.Sendable;
//import general.network.deprecated.Request;
import general.network.Request;
import general.network.requestDecorators.Response;
import server.commandRealization.Command;
import server.managers.CollectionManager;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command 'print_field_descending_salary'. Displays salary values of all elements in descending order
 * @author ren1kron
 */
public class PrintFieldDescendingSalaryCommand extends Command {
    private final CollectionManager collectionManager;
    public PrintFieldDescendingSalaryCommand(CollectionManager collectionManager) {
        super("print_field_descending_salary", "Displays salary values of all elements in descending order");
        this.collectionManager = collectionManager;
    }
    /**
     * Applies command
     * @param request Arguments for applying command
     * @return Command status
     */
    @Override
    public Response apply(Sendable request) {
//        if (!request.getStatus().equals(RequestStatus.NORMAL))
//            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        var stringBuilder = new StringBuilder("* Salaries of workers in descending order:\n");
//        var stringBuilder = new StringBuilder("* Salaries of workers in descending order: ");
        var result = collectionManager.getKeyMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue((w1, w2) -> Float.compare(w2.getSalary(), w1.getSalary())))
//                .forEach(entry -> stringBuilder.append(entry.getValue().getSalary())
////                        .append("\n"));
//                        .append("; "));
                .map(entry -> String.valueOf(entry.getValue().getSalary()))
                .collect(Collectors.joining("; ", "* Salaries of workers in descending order: ", ""));
        return new Response(new Request(result));
    }
}
