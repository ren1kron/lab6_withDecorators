package client.utility;

import client.network.TcpClientManager;
import general.console.Console;
import general.exceptions.AskExitException;
//import general.network.depricated.Request;
import general.models.Position;
import general.models.Worker;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.ElementRequest;
import general.network.requestDecorators.KeyRequest;
import general.network.requestDecorators.PositionRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Requester {
//    private final static String[] key_commands = {"insert"};
    private final static List<String> key_commands = List.of("insert", "remove_key", "replace_if_greater", "remove_greater_key", "update");
//    private final static List<String> key_commands = List.of("insert", "remove_key", "replace_if_greater", "remove_greater_key");
    private final static List<String> element_commands = List.of("insert", "update", "remove_lower", "replace_if_greater");
//    private final static List<String> id_commands = List.of("update");
    private final static List<String> position_commands = List.of("filter_by_position");
//    private final static String[] id_commands = {"update"};
    private final List<String> scriptStack = new ArrayList<>();
    private Console console;
    private TcpClientManager client;

    public Requester(Console console, TcpClientManager client) {
        this.console = console;
        this.client = client;
    }
    public void interactiveMode() {
        String[] userInput;
        boolean exit = false;
        while (!exit) {
            console.prompt();
            userInput = (console.readln().trim()).split(" ");
            if (userInput.length > 1) userInput[1] = userInput[1].trim();

            if (userInput[0].isEmpty()) continue;
            else if (userInput[0].equals("exit")) {
                console.printError("Closing client...");
                System.exit(1);
            }

            if (userInput.length > 2) {
                console.printError("Wrong amount of arguments! Write 'help' for help");
//                console.prompt();
                continue;
            }
            executeCommand(userInput);

        }
    }
    private void executeCommand(String[] userCommand) {
        try {
//            Request request;
            Sendable request = null;
            String command = userCommand[0];
//            if (command.equals("execute_script") && userCommand.length == 1) console.printError("Wrong amount of arguments! You suppose to write 'execute_script file_name'");
            Request request_build = new Request(command);
//            if (command.endsWith("_position")) {
            if (key_commands.contains(command)) {
                int key = Integer.parseInt(userCommand[1]);
                request = new KeyRequest(request_build, key);
            }
            if (position_commands.contains(command)) {
                Position position = Asker.askPosition(console);
                request = new PositionRequest(request_build, position);
            }
//            if (id_commands.contains(command)) {
//                int id = Integer.parseInt(userCommand[1]);
//                request = new KeyRequest(request_build, id);
//            }
            if (element_commands.contains(command)) {
                Worker worker = Asker.askWorker(console, 0, 0);
                request = new ElementRequest(request_build, worker);
            }
            client.sendRequest(request);



////            if (userCommand.length > 1) Integer.parseInt(userCommand[1]);
//            if (userCommand.length == 1) {
//                if (userCommand[0].equals("exit")) {
//                    console.println("Closing client...");
//                    System.exit(1);
//                }
//                if (userCommand[0].equals("execute_script")) {
//                    console.println("Wrong amount of arguments! You suppose to write 'execute_script file_name'");
////                    console.prompt();
//                    return;
//                }
//                // TODO пофиксить кринжовый костыль...
//                if (userCommand[0].equals("filter_by_position")) {
//                    Position position = Asker.askPosition(console);
//                    building_request = new Request(command, position);
//                }
//                else
//                    building_request = new Request(command);
//            }
////        Request response = client.sendRequest(building_request);
//            else {
//                // execute_script realization
//                if (userCommand[0].equals("execute_script")) {
//                    String scriptName = userCommand[1];
//                    scriptMode(scriptName);
//                    return;
////
////                    console.println(scriptOutput.getMessage());
////                    console.println("Script " + scriptName + " successfully executed!");
//
//                }
//
//                int key = Integer.parseInt(userCommand[1]);
//                if (key_commands.contains(command)) {
//                    Worker worker = Asker.askWorker(console, key, 0);
//                    building_request = new Request(command, key, worker);
//                } else if (id_commands.contains(command)) {
//                    Worker worker = Asker.askWorker(console, 0, key);
//                    building_request = new Request(command, key, worker);
//                } else building_request = new Request(command, key);
//
//            }
//        else if (userCommand.length == 2 && Arrays.asList(key_commands).contains(userCommand[0])) {
//            client.sendRequest(request);
        } catch (AskExitException e) {
            console.println("Abort the operation...");
        } catch (NumberFormatException e) {
            console.println("Inserted key is invalid!");
        }

    }
    private void scriptMode(String scriptName) {
        String[] userCommand;
//        StringBuilder executionOutput = new StringBuilder();


        if (!new File(scriptName).exists()) {
//            return new Request("This file does not exist!");
            console.printError("This file does not exist!");
            return;
        }
        if (!Files.isReadable(Paths.get(scriptName))) {
//            return new Request("You don't have access to this file!");
            console.printError("You don't have access to this file!");
            return;
        }

        try (Scanner scriptScanner = new Scanner(new File(scriptName))) {

            Request commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim()).split(" ");
                if (userCommand.length > 1) userCommand[1] = userCommand[1].trim();

                if (userCommand[0].isEmpty()) {
//                    console.println("");
//                    console.prompt();
                    continue;
                }

                if (userCommand.length > 2) {
//                    console.printError("Wrong amount of arguments!");
//                    console.prompt();
                    continue;
                }
                boolean needLaunch = true;
                if (userCommand[0].equals("execute_script"))
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
//                commandStatus = needLaunch ? executeCommand(userCommand) : new Request("Recursion detected");
                executeCommand(userCommand);
            } while(!userCommand[0].equals("exit") && console.isCanReadln());
            console.selectConsoleScanner();
        } catch (FileNotFoundException e) {
//            return new Request("Script file was not found!");
            console.printError("Script file was not found!");
//            return;
        } catch (NoSuchElementException e) {
//            return new Request("Script file is empty!");
            console.printError("Script file is empty!");
//            return;
        }
    }

    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
//                if (lengthRecursion < 0) {
//                    console.selectConsoleScanner();
////                    console.println("Recursion was detected! Enter max size of recursion (0..500)");
////                    while (lengthRecursion < 0 || lengthRecursion > 500) {
////                        try { console.print("> "); lengthRecursion = Integer.parseInt(console.readln().trim()); } catch (NumberFormatException e) { console.println("длина не распознана"); }
////                    }
//                    console.selectFileScanner(scriptScanner);
//                }
                if (i > recStart  || i > 500)
                    return false;
            }
        }
        return true;
    }
}
