package app.util;

import java.util.Collections;

import app.model.Card;
import app.model.Deck;
import app.model.MainGame;

public class GameManager {
    private static GameManager instance;
    public static GameManager getInstance() {
        if(instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    private GameManager() {}

    /* -------------------------------------------------- */

    private String currentSaveName = null;

    // === Parametres de la partie ===
    private int nbJoueur = 1;
    private Integer tempsLimite = -1;
    private Deck deck = null;
    private int score = 0;

	// === Etat de la partie ===
    private int currentPlayer = 1;
    private boolean gameStarted = false;
    private MainGame currentGame = null;
    private String nomJ1 = "";
    private String nomJ2 = "";
    private String nomWin = "";

    /* ----- setter ----- */
    public void setCurrentSaveName(String name) {currentSaveName = name;}
    public void setNbJoueur(int nb)             { nbJoueur = nb; }
    public void setTempsLimite(Integer seconds) { tempsLimite = seconds; }
    public void setDeck(Deck deck_)             { deck = deck_; }
	public void setScore(int score)             { this.score = score; }
    public void setCurrentPlayer(int player)    { currentPlayer = player; }
    public void setGameStarted(boolean started) { gameStarted = started; }
    public void setCurrentGame(MainGame game)   { currentGame = game; }
    public void setNomJ1(String name)           { nomJ1 = name; }
    public void setNomJ2(String name)           { nomJ2 = name; }
	public void setNomWin(String nomWin)        { this.nomWin = nomWin; }
    
    /* ----- getter ----- */
    public String getCurrentSaveName()  {return currentSaveName;}
    public boolean isTimedMode()        { return tempsLimite != null; }
    public int getNbJoueur()            { return nbJoueur; }
    public int getScore()               {return score;}
    public int getTempsLimite()         { return tempsLimite; }
    public int getCurrentPlayer()       { return currentPlayer; }
    public boolean isGameStarted()      { return gameStarted; }
    public MainGame getCurrentGame()    { return currentGame; }
    public String getNomJ1()            { return nomJ1; }
    public String getNomJ2()            { return nomJ2; }
    public String getNomWin()           {return nomWin;}
	
    /* ----- utilitaire ----- */
    public void reset() {
    	nbJoueur = 1;
        tempsLimite = -1;
        deck = null;
        currentPlayer = 1;
        gameStarted = false;
        currentGame = null;
        nomJ1 = "";
        nomJ2 = "";
    }

	public Deck getDeck() {
		Deck gameDeck = new Deck("","");
        gameDeck.setTitle(deck.getTitle());
        gameDeck.setImageUrl(deck.getImageUrl());
		for(Card card : deck.getCards()) {
			gameDeck.addCard(card);
		}
		Collections.shuffle(gameDeck.getCards());
		return gameDeck;
	}
}