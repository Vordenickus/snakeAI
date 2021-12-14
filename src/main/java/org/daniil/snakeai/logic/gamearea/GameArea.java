package org.daniil.snakeai.logic.gamearea;

import lombok.Getter;
import org.daniil.snakeai.Panel;
import org.daniil.snakeai.logic.snake.cell.Cell;
import org.daniil.snakeai.logic.snake.cell.CellType;

import java.awt.*;

public class GameArea {

    @Getter
    private final Cell[][] gameArea;

    int count = 0;

    public GameArea(int snakeOGX, int snakeOGY) {

        this.gameArea = initializeGameArea(snakeOGX,snakeOGY);
        initializeApple();

    }


    public void draw(Graphics g) {
        for (Cell[] cells : gameArea) {
            for (Cell cell : cells) {
                cell.draw(g);
            }
        }
    }


    // Создание игрового поля с инициализацией положения змейки
    private Cell[][] initializeGameArea(int snakeOGX, int snakeOGY) {

        if (snakeOGY>Panel.HEIGHT/10-3)
            throw new IndexOutOfBoundsException("Impossible to create snake with such coordinates");

        Cell[][] target = new Cell[Panel.WIDTH/10][Panel.WIDTH/10];

        for (int i = 0, limit = target.length; i < limit; i++) {
            for (int x = 0; x < limit; x++) {

                if (x==snakeOGX && i == snakeOGY)
                    target[i][x] = new Cell(CellType.HEAD,x,i);
                else if (x==snakeOGX && i == snakeOGY + 1)
                    target[i][x] = new Cell(CellType.BODY,x,i);
                else if (x==snakeOGX && i == snakeOGY + 2)
                    target[i][x] = new Cell(CellType.BODY,x,i);
                else
                    target[i][x] = new Cell(CellType.EMPTY,x,i);
            }
        }
        return target;
    }

    public void initializeApple() {
        int[] coord = Cell.getAppleCoordinates();


        if (gameArea[coord[1]][coord[0]].getCellType()==CellType.EMPTY)
            gameArea[coord[1]][coord[0]].setCellType(CellType.DOT);
        else
            initializeApple();
    }

}
