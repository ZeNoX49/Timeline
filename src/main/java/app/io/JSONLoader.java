package app.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Main;
import app.model.Card;
import app.model.Deck;
import app.pojo.CardPOJO;
import app.pojo.DeckPOJO;

public class JSONLoader {

    private final static Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "json", "data.json");

    public static void load() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        		
        try {
            File file = PATH.toFile();

            // si le fichier n'existe pas
            if (!file.exists()) {
            	System.out.println("Repertoire courant : " + System.getProperty("user.dir"));
                System.err.println("Erreur : Le fichier " + PATH + " est introuvable.");
                return;
            }
            
            List<DeckPOJO> collectionPOJO = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, DeckPOJO.class));
            
            // si il n'y a rien dans le fichier
            if (collectionPOJO == null || collectionPOJO.isEmpty()) {
                System.err.println("Erreur : Donnees JSON invalides ou vides.");
                return;
            }

            for (DeckPOJO deckPOJO : collectionPOJO) {
            	Deck deck = new Deck(deckPOJO);
            	
                for (CardPOJO cardPOJO : deckPOJO.cards) {
                    deck.cards.add(new Card(cardPOJO));
                }
                
                Main.DECKS.add(deck);
                System.out.println("Deck \"" + deckPOJO.title + "\" charge avec succes (" + deckPOJO.cards.length + " cartes).");
            }
            
        } catch (JsonProcessingException e) {
            System.err.println("Erreur de traitement JSON : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier de donnees : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
