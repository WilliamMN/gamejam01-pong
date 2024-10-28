package com.gamejam.pong;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ball extends Rectangle implements KeyListener {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public int spdx = 5;
    public int spdy = 5;
    private int maxspd = 10;
    private List<Rectangle> rectangles;
    private boolean stoped = true;

    public Ball() {
        super(Game.WIDTH / 2 - WIDTH / 2, Game.HEIGHT /2 - HEIGHT / 2, WIDTH, HEIGHT);
        rectangles = new ArrayList<>();
    }

    public void playsound() {
        try {
            String resource = "/quick_fart_x.wav";
            InputStream input = getClass().getResourceAsStream(resource);
            Clip oClip = AudioSystem.getClip();
            assert input != null;
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(input);
            oClip.open(audioInput);

            oClip.loop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void colisionCheck() {
        for (Rectangle e : rectangles) {
            if (e instanceof Player || e instanceof Enemy) {
                if (e.intersects(this)) {
                    playsound();
                    up = ThreadLocalRandom.current().nextBoolean();
                    down = !up;
                    right = !right;
                    left = !left;

                    spdx = ThreadLocalRandom.current().nextInt(maxspd);
                    spdy = ThreadLocalRandom.current().nextInt(maxspd);
                    return;
                }
            }

            if (e instanceof LostBox) {
                if (e.intersects(this)) {
                    if (x < Game.WIDTH / 2) {
                        Enemy.ponto++;
                    }

                    if (x > Game.WIDTH / 2) {
                        Player.ponto++;
                    }
                    x = Game.WIDTH / 2 - WIDTH / 2;
                    y = Game.HEIGHT /2 - HEIGHT / 2;
                    stoped = true;

                    spdx = 5;
                    spdy = 5;
                    return;
                }
            }

            if (e instanceof Wall) {
                if (e.intersects(this)) {
                    playsound();
                    up = !up;
                    down = !down;
                    return;
                }
            }
        }
    }

    public void addColisionRectangle(Rectangle r) {
        rectangles.add(r);
    }

    public void addColisionRectangles(Rectangle[] rs) {
        rectangles.addAll(List.of(rs));
    }

    public void tick () {
        if (!stoped) {
            if (up) {
                y-=spdy;
            } else if (down) {
                y+=spdy;
            }

            if (right) {
                x+=spdx;
            } else if (left) {
                x-=spdx;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.getHSBColor(ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat()));
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            stoped = false;
            up = ThreadLocalRandom.current().nextBoolean();
            right = ThreadLocalRandom.current().nextBoolean();
            down = !up;
            left = !right;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
