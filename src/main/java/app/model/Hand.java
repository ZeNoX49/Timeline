package app.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;
    
    public Hand() {
        this.cards = new ArrayList<>();
    }

	public Card drawCard(List<Card> pile) {
		if(pile.isEmpty()) {
			return null;
		}

		Card card = pile.remove(0);
		cards.add(card);
		return card;
	}

	public boolean stillHasCard() {
		return !cards.isEmpty();
	}

	public int getNbCards() {
		return cards.size();
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
}