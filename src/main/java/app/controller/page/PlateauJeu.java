package app.controller.page;

import java.io.IOException;
import java.util.Map;

import app.controller.card.ControllerCarte;
import app.model.Card;
import app.model.Hand;
import app.util.GameManager;
import app.util.ImageManager;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

/**
 * Classe abstraite mère pour les plateaux de jeu (1 joueur et 2 joueurs)
 * Contient toute la logique commune de gestion des cartes, du drag and drop, et des animations
 */
public abstract class PlateauJeu {
    
    protected ImageManager imageManager = ImageManager.getInstance();
    protected GameManager gameManager = GameManager.getInstance();
    
    // Éléments FXML communs
    @FXML protected HBox HBoxCards;
    @FXML protected ScrollPane scrollPaneTimeline;
    @FXML protected HBox hBoxTimeline;
    @FXML protected Label labelRemainingCards;
    @FXML protected Label piocheCarteLabel;
    @FXML protected Label resCoup;
    
    // Placeholder pour le drag and drop
    protected final Region placeholder = new Region();
    protected int currentDropIndex = -1;
    
    // Maps pour stocker les cartes
    protected abstract Map<Card, ControllerCarte> getCartesController();
    protected abstract Map<Card, Pane> getCartesPane();
    
    /**
     * Initialise le placeholder avec un style par défaut
     */
    protected void initializePlaceholder() {
        placeholder.setPrefSize(160, 200);
        placeholder.setStyle(
            "-fx-background-color: rgba(100, 200, 255, 0.3);" +
            "-fx-border-color: #4A90E2;" +
            "-fx-border-width: 3;" +
            "-fx-border-style: dashed;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(74, 144, 226, 0.6), 10, 0, 0, 0);"
        );
    }
    
    /**
     * Crée une carte avec tous ses événements et animations
     */
    protected void createCard(Card card) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        Pane cardPane = loader.load();
        ControllerCarte controller = loader.getController();
        controller.setCard(card);
        controller.setDescriptionVisible(false);
        controller.setDateVisible(false);

        // Effet hover
        setupCardHoverEffect(cardPane);
        
        // Configuration du drag
        setupCardDrag(cardPane, card);

