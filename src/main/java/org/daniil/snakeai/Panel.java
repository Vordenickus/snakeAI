package org.daniil.snakeai;


import org.daniil.snakeai.logic.intefaces.Drawable;
import org.daniil.snakeai.logic.intefaces.Tickable;
import org.daniil.snakeai.logic.snake.cell.Cell;
import org.daniil.snakeai.logic.state.MainState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Класс содержащий основной цикл, и управляющий всеми объектами
 */
public class Panel extends JPanel implements Runnable, KeyListener, Tickable, Drawable {

    public final static int WIDTH = 1000, HEIGHT = 1000;

    private final Thread mainThread;

    private boolean isRunning;

    public static int FPS = 30;

    private MainState mainState;


    public Panel() {

        setPreferredSize(new Dimension(Panel.WIDTH, Panel.HEIGHT));

        addKeyListener(this);
        setFocusable(true);

        synchronized (this) {
            isRunning = true;
            mainThread = new Thread(this);
            mainThread.start();
        }
    }


    @Override
    public void run() {

        long start, elapsed, wait;


        mainState = new MainState();

        while (isRunning) {

            final int targetTime = 1000 / FPS;
            start = System.currentTimeMillis();

            tick();

            for (Cell[] cells : mainState.getGameArea().getGameArea()) {
                for (Cell cell : cells) {
                    if (cell.isNeedUpdate())
                        repaint(cell.getRawX(),cell.getRawY(),cell.getWidth(),cell.getHeight());
                }
            }

            elapsed = System.currentTimeMillis() - start;

            wait = targetTime - elapsed;

            if (wait < 0)
                wait = 5;

            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                System.exit(3);
            }

        }


    }

    @Override
    public void draw(Graphics g) {
        mainState.draw(g);
    }

    @Override
    public void tick() {
        mainState.tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,Panel.WIDTH,Panel.HEIGHT);
        draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        mainState.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        mainState.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mainState.keyReleased(e);
    }

}
