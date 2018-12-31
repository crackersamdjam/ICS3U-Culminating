package sample;

import javafx.event.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {
    
    Static Board board = new Board();
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
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
                root.add(square, j, i);
            }
        }
        for (int i = 0; i < 8; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
