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
	@FXML private TextField TextField_CardName;
    @FXML private TextField TextField_CardDate;
    @FXML private TextField TextField_CardDescription;
    @FXML private TextField TextField_CardImage;
    @FXML private StackPane placeCard;
    
    private ControllerCarte controller;
	
    private static Card card;
	public static void setCard(Card card) { ControllerPageCreationCarte.card = card; }
	private static Deck deck;
	public static void setDeck(Deck deck) { ControllerPageCreationCarte.deck = deck; }

	@FXML
	public void initialize() throws IOException {
		TextField_CardName.setText(card.getTitle());
		TextField_CardDate.setText(card.getDate());
		TextField_CardDescription.setText(card.getDescription());
		TextField_CardImage.setText(card.getImageUrl());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
		Pane cardPane = loader.load();
		cardPane.setScaleX(2.0);
		cardPane.setScaleY(2.0);
		controller = loader.getController();
		controller.setCard(card);
		controller.setDescriptionVisible(false);
		placeCard.getChildren().add(cardPane);
		
		TextField_CardName.textProperty().addListener((_, _, newValue) -> {
		    card.setTitle(newValue);
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		TextField_CardDate.textProperty().addListener((_, _, newValue) -> {
		    card.setDate(newValue);
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		TextField_CardDescription.textProperty().addListener((_, _, newValue) -> {
		    card.setDescription(newValue);
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
		TextField_CardImage.textProperty().addListener((_, _, newValue) -> {
		    card.setimageUrl(newValue);
		    try { controller.setCard(card);
			} catch (IOException e) {}
		});
	}
	
    @FXML
    void confirm(ActionEvent event) throws IOException {
		card.loadCardPane(deck);
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