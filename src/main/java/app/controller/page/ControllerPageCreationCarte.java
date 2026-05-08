package app.controller.page;

import java.io.IOException;

import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.modal.ControllerModalSupprimer;
import app.model.Card;
import app.model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ControllerPageCreationCarte {

	@FXML private TextField tfCardName;
    @FXML private TextField tfCardDate;
    @FXML private TextField tfCardDescription;
    @FXML private TextField tfCardImage;
    @FXML private StackPane placeCard;
    
    private ControllerCarte controller;
	
    private static Card card;
	public static void setCard(Card card) { ControllerPageCreationCarte.card = card; }
	private static Deck deck;
	public static void setDeck(Deck deck) { ControllerPageCreationCarte.deck = deck; }

	@FXML
	public void initialize() throws IOException {
		tfCardName.setText(card.title);
		tfCardDate.setText(Integer.toString(card.date));
		tfCardDescription.setText(card.description);
		tfCardImage.setText(card.imageUrl);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
		Pane cardPane = loader.load();
		cardPane.setScaleX(2.0);
		cardPane.setScaleY(2.0);
		controller = loader.getController();
		controller.setCard(card);
		controller.setDescriptionVisible(false);
		placeCard.getChildren().add(cardPane);
		
		tfCardName.textProperty().addListener((_, _, newValue) -> {
		    card.title = newValue;
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		tfCardDate.textProperty().addListener((_, _, newValue) -> {
		    card.date = Integer.parseInt(newValue);
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		tfCardDescription.textProperty().addListener((_, _, newValue) -> {
		    card.description = newValue;
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		tfCardImage.textProperty().addListener((_, _, newValue) -> {
		    card.imageUrl = newValue;
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
	}
	
    @FXML
    void confirm(ActionEvent event) throws IOException {
		// card.loadCardPane(deck);
    	Main.switchPage("pageCreationDeck.fxml");
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
		ControllerModalSupprimer.setDeck(deck);
		ControllerModalSupprimer.setCard(card);
		Main.loadModalPage("supprimer.fxml", true);

		Main.switchPage("pageCreationDeck.fxml");
    }

}