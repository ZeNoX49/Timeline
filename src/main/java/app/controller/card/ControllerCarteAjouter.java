package app.controller.card;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ControllerCarteAjouter {
	
	@FXML private Label lAjouter;
	
	private final String text;
	
	public ControllerCarteAjouter(String txt) {
		this.text = txt;
	}

	@FXML
	private void initialize() {
		this.lAjouter.setText(this.text);
	}

	public Pane getRoot() {
        try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carteAjouter.fxml"));
			loader.setController(this);
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Impossible de charger carteAjouter.fxml", e);
		}
    }
}