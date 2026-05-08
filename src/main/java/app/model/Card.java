package app.model;

import java.io.IOException;

import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.page.ControllerPageCreationCarte;
import app.pojo.CardPOJO;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Card {

	public String title;
	public int date;
	public String description;
	public String imageUrl;
	private Pane cardPane;
	
	public Card(CardPOJO cardP) {
		this.title = cardP.title;
		this.date = cardP.date;
		this.description = cardP.description;
		this.imageUrl = cardP.imageUrl;
	}

	public Card(String title, int date, String imageUrl, String description) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	/**
	 * retourne le pane de la carte.
	 * le créer s'il n'existe pas
	 * @param deck deck contenant la carte
	 */
	public Pane getCardPane(Deck deck) throws IOException {
		if(this.cardPane == null) this.createCardPane(deck);
		return this.cardPane;
	}
	
	/**
     * Créer le pane de la carte
     */
    private void createCardPane(Deck deck) throws IOException {
        System.out.println("\t - " + this.title);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        this.cardPane = loader.load();
        ControllerCarte controller = loader.getController();
        controller.setCard(this);
        controller.setDateVisible(true);

        cardPane.setOnMouseClicked(_ -> {
            ControllerPageCreationCarte.setDeck(deck);
            ControllerPageCreationCarte.setCard(this);
            Main.switchPage("pageCreationCarte.fxml");
        });
    }

}