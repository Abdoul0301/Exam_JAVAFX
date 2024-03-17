package sn.groupeisi.examfx.dao;

import java.sql.*;

public class DBConnexion {
    //pour la connexion
   private Connection cnx;
   //pour les requete preparés
    private PreparedStatement pstm;
    //pour les requete de consultation (SELECT)
    private ResultSet rs;
    //pour les requetes de mis a jour (INSERT INTO, UPDATE, DELETE)
    private int ok;
    //methode d'ouverture de la connexion

    private Connection getConnection() {

        //parametre de connexion
        String host = "localhost";
        String database = "examfx";
        String url = "jdbc:mysql://" + host + ":3306/" + database;
        String user = "root";
        String password = "";
        try {
            //chargement du pilote
            Class.forName("com.mysql.jdbc.Driver");
            //ouverture de la connexion
            cnx = DriverManager.getConnection(url, user, password);
            //System.out.println("connexion reussie");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cnx;
    }
    public void initPrepar(String sql){
        try {
            getConnection();
            pstm = cnx.prepareStatement(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeSelect(){
        rs = null;
        try {
            rs = pstm.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeMaj(){
        try {
            ok = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public void closeConnection(){
        try {
            if (cnx != null)
                cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPstm(){
        return pstm;
    }
}
