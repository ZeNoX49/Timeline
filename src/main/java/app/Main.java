package app;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.io.JSONLoader;
import app.model.Deck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public final static List<Deck> DECKS = new ArrayList<>();

	private static Stage primaryStage;
	private static HashMap<String, String> pageTitle;

	private static Object currentController;
	
	@Override
	public void start(Stage stage) throws IOException {
		pageTitle = new HashMap<>();
		// Page principale
		pageTitle.put("pageAccueil.fxml", 		 "Timeline - Accueil");
		pageTitle.put("pageCreationCarte.fxml", "Timeline - Faire une Carte");
		pageTitle.put("pageCreationDeck.fxml",  "Timeline - Faire un Deck");
		pageTitle.put("pageSauvegarde.fxml", 	 "Timeline - Charger une Partie");
		pageTitle.put("pageNouvellePartie.fxml","Timeline - Paramètres de Nouvelle Partie");
		pageTitle.put("pagePlateau1J.fxml", 	 "Timeline - Jeu 1 joueur");
		pageTitle.put("pagePlateau2J.fxml", 	 "Timeline - Jeu 2 joueurs");
		pageTitle.put("pageSelectionDeck.fxml", "Timeline - Création / Modification de Deck");
		// Page modale
		pageTitle.put("erreurNewGame.fxml", 	 "Timeline - Erreur dans la création de la partie");
		pageTitle.put("quitterJeu.fxml", 		 "Timeline - Quitter le jeu ?");
		pageTitle.put("quitterPartie.fxml", 	 "Timeline - Quitter le partie ?");
		pageTitle.put("rules.fxml", 			 "Timeline - Règles");
		pageTitle.put("supprimer.fxml", 		 "Timeline - Supprimer ?");
		pageTitle.put("winGame.fxml", 			 "Timeline - Fin de partie");

		primaryStage = stage;
		JSONLoader.load();
		
		stage.setOnCloseRequest(_ -> {
            loadModalPage("quitterJeu.fxml", true);
        });
		
    	switchPage("pageAccueil.fxml");
	} public static void main(String[] args) {
		deleteUselessLog();
		launch(args);
	}

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

	/**
	 * Supprime tous les logs inutiles de JavaFX
	 * - "javafx.fxml.FXMLLoader$ValueElement processValue"
	 * - "Loading FXML document with JavaFX API of version"
	 */
	private static void deleteUselessLog() {
		List<String> toDelete = List.of(
			"javafx.fxml.FXMLLoader$ValueElement processValue",
			"Loading FXML document with JavaFX API of version"
		);

		PrintStream originalErr = System.err;
		System.setErr(new PrintStream(new OutputStream() {
			private final StringBuilder sb = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				if (b == '\n') {
					String line = sb.toString();
					sb.setLength(0);

					// Vérifie si la ligne contient un des motifs à supprimer
					boolean shouldDelete = toDelete.stream().anyMatch(line::contains);
					if (!shouldDelete) {
						originalErr.println(line);
					}
				} else {
					sb.append((char)b);
				}
			}
		}, true));
	}
}