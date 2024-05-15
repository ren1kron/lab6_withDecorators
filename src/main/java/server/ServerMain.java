package server;

import general.console.StandardConsole;
import server.commandRealization.commands.*;
import server.commandRealization.commands.serverOnly.ExitCommand;
import server.commandRealization.commands.serverOnly.SaveCommand;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.DumpManager;
import server.network.TcpServerManager;
import server.utility.CommandExecutor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerMain {
    private static final String ENV_KEY = "lab5";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var console = new StandardConsole();
        String fileName = System.getenv(ENV_KEY);
        System.out.println(fileName);
//        String fileName = "Worker.csv";
//        Path path = Paths.get(fileName);
//        System.out.println(path);
        DumpManager dumpManager = new DumpManager(fileName, console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.init()) {
            console.printError("Not valid data in loaded file!");
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new HelpCommand(this));
            register("info", new InfoCommand(collectionManager));
            register("show", new ShowCommand(collectionManager));
            register("insert", new InsertCommand(console, collectionManager));
            register("update", new UpdateCommand(console,collectionManager));
            register("remove_key", new RemoveKeyCommand(collectionManager));
            register("clear", new ClearCommand(collectionManager));
            register("save", new SaveCommand(collectionManager));
//            register("execute_script", new ExecuteScriptCommand());
            register("exit", new ExitCommand(console));
            register("remove_lower", new RemoveLowerCommand(console, collectionManager));
            register("replace_if_greater", new ReplaceIfGreaterCommand(console, collectionManager));
            register("remove_greater_key", new RemoveGreaterKeyCommand(console, collectionManager));
            register("group_counting_by_creation_date", new GroupCountingByCreationDateCommand(collectionManager));
            register("filter_by_position", new FilterByPosition(console, collectionManager));
            register("print_field_descending_salary", new PrintFieldDescendingSalaryCommand(collectionManager));
        }};
//         TODO сделать два класса, которые как-то с собой взаимодействуют.
//          один из них – подготавливает данные, а другой – их от него получает (пока без сети)
//          затем сделать простую программу, которая обменивается инфой по сети
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);

        CommandExecutor commandExecutor = new CommandExecutor(commandManager);
        TcpServerManager tcpServer = new TcpServerManager(address, commandExecutor, console);
        tcpServer.start();
//        commandExecutor.interactiveMode();
    }
}