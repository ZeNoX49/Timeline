package app.model;

import java.io.IOException;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import app.controller.card.ControllerCarte;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

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

	/* ============================================================================================= */

	private Pair<Pane, ControllerCarte> controllerData;
	private EventType<? extends Event> lastEvent;
	private EventHandler<Event> lastHandler;

	/**
	 * retourne le pane de la carte.
	 * le créer s'il n'existe pas
	 * @param deck deck contenant la carte
	 * 
	 * - ControllerPageCreationCarte.setDeck(deck);
	 * - ControllerPageCreationCarte.setCard(this);
	 * - Main.switchPage("pageCreationCarte.fxml");
	 */
	public Pair<Pane, ControllerCarte> getCardPane(Deck deck, EventType<? extends Event> event, Consumer<?> consumer) { 
		if (this.controllerData == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
				this.controllerData = new Pair<>(loader.load(), new ControllerCarte(this));
				loader.setController(this.controllerData.getValue());
			} catch(IOException e) { e.printStackTrace(); }
		}

		if (this.lastEvent != null && this.lastHandler != null) {
			this.controllerData.getKey().removeEventHandler(this.lastEvent, this.lastHandler);
		}

    	EventHandler<Event> handler = e -> consumer.accept(null);

		this.lastEvent = event;
    	this.lastHandler = handler;

		this.controllerData.getKey().addEventHandler(event, handler);
		
		return this.controllerData;
	}

}