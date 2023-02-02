package com.agmg.carsparadise.GestionePresenza.Interface;

import com.agmg.carsparadise.GestionePresenza.Control.GestoreIngresso;
import com.agmg.carsparadise.Util.ProcessThread;
import com.agmg.carsparadise.Util.Utils;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormIngresso {

    private GestoreIngresso gestoreIngresso;

    @FXML
    private TextField cognomeField;

    @FXML
    private TextField matricolaField;

    @FXML
    private TextField nomeField;

    @FXML
    void registraIngresso(ActionEvent event) {
        gestoreIngresso = ProcessThread.getGestoreIngresso();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        gestoreIngresso.registraIngresso(time , nomeField, cognomeField, matricolaField);
    }

    @FXML
    void tornaAInterfacciaPresenza(ActionEvent event) {
        Utils.cambiaInterfaccia("GestionePresenza/InterfacciaRegistrazioneIngressoUscita.fxml", "Interfaccia ingresso e uscita", 600, 400);
    }

}
