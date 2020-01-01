import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GUIProperties {
    static final String APPLICATION_NAME = "League of Legends Bot Launcher";
    static final URL FAVICON_LOCATION = GUIProperties.class.getResource("resources/favicon.jpg");
    static final URL SUMMONERS_RIFT_LOCATION = GUIProperties.class.getResource("resources/summoners-rift.jpg");
    static final URL RESOURCES_LOCATION = GUIProperties.class.getResource("resources/");
    static final String CHAMPIONS_FILE = "./data/Champions.txt";
    static final String STATUS_LABEL_DEFAULT = "Status: Select a role and a champion.";
    static final String STATUS_LABEL_CHAMPION = "Status: Selection a champion to play %s.";
    static final String STATUS_LABEL_NOT_READY = "Status: Not ready to play yet, select a role and a champion.";
    static final String STATUS_LABEL_NOT_READY_CHAMPION = "Status: Not ready to play yet, select a champion.";
    static final String STATUS_LABEL_READY = "Status: Ready to play %s %s. Press [Ctrl] + [R] to start.";
    static final String STATUS_LABEL_PLAYING = "Status: Playing %s %s. Press [Ctrl] + [C] to stop.";
    static final String STATUS_LABEL_BAD_RESOLUTION = "Status: Input resolution is incorrect. Please fix.";
    static final String TIME_LABEL_FORMAT = "Time: %s";
    static final int FONT_SIZE = 12;
    static final Font STANDARD_FONT = new Font("Constantia", Font.PLAIN, FONT_SIZE);
    static final Font STANDARD_FONT_LARGE = new Font("Constantia", Font.PLAIN, Math.round(1.5f * FONT_SIZE));
    static final Font STANDARD_BOLD_FONT = new Font("Constantia", Font.BOLD, FONT_SIZE);
    static final Font MONOSPACED = new Font("Consolas", Font.PLAIN, FONT_SIZE);
    static final Color LOL_BACKGROUND_BLUE = new Color(1, 10, 19);
    static final Color LOL_ACCENT_GOLD = new Color(120, 90, 40);
    static final Color DARK_GRAY = new Color(60, 63, 65);
    static final String CHAMPION_ICONS = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/%s.png";
    static final String LOADING_ICON_URL = "https://www.drupal.org/files/issues/throbber_13.gif";
    static ImageIcon LOADING_ICON = null;

    static {
        try {
            LOADING_ICON = new ImageIcon(new URL(LOADING_ICON_URL));
        } catch (MalformedURLException ignored) {
        }
    }
}