        getCartesController().put(card, controller);
        getCartesPane().put(card, cardPane);
    }
    
    /**
     * Configure l'effet hover sur une carte
     */
    protected void setupCardHoverEffect(Pane cardPane) {
        cardPane.setOnMouseEntered(e -> {
            if (!cardPane.getStyleClass().contains("dragging")) {
                ScaleTransition hover = new ScaleTransition(Duration.millis(150), cardPane);
                hover.setToX(1.1);
                hover.setToY(1.1);
                hover.play();
                cardPane.setStyle("-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
            }
        });

        cardPane.setOnMouseExited(e -> {
            if (!cardPane.getStyleClass().contains("dragging")) {
                ScaleTransition hover = new ScaleTransition(Duration.millis(150), cardPane);
                hover.setToX(1.0);
                hover.setToY(1.0);
                hover.play();
                cardPane.setStyle("");
            }
        });
    }
    
    /**
     * Configure le drag and drop d'une carte
     */
    protected void setupCardDrag(Pane cardPane, Card card) {
        // Détection du début du drag
        cardPane.setOnDragDetected(event -> {
            Hand.setSelectedCard(card);
            
            // Démarrer le drag
            Dragboard db = cardPane.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("CARD:" + card.getTitle());
            db.setContent(content);

            // Créer une belle preview
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            params.setTransform(new Scale(0.85, 0.85));
            
            // Ajouter une ombre à la preview
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.color(0, 0, 0, 0.6));
            shadow.setOffsetX(8);
            shadow.setOffsetY(8);
            shadow.setRadius(20);
            cardPane.setEffect(shadow);
            
            db.setDragView(cardPane.snapshot(params, null));
            cardPane.setEffect(null);

            // Rendre la carte originale transparente
            cardPane.setOpacity(0.3);
            cardPane.getStyleClass().add("dragging");
            
            event.consume();
        });

        // Quand le drag se termine
        cardPane.setOnDragDone(event -> {
            cardPane.setOpacity(1.0);
            cardPane.setScaleX(1.0);
            cardPane.setScaleY(1.0);
            cardPane.getStyleClass().remove("dragging");
            cardPane.setStyle("");
            
            if (event.getTransferMode() == TransferMode.MOVE) {
                // Animation de sortie
                ScaleTransition fade = new ScaleTransition(Duration.millis(200), cardPane);
                fade.setToX(0);
                fade.setToY(0);
                fade.setOnFinished(e -> HBoxCards.getChildren().remove(cardPane));
                fade.play();
            }
            
            event.consume();
        });
    }
    
    /**
     * Configure le drag and drop sur la timeline
     */
    protected void setupTimelineDragAndDrop() {
        // Gestionnaire pour le ScrollPane
        scrollPaneTimeline.setOnDragOver(event -> {
            if (event.getGestureSource() != hBoxTimeline && event.getDragboard().hasString()) {
                hBoxTimeline.getChildren().remove(placeholder);

                double mouseX = event.getX() + scrollPaneTimeline.getHvalue() * 
                    (hBoxTimeline.getWidth() - scrollPaneTimeline.getViewportBounds().getWidth());
                int insertIndex = calculateInsertIndex(mouseX);

                showPlaceholderAt(insertIndex);
                currentDropIndex = insertIndex;

                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        
        hBoxTimeline.setOnDragOver(event -> {
            if (event.getGestureSource() != hBoxTimeline && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        hBoxTimeline.setOnDragExited(event -> {
            if (!scrollPaneTimeline.getBoundsInParent().contains(event.getX(), event.getY())) {
                hidePlaceholder();
                currentDropIndex = -1;
            }
            event.consume();
        });

        hBoxTimeline.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasString() && currentDropIndex >= 0) {
                String data = db.getString();
                if (data.startsWith("CARD:")) {
                    Card card = Hand.getSelectedCard();
                    
                    if (card != null) {
                        try {
                            handleCardDropped(card, currentDropIndex);
                            success = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            
            hidePlaceholder();
            currentDropIndex = -1;
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
    /**
     * Méthode abstraite à implémenter par les classes filles
     * Gère la logique spécifique quand une carte est déposée
     */
    protected abstract void handleCardDropped(Card card, int index) throws IOException;
    
    /**
     * Calcule l'index d'insertion pour le placeholder
     */
    protected int calculateInsertIndex(double mouseX) {
        int insertIndex = 0;
        boolean found = false;

        for (int i = 0; i < hBoxTimeline.getChildren().size(); i++) {
            Node node = hBoxTimeline.getChildren().get(i);
            if (node == placeholder) continue;
            
            double nodeStartX = node.getBoundsInParent().getMinX();
            double nodeWidth = node.getBoundsInParent().getWidth();
            
            if (mouseX < nodeStartX + nodeWidth / 2) {
                insertIndex = i;
                found = true;
                break;
            }
        }
        
        if (!found) {
            insertIndex = hBoxTimeline.getChildren().size();
        }
        
        return insertIndex;
    }
    
    /**
     * Affiche le placeholder à un index donné
     */
    protected void showPlaceholderAt(int index) {
        hBoxTimeline.getChildren().remove(placeholder);
        int safeIndex = Math.min(index, hBoxTimeline.getChildren().size());
        hBoxTimeline.getChildren().add(safeIndex, placeholder);
        
        // Animation du placeholder
        placeholder.setScaleX(0.8);
        placeholder.setScaleY(0.8);
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), placeholder);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
    }
    
    /**
     * Cache le placeholder
     */
    protected void hidePlaceholder() {
        hBoxTimeline.getChildren().remove(placeholder);
    }
    
    /**
     * Vérifie si une carte est bien placée chronologiquement
     */
    protected boolean checkIfCardIsWellPlaced(Card card, int index) {
        if (hBoxTimeline.getChildren().isEmpty()) {
            return true; // Première carte, toujours correcte
        }
        
        int cardDate = card.getDateAsInt();
        
        // Vérifier la carte avant
        if (index > 0) {
            Node nodeBefore = hBoxTimeline.getChildren().get(index - 1);
            if (nodeBefore != placeholder) {
                Card cardBefore = findCardByPane(nodeBefore);
                if (cardBefore != null && cardDate < cardBefore.getDateAsInt()) {
                    return false;
                }
            }
        }
        
        // Vérifier la carte après
        if (index < hBoxTimeline.getChildren().size()) {
            Node nodeAfter = hBoxTimeline.getChildren().get(index);
            if (nodeAfter != placeholder) {
                Card cardAfter = findCardByPane(nodeAfter);
                if (cardAfter != null && cardDate > cardAfter.getDateAsInt()) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Trouve une carte à partir de son Pane
     */
    protected Card findCardByPane(Node pane) {
        for (Map.Entry<Card, Pane> entry : getCartesPane().entrySet()) {
            if (entry.getValue() == pane) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    /**
     * Configure une carte pour qu'elle soit sur la timeline
     */
    protected void cardIsOnTimeline(Card card, boolean wellPlaced) throws IOException {
        ControllerCarte controller = getCartesController().get(card);
        controller.setDateVisible(true);
        controller.setDescriptionVisible(wellPlaced);
        
        Pane cardPane = getCartesPane().get(card);
        cardPane.setOnMouseClicked(null);
        cardPane.setOnDragDetected(null);
        cardPane.setOnMouseEntered(null);
        cardPane.setOnMouseExited(null);
        cardPane.setStyle("-fx-cursor: default;");
    }
    
    /**
     * Ajoute une carte à la timeline avec animation
     */
    protected void addCardToTimelineWithAnimation(Pane cardPane, int index) {
        hBoxTimeline.getChildren().add(index, cardPane);
        
        // Animation d'arrivée
        cardPane.setScaleX(0.7);
        cardPane.setScaleY(0.7);
        ScaleTransition arrival = new ScaleTransition(Duration.millis(300), cardPane);
        arrival.setToX(1.0);
        arrival.setToY(1.0);
        
        TranslateTransition bounce = new TranslateTransition(Duration.millis(200), cardPane);
        bounce.setByY(-20);
        bounce.setCycleCount(2);
        bounce.setAutoReverse(true);
        
        arrival.play();
        bounce.play();
    }
    
    /**
     * Animation pour une carte bien placée
     */
    protected void animateCardSuccess(Pane cardPane) {
        cardPane.setStyle("-fx-border-color: green; -fx-border-width: 3; -fx-background-color: rgba(0, 255, 0, 0.1);");
        
        PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
        pause.setOnFinished(e -> cardPane.setStyle(""));
        pause.play();
    }
    
    /**
     * Animation pour une carte mal placée
     */
    protected void animateCardError(Pane cardPane) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), cardPane);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
        
        cardPane.setStyle("-fx-border-color: red; -fx-border-width: 3; -fx-background-color: rgba(255, 0, 0, 0.1);");
        
        shake.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
            pause.setOnFinished(ev -> cardPane.setStyle(""));
            pause.play();
        });
    }
    
    /**
     * Affiche un message de feedback à l'écran
     */
    protected void showFeedback(String message, Color color) {
        if (resCoup == null) return;
        
        resCoup.setText(message);
        resCoup.setTextFill(color);
        resCoup.setOpacity(1.0);
        
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), resCoup);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
        
        FadeTransition fade = new FadeTransition(Duration.seconds(2), resCoup);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDelay(Duration.seconds(1));
        fade.play();
    }
    
    /**
     * Animation d'apparition d'une carte avec scale
     */
    protected void animateCardAppearance(Pane cardPane) {
        cardPane.setScaleX(0);
        cardPane.setScaleY(0);
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), cardPane);
        scale.setToX(1);
        scale.setToY(1);
        scale.play();
    }
    
    /**
     * Animation de disparition d'une carte
     */
    protected void animateCardDisappearance(Pane cardPane, Runnable onFinished) {
        ScaleTransition fade = new ScaleTransition(Duration.millis(200), cardPane);
        fade.setToX(0);
        fade.setToY(0);
        fade.setOnFinished(e -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        fade.play();
    }
}