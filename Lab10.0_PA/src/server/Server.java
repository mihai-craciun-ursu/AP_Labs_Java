package server;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            GameServer gameServer = new GameServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
