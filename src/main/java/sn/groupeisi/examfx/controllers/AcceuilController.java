package sn.groupeisi.examfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import sn.groupeisi.examfx.tools.Outils;

import java.io.IOException;

public class AcceuilController {

    @FXML
    private Pane dynamique;

    @FXML
    void ACCEUIL(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/acceuil.fxml"));
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
    void DOCUMENT(ActionEvent event) {

    }

    @FXML
    void PRODUIT(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/pages/produit.fxml"));
        dynamique.getChildren().removeAll();
        dynamique.getChildren().setAll(fxml);
    }

}
