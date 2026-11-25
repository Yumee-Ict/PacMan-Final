package p;

final class LevelManager {
    private final GameState state;
    private final CharacterPositions characters;

    LevelManager(Model2 game) {
        state = game.state;
        characters = game.characters;
    }

    void startNewGame() {
        state.lives = 8;
        state.score = 0;
        characters.ghostCount = 1;
        state.level = 1;
        state.gameWon = false;
        state.gameStartedAtNanos = 0;
        state.gameCompletedAtNanos = 0;
        loadLevel(1);
    }

    void loadLevel(int level) {
        characters.ghostCount = level;
        state.level = level;

        short[] layout = MazeLayouts.getLayout(level);
        System.arraycopy(layout, 0, state.screenData, 0, state.screenData.length);
        int pelletInterval = 3;
        switch (level) {
            case 1:
                pelletInterval = 4;
                break;
            case 2:
                pelletInterval = 3;
                break;
            case 3:
                pelletInterval = 3;
                break;
            case 4:
                pelletInterval = 3;
                break;
            case 5:
                pelletInterval = 3;
                break;
            default:
                break;
        }
        keepEveryNthPellet(pelletInterval);
        resetPositions();
    }

    void resetPositions() {
        int ghostDirection = 1;
        double levelSpeed = GameSpeed.getGhostSpeed(state.level);
        characters.pacmanSpeed = GameSpeed.getPacmanSpeed(state.level);

        for (int ghostIndex = 0; ghostIndex < characters.ghostCount; ghostIndex++) {
            characters.ghostY[ghostIndex] = 4 * GameConstants.BLOCK_SIZE;
            characters.ghostX[ghostIndex] = 4 * GameConstants.BLOCK_SIZE;
            characters.ghostDirectionY[ghostIndex] = 0;
            characters.ghostDirectionX[ghostIndex] = ghostDirection;
            ghostDirection = -ghostDirection;
            characters.ghostSpeed[ghostIndex] = (int) levelSpeed;
            characters.ghostSpeedFraction[ghostIndex] = levelSpeed - (int) levelSpeed;
            characters.ghostMoveAccumulator[ghostIndex] = 0;
        }

        characters.pacmanX = 7 * GameConstants.BLOCK_SIZE;
        characters.pacmanY = 11 * GameConstants.BLOCK_SIZE;
        characters.pacmanDirectionX = 0;
        characters.pacmanDirectionY = 0;
        characters.requestedDirectionX = 0;
        characters.requestedDirectionY = 0;
        state.dying = false;
    }

    private void keepEveryNthPellet(int interval) {
        int pelletIndex = 0;
        for (int mazeCellIndex = 0; mazeCellIndex < state.screenData.length; mazeCellIndex++) {
            if ((state.screenData[mazeCellIndex] & 16) != 0) {
                if (pelletIndex % interval != 0) {
                    state.screenData[mazeCellIndex] = (short) (state.screenData[mazeCellIndex] & 15);
                }
                pelletIndex++;
            }
        }
    }
}
