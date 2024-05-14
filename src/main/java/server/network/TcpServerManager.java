package server.network;

import general.network.Request;
import server.utility.Executor;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class TcpServerManager {
    private InetSocketAddress address;
    private Selector selector;
    private Executor executor;
//    private Set<SocketChannel> sessions;

    public TcpServerManager(InetSocketAddress address, Executor executor) {
        this.address = address;
        this.executor = executor;
    }
    public void start() throws IOException, ClassNotFoundException {
//        try {
            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(this.address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //
            // Есть вариация через serverSocketChannel.register(selector, ops, null). В чём разница и как лучше?
            System.out.println("––– Server started on port: " + address.getPort() + " –––");
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
//        } catch (IOException e) {
//            System.err.println("Error in server (while opening selector): " + e.getMessage());
//        }
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
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                System.out.println("Client disconnected...");
                return;
            }
            buffer.flip();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Request request = (Request) objectInputStream.readObject();
            Request execute = this.executor.execute(request);

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
            System.err.println("Error in server (while reading key): " + e.getMessage());
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
