package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

	public String title;
	public int date;
	public String description;
	public String imageUrl;

	@JsonCreator
	public Card(
		@JsonProperty("title") String title,
		@JsonProperty("date") int date,
		@JsonProperty("imageUrl") String imageUrl,
		@JsonProperty("description") String description
	) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	/**
	 * Duplication d'une carte
	 */
	public Card(Card other) {
		this.title = other.title;
		this.date = other.date;
		this.description = other.description;
		this.imageUrl = other.imageUrl;
	}

}