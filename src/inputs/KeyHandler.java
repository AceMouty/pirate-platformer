package inputs;

import main.GamePanel;

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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gamePanel.updateYDelta(-5);
                break;
            case KeyEvent.VK_A:
                gamePanel.updateXDelta(-5);
                break;
            case KeyEvent.VK_S:
                gamePanel.updateYDelta(5);
                break;
            case KeyEvent.VK_D:
                gamePanel.updateXDelta(5);
                break;
        }
    }
}
