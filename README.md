# SAE 2.01 - Timeline

## Nécessaire pour le projet

[Fichiers nécessaires](https://drive.google.com/file/d/1g6xjN63KGVQb0VHA9itjUjoDCjigmC0o/view?usp=sharing)  
contient :
- JDK 24  
- Librairie JavaFX  
- Librairie Jackson

Ajouter les arguments suivants dans **Run Configuration → VM Arguments** :

```bash
--module-path "C:\path\to\javafx-sdk-XX\lib" --add-modules javafx.controls,javafx.fxml
--enable-native-access=javafx.graphics
```

## Liste des choses à faire

### Fichier .fxml
- [X] accueil
- [X] selectionDeck
- [X] faireDeck
- [X] faireCarte
- [X] pageSauvegarde
- [X] parametreJeu
- [X] plateau1
- [X] plateau2
- [X] carteAvecDate
- [X] carteSansDate
- [X] descriptionCarte
- [X] sauvegarde