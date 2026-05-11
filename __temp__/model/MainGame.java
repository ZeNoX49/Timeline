package app.model;

public class MainGame {
	// GameManager gameManager = GameManager.getInstance();
	
	// private int nbJoueur;
	// private boolean isTimedMode;
	// private int timeLimitSeconds;

	// private static final int INITIAL_NB_CARDS = 4;
	// private Player player1;
	// private Player player2;
	// private Deck deck;
	// private List<Card> timeline;
	
	// public MainGame() {
	// 	setupGame();
	// }
	
	// public MainGame(String playerName) {
	//     this.nbJoueur = 1;
	//     this.isTimedMode = false; 
	//     this.timeLimitSeconds = 0;
	//     timeline = new ArrayList<>();

	//     player1 = new Player(playerName);
	//     deck = gameManager.getDeck();

	//     // Distribution initiale des cartes
	//     for (int i = 0; i < INITIAL_NB_CARDS; i++) {
	//         // Card c = deck.drawCard();
	//         // if (c != null) {
	//         //     player1.addInHandCard(c);
	//         // } else {
	//         //     System.out.println("Deck vide ou carte nulle !");
	//         // }
	//     }
	// }
	
	// public MainGame(String nomJ1, String nomJ2) {
	//     this.nbJoueur = 2;
	//     this.isTimedMode = false;
	//     this.timeLimitSeconds = 0;
	//     timeline = new ArrayList<>();

	//     player1 = new Player(nomJ1);
	//     player2 = new Player(nomJ2);
	//     deck = gameManager.getDeck();

	//     // Distribution initiale des cartes aux deux joueurs
	//     for (int i = 0; i < INITIAL_NB_CARDS; i++) {
	//         // Card c1 = deck.drawCard();
	//         // if (c1 != null) {
	//         //     player1.addInHandCard(c1);
	//         // } else {
	//         //     System.out.println("Deck vide ou carte nulle !");
	//         // }

	//         // Card c2 = deck.drawCard();
	//         // if (c2 != null) {
	//         //     player2.addInHandCard(c2);
	//         // } else {
	//         //     System.out.println("Deck vide ou carte nulle !");
	//         // }
	//     }
	// }
	
	// public List<Card> getTimeline() {
	//     return timeline;
	// }
	
	// public Hand getPlayerHand() {
	// 	return player1.getHand();
	// }
	
	// public Player getPlayer1() {
	// 	return player1;
	// }
	
	// public Player getPlayer2() {
	// 	return player2;
	// }

	// public Deck getDeck() {
	// 	return deck;
	// }
	
	// public int getNbJoueur() { return nbJoueur; }
	// public void setMultiplayer(int nb) { nbJoueur = nb; }

	// public boolean isTimedMode() { return isTimedMode; }
	// public void setTimedMode(boolean timedMode) { this.isTimedMode = timedMode; }

	// public int getTimeLimitSeconds() { return timeLimitSeconds; }
	// public void setTimeLimitSeconds(int seconds) { this.timeLimitSeconds = seconds; }

	// public String getDeckName() { return deck.getTitle(); }
	// public void setTimeline(List<Card> newTimeline) { timeline = newTimeline;}

	// private void setupGame() {
	//     player1 = new Player("Joueur 1");
	//     deck = new Deck("", "");
	//     for (int i = 0; i < INITIAL_NB_CARDS; i++) {
	//         // Card c = deck.drawCard();
	//         // if (c != null) {
	//         //     player1.addInHandCard(c);
	//         // } else {
	//         //     System.out.println("Deck vide ou carte nulle !");
	//         // }
	//     }
	// }

}