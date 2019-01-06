package sample;
import javafx.scene.Group;
import javafx.event.*;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.xml.soap.Text;

public class Main extends Application {

    //Static Board board = new Board();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double WIDTH = screenSize.getWidth() - 80;
    double HEIGHT = screenSize.getHeight() - 80;
    @Override

    public void start(Stage primaryStage) {
        /**
         * Ignore this section
         * 
         * 
        GridPane board_vis = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane square = new StackPane();
                String colour;
                if ((i + j) % 2 == 0) {
                    colour = "beige";
                } else {
                    colour = "brown";
                }
                square.setStyle("-fx-background-color: " + colour + ";");
                board_vis.add(square, j, i);
            }

        }**/

        Button[] btns = new Button[64];

        GridPane board_button = new GridPane();
        int k = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button_square = new Button();
                String colour;
                if ((i + j) % 2 == 0) {
                    colour = "beige";
                } else {
                    colour = "brown";
                }
                button_square.setStyle("-fx-background-color: " + colour + ";");
                button_square.setPrefWidth(50);
                button_square.setPrefHeight(50);
                board_button.add(button_square, j, i);
                btns[k] = button_square;


            }

        }


        TextField myTextField = new TextField();
        HBox hbox = new HBox(board_button);
        hbox.setPrefWidth(400);
        hbox.setPrefHeight(400);
        VBox vbox = new VBox(hbox);

        hbox.getChildren().add(myTextField);
        HBox.setHgrow(myTextField, Priority.ALWAYS);


        for (int i = 0; i < 8; i++) {
            board_button.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board_button.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }


        //primaryStage.setScene(scene);
        Scene scene = new Scene(vbox, 650, 450);
        primaryStage.setTitle("Chess");

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {  //this is the gay part that doesnt work
            @Override
            public void handle(long now) {
                String colour_sel = "red";
                for (int l = 0; l < btns.length; l++) {
                    int finalL = l;
                    btns[l].setOnAction(e -> btns[finalL].setStyle("-fx-background-color: " + colour_sel + ";"));
                }
            }
        }.start();

/**

 **/

    }


    public static void main(String[] args) {
        launch(args);

    }

/**
 * 
 * Ignore this section

    private Pane getGrid() {
        int i = 0;
        GridPane gridPane = new GridPane();
        for(Button b : btns) {
            // do something with your button
            // maybe add an EventListener or something
            gridPane.add(b, i*(i+(int)b.getWidth()), 0);
            i++;
        }
        return gridPane;
    }
    private void initBtnsArray() {
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new Button("Button-" + i);
        }
    } **/
}
