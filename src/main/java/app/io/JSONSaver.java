package app.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.Collection;
import app.model.Card;
import app.model.Deck;
import app.pojo.CardPOJO;
import app.pojo.CollectionPOJO;
import app.pojo.DeckPOJO;

public class JSONSaver {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "json", "data.json");

    public static void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        CollectionPOJO collectionPOJO = new CollectionPOJO();
        List<DeckPOJO> deckList = new ArrayList<>();

        for (Deck deck : Collection.getDecks()) {
            DeckPOJO deckPOJO = new DeckPOJO();
            deckPOJO.title = deck.getTitle();
            deckPOJO.imageUrl = deck.getImageUrl();

            List<CardPOJO> cardList = new ArrayList<>();

            for(Card card : deck.getCards()) {
                CardPOJO cardPOJO = new CardPOJO();

                cardPOJO.title = card.getTitle();
                cardPOJO.description = card.getDescription();
                cardPOJO.date = card.getDate();
                cardPOJO.imageUrl = card.getImageUrl();

                cardList.add(cardPOJO);
            }

            deckPOJO.cards = cardList.toArray(CardPOJO[]::new);
            deckList.add(deckPOJO);
        }

        collectionPOJO.decks = deckList.toArray(DeckPOJO[]::new);

        try {
            File file = PATH.toFile();
            objectMapper.writeValue(file, collectionPOJO);
            System.out.println("Sauvegarde JSON réussie vers " + PATH);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
            e.printStackTrace();
        }
    }
}