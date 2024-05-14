package client.utility;

import client.network.TcpClientManager;
import client.utility.console.Console;
import general.network.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Requester {
    private final static String[] adding_commands = {"insert"};
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
        Request request;
        if (userCommand.length < 1) {
            console.println("");
            console.prompt();
            return;
        }
        else if (userCommand.length == 1) request = new Request(userCommand[0]);
//        Request response = client.sendRequest(request);
        else if (userCommand.length == 2 && !Arrays.asList(adding_commands).contains(userCommand[0])) {
            Asker
        }
        else if (userCommand.length == 2 && Arrays.asList(adding_commands).contains(userCommand[0])) {

        }
        client.sendRequest(request);
//        console.println(client.read().getMessage());
//        console.println(response.getMessage());
    }
}
