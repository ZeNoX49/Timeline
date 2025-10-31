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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ControllerPagePlateau1J extends PlateauJeu {
    
    private Player player;
    private Map<Card, ControllerCarte> cartes_controller = new HashMap<>();
    private Map<Card, Pane> cartes_pane = new HashMap<>();
    
    @FXML private Label labelScore;
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

        player = new Player(gameManager.getNomJ1());
        for(int i = 0; i < 4; i++) {
            piocherCarte();
        }
        
        piocheCarteLabel.setText(Integer.toString(Hand.pile.size()));
        labelRemainingCards.setText(Integer.toString(player.getHand().getNbCards()));
        labelPlayerName.setText(player.getName());
        labelScore.setText("0");
        
        // Configuration du drag and drop sur la timeline
        setupTimelineDragAndDrop();
    }

    private void piocherCarte() throws IOException {
        Card card = player.drawCard();
        createCard(card);
        
        Pane cardPane = cartes_pane.get(card);
        HBoxCards.getChildren().add(cardPane);
        
        // Animation d'apparition
        animateCardAppearance(cardPane);
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
        
        // Retirer de la main du joueur
        player.getHand().removeCard(card);
        HBoxCards.getChildren().remove(cardPane);
        
        // Vérifier si la carte est bien placée
        boolean isCorrectlyPlaced = checkIfCardIsWellPlaced(card, index);
        
        // Configurer la carte pour la timeline
        cardIsOnTimeline(card, isCorrectlyPlaced);
        
        // Ajouter à la timeline avec animation
        addCardToTimelineWithAnimation(cardPane, index);
        
        // Gérer les points et le feedback
        if (isCorrectlyPlaced) {
            player.incPoints();
            player.incPoints();
            showFeedback("Bien placé ! +2 points", Color.GREEN);
            animateCardSuccess(cardPane);
        } else {
            player.decPoints();
            showFeedback("Mal placé ! -1 point", Color.RED);
            animateCardError(cardPane);
            
            // Piocher une carte de pénalité
            if (!Hand.pile.isEmpty()) {
                piocherCarte();
            }
        }
        
        // Mettre à jour l'UI
        updateUI();
        
        // Vérifier si le joueur a gagné
        if (player.getHand().getNbCards() == 0) {
            showWinMessage();
        }
    }

    private void showWinMessage() {
        gameManager.setScore(player.getScore());
        gameManager.setNomWin(player.getName());
        showFeedback("Victoire ! Score : " + player.getScore(), Color.GOLD);
        
        // TODO: Afficher l'écran de victoire après 2 secondes
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        pause.setOnFinished(_ -> {
            // Charger la scène de victoire
            System.out.println("Victoire ! Score : " + player.getScore());
        });
        pause.play();
    }

    private void updateUI() {
        labelPlayerName.setText(player.getName());
        labelRemainingCards.setText(Integer.toString(player.getHand().getNbCards()));
        labelScore.setText(Integer.toString(player.getScore()));
        piocheCarteLabel.setText(Integer.toString(Hand.pile.size()));
    }

    @FXML
    void QuitGame(ActionEvent event) throws IOException {
        Main.loadModalPage("quitterPartie.fxml", true);
    }
}