package app.model;

import java.io.IOException;
import java.util.List;
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

	/* ============================================================================================= */

	private Pair<Pane, ControllerCarte> controllerData;
	private EventType<? extends Event> lastEvent = null;
	private EventHandler<Event> lastHandler = null;

	/**
	 * retourne le pane du deck.
	 * le créer s'il n'existe pas
	 */
	public Pair<Pane, ControllerCarte> getDeckPane(EventType<? extends Event> event, Consumer<?> consumer) { 
		if (this.controllerData == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
				ControllerCarte controller = new ControllerCarte(this);
				loader.setController(controller);
				this.controllerData = new Pair<>(loader.load(), controller);
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