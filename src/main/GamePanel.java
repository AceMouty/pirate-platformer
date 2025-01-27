package main;

import inputs.KeyHandler;
import inputs.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    private final MouseHandler mouseHandler;
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        mouseHandler = new MouseHandler(this);

        setPanelSize();
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseHandler); // mouse clicks
        addMouseMotionListener(mouseHandler); // mouse movements

    }

    public Game getGame() {
        return game;
    }

    public void updateGame() {

    }

    // magic-function: gets called internally by Java Swing
    // Graphics g: The thing that actually handles drawing; the "Paint brush"
    public void paintComponent(Graphics g){
        super.paintComponent(g); // stages the Panel to paint next frame
        // custom area ready to paint...

        game.render(g);
    }

    private void setPanelSize(){
        Dimension dimension = new Dimension(game.GAME_WIDTH, game.GAME_HEIGHT);
        setPreferredSize(dimension);
        System.out.println("Game Dimensions: WIDTH {" + game.GAME_WIDTH + "}" + " HEIGHT: {" + game.GAME_HEIGHT + "}");
    }
}
