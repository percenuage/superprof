package fr.superprof.network;

import fr.superprof.MonkeyIsland;
import fr.superprof.command.Command;
import fr.superprof.model.Island;
import fr.superprof.model.Pirate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final Integer PORT = Integer.valueOf(MonkeyIsland.CONFIG.getString("PORT"));

    public static void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server listening on " + PORT);
        try {
            while (true) {
                new Client(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }
}
