package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    public static final int PORT = 8000;
    private GamesState gamesState = GamesState.getInstance();

    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            while(true){
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                new ClientThread(socket).start();
            }
        }catch(IOException e){
            System.err.println("Server Error " + e);
        }finally {
            serverSocket.close();
        }
    }



}
