package com.agmg.carsparadise.GestionePresenza.Interface;

import com.agmg.carsparadise.GestionePresenza.Control.GestoreUscita;
import com.agmg.carsparadise.Util.ProcessThread;
import com.agmg.carsparadise.Util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormUscita {

    private GestoreUscita gestoreUscita;
    @FXML
    private TextField cognomeField;

    @FXML
    private TextField matricolaField;

    @FXML
    private TextField nomeField;

    @FXML
    void registraUscita(ActionEvent event) {
        gestoreUscita = ProcessThread.getGestoreUscita();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        gestoreUscita.registraUscita(time, nomeField, cognomeField, matricolaField);
    }

    @FXML
    void tornaAInterfacciaPresenza(ActionEvent event) {
        Utils.cambiaInterfaccia("GestionePresenza/InterfacciaRegistrazioneIngressoUscita.fxml", "Interfaccia ingresso e uscita", 600, 400);
    }

}
