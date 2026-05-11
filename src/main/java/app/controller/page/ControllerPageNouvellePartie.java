package app.controller.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Main;
import app.controller.modal.ControllerModalErreurParamNewGame;
import app.controller.page.PlateauJeu.Time;
import app.model.Deck;
import app.model.Player;
import app.util.PageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControllerPageNouvellePartie {
    private static final PageManager P_M = SingletonRegistry.get(PageManager.class, PageManager::new);

    @FXML private Button btnJ1;
    @FXML private Button btnJ2;
    @FXML private TextField tfNomJ1;
    @FXML private TextField tfNomJ2;
    @FXML private ComboBox<String> cbDeck;
    @FXML private ComboBox<String> cbTime;

    private final Map<String, Deck> decks = new HashMap<>();
    private int nbJoueur = 1;
    
    @FXML
    public void initialize() {
        for(Deck deck : Main.DECKS) {
            this.decks.put(deck.title, deck);
            this.cbDeck.getItems().add(deck.title);
        }

        for(Time time : Time.values()) {
            this.cbTime.getItems().add(time.toString());
        }
        this.cbTime.getSelectionModel().select("Infini");
    }

    @FXML
    void on1Jclick(ActionEvent event) {
        if(this.nbJoueur != 1) {
            this.activerBouton(this.btnJ1, this.btnJ2);
            this.tfNomJ2.setDisable(true);
            this.nbJoueur = 1;
        }
    }

    @FXML
    void on2Jclick(ActionEvent event) {
        if(this.nbJoueur != 2) {
            this.activerBouton(this.btnJ2, this.btnJ1);
            this.tfNomJ2.setDisable(false);
            this.nbJoueur = 2;
        }
    }

    private void activerBouton(Button actif, Button inactif) {
        // Nettoyer les styles d'abord
        actif.getStyleClass().removeAll("button_active", "button_inactive");
        inactif.getStyleClass().removeAll("button_active", "button_inactive");

        // Appliquer les bons
        actif.getStyleClass().add("button_active");
        inactif.getStyleClass().add("button_inactive");
    }

    @FXML
    void onConfirmClick(ActionEvent event) {
        ControllerModalErreurParamNewGame cmepng = new ControllerModalErreurParamNewGame();

        String nomJ1 = this.tfNomJ1.getText().trim();
        if(nomJ1.isBlank()) {
            cmepng.addError("Le joueur 1 n'a pas de nom");
        }

        String nomJ2 = this.tfNomJ2.getText().trim();
        if(this.nbJoueur == 2 && nomJ2.isBlank()) {
            cmepng.addError("Le joueur 2 n'a pas de nom");
        }

        // on duplique le deck pour pas modifier le deck originel
        Deck selectedDeck = new Deck(this.decks.get(this.cbDeck.getValue()));
        Time selectedTime = Time.toEnum(this.cbTime.getValue());

        if (cmepng.areThereError()) {
            P_M.loadModalPage("erreurParamNewGame.fxml", cmepng, false);
        }
        else {
        	if(this.nbJoueur == 1) {
                ControllerPagePlateau1J cpp1j = new ControllerPagePlateau1J(new Player(nomJ1), selectedDeck, selectedTime);
	            P_M.switchPage("pagePlateau1J.fxml", cpp1j);
        	} else {
                // ControllerPagePlateau1J cpp2j = new ControllerPagePlateau1J(new Player(nomJ1), new Player(nomJ2), selectedDeck, selectedTime);
	            // P_M.switchPage("pagePlateau2J.fxml", cpp2j);
        	}
        }
    }

    @FXML
    void onRetour(ActionEvent event) throws IOException {
        P_M.switchPage("pageAccueil.fxml", null);
    }
}