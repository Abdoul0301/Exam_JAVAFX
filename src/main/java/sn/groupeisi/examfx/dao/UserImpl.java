package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.User;

import java.sql.ResultSet;

public class UserImpl implements IUser{
    private  DBConnexion db = new DBConnexion();
    private ResultSet rs;
    @Override
    public User seConnecter(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            //initialisation de la requete
            db.initPrepar(sql);

            //passage de valeurs
            db.getPstm().setString(1,email);
            db.getPstm().setString(2,password);

            //execution de la requete
            rs = db.executeSelect();
            if (rs.next()){
                user = new User();
                user.setIdU(rs.getInt("idU"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }

            //fermeture de la requete
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
