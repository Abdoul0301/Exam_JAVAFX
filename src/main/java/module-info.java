module sn.groupeisi.examfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayTester;
    requires org.apache.poi.ooxml;
    requires itextpdf;


    opens sn.groupeisi.examfx to javafx.fxml;
    exports sn.groupeisi.examfx;
    //exposition des controllers
    opens sn.groupeisi.examfx.controllers to javafx.fxml;
    exports sn.groupeisi.examfx.controllers;
    //exposition des dao
    opens sn.groupeisi.examfx.dao to javafx.fxml;
    exports sn.groupeisi.examfx.dao;
    //exposition des entities
    opens sn.groupeisi.examfx.entities to javafx.fxml;
    exports sn.groupeisi.examfx.entities;
}