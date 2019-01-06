package sample;
public class Piece{

    static final int white = 0, black = 1;


    /*
    Attributes of the class start here
     */

    String type;
    int colour;
    // white = 0, black = 1
    // numbers can be XOR'd

    boolean exist;
    // when captured, exist = false

    // Constructor
    public Piece(String type, int colour, boolean exist){
        this.type = type;
        this.colour = colour;
        this.exist = exist;
    }

    //en passant
    int epx, epy;


    private void promotion(Piece piece){

    }

    public boolean isValid(int x, int y, int newX, int newY){

        if(!exist)
            return false;

        //same spot
        if(newX == x && newY == y)
            return false;

        switch(type){


            case "Rook":
                return (newX == x || newY == y);


            case "Knight":
                if(Math.abs(x - newX) == 1 && Math.abs(y - newY) == 2)
                    return true;
                if(Math.abs(x - newX) == 2 && Math.abs(y - newY) == 1)
                    return true;
                return false;


            case "Bishop":
                return Math.abs(x - newX) == Math.abs(y - newY);


            case "Queen":
                if (newX == x || newY == y)
                    return true;
                if(Math.abs(x - newX) == Math.abs(y - newY))
                    return true;

                return false;


            case "King":
                return (Math.abs(x - newX) <= 1 && Math.abs(y - newY) <= 1);

                
            case "Pawn":
                if(colour == white){

                    //normal move
                    if(newX == x && newY == y+1 && !Board.get(x, y+1).exist){
                        if(newY == 8)
                            promotion(this);
                        return true;
                    }

                    //first move 2 squares
                    if(newX == x && y == 2 && newY == 4 && !Board.get(x, 4).exist){
                        //set en passant
                        epx = x;
                        epy = 3;
                        return true;
                    }

                    //capture
                    if(Math.abs(newX-x) == 1 && newY == y+1){
                        if(Board.get(newX, newY).colour == black)
                            return true;
                        if(epx == newX && epy == newY){
                            Board.setEnPassant();
                            return true;
                        }
                    }
                }
                else{
                    //normal move
                    if(newX == x && newY == y-1 && !Board.get(x, y-1).exist){
                        if(newY == 1)
                            promotion(this);
                        return true;
                    }

                    //first move 2 squares
                    if(newX == x && y == 7 && newY == 5 && !Board.get(x, 7).exist){
                        //set en passant
                        epx = x;
                        epy = 6;
                        return true;
                    }

                    //capture
                    if(Math.abs(newX-x) == 1 && newY == y-1){
                        if(Board.get(newX, newY).colour == white)
                            return true;
                        if((epx == newX && epy == newY)){
                            Board.setEnPassant();
                            return true;
                        }
                    }
                }
                return false;

            default:
                System.out.println("Empty");
                return false;
        }
    }

    public boolean isLegal(int x, int y, int newX, int newY){

        //no need to check pawn, knight, king  - no way of jumping over

        switch(this.type){

            case "Rook":
                break;

            case "Bishop":
                break;

            case "Queen":
                break;

                default:;
        }
        return true;
    }
}