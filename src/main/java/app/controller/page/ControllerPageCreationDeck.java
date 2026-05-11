package app.controller.page;

import java.io.IOException;

import app.Main;
import app.controller.card.ControllerCarteAjouter;
import app.controller.modal.ControllerModalSupprimer;
import app.model.Card;
import app.model.Deck;
import app.util.CardManager;
import app.util.PageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ControllerPageCreationDeck {
	private static final CardManager C_M = SingletonRegistry.get(CardManager.class, CardManager::new);
	private static final PageManager P_M = SingletonRegistry.get(PageManager.class, PageManager::new);

    @FXML private TextField tfDeckName;
    @FXML private TextField tfImageLink;
	@FXML private Label nbCard;
	@FXML private FlowPane flowPane;
    
    private final Deck deck;
	
	public ControllerPageCreationDeck(Deck deck) {
        this.deck = deck;
    }

    @FXML
    public void initialize() throws IOException {
    	this.tfDeckName.setText(this.deck.title);
    	this.tfImageLink.setText(this.deck.imageUrl);
		this.nbCard.setText(Integer.toString(this.deck.cards.size()));

		tfDeckName.textProperty().addListener((_, _, newVal) -> deck.title = newVal);
        tfImageLink.textProperty().addListener((_, _, newVal) -> deck.imageUrl = newVal);

		this.flowPane.getChildren().clear();
    	
    	for (Card card : this.deck.cards) {
            Pane deckPane = C_M.getCardPane(this.deck, card);
            deckPane.setOnMouseClicked(_ -> P_M.switchPage("pageCreationCarte.fxml", new ControllerPageCreationCarte(deck, card)));
            this.flowPane.getChildren().add(deckPane);
        }

        Pane addDeck = new ControllerCarteAjouter("Créer une carte").getRoot();
        addDeck.setOnMouseClicked(_ -> {
            Card newCard = new Card("", 0, "", "");
            this.deck.cards.add(newCard);
            P_M.switchPage("pageCreationCarte.fxml", new ControllerPageCreationCarte(this.deck, newCard));
        });
        this.flowPane.getChildren().add(addDeck);
    }
    
    @FXML
    void confirm(ActionEvent event) throws IOException {
    	P_M.switchPage("pageSelectionDeck.fxml", null);
    }

	@FXML
    void delete(ActionEvent event) throws IOException {
        ControllerModalSupprimer cms = new ControllerModalSupprimer();
        P_M.loadModalPage("supprimer.fxml", cms, true);
        if(cms.getResult()) {
            Main.DECKS.remove(this.deck);
            P_M.switchPage("pageSelectionDeck.fxml", null);
        }
    }

}