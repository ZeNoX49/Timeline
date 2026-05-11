package app.controller.modal;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerModalSupprimer {
	private boolean result;

    @FXML
    void onConfirm(ActionEvent event) throws IOException {
		this.result = true;
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCancel(ActionEvent event) {
		this.result = false;
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

	public boolean getResult() {
		return this.result;
	}

}