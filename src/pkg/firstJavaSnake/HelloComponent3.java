package pkg.firstJavaSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

class HelloComponent3 extends JComponent implements Runnable // implements MouseMotionListener
{

    int gridCountX=10,gridCountY=10;
    int stepX;
    int stepY;

    Thread myThread;

    ArrayList<Snake> snakeList;
    ArrayList<Point> foodList;

    private void Sleep (long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}

    }



    public void run() {

        Sleep(1000);
        while(true) {

            for (int i=0 ; i<snakeList.size();i++) {
                Date c0 = new Date();
                Snake s = snakeList.get(i);
                PathFind pathFind = new PathFind(snakeList,foodList,gridCountX,gridCountY);
                MoveDirection m=pathFind.FindNextStep(i);
                if (checkIfEat(new Point (s.head().X,s.head().Y),m)){
                    s.growHead(m);}
                else{
                    s.move(m); }


                    repaint();


                if (((new Date()).getTime() - c0.getTime())<25) Sleep(25);
            }
            //paintComponent(this.getGraphics());
        }
    }

    private Point GetNewFoodPoint() {
        Random random = new Random();

        Point p = new Point (-1,-1);
        boolean b = true;
        while (b) {
            b = false;
            p = new Point( random.nextInt(gridCountX), random.nextInt(gridCountY));

            for (int i = 0; i < foodList.size(); i++) {
                if (p.equals(foodList.get(i))) b=true;
            }
            for (int i = 0; i < snakeList.size(); i++) {
                LinkedList<Point> body = snakeList.get(i).getBody();
                //Color color = snakeList.get(i).getColor();
                for (int j = 0; j < body.size(); j++) {
                    if (p.equals(body.get(j))) b=true;
                }
            }

        }
        return p;
    }

    private boolean checkIfEat(Point head,MoveDirection m){
        boolean bCheck=false;
        switch (m) {
                case DOWN:
                    head.Y++;
                    break;
                case UP:
                    head.Y--;
                    break;
                case LEFT:
                    head.X--;
                    break;
                case RIGHT:
                    head.X++;
                    break;
        }

        for (int i=0 ; i<foodList.size();i++) {
            if (head.equals(foodList.get(i))) {
                bCheck=true;
                Point p = GetNewFoodPoint();
                foodList.get(i).X=p.X;
                foodList.get(i).Y=p.Y;
            }
        }
        return bCheck;
    }


    public HelloComponent3(int gridX, int gridY, ArrayList<Snake> snakeList, ArrayList<Point> foodList) {
        gridCountX = gridX;
        gridCountY = gridY;
        this.snakeList = snakeList;
        this.foodList = foodList;

        myThread = new Thread(this);
        myThread.setDaemon(true);
        myThread.start();
    }

    private void drawRectByDesk(Graphics g, int x, int y, Color color){
        g.setColor(color);
        //g.drawRect (x*stepX,y*stepY,stepX,stepY);
        g.fillRect (x*stepX,y*stepY, stepX,stepY);
    }

    public void paintComponent( Graphics g ) {
         stepX = this.getSize().width/ gridCountX;
         stepY = this.getSize().height/ gridCountY;

        for (int i=0 ; i<foodList.size();i++) {
            drawRectByDesk (g,foodList.get(i).X, foodList.get(i).Y, Color.GREEN);
        }

        for (int i=0 ; i<snakeList.size();i++) {
            LinkedList<Point> body = snakeList.get(i).getBody();
            Color color = snakeList.get(i).getColor();

            for (int j = 0; j < body.size(); j++) {
                double d = 1.0-(double)(body.size()-j)/50.0;
                if (d<0.02) d=0.02;
                Color color1 = new Color((int)(d*color.getRed()),(int)(d*color.getGreen()), (int)(d*color.getBlue()));
                drawRectByDesk(g, body.get(j).X, body.get(j).Y, color1);
            }
        }
    }

}
