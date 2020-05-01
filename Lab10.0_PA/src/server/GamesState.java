package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamesState {
    private static GamesState ourInstance = new GamesState();


    private Map<Game, List<Socket>> gameListMap = new HashMap<>();
    private List<Socket> socketList = new ArrayList<>();

    public static GamesState getInstance() {
        return ourInstance;
    }

    private GamesState() {

    }

    public List<Game> getGameList() {
        List<Game> gameList = new ArrayList<>();

        for(Game game : gameListMap.keySet()){
            gameList.add(game);
        }

        return gameList;
    }

    public Game getGameById(int id){
        for(Game game : gameListMap.keySet()){
            if(game.getId() == id){
                return game;
            }
        }
        return null;
    }

    public void addGame(Game game){
        List<Socket> socketList = new ArrayList<>();
        this.gameListMap.put(game, socketList);
    }

    public void addSocket(int gameId, Socket socket){
        Game game = getGameById(gameId);
        List<Socket> socketList = gameListMap.get(game);
        socketList.add(socket);
        gameListMap.put(game, socketList);
    }

    public void broadCast(String message){
        for(Socket socket : socketList){
            try {
                if(!socket.isClosed()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    out.println(message);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadCastToGame(int gameId, String message){
        Game game = getGameById(gameId);
        for(Socket socket: gameListMap.get(game)){
            try {
                if(!socket.isClosed()) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    out.println(message);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initGameMap(Socket socket){
        try {
            if(!socket.isClosed()) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                for (Game game : gameListMap.keySet()) {
                    out.println("created " + game.getId());
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Map<Game, List<Socket>> getGameListMap() {
        return gameListMap;
    }

    public void addSocketForBroadCast(Socket socket){
        socketList.add(socket);
    }

    public List<Socket> getSocketList() {
        return socketList;
    }
}
