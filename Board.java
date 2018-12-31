package sample;
public class Board{

    static Piece[][] board = new Piece[9][9];
    static boolean hasPieceSelected = false;
    static int oldX, oldY;
    static Piece empty = new Piece("null", -1, false);
    static final int white = 0, black = 1;

    // initialize stating position
    public Board(){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                board[i][j] = empty;
            }
        }
        board[1][1] = new Piece("Rook", white, true);
        board[2][1] = new Piece("Knight", white, true);
        board[3][1] = new Piece("Bishop", white, true);
        board[4][1] = new Piece("Queen", white, true);
        board[5][1] = new Piece("King", white,true);
        board[6][1] = new Piece("Bishop", white, true);
        board[7][1] = new Piece("Knight", white, true);
        board[8][1] = new Piece("Rook", white, true);
        for(int i = 1; i <= 8; i++){
            board[i][2] = new Piece("Pawn", white, true);
        }

        board[1][8] = new Piece("Rook", black,  true);
        board[2][8] = new Piece("Knight", black, true);
        board[3][8] = new Piece("Bishop", black, true);
        board[4][8] = new Piece("Queen", black, true);
        board[5][8] = new Piece("King", black, true);
        board[6][8] = new Piece("Bishop", black, true);
        board[7][8] = new Piece("Knight", black, true);
        board[8][8] = new Piece("Rook", black, true);
        for(int i = 1; i <= 8; i++){
            board[i][7] = new Piece("Pawn", black, true);
        }
    }

    static void click(int x, int y){
        System.out.println("Stalin says do stuff\n");

        if(hasPieceSelected){
            //moving to this square

            if(!board[x][y].isValid(oldX, oldY, x, y)){
                System.out.println("Invalid move, piece has been deselected");
                hasPieceSelected = false;
                return;
            }

            // check if legal

            // check if check

            // move the piece

        }
        else{
            if(board[x][y].type == "null"){
                System.out.println("nothing to select");
                return;
            }
            hasPieceSelected = true;
            oldX = x;
            oldY = y;
        }
    }
}
/*
all processing happens here
has direct access to board
 */