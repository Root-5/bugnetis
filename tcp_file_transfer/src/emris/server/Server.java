package emris.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Server has started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            /*Wait for the client to make a connection and when it does, create a new socket to handle the request*/
            System.out.println("connect from: " + clientSocket.getInetAddress().toString());
            new Thread(new Connection(clientSocket)).start();
            ///create new connection  in new thread ( client to server)

        }


    }
}
