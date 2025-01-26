package main;

public class Game {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();
    }
}
