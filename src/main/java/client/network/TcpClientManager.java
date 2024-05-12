package client.network;


import general.network.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpClientManager {
    private InetSocketAddress address;
    private SocketChannel client;
//    private Requester requester;

    public TcpClientManager(InetSocketAddress address) {
        this.address = address;
    }
    public void start() {
        try {
            client = SocketChannel.open(address);
            client.configureBlocking(false);
            System.out.println("You connected to server at " + address.getHostName() + ":" + address.getPort());
        } catch (IOException e) {
            System.err.println("Error while starting client : " + e.getMessage());
            System.exit(1);
        }
    }
    public Request send(Request request) throws IOException, ClassNotFoundException {
//        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(request);
            outputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
            // Read the response back from the server
            buffer.clear();
            client.read(buffer);
            buffer.flip();
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
            Request read = (Request) inputStream.readObject();
            return read;
//        } catch (IOException e) {
//            System.err.println("Error while sending request :" + e.getMessage());
//        } catch (ClassNotFoundException e) {
//            System.err.println("Error while reading response from server : " + e.getMessage());
//        }
//        return null;
    }

    public Request read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            while (client.read(buffer) > 0) {
                buffer.flip();
                ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
                Request response = (Request) objectOutputStream.readObject();
                buffer.clear();
//            CommandExecutor.execute(response);

                return response;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reading response from server : " + e.getMessage());
        }
        return null;
    }
}
