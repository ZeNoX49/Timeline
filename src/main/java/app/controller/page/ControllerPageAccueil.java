package app.controller.page;

import app.util.PageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerPageAccueil {
    private static final PageManager P_M = SingletonRegistry.get(PageManager.class, PageManager::new);

    // public void initialization() {
    //     app.util.GameManager.reset();
    // }

    @FXML
    void onSelectionDeck(ActionEvent event) {
    	P_M.switchPage("pageSelectionDeck.fxml", null);
    }

    @FXML
    void onLoadGame(ActionEvent event) {
    	// P_M.switchPage("pageSauvegarde.fxml", null);
    }

    @FXML
    void onNewGame(ActionEvent event) {
        P_M.switchPage("pageNouvellePartie.fxml", null);
    }
    
    @FXML
    void onExitApp(ActionEvent event) {
        P_M.loadModalPage("quitterJeu.fxml", null, false);
    }
    
    @FXML
    void onCredit(ActionEvent event) {
        P_M.loadModalPage("regle.fxml", null, false);
    }
}