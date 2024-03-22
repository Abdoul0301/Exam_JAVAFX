package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.Produit;

import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProduitImpl implements IProduit{
    private DBConnexion db = new DBConnexion();
    @Override
    public int add(Produit produit) {
        String sql = "INSERT INTO produit VALUES(NULL,?,?,?,?)";
        int ok=0;
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1,produit.getLibelle());
            db.getPstm().setInt(2,produit.getPrix());
            db.getPstm().setInt(3,produit.getQuantite());
            db.getPstm().setInt(4,produit.getCategorie().getIdC());
            ok = db.executeMaj();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Produit> getAll() {
        String sql = "SELECT* FROM produit";
        List<Produit> produits = new ArrayList<>();
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                Produit produit = new Produit();
                produit.setIdP(rs.getInt(1));
                produit.setLibelle(rs.getString(2));
                produit.setQuantite(rs.getInt(3));
                produit.setPrix(rs.getInt(4));
                produit.setCategorie(new CategorieImpl().get(rs.getInt(5)));
                produits.add(produit);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return produits;
    }
}
