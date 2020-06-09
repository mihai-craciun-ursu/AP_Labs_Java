package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Pack implements Serializable{

    private static final long serialVersionUID = 7526472295622776148L;

    private List<Card> listOfCards;
    private List<Card> listOfAvaibleCards;

    public List<Card> getListOfCards() {
        return listOfCards;
    }

    public void setListOfCards(List<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }

    public List<Card> getListOfAvaibleCards() {
        return listOfAvaibleCards;
    }

    public void setListOfAvaibleCards(List<Card> listOfAvaibleCards) {
        this.listOfAvaibleCards = listOfAvaibleCards;
    }

    public void addCard(Card card){
        listOfAvaibleCards.add(card);
    }

    public void removeCard(Card card){
        listOfAvaibleCards.remove(card);
    }

    public Card getACard(int indexFromWhere){
        Card card = listOfAvaibleCards.get(indexFromWhere);
        listOfAvaibleCards.remove(card);
        return card;
    }


    public Pack(){

        listOfCards = new ArrayList<>();
        listOfAvaibleCards = new ArrayList<>();

        listOfCards.add(new Card("ace","clubs", "Resized_cards/ace_of_clubs.jpg"));
        listOfCards.add(new Card("ace","diamonds", "Resized_cards/ace_of_diamonds.jpg"));
        listOfCards.add(new Card("ace","hearts", "Resized_cards/ace_of_hearts.jpg"));
        listOfCards.add(new Card("ace","spades","Resized_cards/ace_of_spades.jpg"));

        listOfCards.add(new Card("2","clubs","Resized_cards/2_of_clubs.jpg"));
        listOfCards.add(new Card("2","diamonds","Resized_cards/2_of_diamonds.jpg"));
        listOfCards.add(new Card("2","hearts", "Resized_cards/2_of_hearts.jpg"));
        listOfCards.add(new Card("2","spades", "Resized_cards/2_of_spades.jpg"));

        listOfCards.add(new Card("3","clubs", "Resized_cards/3_of_clubs.jpg"));
        listOfCards.add(new Card("3","diamonds", "Resized_cards/3_of_diamonds.jpg"));
        listOfCards.add(new Card("3","hearts", "Resized_cards/3_of_hearts.jpg"));
        listOfCards.add(new Card("3","spades", "Resized_cards/3_of_spades.jpg"));

        listOfCards.add(new Card("4","clubs", "Resized_cards/4_of_clubs.jpg"));
        listOfCards.add(new Card("4","diamonds", "Resized_cards/4_of_diamonds.jpg"));
        listOfCards.add(new Card("4","hearts", "Resized_cards/4_of_hearts.jpg"));
        listOfCards.add(new Card("4","spades", "Resized_cards/4_of_spades.jpg"));

        listOfCards.add(new Card("5","clubs", "Resized_cards/5_of_clubs.jpg"));
        listOfCards.add(new Card("5","diamonds", "Resized_cards/5_of_diamonds.jpg"));
        listOfCards.add(new Card("5","hearts", "Resized_cards/5_of_hearts.jpg"));
        listOfCards.add(new Card("5","spades", "Resized_cards/5_of_spades.jpg"));

        listOfCards.add(new Card("6","clubs","Resized_cards/6_of_clubs.jpg"));
        listOfCards.add(new Card("6","diamonds","Resized_cards/6_of_diamonds.jpg"));
        listOfCards.add(new Card("6","hearts","Resized_cards/6_of_hearts.jpg"));
        listOfCards.add(new Card("6","spades","Resized_cards/6_of_spades.jpg"));

        listOfCards.add(new Card("7","clubs","Resized_cards/7_of_clubs.jpg"));
        listOfCards.add(new Card("7","diamonds","Resized_cards/7_of_diamonds.jpg"));
        listOfCards.add(new Card("7","hearts","Resized_cards/7_of_hearts.jpg"));
        listOfCards.add(new Card("7","spades","Resized_cards/7_of_spades.jpg"));

        listOfCards.add(new Card("8","clubs","Resized_cards/8_of_clubs.jpg"));
        listOfCards.add(new Card("8","diamonds","Resized_cards/8_of_diamonds.jpg"));
        listOfCards.add(new Card("8","hearts","Resized_cards/8_of_hearts.jpg"));
        listOfCards.add(new Card("8","spades","Resized_cards/8_of_spades.jpg"));

        listOfCards.add(new Card("9","clubs","Resized_cards/9_of_clubs.jpg"));
        listOfCards.add(new Card("9","diamonds","Resized_cards/9_of_diamonds.jpg"));
        listOfCards.add(new Card("9","hearts","Resized_cards/9_of_hearts.jpg"));
        listOfCards.add(new Card("9","spades","Resized_cards/9_of_spades.jpg"));

        listOfCards.add(new Card("10","clubs","Resized_cards/10_of_clubs.jpg"));
        listOfCards.add(new Card("10","diamonds","Resized_cards/10_of_diamonds.jpg"));
        listOfCards.add(new Card("10","hearts","Resized_cards/10_of_hearts.jpg"));
        listOfCards.add(new Card("10","spades","Resized_cards/10_of_spades.jpg"));

        listOfCards.add(new Card("jack","clubs","Resized_cards/jack_of_clubs.jpg"));
        listOfCards.add(new Card("jack","diamonds","Resized_cards/jack_of_diamonds.jpg"));
        listOfCards.add(new Card("jack","hearts","Resized_cards/jack_of_hearts.jpg"));
        listOfCards.add(new Card("jack","spades","Resized_cards/jack_of_spades.jpg"));

        listOfCards.add(new Card("queen","clubs","Resized_cards/queen_of_clubs.jpg"));
        listOfCards.add(new Card("queen","diamonds","Resized_cards/queen_of_diamonds.jpg"));
        listOfCards.add(new Card("queen","hearts","Resized_cards/queen_of_hearts.jpg"));
        listOfCards.add(new Card("queen","spades","Resized_cards/queen_of_spades.jpg"));

        listOfCards.add(new Card("king","clubs","Resized_cards/king_of_clubs.jpg"));
        listOfCards.add(new Card("king","diamonds","Resized_cards/king_of_diamonds.jpg"));
        listOfCards.add(new Card("king","hearts","Resized_cards/king_of_hearts.jpg"));
        listOfCards.add(new Card("king","spades","Resized_cards/king_of_spades.jpg"));

        listOfCards.add(new Card("joker","red","Resized_cards/joker_red.jpg"));
        listOfCards.add(new Card("joker","black","Resized_cards/joker_black.jpg"));

    }

    public void shuffle(){
        Collections.shuffle(listOfAvaibleCards);
    }




}
