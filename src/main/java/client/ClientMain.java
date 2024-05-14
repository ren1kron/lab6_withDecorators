package client;

import client.network.TcpClientManager;
import client.utility.Requester;
import general.console.Console;
import general.console.StandardConsole;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);
//        TcpClientManager tcpClient = new TcpClientManager(address);
        Console console = new StandardConsole();
        TcpClientManager tcpClient = new TcpClientManager(console, address);

        tcpClient.start();


        Requester requester = new Requester(console, tcpClient);
        requester.interactiveMode();
    }
}
