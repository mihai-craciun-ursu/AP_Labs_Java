package sample;

import java.util.List;

public class Game extends Thread {

    private List<Player> playerList;
    private Board board;

    public Game(List<Player> playerList, Board board){
        this.playerList = playerList;
        this.board = board;
    }

    @Override
    public void run(){
        for(Player player : playerList){
            player.start();
        }
    }
}
