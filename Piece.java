package sample;
public class Piece{

    String type;
    int colour;
    // white = 0, black = 1
    // numbers can be XOR'd

    int x, y;
    // column, row
    // file, rank
    // in chess notation, x should be A - H

    boolean exist;
    // when captured, exist = false

    // Constructor
    public Piece(String type, int colour, int x, int y, boolean exist){
        this.type = type;
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.exist = exist;
    }


    private void promotion(Piece piece){

    }

    public boolean isValid(int newX, int newY){

        //same spot
        if(newX == this.x && newY == this.y)
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
                if(colour == 0){
                    //white
                    if(newX == x && newY == y+1){
                        if(newY == 7)
                            promotion(this);
                        return true;
                    }
                }
                else{
                    if(newX == x && newY == y-1){
                        if(newY == 0)
                            promotion(this);
                        return true;
                    }
                }
                return false;


            default:
                System.out.println("Stalin gave the order");
                return false;
        }
    }
}
