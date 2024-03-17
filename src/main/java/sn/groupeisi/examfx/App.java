package sn.groupeisi.examfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sn.groupeisi.examfx.dao.DBConnexion;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/pages/produit.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("page de connexion");
        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}
