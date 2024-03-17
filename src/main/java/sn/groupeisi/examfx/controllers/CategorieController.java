package sn.groupeisi.examfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.groupeisi.examfx.dao.DBConnexion;
import sn.groupeisi.examfx.entities.Categorie;
import sn.groupeisi.examfx.tools.Notification;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategorieController implements Initializable {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int idcat;

    @FXML
    private Button CeffacerBtn;

    @FXML
    private Button CenregisterBtn;

    @FXML
    private Button CmodifierBtn;

    @FXML
    private Button CsuprimerBtn;

    @FXML
    private TableView<Categorie> categorieTB;

    @FXML
    private TableColumn<Categorie, Integer> idCol;

    @FXML
    private TableColumn<Categorie, String> libelleCOL;

    @FXML
    private TextField libelleTfd;

    @FXML
    void Cgeteffacer(ActionEvent event) {
        clear();
    }

    void clear(){
        libelleTfd.setText("");
    }
    @FXML
    void getData(MouseEvent event) {
        Categorie cat = categorieTB.getSelectionModel().getSelectedItem();
        idcat = cat.getIdC();
        libelleTfd.setText(cat.getLibelle());
        CenregisterBtn.setDisable(true);
    }

    @FXML
    void Cgetenregister(ActionEvent event) {

        // Vérification si le champ libelleTfd est vide
        if (libelleTfd.getText().isEmpty()) {
            Notification.NotifError("Erreur", "Veuillez remplir le champ");
            return;
        }

        String sql = "INSERT INTO categorie VALUES (null, ?)";
        try{
            db.initPrepar(sql);

            //passage des valeur
            db.getPstm().setString(1, libelleTfd.getText());
            //excution de la requete
            db.executeMaj();
            //fermeture
            db.closeConnection();
            loadTable();
            clear();
            Notification.NotifSuccess("Succés","Catégorie enregistré");

        }catch (SQLException e){
            throw new RuntimeException();
        }

    }

    @FXML
    void Cgetmodifier(ActionEvent event) {
        String sql = "UPDATE categorie SET libelle = ? WHERE idC = ?";
        try{
            db.initPrepar(sql);

            //passage des valeur
            db.getPstm().setString(1, libelleTfd.getText());
            db.getPstm().setInt(2, idcat);
            //excution de la requete
            db.executeMaj();
            //fermeture
            db.closeConnection();
            loadTable();
            clear();
            CenregisterBtn.setDisable(false);
            Notification.NotifSuccess("Succés","Catégorie modifier");


        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void Cgetsupprimer(ActionEvent event) {
        String sql = "DELETE FROM categorie WHERE idC = ?";
        try{
            db.initPrepar(sql);

            //passage des valeur
            db.getPstm().setInt(1, idcat);
            //excution de la requete
            db.executeMaj();
            //fermeture
            db.closeConnection();
            loadTable();
            clear();
            CenregisterBtn.setDisable(false);
            Notification.NotifSuccess("Succés","Catégorie supprimer");


        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    public ObservableList<Categorie> getCategorie(){
        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        String sql = "SELECT * FROM categorie ORDER BY libelle ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                Categorie cat = new Categorie();
                cat.setIdC(rs.getInt("idC"));
                cat.setLibelle(rs.getString("libelle"));
                categories.add(cat);
            }
            db.closeConnection();

        }catch (SQLException e){
            throw new RuntimeException();
        }
        return categories;
    }

    public  void  loadTable(){
        ObservableList<Categorie> liste = getCategorie();
        categorieTB.setItems(liste);
        idCol.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("idC"));
        libelleCOL.setCellValueFactory(new PropertyValueFactory<Categorie, String>("libelle"));
    }
}
