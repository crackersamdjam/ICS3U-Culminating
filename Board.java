package sample;

public class Board{

    static Piece[][] board = new Piece[9][9];
    static boolean hasPieceSelected = false, enPassant = false;
    static int oldX, oldY;
    static Piece empty = new Piece("null", -1, false);
    static final int white = 0, black = 1;

    public static String textUpdate = "";

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
    static boolean isCheck(int x, int y){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].isValid(i, j, x, y))
                    return false;
            }
        }
        return true;
    }

    static void click(int x, int y){
        String letter = "";
        switch(x){
            case 1:
                letter = "a";
                break;
            case 2:
                letter = "b";
                break;
            case 3:
                letter = "c";
                break;
            case 4:
                letter = "d";
                break;
            case 5:
                letter = "e";
                break;
            case 6:
                letter = "f";
                break;
            case 7:
                letter = "g";
                break;
            case 8:
                letter = "h";
                break;
        }
        System.out.println("click " + letter + y);
        textUpdate = "click " + letter + y;

        if(hasPieceSelected){
            // moving to this square
            // rm color
            Main.rmColour(oldX, oldY);


            if(board[oldX][oldY].isCastle(oldX, oldY, x, y)){
                if(x > oldX){
                    for(int i = oldX; i <= x; i++){
                        if(isCheck(i, y)){
                            System.out.println("no castle");
                            return;
                        }
                    }
                }
                else{
                    for(int i = oldX; i >= x; i--){
                        if(isCheck(i, y)){
                            System.out.println("no castle");
                            return;
                        }
                    }
                }
            }
            else{
                // check if valid move
                if(!board[oldX][oldY].isValid(oldX, oldY, x, y)){
                    System.out.println("Invalid move, piece has been deselected");
                    textUpdate = "Invalid move, piece has been deselected";
                    hasPieceSelected = false;
                    return;
                }

                // check if move is legal (jumps over pieces)
                if(!board[oldX][oldY].isLegal(oldX, oldY, x, y)){
                    System.out.println("Illegal move");
                    textUpdate = "Illegal move";
                    hasPieceSelected = false;
                    return;
                }
            }

            // check if move results in check
            if(isCheck(turn)){
                System.out.println("Results in check, move reset");
                textUpdate = "Results in check, move reset";
                hasPieceSelected = false;
                return;
            }
            // move the piece (reset en passant too)

            board[x][y] = board[oldX][oldY];
            board[oldX][oldY] = empty;
            // this works

            Main.movePiece(x, y, oldX, oldY);
            turn ^= 1;

            // reset castling
            if(oldX == 1 && oldY == 1)
                Piece.castleOp(white, false);
            else if(oldX == 8 && oldY == 1)
                Piece.castleOp(white, true);
            else if(oldX == 1 && oldY == 8)
                Piece.castleOp(black, false);
            else if(oldX == 8 && oldY == 8)
                Piece.castleOp(black, true);
            else if(oldX == 5 && oldY == 1){
                Piece.castleOp(white, false);
                Piece.castleOp(white, true);
            }
            else if(oldX == 5 && oldY == 8){
                Piece.castleOp(black, false);
                Piece.castleOp(black, true);
            }

        }
        else{
            if(board[x][y].type.equals("null")){
                System.out.println("nothing to select");
                textUpdate = "nothing to select";
                return;
            }
            if(board[x][y].colour != turn){
                System.out.println("Wrong coloured piece");
                textUpdate =  "Wrong coloured piece";
                return;
            }
            hasPieceSelected = true;
            oldX = x;
            oldY = y;
            Main.setColour(x, y);
        }
    }
}
