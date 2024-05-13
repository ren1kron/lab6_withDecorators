package client.network;


import client.utility.console.Console;
import general.network.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

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
                console.println("You connected to server at " + address.getHostName() + ":" + address.getPort());
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
    public Request sendRequest(Request request) throws IOException, ClassNotFoundException {
//        try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(request);
//            outputStream.flush();
        outputStream.close();
//            byte[] bytes = byteArrayOutputStream.toByteArray();
//            ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
        // Read the response back from the server
        Request response = getAnswer();
//        console.println(response);
        return response;
        //            buffer.clear();
//            client.read(buffer);
//            buffer.flip();
//            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
//            Request read = (Request) inputStream.readObject();
//            return read;
//        } catch (IOException e) {
//            System.err.println("Error while sending request :" + e.getMessage());
//        } catch (ClassNotFoundException e) {
//            System.err.println("Error while reading response from server : " + e.getMessage());
//        }
//        return null;
    }
//    public Request getAnswer() throws InterruptedException, IOException, ClassNotFoundException {
//        Thread.sleep(2000);
//        ArrayList<ByteBuffer> bufferList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            client.read(buffer);
//            if (buffer.limit() == buffer.position() || bufferList.isEmpty()) {
//                bufferList.add(buffer);
//            } else {
//                break;
//            }
//        }
//        ByteBuffer bigBuffer = ByteBuffer.allocate(bufferList.size() * 8192);
//        for (ByteBuffer byteBuffer : bufferList) {
//            bigBuffer.put(byteBuffer.array());
//        }
//        ByteArrayInputStream bi = new ByteArrayInputStream(bigBuffer.array());
//        ObjectInputStream oi = new ObjectInputStream(bi);
//
//        return (Request) oi.readObject();
//    }

    public Request getAnswer() throws IOException {
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
                    System.err.println("Error while ");
                }
            }
        }
        return null;
    }

//    public Request read() {
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        try {
//            while (client.read(buffer) > 0) {
//                buffer.flip();
//                ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
//                Request response = (Request) objectOutputStream.readObject();
//                buffer.clear();
////            CommandExecutor.execute(response);
//
//                return response;
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Error while reading response from server : " + e.getMessage());
//        }
//        return null;
//    }
}
