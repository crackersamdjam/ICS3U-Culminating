package sample;
import javafx.scene.Group;
import javafx.event.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application{

    int run = 0;
    static Button[][] btns = new Button[8][8];

    public static void setColour (int j, int i){
        i--;
        i = 7-i;
        j--;
        btns[i][j].setStyle("-fx-background-color: " + "red" + ";");
    }
    public static void rmColour (int j, int i){
        i--;
        i = 7-i;
        j--;
        String colour;
        if((i + j) % 2 == 0){
            colour = "beige";
        }else{
            colour = "brown";
        }
        btns[i][j].setStyle("-fx-background-color: " + colour + ";");

    }

    static Board board = new Board();

    @Override
    public void start(Stage primaryStage){
        GridPane board_button = new GridPane();
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
                button_square.setPrefWidth(80);
                button_square.setPrefHeight(80);
                board_button.add(button_square, j, i);
                btns[i][j] = button_square;
                int finalI = 7-i+1;
                int finalJ = j+1;
                // sketchy but works
                btns[i][j].setOnAction(e -> Board.click(finalJ, finalI));

            }
        }


            Image rw = new Image(getClass().getResourceAsStream("assets/Rook_white.png"));
            btns[7][0].setGraphic(new ImageView(rw));
            btns[7][7].setGraphic(new ImageView(rw));
            Image kw = new Image(getClass().getResourceAsStream("assets/Knight_white.png"));
            btns[7][1].setGraphic(new ImageView(kw));
            btns[7][6].setGraphic(new ImageView(kw));
            Image bw = new Image(getClass().getResourceAsStream("assets/Bishop_white.png"));
            btns[7][2].setGraphic(new ImageView(bw));
            btns[7][5].setGraphic(new ImageView(bw));
            Image Qw = new Image(getClass().getResourceAsStream("assets/Queen_white.png"));
            btns[7][3].setGraphic(new ImageView(Qw));
            Image Kw = new Image(getClass().getResourceAsStream("assets/King_white.png"));
            btns[7][4].setGraphic(new ImageView(Kw));

            Image pw = new Image(getClass().getResourceAsStream("assets/Pawn_white.png"));
            for (int i = 0; i <= 7; i++) {
                btns[6][i].setGraphic(new ImageView(pw));
            }

            Image rb = new Image(getClass().getResourceAsStream("assets/Rook_black.png"));
            btns[0][0].setGraphic(new ImageView(rb));
            btns[0][7].setGraphic(new ImageView(rb));
            Image kb = new Image(getClass().getResourceAsStream("assets/Knight_black.png"));
            btns[0][1].setGraphic(new ImageView(kb));
            btns[0][6].setGraphic(new ImageView(kb));
            Image bb = new Image(getClass().getResourceAsStream("assets/Bishop_black.png"));
            btns[0][2].setGraphic(new ImageView(bb));
            btns[0][5].setGraphic(new ImageView(bb));
            Image Qb = new Image(getClass().getResourceAsStream("assets/Queen_black.png"));
            btns[0][3].setGraphic(new ImageView(Qb));
            Image Kb = new Image(getClass().getResourceAsStream("assets/King_black.png"));
            btns[0][4].setGraphic(new ImageView(Kb));

            Image pb = new Image(getClass().getResourceAsStream("assets/Pawn_black.png"));
            for (int i = 0; i <= 7; i++) {
                btns[1][i].setGraphic(new ImageView(pb));

        }

        TextField myTextField = new TextField();
        HBox hbox = new HBox(board_button);
        hbox.setPrefWidth(520);
        hbox.setPrefHeight(520);
        VBox vbox = new VBox(hbox);
        hbox.getChildren().add(myTextField);
        HBox.setHgrow(myTextField, Priority.ALWAYS);


        for(int i = 0; i < 8; i++){
            board_button.getColumnConstraints().add(new ColumnConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board_button.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        Scene scene = new Scene(vbox, 1000, 640);
        primaryStage.setTitle("assets/Shahmati");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer(){
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

    public static void movePiece(int y, int x, int oldY, int oldX, String type, int color) {

        // sketchy conversions
        x--;
        x = 7-x;
        y--;
        oldX--;
        oldX = 7-oldX;
        oldY--;

        if (color == 0) {
            switch (type) {

                case "Rook":
                    Image rw = new Image(Main.class.getResourceAsStream("assets/Rook_white.png"));
                    btns[x][y].setGraphic(new ImageView(rw));
                    return;
                case "Knight":
                    Image kw = new Image(Main.class.getResourceAsStream("assets/Knight_white.png"));
                    btns[x][y].setGraphic(new ImageView(kw));
                    return;
                case "Bishop":
                    Image bw = new Image(Main.class.getResourceAsStream("assets/Bishop_white.png"));
                    btns[x][y].setGraphic(new ImageView(bw));
                    return;
                case "Queen":
                    Image Qw = new Image(Main.class.getResourceAsStream("assets/Queen_white.png"));
                    btns[x][y].setGraphic(new ImageView(Qw));
                    return;
                case "King":
                    Image Kw = new Image(Main.class.getResourceAsStream("assets/King_white.png"));
                    btns[x][y].setGraphic(new ImageView(Kw));
                    return;
                case "Pawn":
                    Image pw = new Image(Main.class.getResourceAsStream("assets/Pawn_white.png"));
                    btns[x][y].setGraphic(new ImageView(pw));
                    return;
            }
        } else {
            switch (type) {
                case "Rook":
                    Image rb = new Image(Main.class.getResourceAsStream("assets/Rook_black.png"));
                    btns[x][y].setGraphic(new ImageView(rb));
                    return;
                case "Knight":
                    Image kb = new Image(Main.class.getResourceAsStream("assets/Knight_black.png"));
                    btns[x][y].setGraphic(new ImageView(kb));
                    return;
                case "Bishop":
                    Image bb = new Image(Main.class.getResourceAsStream("assets/Bishop_black.png"));
                    btns[x][y].setGraphic(new ImageView(bb));
                    return;
                case "Queen":
                    Image Qb = new Image(Main.class.getResourceAsStream("assets/Queen_black.png"));
                    btns[x][y].setGraphic(new ImageView(Qb));
                    return;
                case "King":
                    Image Kb = new Image(Main.class.getResourceAsStream("assets/King_black.png"));
                    btns[x][y].setGraphic(new ImageView(Kb));
                    return;
                case "Pawn":
                    Image pb = new Image(Main.class.getResourceAsStream("assets/Pawn_black.png"));
                    btns[x][y].setGraphic(new ImageView(pb));
                    return;
            }
        }
        Image blank = new Image(Main.class.getResourceAsStream("80x80.png"));
        btns[oldX][oldY].setGraphic(new ImageView(blank));
    }
}
