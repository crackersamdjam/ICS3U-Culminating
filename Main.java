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

    static final int white = 0, black = 1;
    static Board board = new Board();
    static Button[][] btns = new Button[8][8];

    public static void setColour (int j, int i){
        i--; i = 7-i; j--;
        btns[i][j].setStyle("-fx-background-color: " + "red" + ";");
    }
    public static void rmColour (int j, int i){
        i--; i = 7-i; j--;
        String colour = ((i+j)&1) == 1 ? "brown" : "beige";
        btns[i][j].setStyle("-fx-background-color: " + colour + ";");

    }

    @Override
    public void start(Stage primaryStage){
        GridPane board_button = new GridPane();
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
windowPromote(1);

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
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(label);
        Scene secondScene = new Scene(secondaryLayout, 400, 150);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("End of Game");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    public static String windowPromote(int color){

        String type = "";
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

        if(color == 0)
            type = "white";
        else
            type = "black";

        Image a = new Image(Main.class.getResourceAsStream("assets/Rook_"+ type+ ".png"));
        Rook.setGraphic(new ImageView(a));
        Image b = new Image(Main.class.getResourceAsStream("assets/Knight_"+ type+ ".png"));
        Knight.setGraphic(new ImageView(b));
        Image c = new Image(Main.class.getResourceAsStream("assets/Bishop_"+ type+ ".png"));
        Bishop.setGraphic(new ImageView(c));
        Image d = new Image(Main.class.getResourceAsStream("assets/Queen_"+ type+ ".png"));
        Queen.setGraphic(new ImageView(d));


        StackPane secondaryLayout = new StackPane();
        HBox hbox = new HBox(Queen, Rook, Bishop, Knight);
        hbox.setAlignment(Pos.BASELINE_CENTER);

        Scene secondScene = new Scene(hbox, 400, 150);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Promotion!");
        newWindow.setScene(secondScene);

        newWindow.show();
        String[] choice = {"test"};

        Queen.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                choice[0] = "Queen";
                newWindow.close();
            }
        });
        Queen.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                choice[0] = "Rook";
                newWindow.close();            }
        });
        Bishop.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                choice[0] = "Bishop";
                newWindow.close();            }
        });
        Knight.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                choice[0] = "Knight";
                newWindow.close();            }
        });
        System.out.println(choice[0]);
        return choice[0];
    }

    //move image to new button, delete old image from old button
    public static void movePiece(int y, int x, int oldY, int oldX, String type, int color) {

        // sketchy conversions
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
