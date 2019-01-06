package sample;

/*
all processing happens here
has direct access to board
 */

public class Board{

    static Piece[][] board = new Piece[9][9];
    static boolean hasPieceSelected = false, enPassant = false;
    static int oldX, oldY;
    static Piece empty = new Piece("null", -1, false);
    static final int white = 0, black = 1;

    static int turn = white;

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

    static Piece get(int x, int y){
        return board[x][y];
    }

    static void setEnPassant(){
        enPassant = true;
    }

    //0 = white, 1 = black
    static boolean isCheck(int colour){
        int x = -1, y = -1;
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].colour == colour && board[i][j].type.equals("King")){
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].isValid(i, j, x, y))
                    return false;
            }
        }
        return true;
    }

    static void click(int x, int y){
        System.out.println("Stalin says do stuff\n");

        if(hasPieceSelected){
            //moving to this square

            //check if valid move
            if(!board[x][y].isValid(oldX, oldY, x, y)){
                System.out.println("Invalid move, piece has been deselected");
                hasPieceSelected = false;
                return;
            }

            //check if move is legal (jumps over pieces)
            if(!board[x][y].isLegal(oldX, oldY, x, y)){
                System.out.println("Illegal move");
                hasPieceSelected = false;
                return;
            }

            // check if move results in check
            if(isCheck(turn)){
                System.out.println("Results in check, move reset");
                hasPieceSelected = false;
                return;
            }
            // move the piece (reset en passant)

        }
        else{
            if(board[x][y].type.equals("null")){
                System.out.println("nothing to select");
                return;
            }
            if(board[x][y].colour != turn){
                System.out.println("Wrong coloured piece");
                return;
            }
            hasPieceSelected = true;
            oldX = x;
            oldY = y;
        }
    }
}