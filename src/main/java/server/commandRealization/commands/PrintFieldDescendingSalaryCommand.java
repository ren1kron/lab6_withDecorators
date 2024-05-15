package server.commandRealization.commands;


import general.network.Request;
import general.network.abstractions.RequestStatus;
import server.commandRealization.Command;
import server.managers.CollectionManager;

import java.util.Map;

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
    public Request apply(Request request) {
        if (!request.getStatus().equals(RequestStatus.NORMAL))
            return new Request("Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        var stringBuilder = new StringBuilder("* Salaries of workers in descending order:\n");
        var stringBuilder = new StringBuilder("* Salaries of workers in descending order: ");
        collectionManager.getKeyMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue((w1, w2) -> Float.compare(w2.getSalary(), w1.getSalary())))
                .forEach(entry -> stringBuilder.append(entry.getValue().getSalary())
//                        .append("\n"));
                        .append("; "));
        return new Request(stringBuilder.toString());
    }
}
