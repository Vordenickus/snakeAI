package org.daniil.snakeai.logic.pathfind;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.daniil.snakeai.logic.snake.Direction;
import org.daniil.snakeai.logic.snake.cell.Cell;
import org.daniil.snakeai.logic.snake.cell.CellType;

import java.util.ArrayList;

@RequiredArgsConstructor
@EqualsAndHashCode
public class CellNode {

    @Getter
    private final int x,y;

    @Getter
    private final int distance;

    @Getter
    private final Direction direction;

    @Getter
    private final Cell[][] gameArea;

    @Getter
    private final CellNode parent;



    public ArrayList<CellNode> getChildren() {

        ArrayList<CellNode> children = new ArrayList<>();

        int distance = this.getDistance() + 1;

        if (x>0) {
            if (gameArea[y][x-1].getCellType() != CellType.BODY
                    && gameArea[y][x-1].getCellType()!= CellType.HEAD) {
                children.add(new CellNode(x - 1, y, distance, Direction.LEFT, gameArea, this));
            }
        }
        if (x<gameArea.length-1) {
            if (gameArea[y][x+1].getCellType() != CellType.BODY
                    && gameArea[y][x+1].getCellType() != CellType.HEAD) {
                children.add(new CellNode(x + 1, y, distance, Direction.RIGHT, gameArea, this));
            }
        }
        if (y>0) {
            if (gameArea[y-1][x].getCellType() != CellType.BODY
                    && gameArea[y-1][x].getCellType() != CellType.HEAD) {
                children.add(new CellNode(x, y - 1, distance, Direction.UP, gameArea, this));
            }
        }

        if (y<gameArea.length-1) {
            if (gameArea[y+1][x].getCellType() != CellType.BODY
                    && gameArea[y+1][x].getCellType() != CellType.HEAD) {
                children.add(new CellNode(x, y + 1, distance, Direction.DOWN, gameArea, this));
            }
        }


        return children;

    }

    // Немного доработано для поиска пути когда нет четкой дороги, по сути мы просто даем предпочтение движению по
    // старому вектору
    public ArrayList<CellNode> getChildrenLongest() {

        ArrayList<CellNode> children = new ArrayList<>();

        int distance = this.getDistance() + 1;

        if (x>0) {
            if (gameArea[y][x-1].getCellType() != CellType.BODY
                    && gameArea[y][x-1].getCellType()!= CellType.HEAD) {
                if (this.direction == Direction.LEFT) distance-=1;
                children.add(new CellNode(x - 1, y, distance, Direction.LEFT, gameArea, this));
            }
        }
        if (x<gameArea.length-1) {
            if (gameArea[y][x+1].getCellType() != CellType.BODY
                    && gameArea[y][x+1].getCellType()!= CellType.HEAD) {
                if (this.direction == Direction.RIGHT) distance-=1;
                children.add(new CellNode(x + 1, y, distance, Direction.RIGHT, gameArea, this));
            }
        }
        if (y>0) {
            if (gameArea[y-1][x].getCellType() != CellType.BODY
                    && gameArea[y-1][x].getCellType()!= CellType.HEAD) {
                if (this.direction == Direction.UP) distance -= 1;
                children.add(new CellNode(x, y - 1, distance, Direction.UP, gameArea, this));
            }
        }

        if (y<gameArea.length-1) {
            if (gameArea[y+1][x].getCellType() != CellType.BODY
                    && gameArea[y+1][x].getCellType()!= CellType.HEAD) {
                if (this.direction == Direction.DOWN) distance-=1;
                children.add(new CellNode(x, y + 1, distance, Direction.DOWN, gameArea, this));
            }
        }


        return children;

    }


}
