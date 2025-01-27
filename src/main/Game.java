package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {
    // Dimensions
    public final static int TILES_DEFAULT_SIZE = 32; // we know this, because of the assest sizes
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;

    private Thread gameThread;
    private final int FPS_SET = 120; // fps -> rendering / frames
    private final int UPS_SET = 200; // ups -> updates / ticks

    // Game classes
    private final Player player = new Player(100,200, (int)(64 * Game.SCALE), (int)(40 * Game.SCALE));
    private final LevelManager levelManager = new LevelManager(this);


    public Game() {
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocusInWindow();
        player.setLevel(levelManager.getCurrentLevel());

        startGameLoop();
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirectionBooleans();
    }

    // Game thread
    @Override
    public void run() {
        // how long will each frame last in nanoseconds
        double timePerFrame = 1_000_000_000 / FPS_SET;
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        double timePerUpdate = 1_000_000_000 / UPS_SET; // time between updates
        long previousTime = System.nanoTime();
        int updates = 0;
        double deltaUpdate = 0;
        double deltaFrames = 0;

        while (gameThread != null){
            long currentTime = System.nanoTime(); // for updates

            // deltaUpdate will be 1.0 or more when the duration since the last update
            // is equal or more than timePerUpdate
            deltaUpdate += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaUpdate >= 1) {
                update();
                updates++;
                deltaUpdate --;
            }

            // should we render again?
            if(deltaFrames >= 1) {
                // trigger a re-render
                gamePanel.repaint();
                frames++;
                deltaFrames--;
            }

            // FPS & UPS tracking
            if(System.currentTimeMillis() - lastCheck >= 1_000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

            // stutter fix on linux, needs to be after call to repaint
            // https://stackoverflow.com/questions/19480076/java-animation-stutters-when-not-moving-mouse-cursor
            gameWindow.getToolkit().sync();
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        // render level behind the player
        levelManager.draw(g);
        player.render(g);
    }
//    might need this later...
//    private void initGameCharacters() {
//        player = new Player(100, 100);
//    }
}
