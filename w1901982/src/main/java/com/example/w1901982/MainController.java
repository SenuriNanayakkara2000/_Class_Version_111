package com.example.w1901982;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label titleTxt;

    @FXML
    private Button viewButton;

    @FXML
    private Button searchButton;

    @FXML
    protected void onHelloButtonClick() {
        titleTxt.setText("Welcome to Fuel Management System");
    }

    public void OnViewButton(ActionEvent actionEvent) throws IOException {
        Stage newStage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewPassenger-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setScene(scene);
        newStage.setTitle("View Passenger");
        newStage.show();
    }

    public void OnSearchButton(ActionEvent actionEvent) throws IOException {
        Stage newStage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("searchPassenger-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setScene(scene);
        newStage.setTitle("Search Passenger");
        newStage.show();
    }
}