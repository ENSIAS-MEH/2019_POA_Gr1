package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*
 * La classe du lancement de l'application
 */

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
		Scene sc = new Scene(root);
		primaryStage.setTitle("Le projet POO");
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
