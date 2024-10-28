package com.gamejam.pong;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Rectangle implements KeyListener {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 100;
    public static int ponto = 0;
    public boolean up;
    public boolean down;
    public boolean IA = true;
    private Color c = Color.getHSBColor(ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat());
    public static int SPD = 6;
    private boolean isStopedUp = false;
    private boolean isStopedDown = false;

    public Enemy() {
        super(Game.WIDTH - WIDTH, (Game.HEIGHT/2) - HEIGHT/2, WIDTH, HEIGHT);
    }

    public void tick(Ball b) {
        if (IA) {
            if (b.x > Game.WIDTH/2) {
                if ((Game.HEIGHT - y != Game.HEIGHT) && (y + (HEIGHT / 2)) >= b.y) {
                    y -= SPD;
                } else if ((Game.HEIGHT - (y + HEIGHT) >= 1) && (y + (HEIGHT / 2)) <= b.y) {
                    y += SPD;
                }
            }
        } else {
            if (up && !isStopedUp) {
                y-=SPD;
                isStopedDown = false;
            } else if (down && !isStopedDown) {
                y+=SPD;
                isStopedUp = false;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            IA = !IA;
            if (IA) {
                c = Color.getHSBColor(ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat());
            } else {
                c = Color.WHITE;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
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
