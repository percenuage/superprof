package fr.superprof;

import fr.superprof.model.Island;
import fr.superprof.network.Server;

import java.io.IOException;
import java.util.ResourceBundle;

public class MonkeyIsland {

    public static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    public static void main(String[] args) throws IOException {
        Island island = Island.getInstance();
        island.initializeIsland();
        island.startMonkeys();
        Server.listen();
    }
}
