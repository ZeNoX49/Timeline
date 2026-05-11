package app.controller.card;

import app.model.Card;
import app.model.Deck;
import app.util.ImageManager;
import app.util.SingletonRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerCarte {
	private static final ImageManager I_M = SingletonRegistry.get(ImageManager.class, ImageManager::new);

    @FXML private ImageView imgView;
    @FXML private Label labelTitle;
    @FXML private Button buttonDescription;
	@FXML private Pane paneDate;
    @FXML private Label labelDate;
	@FXML private Pane paneDescription;
	@FXML private Label labelDescription;
    
    private final Card card;
	private final Deck deck;

	public ControllerCarte(Deck deck) {
        this(deck, null);
    }

    public ControllerCarte(Deck deck, Card card) {
        this.deck = deck;
        this.card = card;
    }

	@FXML
    private void initialize() {
        refresh();
    }

    public void refresh() {
        if (this.labelTitle == null) {
            return;
        }

        if (this.card != null) {
            this.labelTitle.setText(this.card.title);
            this.labelDate.setText(Integer.toString(this.card.date));
            this.labelDescription.setText(this.card.description);
            this.imgView.setImage(I_M.getImage(this.card.imageUrl, 150, 150));
            this.setDescriptionVisible(true);
            this.setDateVisible(true);
            this.closeDescription();
        } else {
            this.labelTitle.setText(this.deck.title);
            this.imgView.setImage(I_M.getImage(this.deck.imageUrl, 150, 150));
            this.setDescriptionVisible(false);
            this.setDateVisible(false);
            this.closeDescription();
        }
    }

    public void setDescriptionVisible(boolean visible) {
        this.buttonDescription.setVisible(visible);
        this.buttonDescription.setManaged(visible);
        this.buttonDescription.setDisable(!visible);
    }

    public void setDateVisible(boolean visible) {
        this.paneDate.setVisible(visible);
        this.paneDate.setManaged(visible);
    }

    @FXML
    private void checkDescription() {
        this.paneDescription.setVisible(true);
        this.paneDescription.setManaged(true);
        this.paneDescription.setDisable(false);
    }

    @FXML
    private void closeDescription() {
        this.paneDescription.setVisible(false);
        this.paneDescription.setManaged(false);
        this.paneDescription.setDisable(true);
    }
	
}