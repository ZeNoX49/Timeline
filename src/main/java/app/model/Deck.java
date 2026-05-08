package app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.page.ControllerPageCreationDeck;
import app.pojo.DeckPOJO;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Deck {
	
	public String title;
	public String imageUrl;
	private Pane deckPane;
	public final List<Card> cards;
	
	public Deck(DeckPOJO DeckP) {
		this.title = DeckP.title;
		this.imageUrl = DeckP.imageUrl;
	    this.cards = new ArrayList<>();
	}

	public Deck(String title, String imageUrl) {
		this.title = title;
		this.imageUrl = imageUrl;
	    this.cards = new ArrayList<>();
	}
	
	/**
	 * retourne le pane du deck.
	 * le créer s'il n'existe pas
	 */
	public Pane getDeckPane() throws IOException { 
		if(this.deckPane == null) this.createDeckPane();
		return this.deckPane;
	}
	
	/**
     * Créer le pane du deck
     */
    private void createDeckPane() throws IOException {
        System.out.println(" - " + this.title);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        this.deckPane = loader.load();
        ControllerCarte controller = loader.getController();
        controller.setDeck(this);

        deckPane.setOnMouseClicked(_ -> {
            ControllerPageCreationDeck.setDeck(this);
            Main.switchPage("pageCreationDeck.fxml");
        });
    }
}