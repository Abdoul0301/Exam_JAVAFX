package sn.groupeisi.examfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.groupeisi.examfx.dao.IUser;
import sn.groupeisi.examfx.dao.UserImpl;
import sn.groupeisi.examfx.entities.User;
import sn.groupeisi.examfx.tools.Notification;
import sn.groupeisi.examfx.tools.Outils;

import java.io.IOException;

public class LoginController {

    IUser userDao = new UserImpl();

    @FXML
    private Button connexionBtn;

    @FXML
    private TextField emailTfd;

    @FXML
    private PasswordField passwordTfd;
    @FXML
    void getLogin(ActionEvent event) {
        String email = emailTfd.getText();
        String password = passwordTfd.getText();
        if (email.equals("") || password.equals("")){
            Notification.NotifError("Erreur","Tout les champs sont obligatoire");
        }else {
            User user = userDao.seConnecter(email, password);
            try {
                if (user != null) {
                    Notification.NotifSuccess("Succés", "Connecxion réussie !");
                    Outils.load(event, "Acceuil", "/pages/acceuil.fxml");
                } else {
                    Notification.NotifError("Erreur", "Email et/ou mot de passe incorrects !");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        emailTfd.setText("");
        passwordTfd.setText("");
    }

    @FXML
    void getRegister(ActionEvent event) {
        try {
            Outils.load(event, "S'inscrire", "/pages/register.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
