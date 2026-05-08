package app.util;

import java.io.IOException;
import java.util.HashMap;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PageManager {
    private static PageManager instance;
    public static PageManager getInstance() {
        if(PageManager.instance == null) {
            PageManager.instance = new PageManager();
        }
        return PageManager.instance;
    }

    private static final HashMap<String, String> pageTitle = new HashMap<>();
    private PageManager() {
		// Page principale
		pageTitle.put("pageAccueil.fxml", 		  "Timeline - Accueil");
		pageTitle.put("pageCreationCarte.fxml",  "Timeline - Faire une Carte");
		pageTitle.put("pageCreationDeck.fxml",   "Timeline - Faire un Deck");
		pageTitle.put("pageSauvegarde.fxml", 	  "Timeline - Charger une Partie");
		pageTitle.put("pageNouvellePartie.fxml", "Timeline - Paramètres de Nouvelle Partie");
		pageTitle.put("pagePlateau1J.fxml", 	  "Timeline - Jeu 1 joueur");
		pageTitle.put("pagePlateau2J.fxml", 	  "Timeline - Jeu 2 joueurs");
		pageTitle.put("pageSelectionDeck.fxml",  "Timeline - Création / Modification de Deck");
		
        // Page modale
		pageTitle.put("erreurNewGame.fxml", 	  "Timeline - Erreur dans la création de la partie");
		pageTitle.put("quitterJeu.fxml", 		  "Timeline - Quitter le jeu ?");
		pageTitle.put("quitterPartie.fxml", 	  "Timeline - Quitter le partie ?");
		pageTitle.put("rules.fxml", 			  "Timeline - Règles");
		pageTitle.put("supprimer.fxml", 		  "Timeline - Supprimer ?");
		pageTitle.put("winGame.fxml", 			  "Timeline - Fin de partie");
    }

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;   
    }

    /* ================================================== */

    /**
	 * changer de page, unqiuement pour les pages principales
	 */
	public Object switchPage(String file) {
        if(this.stage == null) throw new Error("stage est null");
	    try {
	        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/page/" + file));
	        Scene scene = new Scene(loader.load());

	        this.stage.setTitle("Destiny Rising");
	        this.stage.setScene(scene);
	        this.stage.show();
	        
            return loader.getController();
	    } catch (IOException e) {
	        System.err.println("Erreur de chargement de la scène : " + file);
	        e.printStackTrace();
            return null;
	    }
	}

    public Object loadModalPage(String file, boolean wait) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/modal/" + file));
	        Scene scene = new Scene(loader.load());
			
			Stage modalStage = new Stage();
            modalStage.setTitle(pageTitle.get(file));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UTILITY);
            modalStage.setResizable(false);

	        modalStage.setScene(scene);
			if(wait) {
                modalStage.showAndWait();
            } else {
                modalStage.show();
            } 

            return loader.getController();
	    } catch (IOException e) {
	        System.err.println("Erreur de chargement de la scène : " + file);
	        e.printStackTrace();
            return null;
	    }
	}
}