package app.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private static Card selectedCard;
    private List<Card> cards;
	public static List<Card> pile;
    
    public Hand() {
        cards = new ArrayList<>();
        selectedCard = null;
    }
	
	// public Card drawCard() {
	// 	if (cards.isEmpty()) {
	// 		return null;
	// 	}
	// 	return cards.remove(0);
	// }

	public Card drawCard() {
		if(pile.isEmpty()) { return null; }
		Card card = pile.remove(0);
		cards.add(card);
		return card;
	}

	public boolean hasMoreCards() {
		return !cards.isEmpty();
	}

	public int getNbCards() {
		return cards.size();
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	public List<Card> getCards() {
		return cards;
	}

	public static void setSelectedCard(Card selectedCard) {
		Hand.selectedCard = selectedCard;
	}

	public static boolean hasOneCardSelected() {
		return selectedCard != null;
	}

	public static Card getSelectedCard() {
		return selectedCard;
	}
	
}