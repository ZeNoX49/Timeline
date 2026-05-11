package app.controller.page;

import app.model.MainGame;
import app.model.Player;
import app.util.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ControllerPagePlateau2J_base {   // extends PlateauJeu {
    /*
    GameManager gameManager = GameManager.getInstance();

    private MainGame game;
    private Player joueur1;
    private Player joueur2;
    private Player joueurActuel;
    private boolean isJoueur1Turn = true;

    @FXML private HBox HBoxMainJ1;
    @FXML private HBox hBoxTimeline;

    @FXML private Label piocheCarteLabel;
    @FXML private Label playerName;
    @FXML private Label remainingCards;
    @FXML private Label scoreLabel1;
    @FXML private Label scoreLabel2;

    // private final Region placeholder = new Region();
    // private int currentDropIndex = -1;

    // {
    //     placeholder.setPrefSize(12, 160);
    //     placeholder.setStyle("-fx-background-color: #00ccff88; -fx-border-color: #005577; -fx-border-width: 2; -fx-background-radius: 5;");
    // }

    // @FXML
    // public void initialize() throws IOException {
    //     game = gameManager.getCurrentGame();
    //     if (game == null) {
    //         String nomJ1 = gameManager.getNomJ1();
    //         String nomJ2 = gameManager.getNomJ2();
    //         game = new MainGame(nomJ1, nomJ2);
    //         gameManager.setCurrentGame(game);
    //     }
    //     joueur1 = game.getPlayer1();
    //     joueur2 = game.getPlayer2();
    //     joueurActuel = joueur1;

    //     if (game.getTimeline().isEmpty()) {
    //         // Card premiereCarte = game.getDeck().drawCard();
    //         // if (premiereCarte != null) {
    //         //     game.getTimeline().add(premiereCarte);
    //         // }
    //     }
    //     updateUI();
    // }

    // private void updateUI() throws IOException {
    //     HBoxMainJ1.getChildren().clear();
    //     List<Card> mainActuelle = joueurActuel.getHand().getCards();
    //     for (Card card : mainActuelle) {
    //         Pane cardPane = loadCardPane(card);
    //         setupHandDrag(cardPane, card);
    //         HBoxMainJ1.getChildren().add(cardPane);
    //     }
    //     updateTimelineUI();

    //     playerName.setText(joueurActuel.getName());
    //     remainingCards.setText("Cartes en main : " + mainActuelle.size());
    //     scoreLabel1.setText("" + joueur1.getScore());
    //     scoreLabel2.setText("" + joueur2.getScore());
    //     piocheCarteLabel.setText("" + game.getDeck().getCards().size());
    // }

    // private void setupHandDrag(Pane cardPane, Card card) {
    //     cardPane.setOnDragDetected(event -> {
    //         var db = cardPane.startDragAndDrop(javafx.scene.input.TransferMode.MOVE);
    //         var content = new javafx.scene.input.ClipboardContent();
    //         content.putString(card.getTitle());
    //         db.setContent(content);
    //         db.setDragView(cardPane.snapshot(null, null));
    //         event.consume();
    //     });
    // }

    // private void updateTimelineUI() throws IOException {
    //     hBoxTimeline.getChildren().clear();
    //     List<Card> timeline = game.getTimeline();

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
    //         var db = event.getDragboard();
    //         if (db.hasString() && currentDropIndex >= 0) {
    //             try {
    //                 moveCardFromHandToTimeline(db.getString(), currentDropIndex);
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
    //     Card cardToMove = null;
    //     for (Card card : joueurActuel.getHand().getCards()) {
    //         if (card.getTitle().equals(cardTitle)) {
    //             cardToMove = card;
    //             break;
    //         }
    //     }
    //     if (cardToMove != null) {
    //         joueurActuel.getHand().getCards().remove(cardToMove);
    //         game.getTimeline().add(index, cardToMove);

    //         boolean isCorrect = isTimelineSorted();
    //         if (isCorrect) {
    //             joueurActuel.addPoints(2);
    //         } else {
    //             joueurActuel.addPoints(-1);
    //             piocherCarte();
    //         }

    //         Collections.sort(game.getTimeline(), (a, b) -> Integer.compare(a.getDateAsInt(), b.getDateAsInt()));
    //         updateUI();

    //         if (joueurActuel.getHand().getCards().isEmpty()) {
    //             gameManager.setScore(joueurActuel.getScore());
    //             gameManager.setNomWin(joueurActuel.getName());
    //             showWinScene();
    //         } else {
    //             isJoueur1Turn = !isJoueur1Turn;
    //             joueurActuel = isJoueur1Turn ? joueur1 : joueur2;
    //             updateUI();
    //         }
    //     }
    // }

    // private boolean isTimelineSorted() {
    //     List<Card> timeline = game.getTimeline();
    //     for (int i = 1; i < timeline.size(); i++) {
    //         if (timeline.get(i).getDateAsInt() < timeline.get(i - 1).getDateAsInt()) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // private void piocherCarte() throws IOException {
    //     // if (game.getDeck().hasMoreCards()) {
    //     //     Card newCard = game.getDeck().drawCard();
    //     //     joueurActuel.addInHandCard(newCard);
    //     // }
    // }

    // private void showWinScene() throws IOException {
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WinGame.fxml"));
    //     Parent root = loader.load();
    //     Scene winScene = new Scene(root);
    //     Stage winStage = new Stage();
    //     winStage.setTitle("Victoire");
    //     winStage.setScene(winScene);
    //     winStage.setResizable(false);
    //     winStage.centerOnScreen();

    //     PauseTransition pause = new PauseTransition(Duration.seconds(2));
    //     pause.setOnFinished(event -> Platform.runLater(() -> {
    //         winStage.initModality(Modality.APPLICATION_MODAL);
    //         winStage.showAndWait();
    //     }));
    //     pause.play();
    // }

    // @FXML
    // void QuitGame(ActionEvent event) throws IOException {
    //     gameManager.setCurrentGame(game);
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuitGame.fxml"));
    //     Parent root = loader.load();
    //     ControllerModalQuitterPartie controller = loader.getController();

    //     Stage modalStage = new Stage();
    //     modalStage.setTitle("Quitter ?");
    //     modalStage.initModality(Modality.WINDOW_MODAL);
    //     modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
    //     modalStage.setResizable(false);
    //     modalStage.setScene(new Scene(root));
    //     modalStage.showAndWait();

    //     // if (controller.isConfirmed()) {
    //     //     Main.switchPage("accueil.fxml");
    //     // }
    // }
    */
}