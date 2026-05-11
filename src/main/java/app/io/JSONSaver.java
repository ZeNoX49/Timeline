package app.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.Main;

public class JSONSaver {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "json", "data.json");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void save() {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);

        File file = PATH.toFile();
        try {
            OBJECT_MAPPER.writeValue(file, Main.DECKS);
            System.out.println("%s -> sauvegardé".formatted(file));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON (%s): %s".formatted(file, e.getMessage()));
        }
    }
}