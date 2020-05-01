package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientThread extends Thread{
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private GamesState gamesState = GamesState.getInstance();
    private Game game;
    private boolean running = true;


    public ClientThread(Socket socket) {
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
        try{
            sleep(1000);
            gamesState.initGameMap(socket);
            gamesState.addSocketForBroadCast(socket);
            while(running) {
                String request = in.readLine();

                //add stop
                if(request != null) {
                    String answer = "[LOG] Server received the request " + request + "!";
                    out.println(answer);
                    out.flush();

                    handleRequest(request);
                }
            }

            gamesState.getSocketList().remove(socket);
        }catch (IOException e){
            System.err.println("Communication error... " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private void handleRequest(String request){
        System.out.println("Request: " + request);
        if(request.startsWith("create")){
            Random random = new Random();

            int gameId = random.nextInt(999) + 1;
            this.game = new Game(gameId);
            gamesState.addGame(game);

            gamesState.broadCast("created " + gameId);

        }else if(request.startsWith("join")){
            String data = request.substring(5);
            int gameId = Integer.parseInt(data);
            if(!gamesState.getGameById(gameId).isStart()){
                this.game = gamesState.getGameById(gameId);
                gamesState.addSocket(gameId, socket);

                int numberOfPlayers = gamesState.getGameListMap().get(gamesState.getGameById(gameId)).size();

                if(numberOfPlayers == 1){
                    out.println("join " + gameId + " 1");
                    out.flush();
                }else {
                    out.println("join " + gameId + " 2");
                    out.flush();
                    gamesState.broadCastToGame(gameId, "start " + data);
                    Game game = gamesState.getGameById(gameId);
                    game.setStart(true);
                    gamesState.getGameListMap().put(game, gamesState.getGameListMap().get(game));
                }
            }
        }else if(request.startsWith("test")){
            out.println("test afirmativ");
            out.flush();
        }else if(request.startsWith("put")){
            String data = request.substring(4);
            String[] dataSeparated = data.split("\\W");

            Game game = gamesState.getGameById(Integer.parseInt(dataSeparated[2]));
            if(game.putPiece(Integer.parseInt(dataSeparated[0]), Integer.parseInt(dataSeparated[1]), Integer.parseInt(dataSeparated[3]))){
                //gamesState.getGameListMap().put(game, gamesState.getGameListMap().get(game));
                if(game.checkIfWon(Integer.parseInt(dataSeparated[0]), Integer.parseInt(dataSeparated[1]), Integer.parseInt(dataSeparated[3]))){
                    game.setTurn(0);
                    gamesState.broadCastToGame(Integer.parseInt(dataSeparated[2]),"win-put " + Integer.parseInt(dataSeparated[0]) + "|" + Integer.parseInt(dataSeparated[1]) + "|" + Integer.parseInt(dataSeparated[3]));
                }else{
                    game.endTurn();
                    gamesState.broadCastToGame(Integer.parseInt(dataSeparated[2]), "put " + Integer.parseInt(dataSeparated[0]) + "|" + Integer.parseInt(dataSeparated[1]) + "|" + Integer.parseInt(dataSeparated[3]));
                }

            }
        }else if(request.startsWith("closing")){
            out.println("stop");
            out.flush();
            running = false;
        }
    }


}
