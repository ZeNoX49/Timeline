package app;

import java.util.ArrayList;
import java.util.List;

import app.model.Deck;

public class Collection {
	
	private static List<Deck> decks = new ArrayList<>();;
	
	public static void addDeck(Deck deck) {
		decks.add(deck);
	}
	
	public static void removeDeck(Deck deck) {
		decks.remove(deck);
	}
	
	public static List<Deck> getDecks() {
		return decks;
	}

}