package sample;
import java.util.Set;
import java.util.TreeSet;

public class RandomPlayer extends Player {

    public RandomPlayer(String name, Board board, int turn, String color) {
        super(name, board, turn, color);
    }


    @Override
    public void run() {
        while (this.getBoard().getListOfAvailableTokens().size() > 0 && !this.getBoard().isGameFinished()) {
            Token token = this.getBoard().getRandomAvailableToken(this.getTurn());
            if(token != null) {
                assignTokenToPlayer(token);
                Main.setColorToButton(this.getBoard().getListOfTokens().indexOf(token), this.getColor());
            }

            Set<Token> tokenSet = new TreeSet<Token>();
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
