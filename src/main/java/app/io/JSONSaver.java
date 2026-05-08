package app.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.Main;
import app.model.Card;
import app.model.Deck;
import app.pojo.CardPOJO;
import app.pojo.DeckPOJO;

public class JSONSaver {

    private final static Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "json", "data.json");

    public static void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<DeckPOJO> deckList = new ArrayList<>();

        for (Deck deck : Main.DECKS) {
            DeckPOJO deckPOJO = new DeckPOJO();
            deckPOJO.title = deck.title;
            deckPOJO.imageUrl = deck.imageUrl;

            List<CardPOJO> cardList = new ArrayList<>();

            for(Card card : deck.cards) {
                CardPOJO cardPOJO = new CardPOJO();

                cardPOJO.title = card.title;
                cardPOJO.description = card.description;
                cardPOJO.date = card.date;
                cardPOJO.imageUrl = card.imageUrl;

                cardList.add(cardPOJO);
            }

            deckPOJO.cards = cardList.toArray(CardPOJO[]::new);
            deckList.add(deckPOJO);
        }

        try {
            File file = PATH.toFile();
            objectMapper.writeValue(file, deckList);
            System.out.println("Sauvegarde JSON réussie vers " + PATH);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
            e.printStackTrace();
        }
    }
}