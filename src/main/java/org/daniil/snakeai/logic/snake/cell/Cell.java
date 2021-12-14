package org.daniil.snakeai.logic.snake.cell;

import lombok.Getter;
import lombok.Setter;
import org.daniil.snakeai.Panel;

import java.awt.*;


public class Cell {

    @Getter
    private CellType cellType;


    @Getter
    private final int x,y;

    @Getter
    private final int width = 10, height = 10;

    // Для мигания цели
    private int tick = 0;

    private int rawX, rawY;

    @Getter
    private boolean needUpdate = false;

    public Cell(CellType cellType, int x, int y) {
        this.cellType=cellType;
        this.x=x;
        this.y=y;
        needUpdate = true;
    }

    public static int[] getAppleCoordinates() {
        return new int[]{(int) (Math.random() * (Panel.HEIGHT/10)), (int) (Math.random() * (Panel.HEIGHT/10))};
    }

    public void draw(Graphics g) {

        switch (cellType) {
            case DOT -> g.setColor(Color.YELLOW);
            case HEAD -> g.setColor(Color.RED);
            case BODY -> g.setColor(Color.GREEN);
            case EMPTY -> g.setColor(Color.black);
        }
        // Реализация мигания цели
        if (cellType == CellType.DOT) {
            //Обнуляем цвет если прошло больше 2/3 секунды на 1/3 секунды
            if (tick > Panel.FPS-Panel.FPS/3) {
                g.setColor(Color.black);
                // Обнуляем счетчик
                if (tick == Panel.FPS)
                    tick = 0;
            }
            tick++;
        }

        g.fillRect(this.getRawX(),this.getRawY(),
                this.getWidth(),this.getHeight());
        needUpdate = false;
    }

    public int getRawX() {
        return Panel.WIDTH/100 * x;
    }

    public int getRawY() {
        return Panel.HEIGHT/100 * y;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
        needUpdate = true;
    }


}
