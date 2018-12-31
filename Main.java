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

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        final int size = 8 ;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "beige";
                } else {
                    color = "brown";
                }
                square.setStyle("-fx-background-color: "+color+";");
                root.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
package sample;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.event.*;
import javafx.animation.*;


public class Main extends Application {

    Group root = new Group();
    Group squares = new Group();
    VBox[] square = new VBox[8];

    //Chess pieces
    char[][] board = new char[8][8];

    GridPane grid = new GridPane();

    /*
    JButtons but in javafx



     *//*
    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(root, 600, 600);
        root.getChildren().addAll(grid);
        grid.setMinSize(100,100);
        grid.setVgap(5);
        grid.setHgap(5);
        Button bt1 = new Button();
        grid.add(bt1, 0, 0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/
