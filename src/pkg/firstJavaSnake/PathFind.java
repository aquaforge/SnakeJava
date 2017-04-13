package pkg.firstJavaSnake;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by User_2013 on 12.04.2017.
 */
public class PathFind {

    int gridCountX=-1,gridCountY=-1;
    ArrayList<Snake> snakeList;
    ArrayList<Point> foodList;
    int scene[][];
    LinkedList<Point> backpath = new LinkedList<Point>();

    public PathFind(ArrayList<Snake> snakeList, ArrayList<Point> foodList, int gridCountX, int gridCountY) {
        this.snakeList = snakeList;
        this.foodList = foodList;
        this.gridCountX = gridCountX;
        this.gridCountY = gridCountY;
    }

    private int[][] CreateNewScene(){
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

    private void FillInSceneWithPath(int xP, int yP, int lvlP){
        int lvl = scene[xP][yP];

        if (lvl == 0 | lvl > lvlP ) {
            scene[xP][yP] = lvlP;
            if (xP-1 >= 0) FillInSceneWithPath(xP-1,yP,lvlP+1);
            if (yP-1 >= 0) FillInSceneWithPath(xP,yP-1,lvlP+1);
            if (xP+1 < gridCountX) FillInSceneWithPath(xP+1,yP,lvlP+1);
            if (yP+1 < gridCountY) FillInSceneWithPath(xP,yP+1,lvlP+1);
        }
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
        int min = 0;
        Point p=new Point(-1,-1);
        Point pSnakeHead = snakeList.get(snakeNum).head();

        scene = CreateNewScene();
        scene[pSnakeHead.X][pSnakeHead.Y]=100;
        FillInSceneWithPath(pSnakeHead.X,pSnakeHead.Y,1);

        for (int i=0 ; i<foodList.size();i++) {
            if (min < scene[foodList.get(i).X][foodList.get(i).Y]) {
                p = foodList.get(i);
                min = scene[p.X][p.Y];
            }
        }

        MoveDirection m = MoveDirection.STAY;
        if (min==0) {
            m= MoveDirection.STAY;
        } else {
            GetBackPath(p.X,p.Y, scene[p.X][p.Y]+1);
            p   =   backpath.get(backpath.size()-1);
            if (p.X < pSnakeHead.X) m= MoveDirection.LEFT;
            if (p.X > pSnakeHead.X) m= MoveDirection.RIGHT;
            if (p.Y < pSnakeHead.Y) m= MoveDirection.UP;
            if (p.Y > pSnakeHead.Y) m= MoveDirection.DOWN;
        }

        return m;
    }

}
