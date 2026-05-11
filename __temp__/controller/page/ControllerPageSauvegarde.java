package app.controller.page;

import app.Main;
import app.controller.card.ControllerCarteSauvegarde;
import app.model.MainGame;
import app.util.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ControllerPageSauvegarde {

    // @FXML private VBox PaneSaves;

    // @FXML
    // public void initialize() {
    //     PaneSaves.getChildren().clear();

    //     String[] saves = SaveManager.listSaves();
    //     if (saves != null) {
    //         for (String saveFile : saves) {
    //             try {
    //                 MainGame saveData = SaveManager.load(saveFile.replace(".dat", ""));
                    
    //                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carteSauvegarde.fxml"));
    //                 Parent saveCard = loader.load();
    //                 ControllerCarteSauvegarde controller = loader.getController();

    //                 controller.setTitre("Sauvegarde N°" + saveFile.replaceAll("\\D+", ""));
    //                 controller.setNbJoueur(saveData.getNbJoueur() + " joueurs");
    //                 controller.setDeck("Deck : " + saveData.getDeckName());
    //                 controller.setSaveName(saveFile.replace(".dat", ""));
    //                 // controller.setController(this);

    //                 PaneSaves.getChildren().add(saveCard);
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }

    // @FXML
    // void onRetourClick(ActionEvent event) {
    //     Main.switchPage("pageAccueil.fxml");
    // }
}
