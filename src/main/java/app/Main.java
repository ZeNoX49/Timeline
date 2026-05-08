package app;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import app.io.JSONLoader;
import app.model.Deck;
import app.util.PageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static final PageManager P_M = PageManager.getInstance();

	public static List<Deck> DECKS;
	
	@Override
	public void start(Stage stage) throws IOException {
		JSONLoader.load();
		
		stage.setOnCloseRequest(_ -> {
			// TOOD: a améliorer
            P_M.loadModalPage("quitterJeu.fxml", true);
        });
		
		P_M.setStage(stage);
    	P_M.switchPage("pageAccueil.fxml");
	} public static void main(String[] args) {
		deleteUselessLog();
		launch(args);
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