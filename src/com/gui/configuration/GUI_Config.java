package com.gui.configuration;

import java.awt.*;
import java.net.URL;

public class GUI_Config {
  public static String APPLICATION_NAME = "League of Legends Bot Launcher";
  public static URL FAVICON_LOCATION = GUI_Config.class.getResource("../resources/favicon.jpg");
  public static Color BACKGROUND_COLOR = new Color(1, 10, 19);
  public static Color PRIMARY_COLOR = new Color(2, 20, 38);
  public static Color ACCENT_COLOR = new Color(120, 90, 40);
  public static String CHARACTER_SELECT_LABEL = "Champion Select:";
  public static String STATUS_LABEL_DEFAULT = "Status: Select a role and a champion.";
  public static String STATUS_LABEL_CHAMPION = "Status: Selection a champion to play %s.";
  public static String STATUS_LABEL_READY = "Status: Ready to play %s %s. Press [Ctrl] + [R] to start.";
  public static String STATUS_LABEL_PLAYING = "Status: Playing %s %s. Press [Ctrl] + [C] to stop.";
  public static Font LABEL_FONT = new Font("Aster", Font.PLAIN, 48);
}
