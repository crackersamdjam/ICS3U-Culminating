package sample;

public class Board{

    static final int white = 0, black = 1;
    static Piece[][] board = new Piece[9][9];
    static boolean hasPieceSelected = false, moveCastle = false;
    static int oldX, oldY;
    static Piece empty = new Piece("null", -1, false);
    static int turn = white;

    // initialize stating position
    public Board(){
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++)
                board[i][j] = empty;
        board[1][1] = new Piece("Rook", white, true);
        board[2][1] = new Piece("Knight", white, true);
        board[3][1] = new Piece("Bishop", white, true);
        board[4][1] = new Piece("Queen", white, true);
        board[5][1] = new Piece("King", white,true);
        board[6][1] = new Piece("Bishop", white, true);
        board[7][1] = new Piece("Knight", white, true);
        board[8][1] = new Piece("Rook", white, true);
        for(int i = 1; i <= 8; i++)
            board[i][2] = new Piece("Pawn", white, true);

        board[1][8] = new Piece("Rook", black,  true);
        board[2][8] = new Piece("Knight", black, true);
        board[3][8] = new Piece("Bishop", black, true);
        board[4][8] = new Piece("Queen", black, true);
        board[5][8] = new Piece("King", black, true);
        board[6][8] = new Piece("Bishop", black, true);
        board[7][8] = new Piece("Knight", black, true);
        board[8][8] = new Piece("Rook", black, true);
        for(int i = 1; i <= 8; i++)
            board[i][7] = new Piece("Pawn", black, true);
    }

    static Piece get(int x, int y){
        return board[x][y];
    }

    // first find king location
    static boolean isCheck(){
        int x = -1, y = -1;
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].colour == turn && board[i][j].type.equals("King")){
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        return isCheck(x, y);
    }

    // using location x, y
    static boolean isCheck(int x, int y){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].colour != turn && board[i][j].isValid(i, j, x, y) && board[i][j].isLegal(i, j, x, y)){
                    System.out.println("piece at " + i + " " + j);
                    return true;
                }
            }
        }
        return false;
    }


    static boolean isStalemate(){
        // check making every move possible
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].colour == turn){

                    for(int k = 1; k <= 8; k++){
                        for(int l = 1; l <= 8; l++){
                            if(board[i][j].isValid(i, j, k, l) && board[i][j].isLegal(i, j, k, l)){
                                // move the piece
                                Piece temp = board[k][l].copy();
                                board[k][l] = board[i][j].copy();
                                board[i][j] = empty;

                                boolean flag = isCheck();

                                // reset pieces
                                board[i][j] = board[k][l].copy();
                                board[k][l] = temp.copy();

                                // if not check after move, then not stalemate
                                if(!flag)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    static void click(int x, int y){

        char file = (char)(x+'a'-1);
        System.out.println("click " + file + y);

        if(hasPieceSelected){
            // moving to this square

            hasPieceSelected = false;
            Main.rmColour(oldX, oldY);

            if(board[oldX][oldY].isCastle(oldX, oldY, x, y)){
                if(x > oldX){
                    // castling moves two squares
                    for(int i = oldX; i <= oldX+2; i++){
                        if(isCheck(i, y)){
                            System.out.println("no castle");
                            return;
                        }
                    }
                }
                else{
                    for(int i = oldX; i >= oldX-2; i--){
                        if(isCheck(i, y)){
                            System.out.println("no castle");
                            return;
                        }
                    }
                }
                moveCastle = true;
            }
            else{
                // check if valid move
                if(!board[oldX][oldY].isValid(oldX, oldY, x, y)){
                    //why is this running after successful move
                    System.out.println("Invalid move, piece has been deselected");
                    return;
                }

                // check if move is legal (jumps over pieces)
                if(!board[oldX][oldY].isLegal(oldX, oldY, x, y)){
                    System.out.println("Illegal move");
                    return;
                }
            }

            // move the piece
            Piece temp = board[x][y].copy();
            board[x][y] = board[oldX][oldY].copy();
            board[oldX][oldY] = empty;

            // check if move results in check
            if(isCheck()){
                System.out.println("Results in check, move reset");

                // reset pieces
                board[oldX][oldY] = board[x][y].copy();
                board[x][y] = temp.copy();
                return;
            }

            // promotion
            if(turn == white && y == 8 && board[x][y].type.equals("Pawn")){
                Main.windowPromote(turn, x, y);
            }
            else if(turn == black && y == 1 && board[x][y].type.equals("Pawn")){
                Main.windowPromote(turn, x, y);
            }

            Main.movePiece(x, y, oldX, oldY, board[x][y].type, board[x][y].colour);
            turn ^= 1;

            // if castle, move rook as well
            if(moveCastle){
                if(turn != white){
                    //white
                    if(x == 3){
                        //queenside
                        board[4][1] = board[1][1].copy();
                        board[1][1] = empty;
                        Main.movePiece(4, 1, 1, 1, "Rook", white);
                    }
                    else{
                        board[6][1] = board[8][1].copy();
                        board[8][1] = empty;
                        Main.movePiece(6, 1, 8, 1, "Rook", white);
                    }
                }
                else{
                    //black
                    if(x == 3){
                        board[4][8] = board[1][8].copy();
                        board[1][8] = empty;
                        Main.movePiece(4, 8, 1, 8, "Rook", black);
                    }
                    else{
                        board[6][8] = board[8][8].copy();
                        board[8][8] = empty;
                        Main.movePiece(6, 8, 8, 8, "Rook", black);
                    }
                }
            }
            moveCastle = false;

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

            if(isStalemate()){

                // checkmate
                if(isCheck()){
                    int winner = turn ^ 1;
                    Main.displayEnd((winner == white ? "White" : "Black") + " checkmated the opponent!");
                }
                // stalemate
                else{
                    Main.displayEnd(turn == white? "White": "Black" + " is stalemated!");
                }
            }
            else{
                Main.flip(turn);
            }
        }
        else{
            if(board[x][y].type.equals("null")){
                System.out.println("Nothing to select");
                return;
            }
            if(board[x][y].colour != turn){
                System.out.println("Wrong coloured piece");
                return;
            }
            hasPieceSelected = true;
            oldX = x;
            oldY = y;
            Main.setColour(x, y);
        }
    }
    public static void setPiece(int color, int x, int y, String choice){
        board[x][y] = new Piece(choice, color, true);
    }
}
