package app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import app.model.MainGame;

public class SaveManager {
    // GameManager gameManager = GameManager.getInstance();

    // private static final String SAVE_FOLDER = "ressources/saves/";
    // private static final String NUM_SAVE_FILE = "ressources/saves/numSave.txt";

    // static {
    //     File folder = new File(SAVE_FOLDER);
    //     if (!folder.exists()) {
    //         folder.mkdirs();
    //     }
    // }
    
    // public static int getAndIncrementSaveNumber() {
    //     int num = 1;
    //     File numFile = new File(NUM_SAVE_FILE);

    //     if (numFile.exists()) {
    //         try (BufferedReader br = new BufferedReader(new FileReader(numFile))) {
    //             String line = br.readLine();
    //             if (line != null) {
    //                 num = Integer.parseInt(line.trim());
    //             }
    //         } catch (IOException | NumberFormatException e) {
    //             System.err.println("Erreur lecture numSave.txt : " + e.getMessage());
    //             num = 1; 
    //         }
    //     }
    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter(numFile, false))) {
    //         bw.write(String.valueOf(num + 1));
    //     } catch (IOException e) {
    //         System.err.println("Erreur ecriture numSave.txt : " + e.getMessage());
    //     }

    //     return num;
    // }


    // public static void save(MainGame game, String saveName) {
    // 	String filePath = SAVE_FOLDER + saveName + ".dat";
    //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
    //         oos.writeObject(game);
    //     } catch (IOException e) {
    //         System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
    //     }
    // }

    // public static MainGame load(String saveName) {
    //     String filePath = SAVE_FOLDER + saveName + ".dat";
    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
    //         MainGame game = (MainGame) ois.readObject();

    //         // gameManager.setNbJoueur(game.getNbJoueur());+
    //         // gameManager.setTempsLimite(game.getTimeLimitSeconds());
    //         // gameManager.setDeck(game.getDeck());

    //         System.out.println("Partie chargee : " + filePath);
    //         return game;
    //     } catch (IOException | ClassNotFoundException e) {
    //         System.err.println("Erreur lors du chargement : " + e.getMessage());
    //         return null;
    //     }
    // }
    
    // public static File deleteSave(String saveName) {
    //     return new File(SAVE_FOLDER + saveName + ".dat");
    // }

    // public static String[] listSaves() {
    //     File folder = new File(SAVE_FOLDER);
    //     return folder.list((dir, name) -> name.endsWith(".dat"));
    // }
}
