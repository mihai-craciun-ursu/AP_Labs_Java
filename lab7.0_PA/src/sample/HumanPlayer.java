package sample;

import java.util.Set;
import java.util.TreeSet;

public class HumanPlayer extends Player {
    public HumanPlayer(String name, Board board, int turn, String color) {
        super(name, board, turn, color);
    }

    @Override
    public void run() {
        while (this.getBoard().getListOfAvailableTokens().size() > 0 && !this.getBoard().isGameFinished()) {
            System.out.print("");
            if (Main.getSelectedToken() != null && this.getTurn() == this.getBoard().getTurn()) {
                //chestia asta nu ar trebui sa fie legala
                Token token = this.getBoard().getToken(
                        this.getBoard().getListOfTokens().indexOf(
                                Main.getSelectedToken()), false);
                assignTokenToPlayer(token);

                Set<Token> tokenSet = new TreeSet<>();

                Main.setColorToButton(this.getBoard().getListOfTokens().indexOf(token), this.getColor());

                tokenSet.addAll(getListOfTokens());
                for(Token tokenIt : tokenSet){
                    if(tokenIt.getNumber() == 0){
                        tokenSet.remove(tokenIt);
                    }
                }
                this.setLongestLAP(lenghtOfLongestAP(tokenSet, tokenSet.size()));
                if(this.getLongestLAP() + this.getTotalNumberOfSpecialTokens() >= 5){
                    System.out.println("Win win win " + this.getTurn());
                    this.getBoard().setGameFinished(true);
                    this.getBoard().setWinningPlayer(this.getTurn());
                }

            }
        }
    }
}
