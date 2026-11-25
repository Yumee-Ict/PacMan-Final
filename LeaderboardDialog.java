package p;

import java.util.List;
import java.util.prefs.BackingStoreException;
import javax.swing.JOptionPane;

final class LeaderboardDialog {
    private static final String ERROR_TITLE = "Leaderboard Error";
    private final Model2 game;

    LeaderboardDialog(Model2 game) {
        this.game = game;
    }

    void promptForEntry() {
        try {
            long completionTime = Math.max(0,
                    (game.state.gameCompletedAtNanos - game.state.gameStartedAtNanos) / 1_000_000L);
            if (!Leaderboard.qualifies(Leaderboard.getEntries(), completionTime)) {
                return;
            }

            String input = JOptionPane.showInputDialog(game,
                    "Your time: " + Leaderboard.formatTime(completionTime) + "\nEnter your name:",
                    "New Top 5 Record", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                return;
            }

            String name = input.trim();
            if (name.isEmpty()) {
                name = "Player";
            }
            Leaderboard.addEntry(name, completionTime);
            showLeaderboard();
        } catch (BackingStoreException | IllegalStateException | SecurityException ex) {
            JOptionPane.showMessageDialog(game,
                    "Could not save the leaderboard: " + ex.getMessage(),
                    ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

    void showLeaderboard() {
        try {
            List<Leaderboard.Entry> entries = Leaderboard.getEntries();
            StringBuilder text = new StringBuilder();
            if (entries.isEmpty()) {
                text.append("No records yet. Win the game to set the first time!");
            } else {
                for (int entryIndex = 0; entryIndex < entries.size(); entryIndex++) {
                    Leaderboard.Entry entry = entries.get(entryIndex);
                    text.append(entryIndex + 1)
                            .append(". ")
                            .append(entry.name)
                            .append(" - ")
                            .append(Leaderboard.formatTime(entry.timeMillis))
                            .append('\n');
                }
            }
            JOptionPane.showMessageDialog(game, text.toString(),
                    "Leaderboard - Top 5", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException | SecurityException ex) {
            JOptionPane.showMessageDialog(game,
                    "Could not load the leaderboard: " + ex.getMessage(),
                    ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
