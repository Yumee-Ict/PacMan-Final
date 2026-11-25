package p;

final class GameSpeed {
    private static final double[] GHOST_SPEEDS = {2.1, 2.2, 2.3, 2.4, 2.5};

    private GameSpeed() {
    }

    static double getGhostSpeed(int level) {
        int levelIndex = level - 1;
        if (levelIndex < 0) {
            levelIndex = 0;
        } else if (levelIndex >= GHOST_SPEEDS.length) {
            levelIndex = GHOST_SPEEDS.length - 1;
        }
        return GHOST_SPEEDS[levelIndex];
    }

    static int getPacmanSpeed(int level) {
        int pacmanSpeed = (int) Math.ceil(getGhostSpeed(level)) + 1;
        while (GameConstants.BLOCK_SIZE % pacmanSpeed != 0) {
            pacmanSpeed++;
        }
        return pacmanSpeed;
    }
}
