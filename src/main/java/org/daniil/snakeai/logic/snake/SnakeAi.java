package org.daniil.snakeai.logic.snake;

import org.daniil.snakeai.Panel;
import org.daniil.snakeai.logic.pathfind.CellNode;
import org.daniil.snakeai.logic.pathfind.PathFindTree;
import org.daniil.snakeai.logic.snake.cell.Cell;

import java.security.DrbgParameters;
import java.util.ArrayList;

public class SnakeAi {


    private final Snake snake;
    private final Cell[][] gameArea;

    private int mIndex = 0;

    private boolean findPath = false;
    private boolean longestPath = false;

    private ArrayList<CellNode> path;


    public SnakeAi(Snake snake, Cell[][] gameArea){
        this.snake=snake;
        this.gameArea=gameArea;
    }

    public void move() {
        if (!snake.isGameOver()) {
            PathFindTree pathFindTree = new PathFindTree(gameArea);
            if (!findPath) {
                //Panel.FPS = 2000;
                if (pathFindTree.pathExists(snake.getHead().getX(), snake.getApple().getX(), snake.getHead().getY(), snake.getApple().getY())) {

                    CellNode target = pathFindTree.getPath(snake.getHead().getX(), snake.getApple().getX(), snake.getHead().getY(), snake.getApple().getY());

                    ArrayList<CellNode> cellNodes = new ArrayList<>();

                    cellNodes.add(target);

                    while (target.getParent().getParent() != null) {
                        target = target.getParent();
                        cellNodes.add(target);
                    }

                    path = new ArrayList<>();

                    for (int i = cellNodes.size() - 1; i >= 0; i--) {
                        path.add(cellNodes.get(i));
                    }
                    findPath = true;
                } else {
                    CellNode target = pathFindTree.getLongestPath(snake.getHead().getX(), snake.getHead().getY());

                    ArrayList<CellNode> cellNodes = new ArrayList<>();

                    cellNodes.add(target);

                    while (target.getParent().getParent() != null) {
                        target = target.getParent();
                        cellNodes.add(target);
                    }

                    path = new ArrayList<>();

                    for (int i = cellNodes.size() - 1; i >= 0; i--) {
                        path.add(cellNodes.get(i));
                    }
                    findPath = true;
                    longestPath = true;

                }
            } else if (!longestPath) {

                // Начинаем двигаться если найден четкий путь

                if (mIndex < path.size()) {
                    snake.move(path.get(mIndex).getDirection());
                    mIndex++;
                } else {
                    mIndex = 0;
                    findPath = false;
                }
            } else {
                // Двигаемся когда четкого пути нет

                //Panel.FPS = 30;
                //System.out.println(mIndex);

                if (mIndex < path.size()) {
                    if (pathFindTree.pathExists(snake.getHead().getX(), snake.getApple().getX(),
                            snake.getHead().getY(), snake.getApple().getY())) {
                        findPath = false;
                        longestPath = false;
                    }
                    snake.move(path.get(mIndex).getDirection());
                    mIndex++;
                } else {
                    mIndex = 0;
                    findPath = false;
                    longestPath = false;
                }

            }
        } else {

        }
    }


}
