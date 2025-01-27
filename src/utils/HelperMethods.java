package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelperMethods {
    // TODO: replace x,y,width,height with a Rect obj
    /*
        Check the 4 corners of our hitbox, if there is a overlap / collision then we should not move

        Check diagonals first (top left, bottom right)
        if all corners eval to false, then there is no collision and the move is a valid move
     */
    public static boolean IsValidMove(float x, float y, float width, float height, int[][]levelData) {
        // Top left corner
        if(IsSolid(x, y, levelData)){
            return false;
        }

        // Bottom Right Corner
        if(IsSolid(x+width, y+height, levelData)){
            return false;
        }

        // Top Right Corner
        if(IsSolid(x+width, y, levelData)){
            return false;
        }

        // Bottom Left Corner
        if(IsSolid(x, y+height, levelData)){
            return false;
        }

        return true;

    }

    /*
        Check if we are trying to move outside of the viewable game window
        And also check if we are trying to move into a "solid" object or not
        within the level
     */
    private static boolean IsSolid(float x, float y, int[][]levelData) {
        // Check the far edges of the viewable game window
        if(x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        } else if( y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        // Check where in the matrix (game level) we are
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        // We only have 48 sprites, check if the "value" is a tile
        int value = levelData[(int)yIndex][(int)xIndex];

        // 48 and above, not a tile; 12th tile (11th index) is a transparent tile
        if(value >= 48 || value < 0 || value != 11) {
            return true;
        }

        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        // check if collision is left or right
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        // right
        if(xSpeed > 0) {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1; // -1 so that our hitbox doesnt overlap the tile we are colliding with
        }

        // left
        return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        // check if collision is left or right
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);

        // falling else jumping
        if(airSpeed > 0) {
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1; // -1 so that our hitbox doesnt overlap the tile we are colliding with
        }

        // left
        return currentTile * Game.TILES_SIZE;
    }

    /*
        Check if a game character / entity is on the floor or not
        by checking bottom left and right corners of their hitbox

        (magic nubmer) 1: look under character feet for the ground.
     */
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        // check bottom left
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)){
            return false;
        }

        // check bottom right
        if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {
            return false;
        }

        return true;
    }
}
