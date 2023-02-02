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

public class GestoreUscita {

    public GestoreUscita(){
        Utils.cambiaInterfaccia("GestionePresenza/FormUscita.fxml", "Registrazione uscita");
    }

    public void registraUscita(String dataEOrario, TextField nomeField, TextField cognomeField, TextField matricolaField){
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

                //idTurno è il turno a cui dobbiamo registrare l'uscita
                int idTurno = controllaOrarioTurno(Integer.parseInt(matricolaField.getText().trim()), dataEOrario.split(" ")[0], oreEMinuti.toString());
                if(idTurno == -1){
                    Utils.creaPannelloErrore("Errore nel registrare l'uscita");
                }else if(idTurno == 0){
                    Utils.creaPannelloErrore("L'ingresso del turno non è stato registrato precedentemente");
                }else if(idTurno == -2){
                    Utils.creaPannelloErrore("Hai già registrato l'uscita");
                }else{
                    //l'ingresso è stato registrato correttamente, si può procedere a registrare l'uscita
                    ProcessoDBMS.registraUscita(Integer.parseInt(matricolaField.getText().trim()), idTurno);
                    Utils.creaPannelloInformazione("Uscita registrata");
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

    public int controllaOrarioTurno(int matricola, String data, String oraEMinuti){ // tempo odierno
        ArrayList<Turno> listaTurni = ProcessoDBMS.recuperaTurniFine(data, ProcessoDBMS.recuperaMatricola(matricola));
        for (Turno turno : listaTurni){
            StringBuilder orarioEMinutiTurno = new StringBuilder();
            orarioEMinutiTurno.append(turno.getFineOrario().split(":")[0]);
            orarioEMinutiTurno.append(":");
            orarioEMinutiTurno.append(turno.getFineOrario().split(":")[1]);
            //out -> ora:minuti del turno
            // HH:mm
            // Dobbiamo confrontare se l'impiegato sta registrando la presenza dopo 10 min
            //orarioEMinutiTurno.toString().equals(oraEMinuti)
            LocalTime fineTurno = LocalTime.parse(orarioEMinutiTurno);
            LocalTime registroUscitaTurno = LocalTime.parse(oraEMinuti);
            Duration duration = Duration.between( fineTurno, registroUscitaTurno);
            // se l'orario di fine turno è "XX:00" allora l'impiegato può timbrara
            // si può timbrare 5 minuti prima dell'inizio?

            /*se non avviene un riscontro (1) allora viene restituito l'id turno
             *se avviene riscontro viene restiuito 0
             *altrimenti viene riscontrato un errore generico (-1)
             */
            if(duration.toMinutes() == 0 || duration.toMinutes() < 10) {
                int result = ProcessoDBMS.verificaIngressoEffettuato(turno.getIdTurno());
                int resultUscita = ProcessoDBMS.verificaUscitaEffettuata(turno.getIdTurno());
                //se è presente un ingresso, e l'uscita non è stata effettuata precedentemente
                if(result == 1 && resultUscita == 0){
                    return turno.getIdTurno();
                }else if(result == 0){
                    return 0;
                }else if(resultUscita == 1){
                    return -2;
                }else
                    return -1;
            }
        }
        return -1;
    }
}

