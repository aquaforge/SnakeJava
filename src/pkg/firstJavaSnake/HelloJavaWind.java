package pkg.firstJavaSnake;

//import java.awt.*;
//import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by User_2013 on 12.04.2017.
 */
public class HelloJavaWind {

    public static void main(String[] args) {
        JFrame frame = new JFrame( "HelloJava2" );


        int x=15;
        int y=10;
        ArrayList<Point> foodList = new ArrayList<Point> ();
        ArrayList<Snake> snakeList = new ArrayList<Snake> ();

        foodList.add(new Point(1,1));
        //foodList.add(new Point(14,9));

        snakeList.add(new Snake(new Point(2,2), Color.RED));
        snakeList.add(new Snake(new Point(3,2), Color.BLUE));
        snakeList.add(new Snake(new Point(4,2), Color.magenta));
        snakeList.add(new Snake(new Point(5,2), Color.cyan));
        snakeList.add(new Snake(new Point(6,2), Color.ORANGE));

        frame.add( new HelloComponent3( x,y, snakeList, foodList) );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 900, 600 );
        frame.setVisible( true );


}
}

