package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";

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
}
