package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage spriteAtlas = null;
        // try-with-resources will auto call is.close() for us
        try (InputStream is = LoadSave.class.getResourceAsStream("/" + fileName))
        {
            spriteAtlas = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }

        return spriteAtlas;
    }

    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int value = color.getRed();
                if(value >= 48) value = 0;
                levelData[y][x] = value; // index layer for sprite
            }
        }

        return levelData;
    }
}
