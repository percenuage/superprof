package fr.superprof.network;

import fr.superprof.command.Command;
import fr.superprof.model.Island;
import fr.superprof.model.Pirate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Pirate pirate;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public void emit(String message) {
        this.out.println(message);
        System.out.println(this.toString() + ": [EMIT] " + message);
    }

    private String listen() {
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {}
        System.out.println(this.toString() + ": [LISTEN] " + message);
        return message;
    }

    private void onConnection() {
        this.emit(Command.islandShape());
        this.emit(Command.identifyPirates());
        this.emit(Command.identifyHunterMonkeys());
        this.emit(Command.identifyCrazyMonkeys());
        this.emit(Command.identifyRhums());
    }

    private void onListening() {
        while (true) {
            String message = this.listen();
            if (message == null) {
                break;
            }
            switchCommand(message);
        }
    }

    private void onDisconnection() {
        System.err.println(this.toString() + " is disconnected!");
        Island.getInstance().removePirate(this.socket.getPort());
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchCommand(String message) {
        Command command = Command.valueOf(message);
        switch (command.getHeader()) {
            case CLIENT_SUBSCRIBE_PIRATE:
                Command.suscribePirate(this);
                break;
            case CLIENT_MOVE_PIRATE:
                Command.movePirateClient(this, command.getBody());
                break;
            default:
                System.err.println("Not implemented yet");
                break;
        }
    }

    @Override
    public void run() {
        System.out.println("Client " + this.socket.getPort());
        this.onConnection();
        this.onListening();
        this.onDisconnection();
    }

    public Pirate getPirate() {
        return pirate;
    }

    public void setPirate(Pirate pirate) {
        this.pirate = pirate;
    }

    public Socket getSocket() {
        return socket;
    }
}
