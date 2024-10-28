package com.gamejam.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 480;
    public static final int SCALE = 3;
    public Player player;
    public Ball ball;
    public Enemy enemy;
    public ColisionBox[] bars = new ColisionBox[4];

    public Game() {
        player = new Player();
        ball = new Ball();
        enemy = new Enemy();
        bars[0] = new Wall(0, 0, Game.WIDTH, 16);
        bars[1] = new LostBox(-50, 0, 50, Game.HEIGHT);
        bars[2] = new Wall(0, Game.HEIGHT-16, Game.WIDTH, 16);
        bars[3] = new LostBox(Game.WIDTH, 0, 500, Game.HEIGHT);
        ball.addColisionRectangle(player);
        ball.addColisionRectangle(enemy);
        ball.addColisionRectangles(bars);
        this.addKeyListener(player);
        this.addKeyListener(enemy);
        this.addKeyListener(ball);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void colisionCheck() {
        ball.colisionCheck();
        player.colisionCheck(bars[0]);
        player.colisionCheck(bars[2]);
        enemy.colisionCheck(bars[0]);
        enemy.colisionCheck(bars[2]);
    }

    public void tick() {
        player.tick();
        enemy.tick(ball);
        ball.tick();
        colisionCheck();

        if (Player.ponto >= 5 || Enemy.ponto >= 5) {
            System.exit(0);
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(Game.WIDTH / 2 - 5 / 2, 0 , 5, Game.HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20*SCALE));
        g.drawString(String.format("%d   %d", Player.ponto, Enemy.ponto), (Game.WIDTH/2)-60, 100);


        enemy.render(g);
        ball.render(g);

        for (ColisionBox b : bars) {
            b.render(g);
        }
        player.render(g);
        bs.show();
    }

    @Override
    public void run() {
        while(true) {
            tick();
            render();

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.setTitle("Gamejam pong");

        frame.pack();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        new Thread(game).start();
    }
}
