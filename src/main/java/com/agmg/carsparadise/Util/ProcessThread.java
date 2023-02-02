package com.agmg.carsparadise.Util;


import com.agmg.carsparadise.GestionePresenza.Control.GestoreIngresso;
import com.agmg.carsparadise.GestionePresenza.Control.GestoreUscita;

public class ProcessThread {
    private static GestoreIngresso gestoreIngresso;
    private static GestoreUscita gestoreUscita;

    public static GestoreIngresso getGestoreIngresso() {
        return gestoreIngresso;
    }

    public static void setGestoreIngresso(GestoreIngresso gestoreIngresso) {
        ProcessThread.gestoreIngresso = gestoreIngresso;
    }

    public static GestoreUscita getGestoreUscita() {
        return gestoreUscita;
    }

    public static void setGestoreUscita(GestoreUscita gestoreUscita) {
        ProcessThread.gestoreUscita = gestoreUscita;
    }
}
