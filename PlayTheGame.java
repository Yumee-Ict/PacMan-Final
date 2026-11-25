package p;

import javax.swing.SwingUtilities;
import pacman.App;

public class PlayTheGame {

    public static void main(String[] args) {
        if (args.length > 0) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            App game = new App();
            game.setVisible(true);
        });
    }
}
