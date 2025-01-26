package main;

import inputs.KeyHandler;
import inputs.MouseHandler;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GamePanel extends JPanel {
    private final MouseHandler mouseHandler;
    private int xDelta = 100, yDelta = 100;

    public GamePanel() {
        mouseHandler = new MouseHandler(this);

        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseHandler); // mouse clicks
        addMouseMotionListener(mouseHandler); // mouse movements

    }

    public void updateXDelta(int value) {
        this.xDelta += value;
        repaint();
    }

    public void updateYDelta(int value){
        this.yDelta += value;
        repaint();
    }

    public void setRectPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    // magic-function: gets called internally by Java Swing
    // Graphics g: The thing that actually handles drawing; the "Paint brush"
    public void paintComponent(Graphics g){
        super.paintComponent(g); // stages the Panel to paint next frame

        g.fillRect(xDelta, yDelta , 200, 50);
    }
}
