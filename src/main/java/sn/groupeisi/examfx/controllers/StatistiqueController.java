package sn.groupeisi.examfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import sn.groupeisi.examfx.dao.DBConnexion;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StatistiqueController implements Initializable {

    private DBConnexion db = new DBConnexion();
    private ResultSet rs;

    @FXML
    private PieChart pieChart;

    public void graf() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            String sql = "SELECT count(*) as nb, categorie.libelle from produit, categorie where idCat = idC group by categorie.libelle";

            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                pieChartData.add(new PieChart.Data(rs.getString("libelle"), rs.getInt("nb")));
            }
            pieChart.setData(pieChartData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graf();
    }
}
