package client;

import client.network.TcpClientManager;
import client.utility.Requester;
import general.console.Console;
import general.console.StandardConsole;
import general.exceptions.AskExitException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, AskExitException {
//        Request hui = new Request("hui");
//        KeyRequest keyRequest = new KeyRequest(hui, 1);
//        Console console = new StandardConsole();
//        ElementRequest elementRequest = new ElementRequest(keyRequest, Asker.askWorker(console, 0, 0));
//
//        System.out.println(elementRequest.element());
//        Sendable wrappedSendable = elementRequest;
//        KeyRequest wrappedKey = (KeyRequest) wrappedSendable;
//        System.out.println(keyRequest.key());

        InetSocketAddress address = new InetSocketAddress("localhost", 2612);
//        TcpClientManager tcpClient = new TcpClientManager(address);
        Console console = new StandardConsole();
        TcpClientManager tcpClient = new TcpClientManager(console, address);

        tcpClient.start();


        Requester requester = new Requester(console, tcpClient);
        requester.interactiveMode();

    }
}
