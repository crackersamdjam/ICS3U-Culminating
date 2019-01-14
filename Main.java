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
import java.util.*;
import java.io.*;


public class Main extends Application{

    static final int white = 0, black = 1;
    static Button[][] btns = new Button[8][8];
    static GridPane board_button = new GridPane();

    public static void setColour(int j, int i, boolean red){
        i--; i = 7-i; j--;
        if(red)
            btns[i][j].setStyle("-fx-background-color: darkseagreen ;");
        else{
            String colour = ((i+j)&1) == 1 ? "darkkhaki" : "palegoldenrod";
            btns[i][j].setStyle("-fx-background-color: " + colour + ";");
        }
    }
    public static void rmColour(int j, int i){
        i--; i = 7-i; j--;
        String colour = ((i+j)&1) == 1 ? "brown" : "beige";
        btns[i][j].setStyle("-fx-background-color: " + colour + ";");
    }
    public static void clearColour(){
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++)
                rmColour(i, j);
    }

    public static void flip(int colour){
        int value = colour == white ? 0 : 180;
        board_button.setRotate(value);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                btns[i][j].setRotate(value);
            }
        }
    }

    public static void initGame(){
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                btns[i][j].setGraphic(null);
        Image rw = new Image(Main.class.getResourceAsStream("assets/Rook_white.png"));
        btns[7][0].setGraphic(new ImageView(rw));
        btns[7][7].setGraphic(new ImageView(rw));
        Image kw = new Image(Main.class.getResourceAsStream("assets/Knight_white.png"));
        btns[7][1].setGraphic(new ImageView(kw));
        btns[7][6].setGraphic(new ImageView(kw));
        Image bw = new Image(Main.class.getResourceAsStream("assets/Bishop_white.png"));
        btns[7][2].setGraphic(new ImageView(bw));
        btns[7][5].setGraphic(new ImageView(bw));
        Image Qw = new Image(Main.class.getResourceAsStream("assets/Queen_white.png"));
        btns[7][3].setGraphic(new ImageView(Qw));
        Image Kw = new Image(Main.class.getResourceAsStream("assets/King_white.png"));
        btns[7][4].setGraphic(new ImageView(Kw));

        Image pw = new Image(Main.class.getResourceAsStream("assets/Pawn_white.png"));
        for(int i = 0; i <= 7; i++)
            btns[6][i].setGraphic(new ImageView(pw));

        Image rb = new Image(Main.class.getResourceAsStream("assets/Rook_black.png"));
        btns[0][0].setGraphic(new ImageView(rb));
        btns[0][7].setGraphic(new ImageView(rb));
        Image kb = new Image(Main.class.getResourceAsStream("assets/Knight_black.png"));
        btns[0][1].setGraphic(new ImageView(kb));
        btns[0][6].setGraphic(new ImageView(kb));
        Image bb = new Image(Main.class.getResourceAsStream("assets/Bishop_black.png"));
        btns[0][2].setGraphic(new ImageView(bb));
        btns[0][5].setGraphic(new ImageView(bb));
        Image Qb = new Image(Main.class.getResourceAsStream("assets/Queen_black.png"));
        btns[0][3].setGraphic(new ImageView(Qb));
        Image Kb = new Image(Main.class.getResourceAsStream("assets/King_black.png"));
        btns[0][4].setGraphic(new ImageView(Kb));

        Image pb = new Image(Main.class.getResourceAsStream("assets/Pawn_black.png"));
        for(int i = 0; i <= 7; i++)
            btns[1][i].setGraphic(new ImageView(pb));

        flip(white);
        Board.initGame();
    }

    @Override
    public void start(Stage primaryStage){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Button button_square = new Button();
                String colour = ((i+j)&1) == 1 ? "brown" : "beige";
                button_square.setStyle("-fx-background-color: " + colour + ";");
                button_square.setPrefWidth(80);
                button_square.setPrefHeight(80);
                board_button.add(button_square, j, i);
                btns[i][j] = button_square;
                int finalI = 7-i+1, finalJ = j+1;
                btns[i][j].setOnAction(e -> Board.click(finalJ, finalI));
            }
        }
        // everything is reset
        initGame();

        TextField myTextField = new TextField();
        HBox hbox = new HBox(board_button);
        hbox.setPrefWidth(520);
        hbox.setPrefHeight(520);

        final String[] color = {""};

        //undo button
        Button undoButton = new Button();
        undoButton.setPrefWidth(100);
        undoButton.setPrefHeight(60);
        undoButton.setText("Undo");
        undoButton.setOnAction(event -> Board.undo());
        //undoButton.setStyle("-fx-background-color: red;");

        Button resignButton = new Button();
        resignButton.setPrefWidth(100);
        resignButton.setPrefHeight(60);
        resignButton.setText("Resign");
        resignButton.setOnAction(event -> Board.undo());
        resignButton.setOnAction(e -> {
            if (Board.getTurn() == 1)
                color[0] = "White";
            else
                color[0] = "Black";
            String text = color[0] + " wins by resignation";
            displayEnd(text);
        });

        Button drawButton = new Button();
        drawButton.setPrefWidth(100);
        drawButton.setPrefHeight(60);
        drawButton.setText("Offer draw");

        drawButton.setOnAction(e -> {displayEnd("Draw by agreement");});

        VBox vbox_b = new VBox(undoButton, resignButton, drawButton);

        hbox.getChildren().add(vbox_b);

        vbox_b.setSpacing(10);

        hbox.setSpacing(15);


        //end

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

    public static void displayEnd(String text){

        Label label = new Label(text);
        label.setFont(new Font("Times New Roman", 20));

        Button button = new Button();
        button.setPrefWidth(80);
        button.setPrefHeight(40);
        button.setText("Restart");

        VBox vbox = new VBox(label, button);
        vbox.setAlignment(Pos.CENTER);
        //vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setSpacing(10);

        Scene secondScene = new Scene(vbox, 400, 150);
        Stage newWindow = new Stage();
        newWindow.setTitle("End of Game");
        newWindow.setScene(secondScene);

        newWindow.show();

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                System.out.println("clicked restarted");
                newWindow.close();
                initGame();
                // restart
            }
        });
    }

    static String choice = "";

    public static void windowPromote(int color, int x, int y){

        Label label = new Label("Choose piece to promote to:");
        label.setFont(new Font("Times New Roman", 20));

        Button Queen = new Button();
        Button Rook = new Button();
        Button Bishop = new Button();
        Button Knight = new Button();

        Queen.setPrefWidth(80);
        Queen.setPrefHeight(80);
        Rook.setPrefWidth(80);
        Rook.setPrefHeight(80);
        Bishop.setPrefWidth(80);
        Bishop.setPrefHeight(80);
        Knight.setPrefWidth(80);
        Knight.setPrefHeight(80);

        String type = color == white? "white" : "black";

        Image a = new Image(Main.class.getResourceAsStream("assets/Rook_"+ type+ ".png"));
        Rook.setGraphic(new ImageView(a));
        Image b = new Image(Main.class.getResourceAsStream("assets/Knight_"+ type+ ".png"));
        Knight.setGraphic(new ImageView(b));
        Image c = new Image(Main.class.getResourceAsStream("assets/Bishop_"+ type+ ".png"));
        Bishop.setGraphic(new ImageView(c));
        Image d = new Image(Main.class.getResourceAsStream("assets/Queen_"+ type+ ".png"));
        Queen.setGraphic(new ImageView(d));

        HBox hbox = new HBox(Queen, Rook, Bishop, Knight);
        hbox.setAlignment(Pos.BASELINE_CENTER);

        Scene secondScene = new Scene(hbox, 400, 150);
        Stage newWindow = new Stage();
        newWindow.setTitle("Promotion!");
        newWindow.setScene(secondScene);

        newWindow.show();

        Queen.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                choice = "Queen";
                System.out.println("choose queen");
                newWindow.close();
                setPiece(color, x, y, choice);
                Board.setPiece(color, x, y, choice);
            }
        });
        Rook.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                choice = "Rook";
                newWindow.close();
                setPiece(color, x, y, choice);
                Board.setPiece(color, x, y, choice);
            }
        });
        Bishop.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                choice = "Bishop";
                newWindow.close();
                setPiece(color, x, y, choice);
                Board.setPiece(color, x, y, choice);
            }
        });
        Knight.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                choice = "Knight";
                newWindow.close();
                setPiece(color, x, y, choice);
                Board.setPiece(color, x, y, choice);
            }
        });
    }

    public static void setPiece(int color, int y, int x, String type) {
        x--; x = 7-x; y--;

        if(type.equals("null")){
            btns[x][y].setGraphic(null);
            return;
        }

        String colour_str = color == white? "white" : "black";
        Image image = new Image(Main.class.getResourceAsStream("assets/" + type + "_" + colour_str + ".png"));
        btns[x][y].setGraphic(new ImageView(image));
    }

    public static void movePiece(int y, int x, int oldY, int oldX, String type, int color) {

        x--; x = 7-x; y--; oldX--; oldX = 7-oldX; oldY--;

        String colour_str = color == white? "white" : "black";
        Image image = new Image(Main.class.getResourceAsStream("assets/" + type + "_" + colour_str + ".png"));
        btns[x][y].setGraphic(new ImageView(image));
        btns[oldX][oldY].setGraphic(null);
    }

    public static void main(String[] args){
        launch(args);
    }
}
