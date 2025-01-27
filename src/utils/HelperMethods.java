package utils;

import main.Game;

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
}
