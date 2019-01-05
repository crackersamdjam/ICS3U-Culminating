package sample;
import javafx.scene.Group;
import javafx.event.*;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class Main extends Application {

    //Static Board board = new Board();
    @Override
    public void start(Stage primaryStage) {
        GridPane board_vis = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j ++) {
                StackPane square = new StackPane();
                String colour ;
                if ((i + j) % 2 == 0) {
                    colour = "beige";
                } else {
                    colour = "brown";
                }
                square.setStyle("-fx-background-color: "+colour+";");
                board_vis.add(square, j, i);
            }
        }
        TextField myTextField = new TextField();
        HBox hbox = new HBox(board_vis);
        hbox.setPrefWidth(400);
        hbox.setPrefHeight(400);
        VBox vbox = new VBox(hbox);

        hbox.getChildren().add(myTextField);
        HBox.setHgrow(myTextField, Priority.ALWAYS);


        for (int i = 0; i < 8; i++) {
            board_vis.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board_vis.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        //primaryStage.setScene(scene);
        Scene scene = new Scene(vbox, 650, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
