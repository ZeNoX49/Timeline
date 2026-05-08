package app.controller.modal;

import java.io.IOException;

import app.Main;
import app.model.Card;
import app.model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerModalSupprimer {
	private static Deck deck = null;
	private static Card card = null;

	public static void setCard(Card card) { ControllerModalSupprimer.card = card; }
	public static void setDeck(Deck deck) { ControllerModalSupprimer.deck = deck; }
	
    @FXML
    void onConfirm(ActionEvent event) throws IOException {
    	if (card != null) {   			// on supprime la carte du deck
    		deck.cards.remove(card);
    	} else {   						// on supprime le deck
			Main.DECKS.remove(deck);
    	}
		this.onCancel(event);   // fermer la page
    }

    @FXML
    void onCancel(ActionEvent event) {
		card = null;
		deck = null;
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}