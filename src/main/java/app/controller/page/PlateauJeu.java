package app.controller.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.controller.card.ControllerCarte;
import app.model.Card;
import app.model.Deck;
import app.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Classe abstraite mère pour les plateaux de jeu (1 joueur et 2 joueurs)
 * Contient toute la logique commune de gestion des cartes, du drag and drop, et des animations
 */
public abstract class PlateauJeu {
    public static enum Time {
        INFINI, SEC_15, SEC_30, SEC_45, MIN_1;

        public static Time toEnum(String val) {
            return switch(val) {
                case "Infini" -> INFINI;
                case "15sec" -> SEC_15;
                case "30sec" -> SEC_30;
                case "45sec" -> SEC_45;
                case "1min" -> MIN_1;
                default -> null;
            };
        }

        public Integer getValue() {
            return switch(this) {
                case INFINI -> null;
                case SEC_15 -> 15;
                case SEC_30 -> 30;
                case SEC_45 -> 45;
                case MIN_1 -> 60;
                default -> null;
            };
        }

        @Override
        public String toString() {
            return switch(this) {
                case INFINI -> "Infini";
                case SEC_15 -> "15sec";
                case SEC_30 -> "30sec";
                case SEC_45 -> "45sec";
                case MIN_1 -> "1min";
                default -> null;
            };
        }
    }

    protected final Deck deck;
    protected final Time time;
    protected Card selectedCard;
    protected final Map<Card, Pane> cardPanes = new HashMap<>();
    protected final Map<Card, ControllerCarte> cardControllers = new HashMap<>();

    public PlateauJeu(Deck deck, Time time) {
        this.deck = deck;
        this.time = time;
    }

    protected void createCard(Card card) {
        Pane cardPane;
        ControllerCarte cardController = new ControllerCarte(this.deck, card);
        try {
            FXMLLoader loaderDeck = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
            loaderDeck.setController(cardController);
            cardPane = loaderDeck.load();
        } catch(IOException e) { throw new Error("Erreur lors de la création du visuelle : " + e.getMessage(), e); }
        
		cardController.setDescriptionVisible(false);
		cardController.setDateVisible(false);

        cardPane.setOnMouseClicked(_ -> {
            this.selectedCard = card;
            cardPane.setScaleX(1.25);
		    cardPane.setScaleY(1.25);
        });

        cardPane.setOnMouseReleased(_ -> {
            this.selectedCard = null;
            cardPane.setScaleX(1);
		    cardPane.setScaleY(1);
        });

        cardPane.setOnDragDetected(e -> {
            this.selectedCard = card;
            // Dragboard db = cardPane.startDragAndDrop(TransferMode.MOVE);
            // ClipboardContent content = new ClipboardContent();
            // content.putString(card.title);
            // db.setContent(content);
            // db.setDragView(cardPane.snapshot(null, null));
            e.consume();
        });

        this.cardControllers.put(card, cardController);
        this.cardPanes.put(card, cardPane);
    }

    protected void setCardOnTimeline(Card card, boolean wellPlaced) {
        ControllerCarte controller = this.cardControllers.get(card);
        controller.setDateVisible(true);
        controller.setDescriptionVisible(wellPlaced);
		Pane cardPane = cardPanes.get(card);
		cardPane.setOnMouseClicked(_ -> {});
        cardPane.setOnMouseReleased(_ -> {});
        cardPane.setOnDragDetected(_ -> {});
    }

    protected Card drawCard(Player player) {
        Card card = player.hand.drawCard(this.deck.cards);
        if(card != null) {
            this.createCard(card);
        }
        return card;
    }
}