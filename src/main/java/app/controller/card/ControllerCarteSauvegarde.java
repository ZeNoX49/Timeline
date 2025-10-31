package app.controller.card;

import app.Main;
import app.controller.page.ControllerPageSauvegarde;
import app.model.MainGame;
import app.util.GameManager;
import app.util.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerCarteSauvegarde {
    // GameManager gameManager = GameManager.getInstance();

	// @FXML private Label titre;
	// @FXML private Label nbJoueur;
	// @FXML private Label deck;
	// private String saveName;
	// private ControllerPageSauvegarde controller;

	// public void setTitre(String t) { titre.setText(t); }
	// public void setNbJoueur(String n) { nbJoueur.setText(n); }
	// public void setDeck(String d) { deck.setText(d); }
	// public void setSaveName(String s) { this.saveName = s; }
	// public void setController(ControllerPageSauvegarde controller) { this.controller = controller; }
	
    // @FXML
    // void onDeleteSaveClick(ActionEvent event) {
    //     SaveManager.deleteSave(saveName).delete();
    //     System.out.println("Partie " + saveName + " supprimée");
    //     controller.initialize();
    // }

    // @FXML
    // void onStartSaveClick(ActionEvent event) {
    // 	MainGame loadedGame = SaveManager.load(saveName);
    // 	if (loadedGame != null) {
    // 	    gameManager.setCurrentGame(loadedGame);
    // 	    gameManager.setCurrentSaveName(saveName);
    // 	    Main.switchPage("plateau1.fxml");
    // 	}

    //     if (loadedGame != null) {
    //         System.out.println("Partie " + saveName + " chargee, lancement !");
    //         Main.switchPage("plateau1.fxml");
    //     } else {
    //         javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
    //         alert.setTitle("Erreur de chargement");
    //         alert.setHeaderText(null);
    //         alert.setContentText("Impossible de charger la sauvegarde " + saveName + ".");
    //         alert.showAndWait();
    //     }
    // }
    
}