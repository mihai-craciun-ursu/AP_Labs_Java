package app.models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> playerList;

    public Game() {
        playerList = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public boolean addPlayerToList(Player player){
        if(playerList.indexOf(player) > 0){
            return false;
        }

        playerList.add(player);
        return true;
    }
}
