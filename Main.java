package sample;
import javafx.event.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.animation.AnimationTimer;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;


public class Main extends Application{

    // player colours
    static final int white = 0, black = 1;

    // this is the board that detects clicks and displays pieces
    static Button[][] btns = new Button[8][8];

    // holds buttons
    static GridPane board_button = new GridPane();

    // booleans to store whether player offered draw, opponent offered draw last move
    static boolean drawOffered, offerDraw;

    // colour of dark squares
    static  String color2 = "";
    // colour of light squares
    static  String color1 = "";

    // options for board colours
    static final String colors1[] =  {"darkkhaki", "sienna", "peru", "dimgrey", "darkcyan", "steelblue"};
    static final String colors2[] =  {"palegoldenrod", "blanchedalmond", "moccasin", "tan", "white", "white"};
    // move number
    static int num;

    // sets board to random colour
    public static void randomColor(){
        num = (int)(Math.random()*colors1.length);
        color1 = colors1[num];
        color2 = colors2[num];
    }

    // sets colour of square j, i to 'colour'
    // if 'colour' is empty, sets to default
    public static void setColour(int j, int i, String colour){
        i--; i = 7-i; j--;
        if(colour.equals("green"))
            btns[i][j].setStyle("-fx-background-color: darkseagreen;");
        else if(colour.equals("red"))
            btns[i][j].setStyle("-fx-background-color: red;");
        else{
            colour = ((i+j)&1) == 1 ? color1 : color2;
            btns[i][j].setStyle("-fx-background-color: " + colour + ";");
        }
    }

    // resets colour of entire board
    public static void clearColour(){
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++)
                setColour(i, j, "");
    }

    // flips the board
    public static void flip(int colour){
        int value = colour == white ? 0 : 180;
        board_button.setRotate(value);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                btns[i][j].setRotate(value);
            }
        }
    }

    // button to offer draw
    static Button drawButton = new Button();

    // when turn changes, check if the previous player offered a draw
    public static void cycleDraw(){
        drawButton.setStyle("fx-background-color: transparent;");
        drawOffered = offerDraw;
        offerDraw = false;
        drawButton.setText(drawOffered? "Accept Draw" : "Offer Draw");
    }

    // initialize the game
    public static void initGame(){
        randomColor();
        Board.popAll();
        outputPgn("");
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

        cycleDraw();
        cycleDraw();
        clearColour();
        flip(white);
        Board.initGame();
    }

    // log --> movelog
    // file --> "pgn.txt" in project folder
    // RAF to edit file
    static Label log = new Label("");
    static File file;
    static RandomAccessFile randomFile;

    // outputs current pgn on screen and to file
    public static void outputPgn(String str){
        log.setText(str);
        try{
            //empty file first
            randomFile = new RandomAccessFile(file, "rw");
            randomFile.setLength(0);
            randomFile.close();

            randomFile = new RandomAccessFile(file, "rw");
            randomFile.writeChars(str + "\n");
            randomFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage){

        try{
            file = new File("pgn.txt");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Button button_square = new Button();
                String colour = ((i+j)&1) == 1 ? color1 : color2;
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

        HBox hbox = new HBox(board_button);
        hbox.setPrefWidth(520);
        hbox.setPrefHeight(520);

        //undo button
        Button undoButton = new Button();
        undoButton.setPrefWidth(100);
        undoButton.setPrefHeight(60);
        undoButton.setText("Undo");
        undoButton.setOnAction(event -> Board.undo());

        Button resignButton = new Button();
        resignButton.setPrefWidth(100);
        resignButton.setPrefHeight(60);
        resignButton.setText("Resign");
        resignButton.setOnAction(event -> Board.undo());
        resignButton.setOnAction(e -> {
            if (Board.getTurn() == 1)
                displayEnd("White wins by resignation");
            else
                displayEnd("Black wins by resignation");

        });

        // dark mode not yet supported
        /*
        Button darkButton = new Button();
        darkButton.setPrefWidth(100);
        darkButton.setPrefHeight(60);
        darkButton.setText("Dark Mode");
        darkButton.setOnAction(e -> {
                    color1 = "black";
                    color2 = "grey";
                    clearColour();
                }
        );
        */

        drawButton.setPrefWidth(100);
        drawButton.setPrefHeight(60);
        drawButton.setText("Offer draw");

        drawButton.setOnAction(e -> {
            offerDraw = true;
            drawButton.setStyle("-fx-background-color: darkseagreen;");
            if(drawOffered)
                displayEnd("Draw by agreement");
        });

        VBox vbox_b = new VBox(undoButton, resignButton, drawButton);
        // dark mode in development
        //vbox_b.getChildren().add(darkButton);

        ScrollPane sp = new ScrollPane();

        sp.setContent(log);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        sp.setPrefWidth(240);

        vbox_b.setSpacing(10);
        hbox.setSpacing(15);

        hbox.setSpacing(10);
        hbox.getChildren().addAll(vbox_b, sp);

        for(int i = 0; i < 8; i++){
            board_button.getColumnConstraints().add(new ColumnConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board_button.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        Scene scene = new Scene(hbox, 1000, 640);
        primaryStage.setTitle("Chess");
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

    // display message 'text' on end screen
    public static void displayEnd(String text){

        Label label = new Label(text);
        label.setFont(new Font("Times New Roman", 20));

        Button restartButton = new Button();
        restartButton.setPrefWidth(80);
        restartButton.setPrefHeight(40);
        restartButton.setText("Restart");

        VBox vbox = new VBox(label, restartButton);
        vbox.setAlignment(Pos.CENTER);
        //vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setSpacing(10);

        Scene secondScene = new Scene(vbox, 400, 150);
        Stage newWindow = new Stage();
        newWindow.setTitle("End of Game");
        newWindow.setScene(secondScene);

        newWindow.show();

        restartButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                System.out.println("clicked restarted");
                newWindow.close();
                initGame();
            }
        });
    }

    // display window for pawn promotion choices
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

    // sets the piece at y, x to type 'type' and colour 'colour
    public static void setPiece(int colour, int y, int x, String type){
        x--; x = 7-x; y--;

        if(type.equals("null")){
            btns[x][y].setGraphic(null);
            return;
        }
        String colour_str = colour == white? "white" : "black";
        Image image = new Image(Main.class.getResourceAsStream("assets/" + type + "_" + colour_str + ".png"));
        btns[x][y].setGraphic(new ImageView(image));
    }

    // move the piece at oldY, oldX --> y, x
    // type and colour of the piece is passed into function as well
    public static void movePiece(int y, int x, int oldY, int oldX, String type, int colour){
        x--; x = 7-x; y--; oldX--; oldX = 7-oldX; oldY--;

        String colour_str = colour == white? "white" : "black";
        Image image = new Image(Main.class.getResourceAsStream("assets/" + type + "_" + colour_str + ".png"));
        btns[x][y].setGraphic(new ImageView(image));
        btns[oldX][oldY].setGraphic(null);
    }

    public static void main(String[] args){
        launch(args);
    }
}
