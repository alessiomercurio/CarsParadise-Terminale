module com.agmg.carsparadise {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.agmg.carsparadise to javafx.fxml;
    exports com.agmg.carsparadise;
    exports com.agmg.carsparadise.GestionePresenza.Interface;

    opens com.agmg.carsparadise.GestionePresenza.Interface to javafx.fxml;
}