package main;

public class Game implements Runnable {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameThread;
    private final int TARGET_FPS = 80;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    // Game thread
    @Override
    public void run() {
        // how long will each frame last in nano seconds
        double timePerFrame = 1_000_000_000 / TARGET_FPS;
        long lastFrame = System.nanoTime();
        long now;
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (gameThread != null){
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame){
                // trigger a new frame
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1_000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
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
}
