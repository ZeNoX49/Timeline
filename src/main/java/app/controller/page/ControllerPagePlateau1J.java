package app.controller.page;

import java.io.IOException;
import java.util.List;

import app.controller.card.ControllerCarte;
import app.model.Card;
import app.model.Deck;
import app.model.Player;
import app.util.ImageManager;
import app.util.SingletonRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ControllerPagePlateau1J extends PlateauJeu {
    private static final ImageManager I_M = SingletonRegistry.get(ImageManager.class, ImageManager::new);
    
    @FXML private HBox hTimeline;
    @FXML private HBox hCards;
    @FXML private StackPane spPile;
    @FXML private Label lScore;
    @FXML private Label lPlayerName;
    @FXML private Label lRemainingCards;
    @FXML private Label lRemainingPioche;
    @FXML private Label lResultCoup;

    // private final Region placeholder = new Region();
    // private int currentDropIndex = -1;

    // {
    //     placeholder.setPrefSize(12, 160);
    //     placeholder.setStyle("-fx-background-color: #00ccff88; -fx-border-color: #005577; -fx-border-width: 2; -fx-background-radius: 5;");
    // }

    // private MainGame game;
    private final Player player;

    public ControllerPagePlateau1J(Player player, Deck deck, Time time) {
        super(deck, time);
        this.player = player;
    }

    @FXML
    private void initialize() {
        this.lPlayerName.setText(this.player.name);

        // créer la carte deck / pioche
        Pane deckPane;
        try {
            FXMLLoader loaderDeck = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
            ControllerCarte controllerDeck = new ControllerCarte(super.deck);
            loaderDeck.setController(controllerDeck);
            deckPane = loaderDeck.load();
        } catch(IOException e) { throw new Error("Erreur lors de la création du visuelle : " + e.getMessage(), e); }
		deckPane.setScaleX(1.25);
		deckPane.setScaleY(1.25);
		this.spPile.getChildren().add(deckPane);

        // met un carte dans la timeline
        Card card = super.deck.cards.remove(0);
        super.createCard(card);
        super.setCardOnTimeline(card, true);
        this.hTimeline.getChildren().add(super.cardPanes.get(card));


        /* =============================================================================================================================== */

        for(int i = 0; i < 4; i++) {
            this.drawCard();
        }
        this.lRemainingPioche.setText(Integer.toString(super.deck.cards.size()));
        this.lRemainingCards.setText(Integer.toString(this.player.hand.getNbCards()));

        // updateUI();

        // game = gameManager.getCurrentGame();
        // if (game == null) {
        //     String nomJoueur1 = gameManager.getNomJ1();
        //     game = new MainGame(nomJoueur1);
        //     gameManager.setCurrentGame(game);
        // }
        // joueur = game.getPlayer1();

        // if (game.getTimeline().isEmpty()) {
        //     // Card premiereCarte = game.getDeck().drawCard();
        //     // if (premiereCarte != null) {
        //     //     game.getTimeline().add(premiereCarte);
        //     // }
        // }
        // updateUI();
        // lRemainingPioche.setText(game.getDeck().getCards().size() + "");

        // while (this.player.hand.stillHasCard()) {
        //     // TODO
        // }
    }

    private void drawCard() {
        Card card = super.drawCard(this.player);
        if(card != null) {
            this.hCards.getChildren().add(super.cardPanes.get(card));
            // TODO ? : this.setupHandDrag(card);
        }
    }

    private void updateUI() {
        updateTimelineUI();
        remainingCards.setText("Cartes en main : " + joueur.getHand().getCards().size());
        scoreLabel.setText("" + joueur.getScore());
    }

    // private void updateTimelineUI() {
    //     hTimeline.getChildren().clear();
    //     List<Card> timeline = game.getTimeline();

    //     // Ajoute toutes les cartes a la timeline avec carte.fxml
    //     for (Card card : timeline) {
    //         Pane cardPane = loadCardPane(card);
    //         hTimeline.getChildren().add(cardPane);
    //     }

    //     hTimeline.setOnDragOver(event -> {
    //         if (event.getGestureSource() != hTimeline && event.getDragboard().hasString()) {
    //             hTimeline.getChildren().remove(placeholder);

    //             double mouseX = event.getX();
    //             int insertIndex = 0;
    //             boolean found = false;

    //             for (int i = 0; i < hTimeline.getChildren().size(); i++) {
    //                 Node node = hTimeline.getChildren().get(i);
    //                 double nodeStartX = node.getBoundsInParent().getMinX();
    //                 double nodeWidth = node.getBoundsInParent().getWidth();
    //                 if (mouseX < nodeStartX + nodeWidth / 2) {
    //                     insertIndex = i;
    //                     found = true;
    //                     break;
    //                 }
    //             }
    //             if (!found) {
    //                 insertIndex = hTimeline.getChildren().size();
    //             }

    //             showPlaceholderAt(insertIndex);
    //             currentDropIndex = insertIndex;

    //             event.acceptTransferModes(javafx.scene.input.TransferMode.MOVE);
    //         }
    //         event.consume();
    //     });

    //     hTimeline.setOnDragExited(event -> {
    //         hidePlaceholder();
    //         currentDropIndex = -1;
    //         event.consume();
    //     });

    //     hTimeline.setOnDragDropped(event -> {
    //         javafx.scene.input.Dragboard db = event.getDragboard();
    //         if (db.hasString() && currentDropIndex >= 0) {
    //             String cardTitle = db.getString();
    //             try {
    //                 moveCardFromHandToTimeline(cardTitle, currentDropIndex);
    //             } catch (IOException e) {
    //                 e.printStackTrace();
    //             }
    //             event.setDropCompleted(true);
    //             hidePlaceholder();
    //         } else {
    //             event.setDropCompleted(false);
    //         }
    //         currentDropIndex = -1;
    //         event.consume();
    //     });
    // }

    // private void showPlaceholderAt(int index) {
    //     hTimeline.getChildren().remove(placeholder);
    //     int safeIndex = Math.min(index, hTimeline.getChildren().size());
    //     hTimeline.getChildren().add(safeIndex, placeholder);
    // }

    // private void hidePlaceholder() {
    //     hTimeline.getChildren().remove(placeholder);
    // }

    // private Pane loadCardPane(Card card) {
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carte.fxml"));
    //     Pane pane = loader.load();
    //     ControllerCarte controller = loader.getController();
    //     controller.setCard(card);
    //     return pane;
    // }

    // // private Pane loadCardPane(Card card) {
    // //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carte.fxml"));
    // //     Pane pane = loader.load();
    // //     ControllerCarte controller = loader.getController();
    // //     controller.setCard(card);
    // //     return pane;
    // // }

    // private void moveCardFromHandToTimeline(String cardTitle, int index) {
    //     Pane paneToMove = null;
    //     Card cardToMove = null;
    //     for (int i = 0; i < joueur.getHand().getCards().size(); i++) {
    //         Card card = joueur.getHand().getCards().get(i);
    //         if (card.getTitle().equals(cardTitle)) {
    //             cardToMove = card;
    //             paneToMove = (Pane) HBoxMainJ1.getChildren().get(i);
    //             break;
    //         }
    //     }
    //     if (cardToMove != null && paneToMove != null) {
    //         joueur.getHand().getCards().remove(cardToMove);
    //         game.getTimeline().add(index, cardToMove);

    //         HBoxMainJ1.getChildren().remove(paneToMove);

    //         hTimeline.getChildren().add(index, paneToMove);

    //         remainingCards.setText("Cartes en main : " + joueur.getHand().getCards().size());
    //         scoreLabel.setText("" + joueur.getScore());
    //         lRemainingPioche.setText(game.getDeck().getCards().size() + "");

    //         boolean isCorrect = isTimelineSorted();
    //         animateCardPlacement(paneToMove, isCorrect);

    //         if (isCorrect) {
    //             joueur.addPoints(2);
    //             animatePointsChange(2);  // Animation des points
    //             showFeedbackMessage("Carte bien placee", "titleresultat1", Color.GREEN);
    //         } else {
    //             joueur.addPoints(-1);
    //             animatePointsChange(-1);  // Animation des points
    //             showFeedbackMessage("Carte mal placee", "titleresultat2", Color.RED);
    //             piocherCarte();
    //         }

    //         Collections.sort(game.getTimeline(), (card1, card2) -> Integer.compare(card1.getDateAsInt(), card2.getDateAsInt()));

    //         hTimeline.getChildren().clear();
    //         for (Card sortedCard : game.getTimeline()) {
    //             Pane cardPane = loadCardPane(sortedCard);
    //             hTimeline.getChildren().add(cardPane);
    //         }

    //         remainingCards.setText("" + joueur.getHand().getCards().size());
    //         scoreLabel.setText("" + joueur.getScore());

    //         if (joueur.getHand().getCards().isEmpty()) {
    //         	System.out.println("Score : "+(scoreLabel.getText()));
    //         	gameManager.setScore(Integer.parseInt(scoreLabel.getText()));
    //         	System.out.println("Score : "+gameManager.getScore());
    //         	gameManager.setNomWin(gameManager.getNomJ1());
    //         	showWinScene();
    //         }
    //     }
    // }

    // private void showFeedbackMessage(String message, String styleClass, Color color) {
    //     lResCoup.setText(message);
    //     lResCoup.setStyle("-fx-text-fill: " + color.toString().replace("0x", "#")); 
    //     lResCoup.getStyleClass().clear();
    //     lResCoup.getStyleClass().add(styleClass);

    //     // Animation d'agrandissement pour attirer l'attention
    //     ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), lResCoup);
    //     scaleTransition.setByX(1.1);
    //     scaleTransition.setByY(1.1);
    //     scaleTransition.setCycleCount(2);
    //     scaleTransition.setAutoReverse(true);

    //     // Animation de fondu
    //     FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), lResCoup);
    //     fadeTransition.setFromValue(0);
    //     fadeTransition.setToValue(1);
    //     fadeTransition.setCycleCount(1);

    //     // Execution simultanee des animations
    //     scaleTransition.play();
    //     fadeTransition.play();
    // }

    // private void animatePointsChange(int points) {
    //     ScaleTransition scale = new ScaleTransition(Duration.millis(500));
    //     scale.setNode(scoreLabel);
    //     scale.setByX(1.2);
    //     scale.setByY(1.2);
    //     scale.setCycleCount(2);
    //     scale.setAutoReverse(true);
    //     scale.play();

    //     scoreLabel.setText("Score : " + joueur.getScore());
    // }
    
    // private void animateCardPlacement(Pane cardPane, boolean isCorrect) {
    //     if (isCorrect) {
    //         ScaleTransition scale = new ScaleTransition(Duration.millis(500), cardPane);
    //         scale.setByX(1.1);
    //         scale.setByY(1.1);
    //         scale.setCycleCount(2);
    //         scale.setAutoReverse(true);
    //         scale.play();

    //         cardPane.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-background-color: lightgreen;");
    //         scale.setOnFinished(event -> cardPane.setStyle("-fx-border-color: #000000; -fx-border-width: 1; -fx-background-color: transparent;"));
    //     } else {
    //         TranslateTransition shake = new TranslateTransition(Duration.millis(300), cardPane);
    //         shake.setByX(10);
    //         shake.setCycleCount(6);
    //         shake.setAutoReverse(true);
    //         shake.play();

    //         cardPane.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: #ffcccc;");
    //         shake.setOnFinished(event -> cardPane.setStyle("-fx-border-color: #000000; -fx-border-width: 1; -fx-background-color: transparent;"));
    //     }
    // }

    // public boolean isTimelineSorted() {
    //     List<Card> timelineCards = game.getTimeline();
    //     for (int i = 1; i < timelineCards.size(); i++) {
    //         int prevDate = timelineCards.get(i - 1).getDateAsInt();
    //         int currentDate = timelineCards.get(i).getDateAsInt();

    //         if (currentDate < prevDate) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // private void showWinScene() {
    //     // Charger la sc�ne WinGame.fxml
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WinGame.fxml"));
    //     Parent root = loader.load();
    //     Scene winScene = new Scene(root);

    //     Stage winStage = new Stage();
    //     winStage.setTitle("Victoire");
    //     winStage.setScene(winScene);

    //     winStage.setResizable(false); 
    //     winStage.centerOnScreen();

    //     PauseTransition pause = new PauseTransition(Duration.seconds(2));
    //     pause.setOnFinished(event -> {
    //         Platform.runLater(() -> {
    //             winStage.initModality(Modality.APPLICATION_MODAL);
    //             winStage.showAndWait();
    //         });
    //     });
    //     pause.play();
    // }
    
    @FXML
    void QuitGame(ActionEvent event) {
        // gameManager.setCurrentGame(game);
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuitGame.fxml"));
        // Parent root = loader.load();
        // ControllerModalQuitterPartie controller = loader.getController();

        // Stage modalStage = new Stage();
        // modalStage.setTitle("Quitter ?");
        // modalStage.initModality(Modality.WINDOW_MODAL);
        // modalStage.initOwner(((Node)event.getSource()).getScene().getWindow());
        // modalStage.setResizable(false);
        // modalStage.setScene(new Scene(root));
        // modalStage.showAndWait();

        // if (controller.isConfirmed()) {
        //     Main.switchPage("accueil.fxml");
        // }
    }
}
