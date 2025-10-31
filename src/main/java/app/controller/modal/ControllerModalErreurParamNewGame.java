package app.controller.modal;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControllerModalErreurParamNewGame {
    @FXML private VBox vboxErreur;

    private static List<String> errors = new ArrayList<>();
    public static void resetError() {
        errors.clear();
    }
    public static void addError(String error) {
        errors.add(error);
    }
    public static boolean areThereError() {
        return !errors.isEmpty();
    }

    @FXML
    private void initialize() {
        for(String error : errors) {
            Label label = new Label();
            label.setText(error);
            vboxErreur.getChildren().add(label);
        }
    }
}
