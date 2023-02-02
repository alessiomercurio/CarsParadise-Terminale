package com.agmg.carsparadise.GestionePresenza.Control;

import com.agmg.carsparadise.GestionePresenza.Object.Turno;
import com.agmg.carsparadise.Util.ErroreDBMS;
import com.agmg.carsparadise.Util.ProcessoDBMS;
import com.agmg.carsparadise.Util.Utils;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestoreIngresso {

    public GestoreIngresso() {
        Utils.cambiaInterfaccia("GestionePresenza/FormIngresso.fxml", "Registrazione ingresso");
    }

    public void registraIngresso(String dataEOrario, TextField nomeField, TextField cognomeField, TextField matricolaField){
        if(nomeField.getText().isBlank() || cognomeField.getText().isBlank() || matricolaField.getText().isBlank()){
            Utils.creaPannelloErrore("I campi non possono essere vuoti!");
            return;
        }

        try {
            int matricola = Integer.parseInt(matricolaField.getText().trim());
            //verificaCredenziali
            int result = ProcessoDBMS.verificaIngressoUscita(nomeField.getText().trim(), cognomeField.getText().trim(), matricolaField.getText().trim());
            //0 non corrispondono, 1 corrispondono, -1 i campi sono vuoti
            if (result == 1) {
                // dataEOrario è il tempo corrente
                String ore = dataEOrario.split(" ")[1].split(":")[0];
                String minuti = dataEOrario.split(" ")[1].split(":")[1];
                StringBuilder oreEMinuti = new StringBuilder();
                oreEMinuti.append(ore);
                oreEMinuti.append(":");
                oreEMinuti.append(minuti);
                // out -> ore : minuti (correnti)

                //idTurno è il turno a cui dobbiamo registrare l'ingresso
                int idTurno = controllaOrarioTurno(Integer.parseInt(matricolaField.getText().trim()), dataEOrario.split(" ")[0], oreEMinuti.toString());
                if(idTurno == -1){
                    Utils.creaPannelloErrore("Errore nel registrare l'ingresso");
                }else if(idTurno == 0){ //ingresso precedentemente registrato
                    Utils.creaPannelloErrore("L'ingresso è stato già registrato");
                }else{ //non esisto nessun record di ingresso, quindi si registra l'ingresso
                    //l'ingresso è stato registrato correttamente, si può procedere a registrare l'uscita
                    ProcessoDBMS.registraIngresso(Integer.parseInt(matricolaField.getText().trim()), idTurno, 0);
                    Utils.creaPannelloInformazione("Ingresso registrato");
                }
            } else if (result == 0) {
                Utils.creaPannelloErrore("Le credenziali sono errate");
            }
        } catch (SQLException e) {
            ErroreDBMS.erroreGenericoDBMS(e);
        }catch (NumberFormatException e) {
            Utils.creaPannelloErrore("Il campo 'matricola' deve contenere solo numeri");
        }
    }

    public int controllaOrarioTurno(int matricola, String data, String oraEMinuti){
        ArrayList<Turno> listaTurni = ProcessoDBMS.recuperaTurniInizio(data, ProcessoDBMS.recuperaMatricola(matricola));

        for (Turno turno : listaTurni){
            StringBuilder orarioEMinutiTurno = new StringBuilder();
            orarioEMinutiTurno.append(turno.getIniziOrario().split(":")[0]);
            orarioEMinutiTurno.append(":");
            orarioEMinutiTurno.append(turno.getIniziOrario().split(":")[1]);
            //out -> ora:minuti del turno
            // HH:mm
            // Dobbiamo confrontare se l'impiegato sta registrando la presenza dopo 10 min
            //orarioEMinutiTurno.toString().equals(oraEMinuti)
            LocalTime inizioTurno = LocalTime.parse(orarioEMinutiTurno);
            LocalTime registroIngressoTurno = LocalTime.parse(oraEMinuti);
            Duration duration = Duration.between( inizioTurno, registroIngressoTurno);
            // Se va oltre i 10 minuti, allora duration sarà +10
            // si può timbrare 5 minuti prima dell'inizio?

            /*se non avviene un riscontro (0) allora viene restituito l'id turno
             *se avviene riscontro viene restiuito 1
             *altrimenti viene riscontrato un errore generico (-1)
             */
            if(duration.toMinutes() < 10 && duration.toMinutes() >= 0) {
                int result = ProcessoDBMS.verificaIngressoEffettuato(turno.getIdTurno());
                if(result == 0){
                    return turno.getIdTurno();
                }else if(result == 1){
                    return 0;
                }else{
                    return -1;
                }
            }
        }
        return -1;
    }
}
