package app.model;

import java.io.Serializable;

public class Player implements Serializable {

    public final String name;
    public final Hand hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.score = 0;
    }

    public void incPoints() {
        this.score++;
    }

    public void decPoints() {
        this.score--;
    }

    public int getScore() {
        return this.score;
    }

}
