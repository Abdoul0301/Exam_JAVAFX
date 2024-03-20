package sn.groupeisi.examfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sn.groupeisi.examfx.dao.IUser;
import sn.groupeisi.examfx.dao.UserImpl;
import sn.groupeisi.examfx.entities.User;
import sn.groupeisi.examfx.tools.Notification;
import sn.groupeisi.examfx.tools.Outils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    IUser userDao = new UserImpl();

    @FXML
    private Button connexionBtn;
    @FXML
    private ImageView exit;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
}
