package app.controller.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerCarteAjouter {
	@FXML private Label Label_Ajouter;
	
	public void setText(String txt) {
		Label_Ajouter.setText(txt);
	}
}