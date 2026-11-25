package p;

final class GameState {
    boolean inGame;
    boolean dying;
    boolean gameWon;
    long gameStartedAtNanos;
    long gameCompletedAtNanos;

    int lives;
    int score;
    int level = 1;
    int levelBannerTimer;
    boolean showLevelBanner;

    final String[] levelDifficulties = {
        "Easy", "Normal", "Hard", "Extra Hard", "Ultra Hard"
    };

    final short[] screenData = new short[GameConstants.N_BLOCKS * GameConstants.N_BLOCKS];
}
