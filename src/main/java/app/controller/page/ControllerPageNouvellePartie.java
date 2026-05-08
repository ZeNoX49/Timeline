package app.controller.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.Collection;
import app.Main;
import app.controller.modal.ControllerModalErreurParamNewGame;
import app.model.Deck;
import app.util.GameManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControllerPageNouvellePartie {
    private final static GameManager GAME_MANAGER = GameManager.getInstance();

    @FXML private Button buttonJ1;
    @FXML private Button buttonJ2;
    
    @FXML private TextField txtFieldnomJ1;
    @FXML private TextField txtFieldnomJ2;

    @FXML private ComboBox<String> comboDecks;
    @FXML private ComboBox<String> comboTime;

    @FXML
    public void initialize() {
        gameManager.reset();

    	/* ----- Decks ----- */
        // chargement des decks dans le combobox
        List<String> deckNames = new ArrayList<>();
        for (Deck deck : Collection.getDecks()) {
            deckNames.add(deck.getTitle());
        }
        comboDecks.setItems(FXCollections.observableArrayList(deckNames));
        
        // Deck par défaut
        comboDecks.setValue(Collection.getDecks().get(0).getTitle());

        /* ----- Temps ----- */
        // chargement des temps dans le combobox
        comboTime.setItems(FXCollections.observableArrayList("15 sec", "30 sec", "45 sec", "60 sec", "Infini"));

        // temps par défaut
        comboTime.getSelectionModel().select("Infini");
    }

    @FXML
    void on1Jclick(ActionEvent event) {
        if(gameManager.getNbJoueur() != 1) {
            activerBouton(buttonJ1, buttonJ2);
            gameManager.setNbJoueur(1);
            txtFieldnomJ2.setDisable(true);
        }
    }

    @FXML
    void on2Jclick(ActionEvent event) {
        if(gameManager.getNbJoueur() != 2) {
            activerBouton(buttonJ2, buttonJ1);
            gameManager.setNbJoueur(2);
            txtFieldnomJ2.setDisable(false);
        }
    }

    private void activerBouton(Button actif, Button inactif) {
        // Nettoyer les styles d’abord
        actif.getStyleClass().removeAll("button_active", "button_inactive");
        inactif.getStyleClass().removeAll("button_active", "button_inactive");

        // Appliquer les bons
        actif.getStyleClass().add("button_active");
        inactif.getStyleClass().add("button_inactive");
    }

    @FXML
    void onConfirmClick(ActionEvent event) {
        ControllerModalErreurParamNewGame.resetError();

        // Nom du J1
        String nomJ1 = txtFieldnomJ1.getText().trim();
        if(nomJ1.isEmpty()) {
            ControllerModalErreurParamNewGame.addError("Le joueur 1 n'a pas de nom");
        } else {
            gameManager.setNomJ1(nomJ1);
        }

        // Nom du J2
        if(!txtFieldnomJ2.isDisable()) {
            String nomJ2 = txtFieldnomJ2.getText().trim();
            if(nomJ2.isEmpty()) {
                ControllerModalErreurParamNewGame.addError("Le joueur 2 n'a pas de nom");
            } else {
                gameManager.setNomJ2(nomJ2);
            }
        }

        // Action lors d'un changement de deck
        String selectedDeck = comboDecks.getValue();
        for(Deck deck : Collection.getDecks()) {
            if(deck.getTitle().equals(selectedDeck)) {
                gameManager.setDeck(deck);
                break;
            }
        }

        // Action lors d'un changement de deck
        String selectedTime = comboTime.getValue();
        boolean timedMode = !selectedTime.equals("Infini");
        gameManager.setTempsLimite(timedMode ? Integer.valueOf(selectedTime) : null);

        // Lancement de la partie
        if(ControllerModalErreurParamNewGame.areThereError()) {
            Main.loadModalPage("erreurParamNewGame.fxml");
        }
        else {
            // gameManager.setCurrentSaveName(null);
            // gameManager.setCurrentGame(null);
            Main.switchPage("pagePlateau"+gameManager.getNbJoueur()+"J.fxml");
        	// if(gameManager.getNbJoueur() == 1) {
	        //     Main.switchPage("pagePlateau1J.fxml");
        	// } else {
	        //     Main.switchPage("pagePlateau2J.fxml");
        	// }
        }
    }

    @FXML
    void onRetour(ActionEvent event) throws IOException {
    	Main.switchPage("pageAccueil.fxml");
    }
}