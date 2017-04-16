package pkg.firstJavaSnake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by User_2013 on 12.04.2017.
 */
public class PathFind {

    int gridCountX=-1,gridCountY=-1;

    ArrayList<Snake> snakeList;
    Point foodPoint;

    private int scene[][];
    LinkedList<Point> backpath = new LinkedList<Point>();

    private HashSet<Point> wave_this;
    private HashSet<Point> wave_future = new HashSet<Point>();



    public PathFind(ArrayList<Snake> snakeList, Point foodPoint, int gridCountX, int gridCountY) {
        this.snakeList = snakeList;
        this.foodPoint = foodPoint;
        this.gridCountX = gridCountX;
        this.gridCountY = gridCountY;
    }

    private int[][] InitNewScene(){
        int scene[][] = new int[gridCountX][gridCountY];

        for (int i=0; i<scene.length; i++) {
            for (int j = 0; j < scene[i].length; j++) {
                scene[i][j] = 0;
            }
        }

//        for (int i=0 ; i<foodList.size();i++) {
//            scene[foodList.get(i).X][foodList.get(i).Y] = -100;
//        }

        for (int i=0 ; i<snakeList.size();i++) {
            LinkedList<Point> body = snakeList.get(i).getBody();
            for (int j = 0; j < body.size(); j++) {
                scene[body.get(j).X][body.get(j).Y] = -1;
            }
        }

        return scene;
    }

//    private void FillInSceneWithPath(int xP, int yP, int lvlP){
//        int lvl = scene[xP][yP];
//
//        if (lvl == 0 | lvl > lvlP ) {
//            scene[xP][yP] = lvlP;
//            if (xP-1 >= 0) FillInSceneWithPath(xP-1,yP,lvlP+1);
//            if (yP-1 >= 0) FillInSceneWithPath(xP,yP-1,lvlP+1);
//            if (xP+1 < gridCountX) FillInSceneWithPath(xP+1,yP,lvlP+1);
//            if (yP+1 < gridCountY) FillInSceneWithPath(xP,yP+1,lvlP+1);
//        }
//    }

    private void PointProcess(int x, int y, int level){
        int val = scene[x][y];
        if (val < 0) return;
        if (val >0 & val <= level) return;

        scene[x][y] = level;
        if (x-1>=0) wave_future.add(new Point(x-1,y));
        if (y-1>=0) wave_future.add(new Point(x,y-1));
        if (x+1<scene.length) wave_future.add(new Point(x+1,y));
        if (y+1<scene[0].length) wave_future.add(new Point(x,y+1));
    }

    private int RunWaves(int xStart, int yStart){
        int waveCount = 0;
        if (scene[xStart][yStart] != 0) return 0;
        wave_future.add(new Point(xStart, yStart));

        while (wave_future.size()>0 & scene[foodPoint.X][foodPoint.Y] == 0) {
            waveCount++;
            wave_this = wave_future;
            wave_future = new HashSet<Point>();

            for(Point p:wave_this){
                PointProcess(p.X,p.Y,waveCount);
            }


        }
        return waveCount;
    }    
    
    private void GetBackPath(int xP, int yP, int lvlP){
        int lvl = scene[xP][yP];

        if (lvl>1 & lvl == lvlP-1) {
            backpath.add(new Point(xP,yP));
            if (xP-1 >= 0) GetBackPath(xP-1,yP,lvlP-1);
            if (yP-1 >= 0) GetBackPath(xP,yP-1,lvlP-1);
            if (xP+1 < gridCountX) GetBackPath(xP+1,yP,lvlP-1);
            if (yP+1 < gridCountY) GetBackPath(xP,yP+1,lvlP-1);
        }
    }


    public MoveDirection FindNextStep(int snakeNum) {
        //int min = 0;
        //Point p=new Point(-1,-1);
        Point pSnakeHead = snakeList.get(snakeNum).head();

        scene = InitNewScene();
        scene[pSnakeHead.X][pSnakeHead.Y]=0;
        RunWaves(pSnakeHead.X,pSnakeHead.Y);

        MoveDirection m = MoveDirection.STAY;
        if (scene[foodPoint.X][foodPoint.Y]==0) {
            m= MoveDirection.STAY;
        } else {
            GetBackPath(foodPoint.X,foodPoint.Y, scene[foodPoint.X][foodPoint.Y]+1);
            Point p   =   backpath.get(backpath.size()-1);
            if (p.X < pSnakeHead.X) m= MoveDirection.LEFT;
            if (p.X > pSnakeHead.X) m= MoveDirection.RIGHT;
            if (p.Y < pSnakeHead.Y) m= MoveDirection.UP;
            if (p.Y > pSnakeHead.Y) m= MoveDirection.DOWN;
        }

        return m;
    }

}
