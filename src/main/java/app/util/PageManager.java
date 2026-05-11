package app.util;

import java.io.IOException;
import java.util.HashMap;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PageManager implements SingletonRegistry.Singleton {
    
	private static final HashMap<String, String> PAGE_TITLES = new HashMap<>();
    public PageManager() {
		// Page principale
		PAGE_TITLES.put("pageAccueil.fxml", 		  "Timeline - Accueil");
		PAGE_TITLES.put("pageCreationCarte.fxml",  "Timeline - Faire une Carte");
		PAGE_TITLES.put("pageCreationDeck.fxml",   "Timeline - Faire un Deck");
		PAGE_TITLES.put("pageSauvegarde.fxml", 	  "Timeline - Charger une Partie");
		PAGE_TITLES.put("pageNouvellePartie.fxml", "Timeline - Paramètres de Nouvelle Partie");
		PAGE_TITLES.put("pagePlateau1J.fxml", 	  "Timeline - Jeu 1 joueur");
		PAGE_TITLES.put("pagePlateau2J.fxml", 	  "Timeline - Jeu 2 joueurs");
		PAGE_TITLES.put("pageSelectionDeck.fxml",  "Timeline - Création / Modification de Deck");
		
        // Page modale
		PAGE_TITLES.put("erreurNewGame.fxml", 	  "Timeline - Erreur dans la création de la partie");
		PAGE_TITLES.put("quitterJeu.fxml", 		  "Timeline - Quitter le jeu ?");
		PAGE_TITLES.put("quitterPartie.fxml", 	  "Timeline - Quitter le partie ?");
		PAGE_TITLES.put("rules.fxml", 			  "Timeline - Règles");
		PAGE_TITLES.put("supprimer.fxml", 		  "Timeline - Supprimer ?");
		PAGE_TITLES.put("winGame.fxml", 			  "Timeline - Fin de partie");
    }

    private Stage stage;
    public void init(Stage stage) {
        this.stage = stage;   
    }

    /* ================================================== */

    /**
     * @param controller null si pas nécessaire
     */
    public <T> void switchPage(String file, T controller) {
        if (this.stage == null) {
            throw new IllegalStateException("stage est null");
        }

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/page/" + file));
            if (controller != null) {
                loader.setController(controller);
            }

            Parent root = loader.load();

            this.stage.setTitle(PAGE_TITLES.getOrDefault(file, "Timeline"));
            this.stage.setScene(new Scene(root));
            this.stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Erreur de chargement de la scène : " + file, e);
        }
    }

    /**
     * @param controller null si pas nécessaire
     */
    public <T> void loadModalPage(String file, T controller, boolean wait) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/modal/" + file));
            if (controller != null) {
                loader.setController(controller);
            }

            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle(PAGE_TITLES.getOrDefault(file, "Timeline"));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UTILITY);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));

            if (wait) {
                modalStage.showAndWait();
            } else {
                modalStage.show();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de chargement de la scène : " + file, e);
        }
    }
}