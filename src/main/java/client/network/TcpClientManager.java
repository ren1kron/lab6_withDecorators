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
        }
    }
    public void send(Request request) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(request);
            outputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
        } catch (IOException e) {
            System.err.println("Error while sending request :" + e.getMessage());
        }
    }

    public Request read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            while (client.read(buffer) > 0) {
                buffer.flip();
                ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
                Request response = (Request) objectOutputStream.readObject();
                buffer.clear();
//            commandExecutor.execute(response);

                return response;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reading response from server : " + e.getMessage());
        }
        return null;
    }
}
