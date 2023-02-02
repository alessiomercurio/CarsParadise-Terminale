package com.agmg.carsparadise.GestionePresenza.Object;

public class Turno {

    private int idTurno;

    // per le date yyyy-mm-dd
    // per gli orari HH:MM:SS
    private String inizioData;

    private String fineData;

    private String iniziOrario;

    private String fineOrario;

    public Turno(int idTurno, String inizioData, String fineData, String iniziOrario, String fineOrario) {
        this.idTurno = idTurno;
        this.inizioData = inizioData;
        this.fineData = fineData;
        this.iniziOrario = iniziOrario;
        this.fineOrario = fineOrario;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getInizioData() {
        return inizioData;
    }

    public void setInizioData(String inizioData) {
        this.inizioData = inizioData;
    }

    public String getFineData() {
        return fineData;
    }

    public void setFineData(String fineData) {
        this.fineData = fineData;
    }

    public String getIniziOrario() {
        return iniziOrario;
    }

    public void setIniziOrario(String iniziOrario) {
        this.iniziOrario = iniziOrario;
    }

    public String getFineOrario() {
        return fineOrario;
    }

    public void setFineOrario(String fineOrario) {
        this.fineOrario = fineOrario;
    }
}
