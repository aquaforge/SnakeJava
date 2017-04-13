package pkg.firstJavaSnake;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by User_2013 on 12.04.2017.
 */


public class Snake {
    private LinkedList<Point> body = new LinkedList<Point>();
    private Color color = Color.black;

    public Color getColor() {
        return color;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Snake(Point head, Color color) {
        this.body.add(head);
        this.color = color;
    }

    public Point head() {
        return body.get(0);
    }

    public Point tail() {
        return body.get(body.size()-1);
    }

    public void move (MoveDirection m) {
        this.growHead(m);
        this.reduceTail();
    }

    public void growHead(MoveDirection m) {
        if (m != MoveDirection.STAY) {
            Point p = new Point(body.get(0).X,body.get(0).Y);
            switch (m) {
                case DOWN:
                    p.Y++;
                    break;
                case UP:
                    p.Y--;
                    break;
                case LEFT:
                    p.X--;
                    break;
                case RIGHT:
                    p.X++;
                    break;
            }
            body.add (0,p);
        }
    }

    public void reduceTail() {
        if (body.size()>1) body.remove(body.size()-1);
    }

    public String toString(){
        String s=body.size() +":";
        for (int i = 0; i < body.size(); i++) {
            s=s+" " + body.get(i).toString();
        }
        return s;
    }

}
