package sample;

import java.io.Serializable;


public class Order implements Serializable {

    private static final long serialVersionUID = 7526472295622776150L;

    private String name;
    private int cardsToDraw;
    private int turnsToStay;
    private Card downCard;
    private Pack packOfCards;

    public Order(String name, int cardsToDraw, int turnsToStay, Card downCard, Pack packOfCards) {
        this.name = name;
        this.cardsToDraw = cardsToDraw;
        this.turnsToStay = turnsToStay;
        this.downCard = downCard;
        this.packOfCards = packOfCards;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Pack getPackOfCards() {
        return packOfCards;
    }

    public void setPackOfCards(Pack packOfCards) {
        this.packOfCards = packOfCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardsToDraw() {
        return cardsToDraw;
    }

    public void setCardsToDraw(int cardsToDraw) {
        this.cardsToDraw = cardsToDraw;
    }

    public int getTurnsToStay() {
        return turnsToStay;
    }

    public void setTurnsToStay(int turnsToStay) {
        this.turnsToStay = turnsToStay;
    }

    public Card getDownCard() {
        return downCard;
    }

    public void setDownCard(Card downCard) {
        this.downCard = downCard;
    }
}
