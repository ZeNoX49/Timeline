package app.controller.page;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerPagePlateau1J_base {   // extends PlateauJeu {
    /*
    ImageManager imageManager = ImageManager.getInstance();
    GameManager gameManager = GameManager.getInstance();
    
    // private MainGame game;
    private Player player;
    private Map<Card, ControllerCarte> cartes_controller = new HashMap<>();
    private Map<Card, Pane> cartes_pane = new HashMap<>();
    
    @FXML private HBox HBoxCards;
    
    @FXML private ScrollPane scrollPaneTimeline;
    @FXML private HBox hBoxTimeline;
    
    @FXML private Label labelScore;
    @FXML private Label labelPlayerName;
    @FXML private Label labelRemainingCards;
    @FXML private Label piocheCarteLabel;
    @FXML private StackPane spPile;
    // @FXML private Label resCoup;

    // private final Region placeholder = new Region();
    // private int currentDropIndex = -1;

    // {
    //     placeholder.setPrefSize(12, 160);
    //     placeholder.setStyle("-fx-background-color: #00ccff88; -fx-border-color: #005577; -fx-border-width: 2; -fx-background-radius: 5;");
    // }

    @FXML
    public void initialize() throws IOException {
        Deck deck = gameManager.getDeck();

        // créer la carte deck / pioche
        FXMLLoader loaderDeck = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
		Pane deckPane = loaderDeck.load();
		deckPane.setScaleX(1.25);
		deckPane.setScaleY(1.25);
		ControllerCarte controllerDeck = loaderDeck.getController();
		controllerDeck.setDeck(deck);
		spPile.getChildren().add(deckPane);

        // met un carte dans la timeline
        Card card = deck.getCards().remove(0);
        createCard(card);
        cardIsOnTimeline(card, true);
        hBoxTimeline.getChildren().add(cartes_pane.get(card));

        Hand.pile = deck.getCards();

        player = new Player(gameManager.getNomJ1());
        for(int i = 0; i < 4; i++) {
            piocherCarte();
        }
        piocheCarteLabel.setText(Integer.toString(Hand.pile.size()));
        labelRemainingCards.setText(Integer.toString(player.getHand().getNbCards()));
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
        // piocheCarteLabel.setText(game.getDeck().getCards().size() + "");
    }

    private void piocherCarte() throws IOException {
        Card card = player.drawCard();

        createCard(card);

        HBoxCards.getChildren().add(cartes_pane.get(card));
    }

    private void createCard(Card card) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
		Pane cardPane = loader.load();
		ControllerCarte controller = loader.getController();
		controller.setCard(card);
		controller.setDescriptionVisible(false);
		controller.setDateVisible(false);

		cardPane.setOnMouseClicked(_ -> {
            Hand.setSelectedCard(card);
            // cardPane.setScaleX(1.25);
		    // cardPane.setScaleY(1.25);
        });

        cardPane.setOnDragDetected(event -> {
            Hand.setSelectedCard(card);
            javafx.scene.input.Dragboard db = cardPane.startDragAndDrop(javafx.scene.input.TransferMode.MOVE);
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(card.getTitle());
            db.setContent(content);
            db.setDragView(cardPane.snapshot(null, null));
            // event.consume();
        });

        cartes_controller.put(card, controller);
        cartes_pane.put(card, cardPane);
    }

    private void cardIsOnTimeline(Card card, boolean wellPlaced) throws IOException {
        ControllerCarte controller  = cartes_controller.get(card);
        controller.setDateVisible(true);
        controller.setDescriptionVisible(wellPlaced);
		Pane cardPane = cartes_pane.get(card);
		cardPane.setOnMouseClicked(_ -> {});
        cardPane.setOnDragDetected(_ -> {});
    }

    // private void updateUI(Pane pane)   // récupère le pane dans la hbox des cartes du joueur pour le mettre dans celle du jeu

    // private void updateUI() throws IOException {
    //     // Affiche la main
    // 	HBoxMainJ1.getChildren().clear();
    // 	List<Card> mainActuelle = joueur.getHand().getCards();
    // 	for (Card card : mainActuelle) {
    // 	    Pane cardPane = loadCardPane(card);
    // 	    setupHandDrag(cardPane, card);
    // 	    HBoxMainJ1.getChildren().add(cardPane);
    // 	}
    //     // Affiche la timeline
    //     updateTimelineUI();
    //     playerName.setText(joueur.getName());
    //     remainingCards.setText("Cartes en main : " + joueur.getHand().getCards().size());
    //     scoreLabel.setText("" + joueur.getScore());
    // }

    // private void updateTimelineUI() throws IOException {
    //     hBoxTimeline.getChildren().clear();
    //     List<Card> timeline = game.getTimeline();

    //     // Ajoute toutes les cartes a la timeline avec carte.fxml
    //     for (Card card : timeline) {
    //         Pane cardPane = loadCardPane(card);
    //         hBoxTimeline.getChildren().add(cardPane);
    //     }

    //     hBoxTimeline.setOnDragOver(event -> {
    //         if (event.getGestureSource() != hBoxTimeline && event.getDragboard().hasString()) {
    //             hBoxTimeline.getChildren().remove(placeholder);

    //             double mouseX = event.getX();
    //             int insertIndex = 0;
    //             boolean found = false;

    //             for (int i = 0; i < hBoxTimeline.getChildren().size(); i++) {
    //                 Node node = hBoxTimeline.getChildren().get(i);
    //                 double nodeStartX = node.getBoundsInParent().getMinX();
    //                 double nodeWidth = node.getBoundsInParent().getWidth();
    //                 if (mouseX < nodeStartX + nodeWidth / 2) {
    //                     insertIndex = i;
    //                     found = true;
    //                     break;
    //                 }
    //             }
    //             if (!found) {
    //                 insertIndex = hBoxTimeline.getChildren().size();
    //             }

    //             showPlaceholderAt(insertIndex);
    //             currentDropIndex = insertIndex;

    //             event.acceptTransferModes(javafx.scene.input.TransferMode.MOVE);
    //         }
    //         event.consume();
    //     });

    //     hBoxTimeline.setOnDragExited(event -> {
    //         hidePlaceholder();
    //         currentDropIndex = -1;
    //         event.consume();
    //     });

    //     hBoxTimeline.setOnDragDropped(event -> {
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
    //     hBoxTimeline.getChildren().remove(placeholder);
    //     int safeIndex = Math.min(index, hBoxTimeline.getChildren().size());
    //     hBoxTimeline.getChildren().add(safeIndex, placeholder);
    // }

    // private void hidePlaceholder() {
    //     hBoxTimeline.getChildren().remove(placeholder);
    // }

    // private Pane loadCardPane(Card card) throws IOException {
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carte.fxml"));
    //     Pane pane = loader.load();
    //     ControllerCarte controller = loader.getController();
    //     controller.setCard(card);
    //     return pane;
    // }

    // // private Pane loadCardPane(Card card) throws IOException {
    // //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carte.fxml"));
    // //     Pane pane = loader.load();
    // //     ControllerCarte controller = loader.getController();
    // //     controller.setCard(card);
    // //     return pane;
    // // }

    // private void moveCardFromHandToTimeline(String cardTitle, int index) throws IOException {
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

    //         hBoxTimeline.getChildren().add(index, paneToMove);

    //         remainingCards.setText("Cartes en main : " + joueur.getHand().getCards().size());
    //         scoreLabel.setText("" + joueur.getScore());
    //         piocheCarteLabel.setText(game.getDeck().getCards().size() + "");

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

    //         hBoxTimeline.getChildren().clear();
    //         for (Card sortedCard : game.getTimeline()) {
    //             Pane cardPane = loadCardPane(sortedCard);
    //             hBoxTimeline.getChildren().add(cardPane);
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
    //     resCoup.setText(message);
    //     resCoup.setStyle("-fx-text-fill: " + color.toString().replace("0x", "#")); 
    //     resCoup.getStyleClass().clear();
    //     resCoup.getStyleClass().add(styleClass);

    //     // Animation d'agrandissement pour attirer l'attention
    //     ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), resCoup);
    //     scaleTransition.setByX(1.1);
    //     scaleTransition.setByY(1.1);
    //     scaleTransition.setCycleCount(2);
    //     scaleTransition.setAutoReverse(true);

    //     // Animation de fondu
    //     FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), resCoup);
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

    // private void showWinScene() throws IOException {
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
    void QuitGame(ActionEvent event) throws IOException {
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
    */
}
