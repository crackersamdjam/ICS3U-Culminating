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

public class Main extends Application{

    //static Board board = new Board();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double WIDTH = screenSize.getWidth() - 80;
    double HEIGHT = screenSize.getHeight() - 80;

    @Override
    public void start(Stage primaryStage){

        Button[] btns = new Button[64];

        GridPane board_button = new GridPane();
        int k = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Button button_square = new Button();
                String colour;
                if((i + j) % 2 == 0){
                    colour = "beige";
                }else{
                    colour = "brown";
                }
                button_square.setStyle("-fx-background-color: " + colour + ";");
                button_square.setPrefWidth(50);
                button_square.setPrefHeight(50);
                board_button.add(button_square, j, i);
                btns[k] = button_square;
                int finalK = k;
                btns[k].setOnAction(e -> btns[finalK].setStyle("-fx-background-color: " + "red" + ";"));
                k++;

            }
        }

        TextField myTextField = new TextField();
        HBox hbox = new HBox(board_button);
        hbox.setPrefWidth(400);
        hbox.setPrefHeight(400);
        VBox vbox = new VBox(hbox);

        hbox.getChildren().add(myTextField);
        HBox.setHgrow(myTextField, Priority.ALWAYS);


        for(int i = 0; i < 8; i++){
            board_button.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board_button.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        Scene scene = new Scene(vbox, 650, 450);
        primaryStage.setTitle("Sovetsky Soyuz");

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer(){  //this is the gay part that doesnt work
            @Override
            public void handle(long now){

            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
