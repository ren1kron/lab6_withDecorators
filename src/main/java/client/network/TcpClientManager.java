package client.network;


import general.console.Console;
import general.network.abstractions.Sendable;
import general.network.depricated.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TcpClientManager {
    private Console console;
    private InetSocketAddress address;
    private SocketChannel client;
//    private Requester requester;

    public TcpClientManager(Console console, InetSocketAddress address) {
        this.console = console;
        this.address = address;
    }
    public void start() {
        boolean connection = false;
        while (!connection) {
            try {
                client = SocketChannel.open(address);
                client.configureBlocking(false);

                console.println("You connected to server at " + address.getHostName() + ":" + address.getPort() +
                        "\n–––    WELCOME!!!    ––– \n" +
                        "Enter command 'help' for help");


                connection = true;

            } catch (IOException e) {
//                System.err.println("Error while starting client : " + e.getMessage());
//                System.exit(1);
                console.printError("Unable to connect to server. We will try to reconnect in 5 seconds...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
    // TODO есть идея запихнуть инициализацию стримов и буфера в старт. А потом из остальных методов их вызывать.
    //  Может, так будет эффективнее, хз.
//    public void sendRequest(Request request) {
    public void sendRequest(Sendable request) {
        boolean sendStatus = false;
        boolean errorThrown = false;
        do {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
                outputStream.writeObject(request);
//            outputStream.flush();
                outputStream.close();
                ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                while (buffer.hasRemaining()) {
                    client.write(buffer);
//                client.read(buffer);
                }
                // Read the response back from the server
                Request response = getAnswer();
                // TODO здесь должна быть проверка на ошибку
                console.println(response.getMessage());
                sendStatus = true;
            } catch (IOException e) {
//                console.printError("Unable to connect to server. We will try to reconnect in 5 seconds...");
                if (!errorThrown) {
                    console.printError("Unable to connect to server. We will try to reconnect...");
                    errorThrown = true;
                }
//                try {
//                    Thread.sleep(5000);
//                    start();
                boolean reconnected = false;
                try {
                    while (!reconnected) {
                        client = SocketChannel.open(address);
                        client.configureBlocking(false);
                        reconnected = true;
                    }
                } catch (IOException ignored) { }
//                console.println("Try again");
//                console.println("Connection restored!");
//                    sendRequest(request);
//                } catch (InterruptedException ex) {
//                    Thread.currentThread().interrupt();
//                }
            }
        } while (!sendStatus);
    }

    private Request getAnswer() throws IOException {
        Selector selector = Selector.open();
        client.register(selector, client.validOps());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 10000) {
            int readyChannels = selector.select(1000); // Таймаут для select, чтобы не блокировать навсегда

            if (readyChannels == 0) {
                continue;
            }

            while (true) {
                int bytesRead = client.read(buffer);
                if (bytesRead == -1) {
                    break; // Конец потока данных
                }
                if (bytesRead == 0) {
                    break; // Нет доступных данных для чтения
                }

                buffer.flip();
                byteArrayOutputStream.write(buffer.array(), 0, buffer.limit());
                buffer.clear();
            }

            byte[] responseBytes = byteArrayOutputStream.toByteArray();
            if (responseBytes.length > 0) {
                try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(responseBytes))) {
                    Request response = (Request) oi.readObject();
                    return response;
                } catch (EOFException | StreamCorruptedException ignored) {
                    // Error while deserializing object. Maybe, some data wasn't gotten
                    // Continue reading data...
                } catch (ClassNotFoundException e) {
                    System.err.println("Error while reading object :" + e.getMessage());
                }
            }
        }
        return null;
    }

}
