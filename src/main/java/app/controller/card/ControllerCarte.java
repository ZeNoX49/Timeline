package app.controller.card;

import java.io.IOException;

import app.model.Card;
import app.model.Deck;
import app.util.ImageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		labelTitle.setText(card.title);
        labelDate.setText(Integer.toString(card.date));
        imgView.setImage(imageManager.getImage(card.imageUrl, 150, 150));
		labelDescription.setText(card.description);
    }

	public void setDeck(Deck deck) throws IOException {
		labelTitle.setText(deck.title);
		imgView.setImage(imageManager.getImage(deck.imageUrl, 150, 150));
		setDescriptionVisible(false);
		setDateVisible(false);
	}

	@FXML
	private void initialize() {
		// TODO
	}
	
	public void setDescriptionVisible(boolean visible) {
		buttonDescription.setVisible(visible);
		buttonDescription.setDisable(!visible);
	}

	public void setDateVisible(boolean visible) {
		paneDate.setVisible(visible);
	}
	
	// @FXML
	public void checkDescription() {
		paneDescription.setVisible(true);
		paneDescription.setDisable(false);
	}

	// @FXML
	public void closeDescription() {
		paneDescription.setVisible(false);
		paneDescription.setDisable(true);
	}
	
}