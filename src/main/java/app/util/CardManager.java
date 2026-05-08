package app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.Main;
import app.model.Card;
import app.model.Deck;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CardManager {
    private static CardManager instance;
    public static CardManager getInstance() {
        if(instance == null) {
            instance = new CardManager();
        }
        return instance;
    }
    private CardManager() {}

    // /* -------------------------------------------------- */

    public final static int NB_CARD_MAX_IN_HBOX = 9;

    /**
     * Créer les decks
     */
    public List<HBox> getDecks() throws IOException {
        List<Pane> paneDecks = new ArrayList<>();
        for(Deck deck : Main.DECKS) {
            paneDecks.add(deck.getDeckPane());
        }
        return this.getHboxs(paneDecks);
    }

    /**
     * Créer les cartes d'un deck
     */
    public List<HBox> getCards(Deck deck) throws IOException {
        List<Pane> paneCards = new ArrayList<>();
        for(Card card : deck.cards) {
            paneCards.add(card.getCardPane(deck));
        }
        return this.getHboxs(paneCards);
    }

    /**
     * Renvoie une list de hboxs contenant les cartes
     */
    private List<HBox> getHboxs(List<Pane> paneList) {
        List<HBox> hboxs = new ArrayList<>();

        HBox hbox = this.createHBox();
        for(Pane pane : paneList) {
            hbox.getChildren().add(pane);
    		if(hbox.getChildren().size() == NB_CARD_MAX_IN_HBOX) {
    			hboxs.add(hbox);
    			hbox = this.createHBox();
    		}
        }
        if(!hbox.getChildren().isEmpty()) {
            hboxs.add(hbox);
        }

        return hboxs;
    }

    /**
     * Création des HBox
     */
    public HBox createHBox() {
        HBox hbox = new HBox();
        hbox.setSpacing(17);
        hbox.setPrefSize(1490, 200);
        hbox.setMinSize(1490, 200);
        hbox.setMaxSize(1490, 200);
        hbox.setStyle("-fx-background-color: #262538");
        return hbox;
    }

}