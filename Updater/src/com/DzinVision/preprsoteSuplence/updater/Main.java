package com.DzinVision.preprsoteSuplence.updater;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    public static String mainPath="";
    public static String path="";


    @Override
    public void start(Stage stage) {
        Text text=new Text("Nadgradnja je v poteku.");

        HBox h1=new HBox(10);
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(text);


        ProgressBar pb=new ProgressBar(-1);
        ProgressIndicator pin=new ProgressIndicator(-1);

        HBox h2=new HBox(10);
        h2.setAlignment(Pos.CENTER);
        h2.getChildren().addAll(pb, pin);


        VBox root=new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(h1, h2);

        Scene scene=new Scene(root, 300, 200);

        stage.setScene(scene);
        stage.setTitle("Nadgradnja");
        stage.getIcons().add(new Image(com.DzinVision.preprsoteSuplence.updater.Main.class.getResourceAsStream("icon.png")));
        stage.show();


        Functions.install();

        stage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}