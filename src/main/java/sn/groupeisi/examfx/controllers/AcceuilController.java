package sn.groupeisi.examfx.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sn.groupeisi.examfx.tools.Outils;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcceuilController implements Initializable {

    @FXML
    private Pane dynamique;

    @FXML
    private ImageView exit;

    @FXML
    private AnchorPane slider;
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;


    @FXML
    void ACCEUIL(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/statistique.fxml"));
        dynamique.getChildren().removeAll();
        dynamique.getChildren().setAll(fxml);
    }



    @FXML
    void logout(ActionEvent event) {
        try {
            Outils.load(event, "", "/pages/login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CATEGORIE(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/categorie.fxml"));
        dynamique.getChildren().removeAll();
        dynamique.getChildren().setAll(fxml);
    }

    @FXML
    void DOCUMENT(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/document.fxml"));
        dynamique.getChildren().removeAll();
        dynamique.getChildren().setAll(fxml);
    }

    @FXML
    void PRODUIT(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/produit.fxml"));
        dynamique.getChildren().removeAll();
        dynamique.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });



    }
}
