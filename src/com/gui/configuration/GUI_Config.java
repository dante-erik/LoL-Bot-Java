package com.gui.configuration;

import java.awt.*;
import java.net.URL;

public class GUI_Config {
  public static final String APPLICATION_NAME = "League of Legends Bot Launcher";
  public static final String DATA_PATH = "./data/";
  public static final URL FAVICON_LOCATION = GUI_Config.class.getResource("../resources/favicon.jpg");
  public static final URL SUMMONERS_RIFT_LOCATION = GUI_Config.class.getResource("../resources/summoners-rift0,25x.jpg");
  public static final Color BACKGROUND_COLOR = new Color(1, 10, 19);
  public static final Color PRIMARY_COLOR = new Color(2, 20, 38);
  public static final Color ACCENT_COLOR = new Color(120, 90, 40);
  /**
   * Pink could be used for buttons in roleButtonsPanel, but does not contrast enough with the Rift
   *
   * @deprecated
   */
  public static final Color BRIGHT_PINK_COLOR = new Color(255, 67, 84);
  public static final String CHARACTER_SELECT_LABEL = "Champion Select:";
  public static final String ROLE_SELECT_LABEL = "Role Select:";
  public static final String STATUS_LABEL_DEFAULT = "Status: Select a role and a champion.";
  public static final String STATUS_LABEL_CHAMPION = "Status: Selection a champion to play %s.";
  public static final String STATUS_LABEL_NOT_READY = "Status: Not ready to play yet, select a role and a champion.";
  public static final String STATUS_LABEL_NOT_READY_CHAMPION = "Status: Not ready to play yet, select a champion.";
  public static final String STATUS_LABEL_READY = "Status: Ready to play %s %s. Press [Ctrl] + [R] to start.";
  public static final String STATUS_LABEL_PLAYING = "Status: Playing %s %s. Press [Ctrl] + [C] to stop.";
  public static final String ADC_BUTTON_DESCRIPTION = "ADC";
  public static final String JUNGLE_BUTTON_DESCRIPTION = "Jungle";
  public static final String MID_BUTTON_DESCRIPTION = "Mid";
  public static final String SUPPORT_BUTTON_DESCRIPTION = "Support";
  public static final String TOP_BUTTON_DESCRIPTION = "Top";
  public static final Dimension ROLE_BUTTON_DIMENSION = new Dimension(100, 40);
  public static final Dimension ROLE_BUTTON_SPACER_DIMENSION = new Dimension(120, 60);
  public static final Font ROLE_BUTTON_FONT = new Font("Constantia", Font.BOLD, 16);
  public static final Font LABEL_FONT = new Font("Constantia", Font.PLAIN, 42);
  public static final double SUMMONERS_RIFT_SCALING = 0.75;
}
