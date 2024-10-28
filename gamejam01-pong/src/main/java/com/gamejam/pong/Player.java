package com.gamejam.pong;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Rectangle implements KeyListener {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 100;
    public static int ponto = 0;
    public boolean up;
    public boolean down;
    public static int SPD = 6;
    private boolean isStopedUp = false;
    private boolean isStopedDown = false;

    public Player() {
        super(0,(Game.HEIGHT/2) - HEIGHT/2, WIDTH, HEIGHT);
    }

    public void tick () {
        if (up && !isStopedUp) {
            y-=SPD;
            isStopedDown = false;
        } else if (down && !isStopedDown) {
            y+=SPD;
            isStopedUp = false;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

    public void colisionCheck(Rectangle e) {
        if (e.intersects(new Rectangle(this.x, this.y + SPD, WIDTH, HEIGHT)) ||
                e.intersects(new Rectangle(this.x, this.y - SPD, WIDTH, HEIGHT))) {
            if (up) {
                isStopedUp = true;
            }

            if (down) {
                isStopedDown = true;
            }
        }
    }
}
