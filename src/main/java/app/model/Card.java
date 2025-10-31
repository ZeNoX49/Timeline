package app.model;

import java.io.IOException;

import app.pojo.CardPOJO;
import app.util.CardManager;
import javafx.scene.layout.Pane;

public class Card {
	private CardManager cardManager = CardManager.getInstance();

	private String title;
	private String date;
	private String description;
	private String imageUrl;
	private Pane cardPane;
	
	/* ----- Constructeur ----- */
	// pojo
	public Card(CardPOJO cardP) {
		this.title = cardP.title;
		this.date = cardP.date;
		this.description = cardP.description;
		this.imageUrl = cardP.imageUrl;
	}

	// normal
	public Card(String title, String date, String imageUrl, String description) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	/* ----- setters ----- */
	public void setTitle(String title) 				{ this.title = title; }
	public void setDate(String date) 				{ this.date = date; }
	public void setDescription(String description) 	{ this.description = description; }
	public void setimageUrl(String imageUrl) 		{ this.imageUrl = imageUrl; }
	public void setCardPane(Pane cardPane) 			{ this.cardPane = cardPane; }

	/* ----- getters ----- */
	public String getTitle() 		{ return title; }
	public String getDate() 		{ return date; }
	public String getDescription() 	{ return description; }
	public String getImageUrl() 	{ return imageUrl; }

	public Pane getCardPane(Deck deck) throws IOException {
		if(cardPane == null) {
			loadCardPane(deck);
		}
		return cardPane;
	}
	public void loadCardPane(Deck deck) throws IOException {
		cardPane = cardManager.createCardPane(deck, this);
	}

	/* ----- utilitaire ----- */
	public int getDateAsInt() {
		return Integer.parseInt(date); 
	}
	
}