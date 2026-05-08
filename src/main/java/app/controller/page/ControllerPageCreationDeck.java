package app.controller.page;

import java.io.IOException;
import java.util.List;

import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.card.ControllerCarteAjouter;
import app.controller.modal.ControllerModalSupprimer;
import app.model.Card;
import app.model.Deck;
import app.util.CardManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ControllerPageCreationDeck {
	private final static CardManager CARD_MANAGER = CardManager.getInstance();

	@FXML private Label nbCard;
	@FXML private VBox placeCard;
    @FXML private TextField tfDeckName;
    @FXML private TextField tfImageLink;
    
    private static Deck deck;
	public static void setDeck(Deck deck) {
        ControllerPageCreationDeck.deck = deck;
    }

    private Pane paneAddCard = null;

    @FXML
    public void initialize() throws IOException {
    	tfDeckName.setText(deck.title);
    	tfImageLink.setText(deck.imageUrl);
    	
    	List<HBox> hboxs = CARD_MANAGER.getCards(deck);

        HBox hbox;
        if(!hboxs.isEmpty()) {
            hbox = hboxs.get(hboxs.size()-1);
            if(hbox.getChildren().size() == CardManager.NB_CARD_MAX_IN_HBOX) {
                hbox = CARD_MANAGER.createHBox();
                hboxs.add(hbox);
            }
        } else {
            hbox = CARD_MANAGER.createHBox();
			hboxs.add(hbox);
        }
		
		// ajout la carte d'ajout a la hbox
		hbox.getChildren().add(getAddCard());

        // Ajouter les cartes
	    placeCard.getChildren().addAll(hboxs);
    	
    	nbCard.setText(Integer.toString(deck.cards.size()));
    }

    private Pane getAddCard() throws IOException {
		/* ----- Carte d'ajout ----- */
		if(paneAddCard == null) {
			// création
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carteAjouter.fxml"));
			paneAddCard = loader.load();
			ControllerCarteAjouter controller = loader.getController();
			controller.setText("Créer une carte");

			// lorsque que clické
			paneAddCard.setOnMouseClicked(_ -> {
				try {          
					Card newCard = new Card("", 0, "", "");
					deck.cards.add(newCard);
					FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
					Pane newCardPane = newLoader.load();
					ControllerCarte newController = newLoader.getController();
					newController.setCard(newCard);

					newCardPane.setOnMouseClicked(_ -> {
						ControllerPageCreationCarte.setDeck(deck);
                        ControllerPageCreationCarte.setCard(newCard);
						Main.switchPage("pageCreationCarte.fxml");
					});

					HBox lastHbox = (HBox) placeCard.getChildren().getLast();   // On récupère la dernière HBox
					Pane carteAjout = (Pane) lastHbox.getChildren().getLast();   // On récupère la carte d'ajout
					lastHbox.getChildren().remove(carteAjout);   // On enleve la carte d'ajout de la HBox
					lastHbox.getChildren().add(newCardPane);   // on met la nouvelle carte a la place

					// on remet la carte d'ajout
					if(lastHbox.getChildren().size() == CardManager.NB_CARD_MAX_IN_HBOX) {
						lastHbox = CARD_MANAGER.createHBox();
						placeCard.getChildren().add(lastHbox);
					}
					lastHbox.getChildren().add(carteAjout);

					// nbCard.setText(Integer.toString(deck.getCards().size()));
                    ControllerPageCreationCarte.setDeck(deck);
                    ControllerPageCreationCarte.setCard(newCard);
                    Main.switchPage("pageCreationCarte.fxml");
				} catch(IOException e) { e.printStackTrace(); }
			});
		}
		return paneAddCard;
	}
    
    @FXML
    void confirm(ActionEvent event) throws IOException {
    	deck.title = tfDeckName.getText();
    	deck.imageUrl = tfImageLink.getText();
        // deck.loadDeckPane();
    	Main.switchPage("pageSelectionDeck.fxml");
    }

	@FXML
    void delete(ActionEvent event) throws IOException {
		ControllerModalSupprimer.setDeck(deck);
		Main.loadModalPage("supprimer.fxml", true);

		Main.switchPage("pageSelectionDeck.fxml");
    }

}