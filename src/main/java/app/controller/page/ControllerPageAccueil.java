package app.controller.page;

import app.util.PageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerPageAccueil {
    private static final PageManager P_M = PageManager.getInstance();

    // public void initialization() {
    //     app.util.GameManager.reset();
    // }

    @FXML
    void onSelectionDeck(ActionEvent event) {
    	P_M.switchPage("pageSelectionDeck.fxml");
    }

    @FXML
    void onLoadGame(ActionEvent event) {
    	P_M.switchPage("pageSauvegarde.fxml");
    }

    @FXML
    void onNewGame(ActionEvent event) {
        P_M.switchPage("pageNouvellePartie.fxml");
    }
    
    @FXML
    void onExitApp(ActionEvent event) {
        P_M.loadModalPage("quitterJeu.fxml", false);
    }
    
    @FXML
    void onCredit(ActionEvent event) {
        P_M.loadModalPage("regle.fxml", false);
    }
}