package server;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> playerList;
    private Board board;
    private boolean start;
    private int turn;
    private int id;

    public Game(int id) {
        this.playerList = new ArrayList<>();
        this.board = new Board();
        this.start = false;
        this.turn = 1;
        this.id = id;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isStart() {
        return start;
    }

    public void addPlayer(Player player){
        this.playerList.add(player);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void endTurn(){
        this.turn = this.turn == 1 ? 2 : 1; //200IQ move
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public boolean putPiece(int i, int j, int playerTurn){
        if(this.start) {
            if(playerTurn == this.turn) {
                if (board.isSpaceFree(i, j)) {
                    board.setPiece(i, j, this.turn);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkIfWon(int i, int j, int playerTurn){
        return (this.board.fiveInARow(i, j, playerTurn));
    }

}
