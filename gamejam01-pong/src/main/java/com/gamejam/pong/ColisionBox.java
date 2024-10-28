package com.gamejam.pong;

import java.awt.*;

public class ColisionBox extends Rectangle {

    public ColisionBox(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}