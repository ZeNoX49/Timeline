package app.controller.modal;

import app.io.JSONSaver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerModalQuitterJeu {

    @FXML
    void onConfirmQuit(ActionEvent event) {
    	JSONSaver.save();
        this.onCancelQuit(event);
        Platform.exit();
    }

    @FXML
    void onCancelQuit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}