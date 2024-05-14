package client.utility;

import client.network.TcpClientManager;
import client.utility.console.Console;
import general.exceptions.AskExitExecption;
import general.models.Worker;
import general.network.Request;

import java.util.List;

public class Requester {
//    private final static String[] key_commands = {"insert"};
    private final static List<String> key_commands = List.of("insert");
    private final static List<String> id_commands = List.of("update");
//    private final static String[] id_commands = {"update"};
    private Console console;
    private TcpClientManager client;

    public Requester(Console console, TcpClientManager client) {
        this.console = console;
        this.client = client;
    }
    public void interactiveMode() {
        String[] userInput;
        while (true) {
            console.prompt();
            userInput = (console.readln().trim()).split(" ");
            if (userInput.length > 1) userInput[1] = userInput[1].trim();

            if (userInput[0].isEmpty()) {
                console.println("");
                console.prompt();
                continue;
            }

            if (userInput.length > 2) {
                console.printError("Wrong amount of arguments! Write 'help' for help");
                console.prompt();
                continue;
            }
            executeCommand(userInput);

        }
    }
    private void executeCommand(String[] userCommand) {
        try {
            Request request;
            String command = userCommand[0];
//            if (userCommand.length > 1) Integer.parseInt(userCommand[1]);
            if (userCommand.length == 1) request = new Request(command);
//        Request response = client.sendRequest(request);
            else {
                int key = Integer.parseInt(userCommand[1]);
                if (key_commands.contains(command)) {
                    Worker worker = Asker.askWorker(console, key, 0);
                    request = new Request(command, key, worker);
                } else if (id_commands.contains(command)) {
                    Worker worker = Asker.askWorker(console, 0, key);
                    request = new Request(command, key, worker);
                } else request = new Request(command, key);

            }
//        else if (userCommand.length == 2 && Arrays.asList(key_commands).contains(userCommand[0])) {
            client.sendRequest(request);
        } catch (AskExitExecption e) {
            console.println("Abort the operation...");
        } catch (NumberFormatException e) {
            console.println("Inserted key is invalid!");
        }

    }
}
