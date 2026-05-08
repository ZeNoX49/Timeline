package app.controller.page;

import java.util.ArrayList;
import java.util.function.Consumer;

import app.Main;
import app.controller.card.ControllerCarteAjouter;
import app.model.Deck;
import app.util.CardManager;
import app.util.PageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ControllerPageSelectionDeck {
	private final static PageManager P_M = PageManager.getInstance();
	private final static CardManager C_M = CardManager.getInstance();

	@FXML private Label lNbDeck;
    @FXML private FlowPane flowPane;
    
    @FXML
    public void initialize() {
    	
		Consumer<Deck> consumer = (deck) -> {
			// TODO: ControllerPageCreationDeck.setDeck(this);
			P_M.switchPage("pageCreationDeck.fxml");
		};

		for(Deck deck: Main.DECKS) {
			this.flowPane.getChildren().add(deck.getDeckPane(MouseEvent.MOUSE_CLICKED, consumer).getKey());
		}

		ControllerCarteAjouter cca = new ControllerCarteAjouter("Créer un deck");
		Pane paneAddCard = cca.getRoot();
		paneAddCard.setOnMouseClicked(_ -> {
			Deck newDeck = new Deck("", "", new ArrayList<>());
			Main.DECKS.add(newDeck);
			
			consumer.accept(null);
		});

		this.flowPane.getChildren().add(paneAddCard);
    	
		// on affiche le nb de decks
		this.lNbDeck.setText(Integer.toString(Main.DECKS.size()));
    }

	@FXML
    void retour(ActionEvent event) {
    	P_M.switchPage("pageAccueil.fxml");
    }
	
}