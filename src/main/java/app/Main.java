package app;

import java.io.IOException;
import java.util.HashMap;

import app.io.JSONLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private static Stage primaryStage;
	private static HashMap<String, String> pageTitle;

	private static Object currentController;
	
	@Override
	public void start(Stage stage) throws IOException {
		pageTitle = new HashMap<>();
		// Page principale
		pageTitle.put("pageAccueil.fxml", 		"Timeline - Accueil");
		pageTitle.put("pageCreationCarte.fxml", "Timeline - Faire une Carte");
		pageTitle.put("pageCreationDeck.fxml", 	"Timeline - Faire un Deck");
		pageTitle.put("pageSauvegarde.fxml", 	"Timeline - Charger une Partie");
		pageTitle.put("pageNouvellePartie.fxml","Timeline - Paramètres de Nouvelle Partie");
		pageTitle.put("pagePlateau1J.fxml", 	"Timeline - Jeu 1 joueur");
		pageTitle.put("pagePlateau2J.fxml", 	"Timeline - Jeu 2 joueurs");
		pageTitle.put("pageSelectionDeck.fxml", "Timeline - Création / Modification de Deck");
		// Page modale
		pageTitle.put("erreurNewGame.fxml", 	"Timeline - Erreur dans la création de la partie");
		pageTitle.put("quitterJeu.fxml", 		"Timeline - Quitter le jeu ?");
		pageTitle.put("quitterPartie.fxml", 	"Timeline - Quitter le partie ?");
		pageTitle.put("rules.fxml", 			"Timeline - Règles");
		pageTitle.put("supprimer.fxml", 		"Timeline - Supprimer ?");
		pageTitle.put("winGame.fxml", 			"Timeline - Fin de partie");

		primaryStage = stage;
		JSONLoader.load();
		
		stage.setOnCloseRequest(event -> {
			event.consume();
            loadModalPage("quitterJeu.fxml", true);
        });
		
    	switchPage("pageAccueil.fxml");
	} public static void main(String[] args) { launch(args); }

	// changer de page, unqiuement pour les pages principales
	public static void switchPage(String file) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/page/" + file));
	        Scene scene = new Scene(loader.load());
	
			currentController = loader.getController();

	        primaryStage.setTitle(pageTitle.get(file));
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	    } catch (IOException e) {
	        System.err.println("Erreur de chargement de la scène : " + file);
	        e.printStackTrace();
	    }
	}

	// charger un page modale
	public static void loadModalPage(String file) {
		loadModalPage(file, false);
	}
	public static void loadModalPage(String file, boolean wait) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/modal/" + file));
	        Scene scene = new Scene(loader.load());

			currentController = loader.getController();
			
			Stage modalStage = new Stage();
            modalStage.setTitle(pageTitle.get(file));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UTILITY);
            modalStage.setResizable(false);

	        modalStage.setScene(scene);
			if(wait) { modalStage.showAndWait(); }
			else     { modalStage.show(); 		 } 
	    } catch (IOException e) {
	        System.err.println("Erreur de chargement de la scène : " + file);
	        e.printStackTrace();
	    }
	}

	// Obtenir le controller de la page (modal) en cours d'utilisation
	public static Object getCurrentController() {
        return currentController;
    }
}