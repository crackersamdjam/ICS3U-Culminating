package sample;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Board{

    static final int white = 0, black = 1;
    // player colours

    static Piece[][] board = new Piece[9][9];
    // board

    static boolean hasPieceSelected, moveCastle;
    // whether a piece has been selected; whether use intended to castle

    static int oldX, oldY, turn, moveNum;
    // previous x, y; who's turn; move number

    static Piece empty = new Piece("null", -1, false);
    // empty piece

    static ArrayDeque<Move> moveLog = new ArrayDeque<>();
    // move log

    static boolean[] king = {true, true}, queen = {true, true};
    // castling conditions for <side>[] = {white, black}

    // reset when game restarts
    public static void resetCastling(){
        king[0] = king[1] = queen[0] = queen[1] = true;
    }

    // when move is undone, go back to these castling conditions
    public static void undoCastle(boolean[] a, boolean[] b){
        king[0] = a[0];
        king[1] = a[1];
        queen[0] = b[0];
        queen[1] = b[1];
    }

    // get kingside castling conditions
    public static boolean[] getKing(){
        return king;
    }
    // get queenside casltine conditions
    public static boolean[] getQueen(){
        return queen;
    }

    // copy castling conditions by value
    public static boolean[] copy(boolean[] arr){
        boolean[] temp = new boolean[2];
        temp[0] = arr[0];
        temp[1] = arr[1];
        return temp;
    }

    // remove the option of castling for player 'colour' and to the 'side' side
    public static void castleOp(int colour, boolean side){
        if(!side)
            queen[colour] = false;
        else
            king[colour] = false;
    }

    // initialize game
    public static void initGame(){
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

        turn = white;
        moveNum = 1;
        hasPieceSelected = false;
        moveCastle = false;
        resetCastling();
    }

    // set the position x, y to this piece of 'colour' colour and 'type' type
    public static void setPiece(int colour, int x, int y, String type){
        board[x][y] = new Piece(type, colour, true);
    }

    // get piece at x, y
    static Piece get(int x, int y){
        return board[x][y];
    }

    // get who's turn it is
    public static int getTurn(){
        return turn;
    }

    // find the king's location
    // then check whether that location is being attacked
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

    // check whether square x, y is being attacked
    static boolean isCheck(int x, int y){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(board[i][j].colour != turn && board[i][j].isValid(i, j, x, y) && board[i][j].isLegal(i, j, x, y))
                    return true;
            }
        }
        return false;
    }

    // check whether current position is stalemate by trying every move
    static boolean isStalemate(){
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

    // clear the move log when game is reset
    public static void popAll(){
        while(!moveLog.isEmpty())
            moveLog.pop();
    }

    // return movelog as String
    public static String printPgn(){
        String str = "";
        for(Move mv: moveLog){
            str = str + mv.pgnf;
        }
        return str;
    }

    // whether square x, y is reachable by another piece with same type and colour
    // this is used when deciding whether extra number or letter is required for pgn format
    public static int reachable(int x, int y, String type, int colour, int oldX, int oldY){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if(i == oldX && j == oldY)
                    continue;
                if(board[i][j].type.equals(type) && board[i][j].colour == colour){
                    if(board[i][j].isLegal(i, j, x, y) && board[i][j].isValid(i, j, x, y)){
                        //file
                        if(j == y)
                            return 1;
                        else
                            return 2;
                        //rank
                    }
                }
            }
        }
        return 0;
    }

    // undo last move
    static void undo(){
        if(moveLog.isEmpty()){
            System.out.println("nothing to undo");
            return;
        }

        Main.cycleDraw();
        Main.cycleDraw();

        Move mv = moveLog.peekLast();
        moveLog.pollLast();

        board[mv.startX][mv.startY] = board[mv.endX][mv.endY].copy();
        board[mv.endX][mv.endY] = mv.last.copy();

        Main.setPiece(mv.colour, mv.startX, mv.startY, board[mv.startX][mv.startY].type);
        Main.setPiece(mv.colour^1, mv.endX, mv.endY, mv.last.type);

        int rank = board[mv.startX][mv.startY].colour == white? 1: 8;
        if(board[mv.startX][mv.startY].type.equals("King") && mv.startX == 5 && mv.startY == rank){
            if(mv.endX == 7){
                board[8][rank] = board[6][rank].copy();
                board[6][rank] = empty;
                Main.setPiece(-1, 6, rank, "null");
                Main.setPiece(mv.colour, 8, rank, "Rook");
            }
            else if(mv.endX == 3){
                board[1][rank] = board[4][rank].copy();
                board[4][rank] = empty;
                Main.setPiece(-1, 4, rank, "null");
                Main.setPiece(mv.colour, 1, rank, "Rook");
            }
        }

        undoCastle(mv.a, mv.b);

        moveNum--;
        turn ^= 1;
        Main.flip(turn);

        Main.clearColour();
        if(!moveLog.isEmpty()){
            Move pre = moveLog.peekLast();
            Main.setColour(pre.startX, pre.startY, "");
            Main.setColour(pre.endX, pre.endY, "");
        }

        Main.setText(printPgn());
    }

    // method called when square x, y is clicked
    static void click(int x, int y){

        if(hasPieceSelected){
            // moving to this square

            hasPieceSelected = false;
            Main.rmColour(oldX, oldY);

            if(board[oldX][oldY].isCastle(oldX, oldY, x, y)){
                if(x > oldX){
                    // castling moves two squares
                    for(int i = oldX; i <= oldX+2; i++){
                        if(isCheck(i, y))
                            return;
                    }
                }
                else{
                    for(int i = oldX; i >= oldX-2; i--){
                        if(isCheck(i, y))
                            return;
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

            int duplicate = reachable(x, y, board[oldX][oldY].type, board[oldX][oldY].colour, oldX, oldY);

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

            // push to move log
            moveLog.addLast(new Move(oldX, oldY, x, y, temp, board[x][y], moveNum, turn, copy(getKing()), copy(getQueen()), duplicate));

            // promotion
            if(turn == white && y == 8 && board[x][y].type.equals("Pawn")){
                Main.windowPromote(turn, x, y);
            }
            else if(turn == black && y == 1 && board[x][y].type.equals("Pawn")){
                Main.windowPromote(turn, x, y);
            }

            Main.movePiece(x, y, oldX, oldY, board[x][y].type, board[x][y].colour);
            Main.clearColour();
            Main.setColour(oldX, oldY, "");
            Main.setColour(x, y, "");
            Main.cycleDraw();
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
                castleOp(white, false);
            else if(oldX == 8 && oldY == 1)
                castleOp(white, true);
            else if(oldX == 1 && oldY == 8)
                castleOp(black, false);
            else if(oldX == 8 && oldY == 8)
                castleOp(black, true);
            else if(oldX == 5 && oldY == 1){
                castleOp(white, false);
                castleOp(white, true);
            }
            else if(oldX == 5 && oldY == 8){
                castleOp(black, false);
                castleOp(black, true);
            }

            if(isCheck()){
                Outer:
                for(int i = 1; i <= 8; i++){
                    for(int j = 1; j <= 8; j++){
                        if(board[i][j].colour == turn && board[i][j].type.equals("King")){
                            Main.setColour(i, j, "red");
                            break Outer;
                        }
                    }
                }
            }

            if(isStalemate()){

                // checkmate
                if(isCheck()){
                    int winner = turn ^ 1;
                    Main.displayEnd((winner == white ? "White" : "Black") + " checkmated the opponent!");
                    return;
                }
                // stalemate
                else{
                    Main.displayEnd(turn == white? "White": "Black" + " is stalemated!");
                    return;
                }
            }
            Main.flip(turn);
            moveNum++;

            Main.setText(printPgn());

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
            Main.setColour(x, y, "green");
        }
    }
}
