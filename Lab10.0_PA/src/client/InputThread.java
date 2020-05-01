package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InputThread extends Thread {

    private BufferedReader in = null;
    private PrintWriter out = null;
    Socket socket = null;

    private boolean running;

    public InputThread(Socket socket){
        running = true;
        this.socket = socket;
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (running){
            try {
                String request = in.readLine();
                if(request != null) {
                    System.out.println("Received from SERVER: " + request);
                    requestHandling(request);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestHandling(String request){
        if(request.startsWith("created")){
            int gameId = Integer.parseInt(request.substring(8));
            GameClient.addRoom(gameId);
        }else if(request.startsWith("join")){
            int gameId = Integer.parseInt(request.substring(5, request.length()-2));
            int gameTurn = Integer.parseInt((request.substring(request.length()-1)));
            GameClient.changeToGameMode(gameId, gameTurn);
        }else if(request.startsWith("put")){
            String data = request.substring(4);
            String[] dataSeparated = data.split("\\W+");
            GameClient.colorPiece(Integer.parseInt(dataSeparated[0]), Integer.parseInt(dataSeparated[1]), Integer.parseInt(dataSeparated[2]));
        }else if(request.startsWith("win-put")){
            String data = request.substring(8);
            String[] dataSeparated = data.split("\\W+");
            for(int i = 0; i < 15; i++){
                for(int j = 0; j<15; j++){
                    GameClient.colorPiece(i, j, Integer.parseInt(dataSeparated[2]));
                }
            }
        }else if(request.startsWith("stop")){
            System.out.println("STOPPP");
            running = false;
        }
    }

    public void sendToServer(String message){
        out.println(message);
        out.flush();
    }
}
