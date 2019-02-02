package sample;

// #Eric

public class Piece{

    static final boolean forceMove = false;
    // when forceMove is set to true, it forces all moves to go through

    static final int white = 0, black = 1;
    int colour;
    // colour of piece

    String type;
    // type of piece

    boolean exist;
    // whether the piece exists

    public Piece(String type, int colour, boolean exist){
        this.type = type;
        this.colour = colour;
        this.exist = exist;
    }

    // copies Piece by value
    public Piece copy(){
        Piece ret = new Piece(type, colour, exist);
        return ret;
    }

    // whether the user tried to castle & can castle
    public boolean isCastle(int x, int y, int newX, int newY){

        if(forceMove)
            return true;

        if(!type.equals("King"))
            return false;

        // rank of pieces depending on player colour
        int ty = colour == white ? 1 : 8;
        if(y == ty && newY == ty && x == 5){

            // castle queenside
            if(newX == 3 && Board.getQueen()[colour]){
                // must be empty between rook and king
                for(int i = 2; i < 5; i++){
                    if(Board.get(i, ty).exist)
                        return false;
                }
                return true;
            }

            // castle kingside
            else if(newX == 7 && Board.getKing()[colour]){
                // must be empty between rook and king
                for(int i = 6; i <= 7; i++){
                    if(Board.get(i, ty).exist)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    // whether piece is able to make the move
    public boolean isValid(int x, int y, int newX, int newY){

        if(forceMove)
            return true;

        if(!exist)
            return false;

        // same spot
        if(newX == x && newY == y)
            return false;

        // can not capture own piece
        if(Board.get(newX, newY).colour == this.colour)
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

                    // normal move
                    if(newX == x && newY == y+1 && !Board.get(x, y+1).exist)
                        return true;

                    // first move 2 squares
                    if(newX == x && y == 2 && newY == 4 && !Board.get(x, 4).exist && !Board.get(x, 3).exist){
                        //set en passant
                        return true;
                    }

                    // capture
                    if(Math.abs(newX-x) == 1 && newY == y+1){
                        if(Board.get(newX, newY).colour == black)
                            return true;
                    }
                }
                else{
                    // normal move
                    if(newX == x && newY == y-1 && !Board.get(x, y-1).exist)
                        return true;

                    // first move 2 squares
                    if(newX == x && y == 7 && newY == 5 && !Board.get(x, 5).exist && !Board.get(x, 6).exist){
                        // set en passant
                        return true;
                    }

                    // capture
                    if(Math.abs(newX-x) == 1 && newY == y-1){
                        if(Board.get(newX, newY).colour == white)
                            return true;
                    }
                }
                return false;

            default:
                return false;
        }
    }

    // whether the piece and make the move without jumping over other pieces
    // no need to check pawn, knight, king  - no way of jumping over other pieces
    public boolean isLegal(int x, int y, int newX, int newY){

        if(forceMove)
            return true;

        switch(this.type){

            //checks rank and file
            case "Rook":
                if(x == newX){
                    if(newY > y){
                        for(int i = y+1; i < newY; i++)
                            if(Board.get(x, i).exist)
                                return false;
                    }
                    else{
                        for(int i = y-1; i > newY; i--)
                            if(Board.get(x, i).exist)
                                return false;
                    }
                }
                else{
                    if(newX > x){
                        for(int i = x+1; i < newX; i++)
                            if(Board.get(i, y).exist)
                                return false;
                    }
                    else{
                        for(int i = x-1; i > newX; i--)
                            if(Board.get(i, y).exist)
                                return false;
                    }
                }
                break;

            // checks diagonal
            case "Bishop":
                if(newX > x){
                    if(newY > y){
                        for(int i = x+1, j = y+1; i < newX; i++, j++){
                            if(Board.get(i, j).exist)
                                return false;
                        }
                    }
                    else{
                        for(int i = x+1, j = y-1; i < newX; i++, j--){
                            if(Board.get(i, j).exist)
                                return false;
                        }
                    }
                }
                else{
                    if(newY > y){
                        for(int i = x-1, j = y+1; i > newX; i--, j++){
                            if(Board.get(i, j).exist)
                                return false;
                        }
                    }
                    else{
                        for(int i = x-1, j = y-1; i > newX; i--, j--){
                            if(Board.get(i, j).exist)
                                return false;
                        }
                    }
                }
                break;

            // reused bishop and rook code
            case "Queen":
                if(x == newX || y == newY){
                    // same code as for rook
                    if(x == newX){
                        if(newY > y){
                            for(int i = y+1; i < newY; i++)
                                if(Board.get(x, i).exist)
                                    return false;
                        }
                        else{
                            for(int i = y-1; i > newY; i--){
                                if(Board.get(x, i).exist)
                                    return false;
                            }
                        }
                    }
                    else{
                        if(newX > x){
                            for(int i = x+1; i < newX; i++)
                                if(Board.get(i, y).exist)
                                    return false;
                        }
                        else{
                            for(int i = x-1; i > newX; i--)
                                if(Board.get(i, y).exist)
                                    return false;
                        }
                    }
                }
                else{
                    // same code as for bishop
                    if(newX > x){
                        if(newY > y){
                            for(int i = x+1, j = y+1; i < newX; i++, j++){
                                if(Board.get(i, j).exist)
                                    return false;
                            }
                        }
                        else{
                            for(int i = x+1, j = y-1; i < newX; i++, j--){
                                if(Board.get(i, j).exist)
                                    return false;
                            }
                        }
                    }
                    else{
                        if(newY > y){
                            for(int i = x-1, j = y+1; i > newX; i--, j++){
                                if(Board.get(i, j).exist)
                                    return false;
                            }
                        }
                        else{
                            for(int i = x-1, j = y-1; i > newX; i--, j--){
                                if(Board.get(i, j).exist)
                                    return false;
                            }
                        }
                    }
                }
                break;
            default:;
        }
        return true;
    }
}