package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Player extends Thread {

    private String name;
    private List<Token> listOfTokens;
    private int turn;
    private Board board;
    private int longestLAP;
    private String color;

    public Player(String name, Board board, int turn, String color) {
        listOfTokens = new ArrayList<Token>();
        this.name = name;
        this.board = board;
        this.turn = turn;
        this.color = color;
    }

    public void assignTokenToPlayer(Token token){
        listOfTokens.add(token);
        token.setAssignedPlayer(this);
    }

    public List<Token> getListOfTokens() {
        return listOfTokens;
    }

    public int getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }

    public int getTotalNumberOfSpecialTokens(){
        int specialTokens = 0;
        for(Token token : listOfTokens){
            if(token.getNumber() == 0){
                specialTokens++;
            }
        }
        return specialTokens;
    }



    public int lenghtOfLongestAP(Set<Token> tokenList, int n)
    {

        if (n <= 2) {return n;}

        int set[] = new int[n];
        int index = 0;
        for(Token token : tokenList){
            set[index] = token.getNumber();
            index++;
        }

        int L[][] = new int[n][n];

        int llap = 2;


        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        for (int j = n - 2; j >= 1; j--)
        {
            int i = j -1 , k = j + 1;
            while (i >= 0 && k <= n - 1)
            {
                if (set[i] + set[k] < 2 * set[j])
                    k++;
                else if (set[i] + set[k] > 2 * set[j])
                {
                    L[i][j] = 2; i--;

                }

                else
                {
                    L[i][j] = L[j][k] + 1;
                    llap = Math.max(llap, L[i][j]);
                    i--; k++;
                }
            }

            while (i >= 0)
            {
                L[i][j] = 2;
                i--;
            }
        }

        if(llap == 5){

        }
        return llap;
    }

    public int getLongestLAP() {
        return longestLAP;
    }

    public void setLongestLAP(int longestLAP) {
        this.longestLAP = longestLAP;
    }

    public String getColor() {
        return color;
    }

    public String getPlayerName(){
        return name;
    }

    @Override
    public abstract void run();
}
