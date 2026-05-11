package app.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.controller.card.ControllerCarte;
import app.model.Card;
import app.model.Deck;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public final class CardManager implements SingletonRegistry.Singleton {

    private final Map<String, Pane> deckPanes;
    private final Map<String, Map<String, Pane>> cardPanes;

    public CardManager() {
        this.deckPanes = new HashMap<>();
        this.cardPanes = new HashMap<>();
    }

    public Pane getDeckPane(Deck deck) {
        Pane pane = this.deckPanes.get(deck.title);

        if(pane == null) {
            pane = this.loadCardPane(new ControllerCarte(deck));
            this.deckPanes.put(deck.title, pane);
        }

        return pane;
    }

    public Pane getCardPane(Deck deck, Card card) {
        if(this.cardPanes.get(deck.title) == null) {
            this.cardPanes.put(deck.title, new HashMap<>());
        }

        Pane pane = this.cardPanes.get(deck.title).get(card.title);

        if(pane == null) {
            pane = this.loadCardPane(new ControllerCarte(deck, card));
            this.cardPanes.get(deck.title).put(card.title, pane);
        }

        return pane;
    }

    private Pane loadCardPane(ControllerCarte controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
            loader.setController(controller);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger carte.fxml", e);
        }
    }

}