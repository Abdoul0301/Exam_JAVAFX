package sn.groupeisi.examfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.groupeisi.examfx.dao.DBConnexion;
import sn.groupeisi.examfx.entities.Categorie;
import sn.groupeisi.examfx.entities.Produit;
import sn.groupeisi.examfx.tools.Notification;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int idPro;
    @FXML
    private Button PeffacerBtn;

    @FXML
    private Button PenregisterBtn;

    @FXML
    private TextField PlibelleTfd;

    @FXML
    private Button PmodifierBtn;

    @FXML
    private Button PsuprimerBtn;

    @FXML
    private TableColumn<Produit, Integer> catégorieCOL;

    @FXML
    private ComboBox<Categorie> combocatégorie;

    @FXML
    private TableColumn<Produit, Integer> idColP;

    @FXML
    private TableColumn<Produit, String> libelleCOLP;

    @FXML
    private TableColumn<Produit, Integer> prixCOL;

    @FXML
    private TextField prixTfd;

    @FXML
    private TableView<Produit> produitTB;

    @FXML
    private TextField quantiteTfd;

    @FXML
    private TableColumn<Produit, Integer> quantitéCOL;

    void clear(){
        PlibelleTfd.setText("");
        prixTfd.setText("");
        quantiteTfd.setText("");
        combocatégorie.setValue(null);
    }

    @FXML
    void Cgeteffacer(ActionEvent event) {
        clear();
    }

    @FXML
    void Cgetenregister(ActionEvent event) {
        // Vérification si le champ libelleTfd est vide
        if (PlibelleTfd.getText().isEmpty() || prixTfd.getText().isEmpty() || quantiteTfd.getText().isEmpty()) {
            Notification.NotifError("Erreur", "Veuillez remplir le champ");
            return;
        }

        String sql = "INSERT INTO produit VALUES (null, ?, ?, ?, ?)";
        try{
            db.initPrepar(sql);

            //passage des valeur
            db.getPstm().setString(1, PlibelleTfd.getText());
            db.getPstm().setInt(2, Integer.parseInt(quantiteTfd.getText()));
            db.getPstm().setInt(3, Integer.parseInt(prixTfd.getText()));
            db.getPstm().setInt(4, getIdCat(combocatégorie.getValue().getLibelle()));
            //excution de la requete
            db.executeSelect();
            //fermeture
            db.closeConnection();
            loadTable();
            clear();
            Notification.NotifSuccess("Succés","produit enregistré");

        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    private int getIdCat(String libelle) throws SQLException {
        String sql = "SELECT idC FROM categorie WHERE libelle = ?";
        db.initPrepar(sql);
        db.getPstm().setString(1, libelle);
        ResultSet rs = db.executeSelect();
        //excution de la requete
        db.executeSelect();
        //fermeture
        db.closeConnection();

        return rs.getInt("idC");
    }


    @FXML
    void Cgetmodifier(ActionEvent event) {

    }

    @FXML
    void Cgetsupprimer(ActionEvent event) {

    }

    @FXML
    void getDataP(MouseEvent event) {
        Produit produit = produitTB.getSelectionModel().getSelectedItem();
        idPro = produit.getIdP();
        PlibelleTfd.setText(produit.getLibelle());
        quantiteTfd.setText(String.valueOf(produit.getQuantite()));
        prixTfd.setText(String.valueOf(produit.getPrix()));
        PenregisterBtn.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        CategorieController categorieController = new CategorieController();
        ObservableList<Categorie> categories = categorieController.getCategorie();
        combocatégorie.setItems(categories);
    }

    public ObservableList<Produit> getProduit(){
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        String sql = "SELECT * FROM produit ORDER BY libelle ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                Produit produit = new Produit();
                produit.setIdP(rs.getInt("idP"));
                produit.setLibelle(rs.getString("libelle"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setPrix(rs.getInt("prix"));
                produit.setIdCat(rs.getInt("idCat"));
                produits.add(produit);
            }
            db.closeConnection();

        }catch (SQLException e){
            throw new RuntimeException();
        }
        return produits;
    }

    public void loadTable(){
        ObservableList<Produit> liste = getProduit();
        produitTB.setItems(liste);
        idColP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("idP"));
        libelleCOLP.setCellValueFactory(new PropertyValueFactory<Produit, String>("libelle"));
        quantitéCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        prixCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
        catégorieCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("idCat"));

    }
}
