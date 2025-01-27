package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level level;

    public LevelManager(Game game){
        this.game = game;
        importOutsideSprites();
        level = new Level(LoadSave.GetLevelData());
    }

    public Level getCurrentLevel() {
        return level;
    }

    public void draw(Graphics g){
        for(int y = 0; y < Game.TILES_IN_HEIGHT; y++) {
            for (int x = 0; x < Game.TILES_IN_WIDTH; x++) {
                int index = level.getSpriteIndex(x, y);
                g.drawImage(
                  levelSprite[index],
                  x * Game.TILES_SIZE,
                  y * Game.TILES_SIZE,
                  Game.TILES_SIZE,
                  Game.TILES_SIZE,
                  null);
            }
        }
    }

    public void update() {

    }

    private void importOutsideSprites() {
        levelSprite = new BufferedImage[48]; // w: 12, h: 4
        BufferedImage spriteAtlas = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);

        // 4 & 12 come from the sprite dimensions
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 12; col++){
                int index = row * 12 + col;
                levelSprite[index] = spriteAtlas.getSubimage(col * 32, row * 32, 32, 32);
            }
        }
    }
}
