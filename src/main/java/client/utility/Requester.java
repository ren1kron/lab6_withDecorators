package client.utility;

import client.network.TcpClientManager;
import client.utility.console.Console;
import general.network.Request;

import java.io.IOException;

public class Requester {
    private Console console;
    private TcpClientManager client;

    public Requester(Console console, TcpClientManager client) {
        this.console = console;
        this.client = client;
    }
    public void interactiveMode() throws IOException, ClassNotFoundException {
        String[] userInput;
        while (true) {
            console.prompt();
            userInput = (console.readln().trim()).split(" ", 1);
            if (userInput.length > 1) userInput[1] = userInput[1].trim();

            if (userInput[0].isEmpty()) continue;
            executeCommand(userInput);

        }
    }
    private void executeCommand(String[] userCommand) throws IOException, ClassNotFoundException {
        Request request = new Request(userCommand[0]);
        Request response = client.sendRequest(request);
//        console.println(client.read().getMessage());
        console.println(response.getMessage());
    }
}
