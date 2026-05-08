package app.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Main;
import app.model.Deck;

public class JSONLoader {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "json", "data.json");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void load() {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        		
        try {
            File file = PATH.toFile();

            // si le fichier n'existe pas
            if (!file.exists()) {
            	System.out.println("Repertoire courant : " + System.getProperty("user.dir"));
                System.err.println("Erreur : Le fichier " + PATH + " est introuvable.");
                return;
            }
            
            Main.DECKS = OBJECT_MAPPER.readValue(file, new TypeReference<List<Deck>>() {});
            
        } catch (JsonProcessingException e) {
            System.err.println("Erreur de traitement JSON : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier de donnees : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
