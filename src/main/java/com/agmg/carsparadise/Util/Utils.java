package com.agmg.carsparadise.Util;

import com.agmg.carsparadise.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

    private static Stage stage;
    private static Scene scene;

    //crea interfaccia per il login, ovvero la principale
    public static void creaInterfacciaPrincipale(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(Main.class.getResource("GestionePresenza/InterfacciaRegistrazioneIngressoUscita.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Interfaccia ingresso e uscita");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //cambia interfaccia con parametri di width e height
    public static void cambiaInterfaccia(String path, String titolo, int width, int height) {
        Parent root = null;
        try {
            root = FXMLLoader.load((Main.class.getResource(path)));
            scene = new Scene(root, width, height);
            stage.setTitle(titolo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            creaPannelloErrore(e.getMessage());
        }
    }

    //cambia interfaccia con parametri di width e height
    public static void cambiaInterfaccia(String path, String titolo) {
        Parent root = null;
        try {
            root = FXMLLoader.load((Main.class.getResource(path)));
            scene = new Scene(root, 700, 600);
            stage.setTitle(titolo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            creaPannelloErrore(e.getMessage());
        }
    }

    //crea un pannello di errore, che verrà usato per errori di tipo diversi da quelli generati dal DBMS
    public static void creaPannelloErrore(String messaggioErrore) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setContentText(messaggioErrore);
        alert.showAndWait();
    }

    //crea un pannello di conferma per confermare o annullare le operazioni
    public static void creaPannelloInformazione(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    //crea un pannello di conferma per confermare o annullare le operazioni
    public static void creaPannelloConferma(String messaggioConferma) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setContentText(messaggioConferma);
        alert.showAndWait();
    }
}

