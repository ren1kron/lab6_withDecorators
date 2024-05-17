package server.network;

import general.console.Console;
import general.network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.commandRealization.interfaces.ServerCommand;
import server.managers.CommandManager;
import server.utility.Executor;

import java.io.*;
import java.net.InetSocketAddress;
//import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class TcpServerManager {
    private static final Logger logger = LogManager.getLogger();
    private InetSocketAddress address;
    private Selector selector;
    private Executor executor;
    private Console console;
//    private Set<SocketChannel> sessions;

    public TcpServerManager(InetSocketAddress address, Executor executor, Console console) {
        this.address = address;
        this.executor = executor;
        this.console = console;
    }
    public void start() {
        try {
            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(this.address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //
            // Есть вариация через serverSocketChannel.register(selector, ops, null). В чём разница и как лучше?
//            System.out.println("––– Server started on port: " + address.getPort() + " –––");
            logger.info("––– Server started on port: " + address.getPort() + " –––");
            new Thread(() -> {
                while (true) {
                    try {
                        CommandManager commandManager = executor.getCommandManager();
                        String input = console.readln().trim();
                        if (input.equals("save")) {
                            commandManager.getCommands().get("save").apply(new Request(""));
                        } else if (input.equals("exit")) {
    //                        commandManager.getCommands().get("save").apply(new Request(""));
                            commandManager.getCommands().get("exit").apply(new Request(""));
                        } else {
                            logger.warn("Inserted command is not available for server. You are able to use this commands: 'save', 'exit'");
//                            console.println("Inserted command is not available for server. You are able to use this commands: 'save', 'exit'");
                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) handleAccept(key);
                    else if (key.isReadable())
                        handleRead(key);
                }
            }
        } catch (IOException e) {
//            System.err.println("Error in server (while opening selector): " + e.getMessage());
            logger.error("Error in server (while opening selector): " + e.getMessage());
        }
    }
    private void handleAccept(SelectionKey key) {
        try {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            // Есть идея здесь добавить отправку сервисных моментов: список команд с ключом, с элементом и пр. Но, наверное, это уже too much
//            ByteBuffer service = ByteBuffer.allocate(1024);
//            service.put(executor.)
//            client.write(service);
            //
            client.register(selector, SelectionKey.OP_READ);
            logger.info("New client connected:" + client.socket().getRemoteSocketAddress().toString() + "\n");
        } catch (IOException e) {
            logger.error("Error in server while accepting client : " + e.getMessage());
        }
    }
    private void handleRead(SelectionKey key) {
        try {
            SocketChannel client = (SocketChannel) key.channel();
            client.configureBlocking(false);
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            int read = client.read(buffer);
            if (read == -1) {
                client.close();
//                System.out.println("Client disconnected...");
                logger.info("Client " + client.socket().getRemoteSocketAddress() + " disconnected...");
//                key.cancel();
                return;
            }
            buffer.flip();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Request request = (Request) objectInputStream.readObject();
            Request execute;
            // if command is for server only, we send back request with error
            if (executor.getCommandManager().getCommands().get(request.getMessage()) instanceof ServerCommand) {
//                System.out.println(execute.getMessage());
                execute = new Request("You don't have permissions to use this command.");
            } else execute = this.executor.execute(request);

            // Echo the request back to client
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(execute);
//            outputStream.flush();
            outputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            ByteBuffer output = ByteBuffer.wrap(data);
            client.write(output);
        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Error in server (while reading key): " + e.getMessage());
            logger.error("Error in server (while reading key): " + e.getMessage());
//            logger.info("Client " + " unexpectedly disconnect");
//            throw new RuntimeException(e);
        }
    }
//    private void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
//        SocketChannel client = (SocketChannel) key.channel();
//        client.configureBlocking(false);
////        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        ByteBuffer buffer = ByteBuffer.allocate(2048);
//        int read = client.read(buffer);
//        if (read == -1) {
//            client.close();
//            System.out.println("Client disconnected...");
//            return;
//        }
//        buffer.flip();
//        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
//        Request request = (Request) objectInputStream.readObject();
//        Request execute = this.executor.execute(request);
//
//        // Echo the request back to client
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
//        outputStream.writeObject(execute);
////            outputStream.flush();
//        outputStream.close();
//        byte[] data = byteArrayOutputStream.toByteArray();
//        ByteBuffer output = ByteBuffer.wrap(data);
//        client.write(output);
//    }


}
