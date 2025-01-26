package inputs;

import main.GamePanel;
import utils.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    // note: no-op
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gamePanel.setPlayerDirection(Constants.PlayerDirection.UP);
                break;
            case KeyEvent.VK_A:
                gamePanel.setPlayerDirection(Constants.PlayerDirection.LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.setPlayerDirection(Constants.PlayerDirection.DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.setPlayerDirection(Constants.PlayerDirection.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.setPlayerMoving(false);
                break;
        }
    }
}
