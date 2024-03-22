package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.Categorie;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategorieImpl implements ICategorie{
    private DBConnexion db = new DBConnexion();
    @Override
    public Categorie get(int id) {
        String sql = "SELECT* FROM categorie WHERE idC =?";
        Categorie  categorie = null;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1,id);
            ResultSet rs = db.executeSelect();
            if (rs.next()){
                categorie = new Categorie();
                categorie.setIdC(rs.getInt("idC"));
                categorie.setLibelle(rs.getString("libelle"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return categorie;
    }

    @Override
    public int add(Categorie categorie) {
        String sql = "INSERT INTO categorie VALUES (NULL,?)";
        int ok=0;
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1,categorie.getLibelle());

            ok = db.executeMaj();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Categorie> getAll() {
        String sql = "SELECT * FROM categorie";
        List<Categorie> categories = new ArrayList<Categorie>();
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                Categorie categorie = new Categorie();
                categorie.setIdC(rs.getInt("idC"));
                categorie.setLibelle(rs.getString("libelle"));
                categories.add(categorie);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return categories;
    }
}
