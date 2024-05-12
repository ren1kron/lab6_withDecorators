package client;

import client.network.TcpClientManager;
import client.utility.Requester;
import client.utility.console.Console;
import client.utility.console.StandardConsole;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);
        TcpClientManager tcpClient = new TcpClientManager(address);

        tcpClient.start();

        Console console = new StandardConsole();
        Requester requester = new Requester(console, tcpClient);
        requester.interactiveMode();
    }
}
