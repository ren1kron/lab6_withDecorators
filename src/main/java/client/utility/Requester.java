package client.utility;

import client.network.TcpClientManager;
import client.utility.console.Console;
import general.network.Request;

public class Requester {
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
            userInput = (console.readln().trim()).split(" ", 1);
            userInput[1] = userInput[1].trim();

            if (userInput[0].isEmpty()) continue;
            executeCommand(userInput);

        }
    }
    private void executeCommand(String[] userCommand) {
        Request request = new Request(userCommand[0]);
        client.send(request);
        console.println(client.read().getMessage());
    }
}
