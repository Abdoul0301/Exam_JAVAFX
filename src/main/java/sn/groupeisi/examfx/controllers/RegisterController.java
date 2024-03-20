package sn.groupeisi.examfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.groupeisi.examfx.dao.DBConnexion;
import sn.groupeisi.examfx.entities.User;
import sn.groupeisi.examfx.tools.Notification;
import sn.groupeisi.examfx.tools.Outils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;

    @FXML
    private Button RannulerBtn;

    @FXML
    private TextField RemailTfd;

    @FXML
    private PasswordField RpasswordTfd;

    @FXML
    private Button RvaliderBtn;

    @FXML
    private PasswordField confimpasswort;

    @FXML
    private TextField nomTfd;

    @FXML
    private TextField prenomTfd;

    @FXML
    private TextField telephoneTfd;

    @FXML
    void getAnnulerR(ActionEvent event) {
        try {
            Outils.load(event, "page de connexion", "/pages/login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getvaliderR(ActionEvent event) {
        if (RemailTfd.getText().isEmpty() || RpasswordTfd.getText().isEmpty() || nomTfd.getText().isEmpty() || prenomTfd.getText().isEmpty() || RpasswordTfd.getText().isEmpty()) {
            Notification.NotifError("Erreur", "Veuillez remplir les champs");
            return;
        }
        String sql="INSERT INTO user VALUES (NULL,?,?,?,?,?)";
        try{
            //initialisation de la requete
            db.initPrepar(sql);

            //passage de valeurs
            db.getPstm().setString(1,RemailTfd.getText());
            db.getPstm().setString(2,RpasswordTfd.getText());
            db.getPstm().setString(3,nomTfd.getText());
            db.getPstm().setString(4,prenomTfd.getText());
            db.getPstm().setString(5,RpasswordTfd.getText());


            db.executeMaj();

            //fermeture de la requete
            db.closeConnection();

            //retoure vers la page login
            try {
                Outils.load(event, "page de connexion", "/pages/login.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RemailTfd.setText("");
        RpasswordTfd.setText("");
        nomTfd.setText("");
        prenomTfd.setText("");
        telephoneTfd.setText("");

    }

}
