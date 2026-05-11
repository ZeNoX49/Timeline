package app.controller.page;

import java.io.IOException;

import app.controller.card.ControllerCarte;
import app.controller.modal.ControllerModalSupprimer;
import app.model.Card;
import app.model.Deck;
import app.util.PageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ControllerPageCreationCarte {
	private static final PageManager P_M = SingletonRegistry.get(PageManager.class, PageManager::new);

	@FXML private TextField tfCardName;
    @FXML private TextField tfCardDate;
    @FXML private TextField tfCardDescription;
    @FXML private TextField tfCardImage;
    @FXML private StackPane placeCard;
    
	private final Deck deck;
    private final Card card;

    private ControllerCarte previewController;
	
    public ControllerPageCreationCarte(Deck deck, Card card) {
        this.deck = deck;
        this.card = card;
    }

	@FXML
	public void initialize() {
		this.tfCardName.setText(this.card.title);
		this.tfCardDate.setText(Integer.toString(this.card.date));
		this.tfCardDescription.setText(this.card.description);
		this.tfCardImage.setText(this.card.imageUrl);

		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
            this.previewController = new ControllerCarte(this.deck, this.card);
            loader.setController(this.previewController);

            Pane cardPane = loader.load();
            cardPane.setScaleX(2.0);
            cardPane.setScaleY(2.0);
            placeCard.getChildren().setAll(cardPane);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger l'aperçu de carte", e);
        }
		
		this.tfCardName.textProperty().addListener((_, _, newVal) -> {
            this.card.title = newVal;
            this.previewController.refresh();
        });

        this.tfCardDate.textProperty().addListener((_, _, newVal) -> {
            this.card.date = this.parseIntSafe(newVal);
            this.previewController.refresh();
        });

        this.tfCardDescription.textProperty().addListener((_, _, newVal) -> {
            this.card.description = newVal;
            this.previewController.refresh();
        });

        this.tfCardImage.textProperty().addListener((_, _, newVal) -> {
            this.card.imageUrl = newVal;
            this.previewController.refresh();
        });
	}

	private int parseIntSafe(String value) {
        try {
            if (value == null || value.isBlank()) {
                return 0;
            }
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
	
    @FXML
    void confirm(ActionEvent event) {
		P_M.switchPage("pageCreationDeck.fxml", new ControllerPageCreationDeck(this.deck));
    }

    @FXML
    void delete(ActionEvent event) {
        ControllerModalSupprimer cms = new ControllerModalSupprimer();
        P_M.loadModalPage("supprimer.fxml", cms, true);
        if(cms.getResult()) {
            this.deck.cards.remove(this.card);
            P_M.switchPage("pageCreationDeck.fxml", new ControllerPageCreationDeck(this.deck));
        }
    }

}