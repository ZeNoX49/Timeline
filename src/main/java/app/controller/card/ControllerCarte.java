package app.controller.card;

import java.io.IOException;

import app.model.Card;
import app.model.Deck;
import app.util.ImageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerCarte {
	private ImageManager imageManager = ImageManager.getInstance();

    @FXML private ImageView imgView;

    @FXML private Label labelTitle;
    
    @FXML private Button buttonDescription;
	
	@FXML private Pane paneDate;
    @FXML private Label labelDate;

	@FXML private Pane paneDescription;
	@FXML private Label labelDescription;
    
    // private Card card;

	/* ----- Constructeur ----- */
	public void setCard(Card card) throws IOException {
		// this.card = card;
		labelTitle.setText(card.getTitle());
        labelDate.setText(card.getDate());
        imgView.setImage(imageManager.getImage(card.getImageUrl(), 150, 150));
		labelDescription.setText(card.getDescription());
    }

	public void setDeck(Deck deck) throws IOException {
		labelTitle.setText(deck.getTitle());
		imgView.setImage(imageManager.getImage(deck.getImageUrl(), 150, 150));
		setDescriptionVisible(false);
		setDateVisible(false);
	}
	
	/* ----- setter ----- */
	public void setTitle(String text) 		{ labelTitle.setText(text); }
	public void setDate(String text) 		{ labelDate.setText(text); }
	public void setCardImage(Image image) 	{ imgView.setImage(image); }
	
	/* ----- Utilitaire ----- */
	// Description
	public void setDescriptionVisible(boolean visible) {
		buttonDescription.setVisible(visible);
		buttonDescription.setDisable(!visible);
	}
	// Date
	public void setDateVisible(boolean visible) {
		paneDate.setVisible(visible);
	}
	
	/* ----- Description ----- */
	@FXML 
	public void checkDescription() {
		paneDescription.setVisible(true);
		paneDescription.setDisable(false);
	}

	@FXML 
	public void closeDescription() {
		paneDescription.setVisible(false);
		paneDescription.setDisable(true);
	}
	
}