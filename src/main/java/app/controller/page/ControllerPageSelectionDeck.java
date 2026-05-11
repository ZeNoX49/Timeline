package app.controller.page;

import java.util.ArrayList;

import app.Main;
import app.controller.card.ControllerCarteAjouter;
import app.model.Deck;
import app.util.CardManager;
import app.util.PageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ControllerPageSelectionDeck {
	private static final CardManager C_M = SingletonRegistry.get(CardManager.class, CardManager::new);
	private static final PageManager P_M = SingletonRegistry.get(PageManager.class, PageManager::new);

	@FXML private Label lNbDeck;
    @FXML private FlowPane flowPane;
    
    @FXML
    public void initialize() {
		this.flowPane.getChildren().clear();
    	
		for (Deck deck : Main.DECKS) {
            Pane deckPane = C_M.getDeckPane(deck);
            deckPane.setOnMouseClicked(_ -> P_M.switchPage("pageCreationDeck.fxml", new ControllerPageCreationDeck(deck)));
            this.flowPane.getChildren().add(deckPane);
        }

        Pane addDeck = new ControllerCarteAjouter("Créer un deck").getRoot();
        addDeck.setOnMouseClicked(_ -> {
            Deck newDeck = new Deck("", "", new ArrayList<>());
            Main.DECKS.add(newDeck);
            P_M.switchPage("pageCreationDeck.fxml", new ControllerPageCreationDeck(newDeck));
        });
        this.flowPane.getChildren().add(addDeck);

        this.lNbDeck.setText(Integer.toString(Main.DECKS.size()));
    }

	@FXML
    void retour(ActionEvent event) {
    	P_M.switchPage("pageAccueil.fxml", null);
    }
	
}