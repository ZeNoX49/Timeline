package app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.Collection;
import app.Main;
import app.controller.card.ControllerCarte;
import app.controller.page.ControllerPageCreationCarte;
import app.controller.page.ControllerPageCreationDeck;
import app.model.Card;
import app.model.Deck;
import javafx.fxml.FXMLLoader;
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

    // /* -------------------------------------------------- */

    private static int nbCardMaxInHBox = 9;
    public static int getNbCardMaxInHBox() {
        return nbCardMaxInHBox;
    }

    // /* -------------------------------------------------- */

    // Créer les decks
    public List<HBox> getDecks() throws IOException {
        List<Pane> paneDecks = new ArrayList<>();
        for(Deck deck : Collection.getDecks()) {
            paneDecks.add(deck.getDeckPane());
        }
        return getHboxs(paneDecks);
    }

    // Créer les cartes d'un deck
    public List<HBox> getCards(Deck deck) throws IOException {
        List<Pane> paneCards = new ArrayList<>();
        for(Card card : deck.getCards()) {
            paneCards.add(card.getCardPane(deck));
        }
        return getHboxs(paneCards);
    }

    // Renvoie une list de hboxs contenant les cartes
    private List<HBox> getHboxs(List<Pane> paneList) {
        List<HBox> hboxs = new ArrayList<>();

        HBox hbox = createHBox();
        for(Pane pane : paneList) {
            hbox.getChildren().add(pane);
    		if(hbox.getChildren().size() == nbCardMaxInHBox) {
    			hboxs.add(hbox);
    			hbox = createHBox();
    		}
        }
        if(!hbox.getChildren().isEmpty()) {
            hboxs.add(hbox);
        }

        return hboxs;
    }

    // Création des HBox
    public HBox createHBox() {
        HBox hbox = new HBox();
        hbox.setSpacing(17);
        hbox.setPrefSize(1490, 200);
        hbox.setMinSize(1490, 200);
        hbox.setMaxSize(1490, 200);
        hbox.setStyle("-fx-background-color: #262538");
        return hbox;
    }

    // Créer le pane d'un deck
    public Pane createDeckPane(Deck deck) throws IOException {
        System.out.println(" - " + deck.getTitle());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        Pane deckPane = loader.load();
        ControllerCarte controller = loader.getController();
        controller.setDeck(deck);

        deckPane.setOnMouseClicked(_ -> {
            ControllerPageCreationDeck.setDeck(deck);
            Main.switchPage("pageCreationDeck.fxml");
        });

        return deckPane;
    }

    // Créer le pane d'une carte
    public Pane createCardPane(Deck deck, Card card) throws IOException {
        System.out.println("\t - " + card.getTitle());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        Pane cardPane = loader.load();
        ControllerCarte controller = loader.getController();
        controller.setCard(card);
        controller.setDateVisible(true);

        cardPane.setOnMouseClicked(_ -> {
            ControllerPageCreationCarte.setDeck(deck);
            ControllerPageCreationCarte.setCard(card);
            Main.switchPage("pageCreationCarte.fxml");
        });

        return cardPane;
    }

}