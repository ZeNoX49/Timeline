package app.controller.page;

import java.io.IOException;
import java.util.List;

import app.Collection;
import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.card.ControllerCarteAjouter;
import app.model.Deck;
import app.util.CardManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ControllerPageSelectionDeck {
	private CardManager cardManager = CardManager.getInstance();

	@FXML private Label Label_nbDeck;
    @FXML private VBox placeDeck;

	private static Pane paneAddCard = null;

	private int nbDeck = 0;
    
    @FXML
    public void initialize() throws IOException {
    	
		// Carte de base
    	List<HBox> hboxs = cardManager.getDecks();

		HBox hbox;
        if(!hboxs.isEmpty()) {
            hbox = hboxs.get(hboxs.size()-1);
			if(hbox.getChildren().size() == CardManager.getNbCardMaxInHBox()) {
				hbox = cardManager.createHBox();
				hboxs.add(hbox);
			}
        } else {
            hbox = cardManager.createHBox();
			hboxs.add(hbox);
        }

		// ajout la carte d'ajout a la hbox
		hbox.getChildren().add(getAddCard());

		// Ajouter les cartes
	    placeDeck.getChildren().addAll(hboxs);
    	
		// on affiche le nb de decks
		nbDeck = (hboxs.size()-1) * CardManager.getNbCardMaxInHBox()  + hbox.getChildren().size() - 1; // on ne compte pas la carte d'ajout
    	Label_nbDeck.setText(Integer.toString(nbDeck));
    }

	private Pane getAddCard() throws IOException {
		/* ----- Carte d'ajout ----- */
		if(paneAddCard == null) {
			// création
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carteAjouter.fxml"));
			paneAddCard = loader.load();
			ControllerCarteAjouter controller = loader.getController();
			controller.setText("Créer un deck");

			// lorsque que clické
			paneAddCard.setOnMouseClicked(_ -> {
				try {          
					Deck newDeck = new Deck("", "");
					Collection.addDeck(newDeck);
					FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
					Pane newCardPane = newLoader.load();
					ControllerCarte newController = newLoader.getController();
					newController.setDeck(newDeck);

					newCardPane.setOnMouseClicked(_ -> {
						ControllerPageCreationDeck.setDeck(newDeck);
						Main.switchPage("pageCreationDeck.fxml");
					});

					HBox lastHbox = (HBox) placeDeck.getChildren().getLast();   // On récupère la dernière HBox
					Pane carteAjout = (Pane) lastHbox.getChildren().getLast();   // On récupère la carte d'ajout
					lastHbox.getChildren().remove(carteAjout);   // On enleve la carte d'ajout de la HBox
					lastHbox.getChildren().add(newCardPane);   // on met la nouvelle carte a la place

					// on remet la carte d'ajout
					if(lastHbox.getChildren().size() == CardManager.getNbCardMaxInHBox()) {
						lastHbox = cardManager.createHBox();
						placeDeck.getChildren().add(lastHbox);
					}
					lastHbox.getChildren().add(carteAjout);

					// nbDeck++;
					// Label_nbDeck.setText(Integer.toString(nbDeck));
					ControllerPageCreationDeck.setDeck(newDeck);
					Main.switchPage("pageCreationDeck.fxml");
				} catch(IOException e) { e.printStackTrace(); }
			});
		}
		return paneAddCard;
	}

	@FXML
    void retour(ActionEvent event) {
    	Main.switchPage("pageAccueil.fxml");
    }
	
}