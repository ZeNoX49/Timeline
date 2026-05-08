package app.controller.card;

import app.model.Card;
import app.model.Deck;
import app.util.ImageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerCarte {
	private static final ImageManager I_M = ImageManager.getInstance();

    @FXML private ImageView imgView;
    @FXML private Label labelTitle;
    @FXML private Button buttonDescription;
	@FXML private Pane paneDate;
    @FXML private Label labelDate;
	@FXML private Pane paneDescription;
	@FXML private Label labelDescription;
    
    private final Card card;
	private final Deck deck;

	public ControllerCarte(Card card) {
		this.card = card;
		this.deck = null;
	}
	
	public ControllerCarte(Deck deck) {
		this.deck = deck;
		this.card = null;
	}

	@FXML
	private void initialize() {
		if(this.card != null) {
			this.labelTitle.setText(this.card.title);
			this.labelDate.setText(Integer.toString(this.card.date));
			this.imgView.setImage(I_M.getImage(this.card.imageUrl, 150, 150));
			this.labelDescription.setText(this.card.description);
		} else {
			this.labelTitle.setText(this.deck.title);
			this.imgView.setImage(I_M.getImage(this.deck.imageUrl, 150, 150));
			this.setDescriptionVisible(false);
			this.setDateVisible(false);
		}
	}
	
	public void setDescriptionVisible(boolean visible) {
		this.buttonDescription.setVisible(visible);
		buttonDescription.setDisable(!visible);
	}

	public void setDateVisible(boolean visible) {
		this.paneDate.setVisible(visible);
	}
	
	@FXML
	private void checkDescription() {
		this.paneDescription.setVisible(true);
		this.paneDescription.setDisable(false);
	}

	@FXML
	private void closeDescription() {
		this.paneDescription.setVisible(false);
		this.paneDescription.setDisable(true);
	}
	
}