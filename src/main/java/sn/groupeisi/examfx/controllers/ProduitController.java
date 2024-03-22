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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sn.groupeisi.examfx.dao.*;
import sn.groupeisi.examfx.entities.Categorie;
import sn.groupeisi.examfx.entities.Produit;
import sn.groupeisi.examfx.tools.Notification;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int idPro;
    private ICategorie catdao = new CategorieImpl();
    private IProduit prodao = new ProduitImpl();

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
    private TableColumn<Produit, Categorie> catégorieCOL;

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
    private TextField champSearch;

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

        Produit produit = new Produit();
        produit.setLibelle(PlibelleTfd.getText());
        int quantite = Integer.parseInt(quantiteTfd.getText());
        int prix = Integer.parseInt(prixTfd.getText());
        produit.setQuantite(quantite);
        produit.setPrix(prix);
        produit.setCategorie(combocatégorie.getValue());

        int ok = prodao.add(produit);
        if (ok != 0) {
            lodProd();
            clear();
        } else {
            Notification.NotifError("Erreur", "Erreur lors de l'enregistrement");


            //passage des valeur
            //excution de la requete
            db.executeSelect();
            //fermeture
            db.closeConnection();


            clear();
            Notification.NotifSuccess("Succés", "produit enregistré");

        }

    }


    @FXML
    void Cgetmodifier(ActionEvent event) {
        String sql = "UPDATE produit SET libelle=?, prix=?, quantite=?, idCat=? WHERE idP=?";
        try {
            if(PlibelleTfd.getText().isEmpty() || prixTfd.getText().isEmpty() || quantiteTfd.getText().isEmpty() ) {
                // Afficher une notification d'erreur indiquant que tous les champs doivent être remplis
                Notification.NotifError("Erreur", "Veuillez remplir les champs");
                return; // Arrêter le traitement car tous les champs ne sont pas remplis
            }

            db.initPrepar(sql);
            db.getPstm().setString(1, PlibelleTfd.getText());
            db.getPstm().setInt(2, Integer.parseInt(prixTfd.getText()));
            db.getPstm().setInt(3, Integer.parseInt(quantiteTfd.getText()));
            db.getPstm().setInt(4, combocatégorie.getValue().getIdC());
            db.getPstm().setInt(5,idPro);

            db.executeMaj();
            db.closeConnection();
            lodProd();
            clear();
            Notification.NotifSuccess("Succés","Catégorie modifier");

        } catch (Exception ex) {
            // Afficher une notification d'erreur en cas d'échec

            Notification.NotifError("Erreur", "Erreur lors de la mise à jour du produit.");

            ex.printStackTrace();
        }
        lodProd();

    }

    @FXML
    void Cgetsupprimer(ActionEvent event) {
        String sql = "DELETE FROM produit WHERE idP = ?";
        try{
            db.initPrepar(sql);

            //passage des valeur
            db.getPstm().setInt(1, idPro);
            //excution de la requete
            db.executeMaj();
            //fermeture
            db.closeConnection();
            lodProd();
            clear();
            PenregisterBtn.setDisable(false);
            Notification.NotifSuccess("Succés","Produit supprimer");


        }catch (SQLException e){
            throw new RuntimeException();
        }
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
        idColP.setCellValueFactory(new PropertyValueFactory<>("idP"));
        libelleCOLP.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        quantitéCOL.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixCOL.setCellValueFactory(new PropertyValueFactory<>("prix"));
        catégorieCOL.setCellValueFactory(new PropertyValueFactory<>("categorie"));



        lodProd();
        lodCat();
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
                //produit.setIdCat(rs.getInt("idCat"));
                produits.add(produit);
            }
            db.closeConnection();

        }catch (SQLException e){
            throw new RuntimeException();
        }
        return produits;
    }

    public ObservableList<Produit> getProduitContains(String mot){
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        String sql = "SELECT * FROM produit WHERE libelle LIKE '%"+mot+"%'";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                Produit produit = new Produit();
                produit.setIdP(rs.getInt("idP"));
                produit.setLibelle(rs.getString("libelle"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setPrix(rs.getInt("prix"));
                //produit.setIdCat(rs.getInt("idCat"));
                produits.add(produit);
            }
            db.closeConnection();

        }catch (SQLException e){
            throw new RuntimeException();
        }
        return produits;
    }

    /*public void loadTable(){
        ObservableList<Produit> liste = getProduit();
        produitTB.setItems(liste);
        idColP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("idP"));
        libelleCOLP.setCellValueFactory(new PropertyValueFactory<Produit, String>("libelle"));
        quantitéCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        prixCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
        catégorieCOL.setCellValueFactory(new PropertyValueFactory<>("idCat"));

    }*/
    public  void lodProd(){
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        List<Produit> produitList = prodao.getAll();
        for (Produit p :produitList){
            produits.add(p);
        }
        produitTB.setItems(produits);
    }

    public  void lodCat(){
        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        List<Categorie> produitList = catdao.getAll();
        for (Categorie c :produitList){
            categories.add(c);
        }
        combocatégorie.setItems(categories);
    }

    @FXML
    void onSearch(KeyEvent event) {
        ObservableList<Produit> liste = getProduitContains(champSearch.getText());

        idColP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("idP"));
        libelleCOLP.setCellValueFactory(new PropertyValueFactory<Produit, String>("libelle"));
        quantitéCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        prixCOL.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
        catégorieCOL.setCellValueFactory(new PropertyValueFactory<>("idCat"));
        produitTB.setItems(liste);

    }



}
