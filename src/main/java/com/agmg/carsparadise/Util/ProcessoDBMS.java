package com.agmg.carsparadise.Util;

import com.agmg.carsparadise.GestionePresenza.Object.Turno;

import java.sql.*;
import java.util.ArrayList;

public class ProcessoDBMS {

    //aprire la connessione con il DB
    public static Connection connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/CarsParadise", //url
                    "root", //username
                    "rootalex" //password
            );
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
            e.printStackTrace();
        }
        return conn;
    }

    //chiudere la connessione con il DB
    public static void closeConnectionToDatabase(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
        }
    }


    public static int recuperaMatricola(int matricola) {
        int matricolaDb = -1;
        try {
            Connection conn = connectToDatabase();
            String query = "SELECT Matricola" +
                    " FROM Impiegato " +
                    " WHERE Matricola = " + matricola;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                matricolaDb = rs.getInt("Matricola");
            }
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
        }
        return matricolaDb;
    }


    // recuperare i turni di un determinato giorno
    public static ArrayList<Turno> recuperaTurniInizio(String data, int matricola){
        int idTurno = 0;
        String dataInizioTurno = "";
        String orarioInizioTurno = "";
        String dataFineTurno = "";
        String orarioFineTurno = "";
        ArrayList<Turno> listaTurni = new ArrayList<Turno>();

        try {
            Connection conn = connectToDatabase();
            String query = " SELECT id_Turno, date(Data_Inizio) as DataInizio,  time(Data_Inizio) as OrarioInizio," +
                            " date(Data_Fine) as DataFine, time(Data_Fine) as OrarioFine" +
                            " FROM Turno T" +
                            " WHERE T.Ref_Impiegato = " + matricola +
                            " AND date(Data_Inizio) = " + "'" + data + "'" ;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idTurno = rs.getInt("id_Turno");
                dataInizioTurno = rs.getString("DataInizio");
                orarioInizioTurno = rs.getString("OrarioInizio");
                dataFineTurno = rs.getString("DataFine");
                orarioFineTurno = rs.getString("OrarioFine");
                listaTurni.add(new Turno(idTurno, dataInizioTurno, dataFineTurno, orarioInizioTurno, orarioFineTurno));
            }
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
        } return listaTurni;
    }
    // recuperare i turni di un determinato giorno
    public static ArrayList<Turno> recuperaTurniFine(String data, int matricola){
        int idTurno = 0;
        String dataInizioTurno = "";
        String orarioInizioTurno = "";
        String dataFineTurno = "";
        String orarioFineTurno = "";
        ArrayList<Turno> listaTurni = new ArrayList<Turno>();
        try {
            Connection conn = connectToDatabase();
            String query = " SELECT id_Turno, date(Data_Inizio) as DataInizio,  time(Data_Inizio) as OrarioInizio," +
                    " date(Data_Fine) as DataFine, time(Data_Fine) as OrarioFine" +
                    " FROM Turno T" +
                    " WHERE T.Ref_Impiegato = " + matricola +
                    " AND date(Data_Fine) = " + "'" + data + "'" ;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idTurno = rs.getInt("id_Turno");
                dataInizioTurno = rs.getString("DataInizio");
                orarioInizioTurno = rs.getString("OrarioInizio");
                dataFineTurno = rs.getString("DataFine");
                orarioFineTurno = rs.getString("OrarioFine");
                listaTurni.add(new Turno(idTurno, dataInizioTurno, dataFineTurno, orarioInizioTurno, orarioFineTurno));
            }
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
        } return listaTurni;
    }

    public static int verificaIngressoUscita(String nome, String cognome, String matricola) throws SQLException{
                    String query = "SELECT COUNT(1) " +
                    " FROM Impiegato" +
                    " WHERE Nome = '" + nome + "'" +
                    " AND Cognome = '" + cognome + "'" +
                    " AND matricola = " + matricola;

            try {
                Connection conn = connectToDatabase();
                Statement st = ProcessoDBMS.connectToDatabase().createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
                closeConnectionToDatabase(conn);
            } catch (SQLException e) {
                ErroreDBMS.erroreGenericoDBMS(e);
                e.printStackTrace();
            }
            return 0;
    }

    public static void registraIngresso(int refImpiegato, int refTurno, int ritardo){
        try {
            Connection conn = connectToDatabase();
            String query = "INSERT INTO Ingresso(Ref_Impiegato, Ref_Turno, Ritardo, Motivazione_Ritardo)" +
                    " VALUES(?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, refImpiegato);
            stm.setInt(2, refTurno);
            stm.setInt(3, 0);
            stm.setString(4, " ");
            stm.executeUpdate();
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
            e.printStackTrace();
        }
    }

    public static void registraUscita(int refImpiegato, int refTurno){
        try {
            Connection conn = connectToDatabase();
            String query = "INSERT INTO Uscita(Ref_Impiegato, Ref_Turno)" +
                    " VALUES(?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, refImpiegato);
            stm.setInt(2, refTurno);
            stm.executeUpdate();
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
            e.printStackTrace();
        }
    }

    public static int verificaIngressoEffettuato(int idTurno){
        String query = "SELECT COUNT(1) " +
                " FROM Ingresso I, Turno T" +
                " WHERE I.Ref_Turno = T.id_Turno" +
                " AND T.id_Turno = " + idTurno;
        try {
            Connection conn = connectToDatabase();
            Statement st = ProcessoDBMS.connectToDatabase().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return 1; //esiste
                } else {
                    return 0; //non esiste
                }
            }
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
            e.printStackTrace();
        }
        return -1;
    }

    public static int verificaUscitaEffettuata(int idTurno){
        String query = "SELECT COUNT(1) " +
                " FROM Uscita U, Turno T" +
                " WHERE U.Ref_Turno = T.id_Turno" +
                " AND T.id_Turno = " + idTurno;
        try {
            Connection conn = connectToDatabase();
            Statement st = ProcessoDBMS.connectToDatabase().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return 1; //esiste
                } else {
                    return 0; //non esiste
                }
            }
            closeConnectionToDatabase(conn);
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
            e.printStackTrace();
        }
        return -1;
    }
}

//public void getTurni(int matricola);

//public void richiediPeriodoIntensivo(int matricola);

//public void registraAstensione(int matricola, motivazione, dataInizio, dataFine);

//recuperaTurniConRitardo(int matricola)

//inserisciImpiegato(nome, cognome, mail, matricola, iban, password, indirizzo, ruolo)

//getImpiegato(matricola)

//eliminaImpiegato(matricola)

//getImpiegati()

//getDatePeriodoIntensivi()

//invioDataInizioFine(dataInizo, dataFine)

//eliminaPeriodoIntensivo(codicePeriodo)

//registraIngresso(int matricola, id_Turno)

//registraIngressoRitardo(int matricola, id_Turno)

//registraUscita(int matricola, id_Turno)