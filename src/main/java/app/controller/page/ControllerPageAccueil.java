package app.controller.page;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerPageAccueil {

    // public void initialization() {
    //     app.util.GameManager.reset();
    // }

    @FXML
    void onSelectionDeck(ActionEvent event) {
    	Main.switchPage("pageSelectionDeck.fxml");
    }

    @FXML
    void onLoadGame(ActionEvent event) {
    	Main.switchPage("pageSauvegarde.fxml");
    }

    @FXML
    void onNewGame(ActionEvent event) {
        Main.switchPage("pageNouvellePartie.fxml");
    }
    
    @FXML
    void onExitApp(ActionEvent event) {
        Main.loadModalPage("quitterJeu.fxml");
    }
    
    @FXML
    void onCredit(ActionEvent event) {
        Main.loadModalPage("regle.fxml");
    }
}