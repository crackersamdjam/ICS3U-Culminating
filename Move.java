package sample;

public class Move{

    int startX, startY, endX, endY, num, colour;
    // starting x, y; ending x, y; move number; colour of piece moved

    Piece last;
    // Piece of piece moved

    boolean[] a, b;
    // castling conditions (see Board.java)

    String pgnf;
    // pgn format of current move

    public Move(int startX, int startY, int endX, int endY, Piece last, Piece current,
                int num, int colour, boolean[] a, boolean[] b, int duplicate){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.last = last;
        this.num = num;
        this.colour = colour;
        this.a = a;
        this.b = b;

        // everything below here is for the standard pgn format of chess games

        pgnf = "" + this.num + ". ";

        // castled
        if(current.type.equals("King") && Math.abs(startX - endX) == 2){
            if(endX == 3)
                pgnf = pgnf + "O-O-O";
            else
                pgnf = pgnf + "O-O";
        }
        else{
            switch(current.type){
                case "Rook":
                    pgnf = pgnf + "R";
                    break;
                case "Knight":
                    pgnf = pgnf + "N";
                    break;
                case "Bishop":
                    pgnf = pgnf + "B";
                    break;
                case "Queen":
                    pgnf = pgnf + "Q";
                    break;
                case "King":
                    pgnf = pgnf + "K";
                    break;
                case "Pawn":
                    break;
                default:
                    pgnf = pgnf + "error";
                    break;
            }
            //file
            if(duplicate == 1)
                pgnf = pgnf + (char)(startX+'a'-1);

            //rank
            else if(duplicate == 2)
                pgnf = pgnf + startY;

            // if capture, add 'x'
            if(last.exist)
                pgnf = pgnf + "x";

            pgnf = pgnf + (char)(endX+'a'-1) + "" + endY;
        }

        if(colour == 0)
            pgnf = pgnf + "  ";
        else
            pgnf = pgnf + "\n";
    }
}