package org.daniil.snakeai.logic.snake;

import lombok.Getter;
import lombok.Setter;
import org.daniil.snakeai.Panel;
import org.daniil.snakeai.logic.gamearea.GameArea;
import org.daniil.snakeai.logic.snake.cell.Cell;
import org.daniil.snakeai.logic.snake.cell.CellType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Snake implements KeyListener {

    private final GameArea gameAreaInst;

    private final Cell[][] gameArea;

    @Getter
    private final ArrayList<Cell> snakeBody;

    @Getter
    private Cell head;

    @Getter @Setter
    private boolean gameOver;

    @Getter
    private Direction currDirection = Direction.UP;




    public Snake(GameArea gameArea) {
        this.gameAreaInst = gameArea;
        this.gameArea = gameArea.getGameArea();
        this.snakeBody=this.initializeSnakeBody();
    }

    public void move(Direction direction) {
        int oldY = head.getY(), oldX=head.getX();
        switch (direction) {
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case UP -> moveUp();
            case DOWN -> moveDown();
        }

        Cell apple = this.getApple();

        boolean isHaveApple = apple==null;

        if (isHaveApple) {
            this.addCell();
        }

        // Обновляем координаты всех клеток в теле змейки на основе предыдущих клеток
        updateBody(oldY, oldX,isHaveApple);


        updateGameArea();
        if (isHaveApple) gameAreaInst.initializeApple();

        if (!gameOver) gameOver = checkCollision();
    }

    private void moveUp() {
        if (head.getY()>0) {
            head = gameArea[head.getY() - 1][head.getX()];
            head.setCellType(CellType.HEAD);
        } else
            gameOver = true;
    }

    private void moveDown() {
        if (head.getY()<gameArea.length-1) {
            head = gameArea[head.getY() + 1][head.getX()];
            head.setCellType(CellType.HEAD);
        } else
            gameOver = true;
    }

    private void moveLeft() {
        if (head.getX()>0) {
            head = gameArea[head.getY()][head.getX() - 1];
            head.setCellType(CellType.HEAD);
        } else
            gameOver = true;
    }

    private void moveRight() {
        if (head.getX()<gameArea.length-1) {
            head = gameArea[head.getY()][head.getX() + 1];
            head.setCellType(CellType.HEAD);
        } else
            gameOver = true;
    }

    private void updateBody(int oldY, int oldX, boolean isHaveApple) {
        // Нужно чтобы не обновить только что добавленную клетку после того как змейка сьела яблоко
        // Просто не смотрим последнюю клетку в массиве
        int minus = 1;
        if (isHaveApple) minus = 2;


        for (int i = snakeBody.size()-minus; i >= 0; i--) {
            Cell cell;
            if (i>0) {
                cell = gameArea[snakeBody.get(i-1).getY()][snakeBody.get(i-1).getX()];
            } else {
                cell = gameArea[oldY][oldX];
            }
            cell.setCellType(CellType.BODY);
            snakeBody.set(i,cell);
        }
    }

    private void addCell() {
        Cell newCell = new Cell(CellType.BODY, snakeBody.get(snakeBody.size()-1).getX(),
                snakeBody.get(snakeBody.size()-1).getY());
        snakeBody.add(newCell);
    }

    @Deprecated
    private boolean isCatchApple() {
        for (Cell[] cells : gameArea) {
            for (Cell cell : cells) {
                if (cell.getCellType() == CellType.DOT)
                    return false;
            }
        }
        return true;
    }

    public Cell getApple() {

        for (Cell [] cells : gameArea) {
            for (Cell cell : cells) {
                if (cell.getCellType() == CellType.DOT)
                    return cell;
            }
        }
        return null;
    }


    // Актуализация игрового поля
    private void updateGameArea() {

        for (Cell[] cells : gameArea) {
            for (Cell cell : cells) {
                if (cell.getCellType() == CellType.DOT) continue;
                cell.setCellType(CellType.EMPTY);
            }
        }

        gameArea[head.getY()][head.getX()].setCellType(CellType.HEAD);
        for (Cell cell : snakeBody) {
            gameArea[cell.getY()][cell.getX()].setCellType(CellType.BODY);
        }
    }

    // Выдираем клетки змейки из поля
    private ArrayList<Cell> initializeSnakeBody() {

        ArrayList<Cell> body = new ArrayList<>();

        for (Cell[] cells : gameArea) {
            for (Cell cell : cells) {
                if (cell.getCellType() == CellType.HEAD)
                    head = cell;
                if (cell.getCellType() == CellType.BODY)
                    body.add(cell);
            }
        }

        if (head == null)
            throw new NullPointerException("No snake head in area");

        return body;

    }

    public boolean checkCollision() {
        return snakeBody.stream()
                .anyMatch(cell -> cell.getX() == head.getX() && cell.getY() == head.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                if (currDirection != Direction.DOWN) {
                    currDirection = Direction.UP;
                }
                break;
            case KeyEvent.VK_S:
                if (currDirection != Direction.UP) {
                    currDirection = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_A:
                if (currDirection != Direction.RIGHT) {
                    currDirection = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_D:
                if (currDirection != Direction.LEFT) {
                    currDirection = Direction.RIGHT;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
