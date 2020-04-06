package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private List<Token> listOfTokens;
    private List<Token> listOfAvailableTokens;
    private int bound;
    private int turn = 1;
    private int numberOfPlayers;
    private int winningPlayer;

    private boolean gameFinished = false;


    private Random random = new Random();

    public Board(int bound, int numberOfTokens, int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        this.bound = bound;
        listOfTokens = new ArrayList<Token>();
        listOfAvailableTokens = new ArrayList<Token>();

        for(int index = 0; index < numberOfTokens; index++){
            int randomIndex = random.nextInt(bound+1);
            Main.setTextToButton(index, String.valueOf(randomIndex));
            Token token = new Token(randomIndex);
            listOfTokens.add(token);
        }

        listOfAvailableTokens.addAll(listOfTokens);
    }

    public synchronized Token getToken(int index, boolean wait){
        while(turn!=this.turn){
            try {
                wait();

                if(listOfAvailableTokens.size() == 0 || gameFinished){
                    return null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            if(wait) {
                wait(1000);
            }else{
                Main.resetSelectedToken();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listOfAvailableTokens.remove(listOfTokens.get(index));
        this.turn = (this.turn % numberOfPlayers) + 1;
        notifyAll();
        return listOfTokens.get(index);
    }

    public synchronized Token getRandomAvailableToken(int turn){

        while(turn!=this.turn){
            try {
                wait();

                if(listOfAvailableTokens.size() == 0 || gameFinished){
                    return null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Token token = listOfAvailableTokens.get(random.nextInt(listOfAvailableTokens.size()));
        listOfAvailableTokens.remove(token);

        System.out.println("Player " + turn + ": " + token.getNumber());

        this.turn = (this.turn % numberOfPlayers) + 1;
        notifyAll();
        return token;
    }

    public int getTurn() {
        return turn;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public List<Token> getListOfAvailableTokens() {
        return listOfAvailableTokens;
    }

    public List<Token> getListOfTokens() {
        return listOfTokens;
    }

    public int getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(int winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
