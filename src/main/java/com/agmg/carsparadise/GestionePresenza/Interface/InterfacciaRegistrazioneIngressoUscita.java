package com.agmg.carsparadise.GestionePresenza.Interface;

import com.agmg.carsparadise.GestionePresenza.Control.GestoreIngresso;
import com.agmg.carsparadise.GestionePresenza.Control.GestoreUscita;
import com.agmg.carsparadise.Util.ProcessThread;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InterfacciaRegistrazioneIngressoUscita {
    @FXML
    private Label orario;

    @FXML
    void cliccaRegistraIngresso() {
        GestoreIngresso gestoreIngresso = new GestoreIngresso();
        ProcessThread.setGestoreIngresso(gestoreIngresso);
    }

    @FXML
    void cliccaRegistraUscita() {
        GestoreUscita gestoreUscita = new GestoreUscita();
        ProcessThread.setGestoreUscita(gestoreUscita);
    }

    @FXML
    void initialize() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                String tempo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                String[] splitTempo = tempo.split(" ");
                orario.setText(splitTempo[1]);
            }
        };
        timer.start();
    }

}
