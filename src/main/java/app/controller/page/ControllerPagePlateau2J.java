package app.controller.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Main;
import app.controller.card.ControllerCarte;
import app.model.Card;
import app.model.Deck;
import app.model.Hand;
import app.model.Player;
import app.util.GameManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ControllerPagePlateau2J extends PlateauJeu {
    
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Map<Card, ControllerCarte> cartes_controller = new HashMap<>();
    private Map<Card, Pane> cartes_pane = new HashMap<>();
    
    @FXML private Label labelScore1;
    @FXML private Label labelScore2;
    @FXML private Label labelPlayerName;
    @FXML private StackPane spPile;

    @FXML
    public void initialize() throws IOException {
        // Initialiser le placeholder
        initializePlaceholder();
        
        Deck deck = gameManager.getDeck();

        // Créer la carte deck / pioche
        FXMLLoader loaderDeck = new FXMLLoader(getClass().getResource("/fxml/card/carte.fxml"));
        Pane deckPane = loaderDeck.load();
        deckPane.setScaleX(1.25);
        deckPane.setScaleY(1.25);
        ControllerCarte controllerDeck = loaderDeck.getController();
        controllerDeck.setDeck(deck);
        spPile.getChildren().add(deckPane);

        // Met une carte dans la timeline
        Card card = deck.getCards().remove(0);
        createCard(card);
        cardIsOnTimeline(card, true);
        hBoxTimeline.getChildren().add(cartes_pane.get(card));

        Hand.pile = deck.getCards();

        // Initialiser les deux joueurs
        player1 = new Player(gameManager.getNomJ1());
        player2 = new Player(gameManager.getNomJ2());
        currentPlayer = player1; // Joueur 1 commence
        
        // Distribuer 4 cartes à chaque joueur
        for(int i = 0; i < 4; i++) {
            player1.drawCard();
            player2.drawCard();
        }
        
        // Afficher les cartes du joueur 1 (qui commence)
        displayCurrentPlayerCards();
        
        piocheCarteLabel.setText(Integer.toString(Hand.pile.size()));
        updateUI();
        
        // Configuration du drag and drop sur la timeline
        setupTimelineDragAndDrop();
    }

    private void displayCurrentPlayerCards() throws IOException {
        HBoxCards.getChildren().clear();
        
        for (Card card : currentPlayer.getHand().getCards()) {
            // Créer la carte si elle n'existe pas encore
            if (!cartes_pane.containsKey(card)) {
                createCard(card);
            }
            
            Pane cardPane = cartes_pane.get(card);
            HBoxCards.getChildren().add(cardPane);
            
            // Animation d'apparition
            animateCardAppearance(cardPane);
        }
    }

    private void switchPlayer() throws IOException {
        // Changer de joueur
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        
        // Mettre à jour l'affichage
        displayCurrentPlayerCards();
        updateUI();
        
        // Message de changement de joueur
        showFeedback("Au tour de " + currentPlayer.getName(), Color.CYAN);
    }

    @Override
    protected Map<Card, ControllerCarte> getCartesController() {
        return cartes_controller;
    }

    @Override
    protected Map<Card, Pane> getCartesPane() {
        return cartes_pane;
    }

    @Override
    protected void handleCardDropped(Card card, int index) throws IOException {
        Pane cardPane = cartes_pane.get(card);
        
        // Retirer de la main du joueur actuel
        currentPlayer.getHand().removeCard(card);
        HBoxCards.getChildren().remove(cardPane);
        
        // Vérifier si la carte est bien placée
        boolean isCorrectlyPlaced = checkIfCardIsWellPlaced(card, index);
        
        // Configurer la carte pour la timeline
        cardIsOnTimeline(card, isCorrectlyPlaced);
        
        // Ajouter à la timeline avec animation
        addCardToTimelineWithAnimation(cardPane, index);
        
        // Gérer les points et le feedback
        if (isCorrectlyPlaced) {
            currentPlayer.incPoints();
            currentPlayer.incPoints();
            showFeedback("Bien placé ! +2 points", Color.GREEN);
            animateCardSuccess(cardPane);
        } else {
            currentPlayer.decPoints();
            showFeedback("Mal placé ! -1 point", Color.RED);
            animateCardError(cardPane);
            
            // Piocher une carte de pénalité
            if (!Hand.pile.isEmpty()) {
                Card penaltyCard = currentPlayer.drawCard();
                if (!cartes_pane.containsKey(penaltyCard)) {
                    createCard(penaltyCard);
                }
            }
        }
        
        // Mettre à jour l'UI
        updateUI();
        
        // Vérifier si le joueur a gagné
        if (currentPlayer.getHand().getNbCards() == 0) {
            showWinMessage();
            return;
        }
        
        // Passer au joueur suivant après 1.5 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            try {
                switchPlayer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    private void showWinMessage() {
        String winner = currentPlayer.getName();
        int score = currentPlayer.getScore();
        
        gameManager.setScore(score);
        gameManager.setNomWin(winner);
        
        showFeedback(winner + " a gagné avec " + score + " points !", Color.GOLD);
        
        // TODO: Afficher l'écran de victoire après 2 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(_ -> {
            System.out.println(winner + " a gagné ! Score : " + score);
            System.out.println("Score J1 : " + player1.getScore() + " | Score J2 : " + player2.getScore());
        });
        pause.play();
    }

    private void updateUI() {
        labelPlayerName.setText(currentPlayer.getName());
        labelRemainingCards.setText(Integer.toString(currentPlayer.getHand().getNbCards()));
        labelScore1.setText(Integer.toString(player1.getScore()));
        labelScore2.setText(Integer.toString(player2.getScore()));
        piocheCarteLabel.setText(Integer.toString(Hand.pile.size()));
    }

    @FXML
    void QuitGame(ActionEvent event) throws IOException {
        Main.loadModalPage("quitterPartie.fxml", true);
    }
}