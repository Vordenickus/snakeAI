package org.daniil.snakeai.logic.state;

import lombok.Getter;
import org.daniil.snakeai.Panel;
import org.daniil.snakeai.logic.gamearea.GameArea;
import org.daniil.snakeai.logic.intefaces.Drawable;
import org.daniil.snakeai.logic.intefaces.Tickable;
import org.daniil.snakeai.logic.snake.Direction;
import org.daniil.snakeai.logic.snake.Snake;
import org.daniil.snakeai.logic.snake.SnakeAi;
import org.daniil.snakeai.logic.snake.cell.Cell;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainState implements KeyListener, Tickable, Drawable {

    @Getter
    private final GameArea gameArea;
    @Getter
    private final Snake snake;
    private final SnakeAi snakeAi;

    private boolean play = false;

    // debug

    private int size = -1;

    public MainState() {

        gameArea = new GameArea(50,50);
        snake = new Snake(gameArea);
        snakeAi = new SnakeAi(snake, gameArea.getGameArea());
    }


    @Override
    public void draw(Graphics g) {
        gameArea.draw(g);
    }

    @Override
    public void tick() {
        //snake.move(Direction.UP);
        if (play) snake.move(snake.getCurrDirection());
        else snakeAi.move();
        if (play) Panel.FPS = 30;

        if (size==-1) size=snake.getSnakeBody().size();

        if (size != snake.getSnakeBody().size()) {
            System.out.printf("snake goes from %d to %d\n", size, snake.getSnakeBody().size());
            size = snake.getSnakeBody().size();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        snake.keyPressed(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_1)
            play = !play;
        if (key == KeyEvent.VK_2)
            snake.setGameOver(!snake.isGameOver());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
