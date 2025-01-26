package utils;

public class Constants {

    // int value represents number of frames in the animation
    public enum PlayerAction {
        IDLE(0, 5),
        RUNNING(1,6),
        JUMP(2, 3),
        FALLING(3,1),
        GROUND(4, 2),
        HIT(5,4),
        ATTACK_1(6, 3),
        ATTACK_JUMP_1(7,3),
        ATTACK_JUMP_2(8, 3),;

        private final int atlasIndex;
        private final int totalFrames;

        private PlayerAction(int atlasIndex, int totalFrames) {
            this.atlasIndex = atlasIndex;
            this.totalFrames = totalFrames;
        }

        public int getAtlasIndex() {
            return atlasIndex;
        }

        public int getTotalFrames() {
            return totalFrames;
        }
    }

    public enum PlayerDirection {
        LEFT(0),
        UP(1),
        RIGHT(2),
        DOWN(3);

        private final int direction;
        private PlayerDirection(int direction){
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }
    }
}
