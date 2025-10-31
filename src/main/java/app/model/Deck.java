package app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.pojo.DeckPOJO;
import app.util.CardManager;
import javafx.scene.layout.Pane;

public class Deck {
	private CardManager cardManager = CardManager.getInstance();

	private String title;
	private String imageUrl;
	private Pane deckPane;
	private List<Card> cards;
	
	/* ----- Constructeur ----- */
	// pojo
	public Deck(DeckPOJO DeckP) {
		this.title = DeckP.title;
		this.imageUrl = DeckP.imageUrl;
	    cards = new ArrayList<>();
	}

	// normal
	public Deck(String title, String imageUrl) {
		this.title = title;
		this.imageUrl = imageUrl;
	    cards = new ArrayList<>();
	}
	
	/* ----- setters ----- */
	public void setTitle(String title) 			{ this.title = title; }
	public void setImageUrl(String imageUrl) 	{ this.imageUrl = imageUrl; }
	public void setDeckPane(Pane deckPane) 		{ this.deckPane = deckPane; }

	/* ----- getters ----- */
	public String getTitle() 		{ return title; }
	public String getImageUrl() 	{ return imageUrl; }
	
	public Pane getDeckPane() throws IOException { 
		if(deckPane == null) {
			loadDeckPane();
		}
		return deckPane;
	}
	public void loadDeckPane() throws IOException { 
		deckPane = cardManager.createDeckPane(this);
	}

	/* ----- list ----- */
	public void addCard(Card card) {
		cards.add(card);
	}
	public void removeCard(Card card) {
		cards.remove(card);
	}
	public List<Card> getCards() {
		return cards;
	}
	
}