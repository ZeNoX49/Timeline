package app.controller.modal;

import java.io.IOException;

import app.Collection;
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
    		deck.removeCard(card);
    	} else {   						// on supprime le deck
			Collection.removeDeck(deck);
    	}
		onCancel(event);   // juste pour fermer la page
    }

    @FXML
    void onCancel(ActionEvent event) {
		card = null;
		deck = null;
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}