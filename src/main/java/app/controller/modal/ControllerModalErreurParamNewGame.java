package app.controller.modal;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControllerModalErreurParamNewGame {
    @FXML private VBox vboxErreur;

    private final List<String> errors = new ArrayList<>();

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean areThereError() {
        return !this.errors.isEmpty();
    }

    @FXML
    private void initialize() {
        for(String error : this.errors) {
            this.vboxErreur.getChildren().add(new Label(error));
            System.out.println(error);
        }
    }
}
