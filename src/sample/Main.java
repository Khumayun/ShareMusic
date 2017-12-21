package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Server me = new Server();
        me.startServer();
        me.scanList();

        
        Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));

        primaryStage.setTitle("ShareMusic");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
