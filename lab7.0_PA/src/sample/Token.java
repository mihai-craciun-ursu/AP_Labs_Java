package sample;

public class Token implements Comparable<Token>{
    private int number;
    private Player assignedPlayer;

    public Token(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setAssignedPlayer(Player assignedPlayer) {
        if(this.assignedPlayer == null) {
            this.assignedPlayer = assignedPlayer;
        }
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }


    @Override
    public int compareTo(Token token) {
        return this.getNumber() - token.getNumber();
    }
}
