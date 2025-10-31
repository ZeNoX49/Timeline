package app.model;

import java.io.Serializable;

public class Player implements Serializable{
	private String name;
    private int score;
    private Hand hand;
    
    public Player(String name) {
        this.name = name;
        this.score = 0;
        hand = new Hand();
    }
    
    public void incPoints() {
        score++;
    }
    public void decPoints() {
        score--;
    }
    
    public int getScore() { return score; }
    public String getName() { return name; }
    public Hand getHand() { return hand; }
    
    // public void addInHandCard(Card card) {
    // 	hand.addCard(card);
    // }
    
    public boolean hasMoreCardsInHand() {
    	return hand.hasMoreCards();
    }

    public Card drawCard() {
    	return hand.drawCard();
    }

}