package app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deck {
	
	public String title;
	public String imageUrl;
	public final List<Card> cards;
	
	@JsonCreator
	public Deck(
		@JsonProperty("title") String title,
		@JsonProperty("imageUrl") String imageUrl,
		@JsonProperty("cards") List<Card> cards
	) {
		this.title = title;
		this.imageUrl = imageUrl;
	    this.cards = cards;
	}

	/**
	 * Duplication d'un deck
	 */
	public Deck(Deck other) {
		this.title = other.title;
		this.imageUrl = other.imageUrl;
		
		this.cards = new ArrayList<>();
		for(Card card : other.cards) {
			this.cards.add(new Card(card));
		}
	}

}