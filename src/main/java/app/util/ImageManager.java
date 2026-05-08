package app.util;

import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.hash;

import javafx.scene.image.Image;

public class ImageManager {

    private static ImageManager instance;
    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    private ImageManager() {}

    /* ================================================== */

    private final static Map<ImgVar, Image> CACHE = new HashMap<>();

    public Image getImage(String urlImage, int maxSizeX, int maxSizeY) {
        ImgVar imgVar = new ImgVar(urlImage, maxSizeX, maxSizeY);
        if (CACHE.containsKey(imgVar)) {
            return CACHE.get(imgVar);
        }
        try {
            Image original = new Image(urlImage, false);

            double originalWidth = original.getWidth();
            double originalHeight = original.getHeight();

            if (originalWidth <= 0 || originalHeight <= 0) {
                System.err.println("Image invalide : " + urlImage);
                return new Image(getClass().getResource("/img/Erreur404.png").toExternalForm(), maxSizeX, maxSizeY, true, true);
            }

            double ratioOriginal = originalWidth / originalHeight;
            double ratioMax = (double) maxSizeX / maxSizeY;

            int sizeX;
            int sizeY;

            if (Double.compare(ratioOriginal, ratioMax) == 0) {
                // Même ratio → remplissage parfait
                sizeX = maxSizeX;
                sizeY = maxSizeY;
            } else if (ratioOriginal > ratioMax) {
                // Image plus large que haute → on limite par la largeur
                sizeX = maxSizeX;
                sizeY = (int) (maxSizeX / ratioOriginal);
            } else {
                // Image plus haute que large → on limite par la hauteur
                sizeX = (int) (maxSizeY * ratioOriginal);
                sizeY = maxSizeY;
            }

            Image image = new Image(urlImage, sizeX, sizeY, true, true);
            CACHE.put(imgVar, image);
            return image;
        } catch (Exception e) {
            return new Image(getClass().getResource("/img/Erreur404.png").toExternalForm(), maxSizeX, maxSizeY, true, true);
        }
    }

    private class ImgVar {
        private final String imgLink;
        private final int sizeX;
        private final int sizeY;
    
        public ImgVar(String imgLink, int sizeX, int sizeY) {
            this.imgLink = imgLink;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ImgVar imgVar = (ImgVar) obj;
            return this.imgLink.equals(imgVar.imgLink)
                    && this.sizeX == imgVar.sizeX
                    && this.sizeY == imgVar.sizeY;
        }
    
        @Override
        public int hashCode() {
            return hash(this.imgLink, this.sizeX, this.sizeY);
        }
    }
}