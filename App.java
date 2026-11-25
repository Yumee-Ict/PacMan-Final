package pacman;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import p.Model2;

public class App extends JFrame {
    private static final String GAME_CARD = "game";
    private static final String SETTINGS_CARD = "settings";

    private final Model2 game = new Model2();
    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);

    public App() {
        setTitle("Pacman");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setFocusable(false);
        leaderboardButton.addActionListener(event -> game.showLeaderboard());

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFocusable(false);
        settingsButton.addActionListener(event -> showSettings());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controls.add(settingsButton);
        controls.add(leaderboardButton);

        content.add(game, GAME_CARD);
        content.add(new p.SettingsMenuController(this::showGame), SETTINGS_CARD);

        add(controls, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        pack();
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    private void showSettings() {
        cards.show(content, SETTINGS_CARD);
    }

    private void showGame() {
        cards.show(content, GAME_CARD);
        game.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App pac = new App();
            pac.setVisible(true);
        });
    }
}
