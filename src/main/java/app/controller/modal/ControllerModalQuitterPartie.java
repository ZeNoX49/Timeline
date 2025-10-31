package app.controller.modal;

import java.io.IOException;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerModalQuitterPartie {
    // GameManager gameManager = GameManager.getInstance();

    @FXML
	void onConfirmQuit(ActionEvent event) throws IOException {
		// MainGame currentGame = gameManager.getCurrentGame();

        // String saveName = gameManager.getCurrentSaveName();
        // if (saveName == null) {
        //     int nextNum = SaveManager.getAndIncrementSaveNumber();
        //     saveName = "save_timeline_partie_" + nextNum;
        //     gameManager.setCurrentSaveName(saveName);
        // }
	    
	    // if (currentGame != null) {
        //     SaveManager.save(currentGame, saveName);
        //     Stage modalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //     modalStage.close();
        //     Main.switchPage("pageAccueil.fxml");
        // } else {
        //     Alert alert = new Alert(Alert.AlertType.ERROR);
        //     alert.setTitle("Erreur");
        //     alert.setHeaderText(null);
        //     alert.setContentText("Aucune partie à sauvegarder !");
        //     alert.showAndWait();
        // }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
        Main.switchPage("pageAccueil.fxml");
	}
    
    @FXML
    void onCancelQuit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}