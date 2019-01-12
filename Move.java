package sample;
public class Move{
    int startX, startY, endX, endY, num, colour;
    Piece last;
    boolean[] a, b;
    public Move(int startX, int startY, int endX, int endY, Piece last, int num, int colour, boolean[] a, boolean[] b){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.last = last;
        this.num = num;
        this.colour = colour;
        this.a = a;
        this.b = b;
    }
}
