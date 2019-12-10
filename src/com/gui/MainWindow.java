package com.gui;

import com.config.lol.LOLRole;
import com.gui.configuration.GUI_Config;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MainWindow extends JFrame {

  private boolean controlPressed;
  private boolean botRunning;
  private GlobalKeyboardHook keyboardHook;
  private HashMap<LOLRole, String[]> characters;
  private LOLRole selectedRole;
  private String selectedChampion;
  private JLabel statusLabel;
  private JComboBox<String> characterSelectComboBox;
  private Thread botThread;

  /**
   * Initializes the window and sets up a global keyboard hook so it can shutdown without focus
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  MainWindow() {
    super(GUI_Config.APPLICATION_NAME);
    initializeKeyboardHook();
    setSize(1280, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> keyboardHook.shutdownHook()));
    loadChampions();
    initializeElements();
    setVisible(true);
  }

  /**
   * The main method at which the program begins
   *
   * @param args Command line args, they get ignored
   * @author Dante Barbieri <pulchroxloom>
   */
  public static void main(String[] args) {
    new MainWindow();
  }

  /**
   * Initializes the keyboard hook attached to this object
   * <p>
   * Utilizes the system-hook library v3.5
   * Source: https://github.com/kristian/system-hook
   *
   * @author kristian
   */
  private void initializeKeyboardHook() {
    // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
    keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

    keyboardHook.addKeyListener(new GlobalKeyAdapter() {
      @Override
      public void keyPressed(GlobalKeyEvent event) {
        if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) controlPressed = true;
        else if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_C && controlPressed) killBot();
        else if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_R && controlPressed && !botRunning) runBot();
      }

      @Override
      public void keyReleased(GlobalKeyEvent event) {
        if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) controlPressed = false;
      }
    });
  }

  /**
   * Helper function to initialize the elements in the main JFrame.
   * It also sets the Look and Feel and the Favicon.
   *
   * @author Dante Barbieri <pulcroxloom>
   */
  private void initializeElements() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
      try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        // Use default look and feel, this is probably what failed to set above
      }
    }
    setIconImage(new ImageIcon(GUI_Config.FAVICON_LOCATION).getImage());
    getContentPane().setBackground(GUI_Config.BACKGROUND_COLOR);
    getContentPane().setLayout(new GridBagLayout());

    makeStatusPanel();

    makeInteractivePanels();
  }

  /**
   * Helper function that generates a basic GridBagConstraints Object with
   * common settings used in this particular GUI.
   *
   * @return outerConstraints - GridBagConstraints object with some minor settings commonly used.
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private GridBagConstraints makeOuterConstraints() {
    var outerConstraints = new GridBagConstraints();
    outerConstraints.weightx = 0.3;
    //    outerConstraints.weighty = 0.01;
    outerConstraints.ipadx = 6;
    outerConstraints.ipady = 10;
    return outerConstraints;
  }

  /**
   * Helper function that makes the Status Panel, a panel with a label that displays the current program status.
   *
   * @author Dante Barbieri
   */
  private void makeStatusPanel() {
    // Set up constraints
    var outerConstraints = makeOuterConstraints();
    var innerConstraints = new GridBagConstraints();

    // Create panel
    var statusPanel = new JPanel();
    statusPanel.setLayout(new GridBagLayout());
    statusPanel.setBackground(GUI_Config.PRIMARY_COLOR);

    // Create Label
    statusLabel = new JLabel(GUI_Config.STATUS_LABEL_DEFAULT);
    statusLabel.setFont(GUI_Config.LABEL_FONT);
    statusLabel.setForeground(GUI_Config.ACCENT_COLOR);

    // Configure constraints
    innerConstraints.weightx = 0.1;
    innerConstraints.insets = new Insets(1, 8, 1, 8);
    innerConstraints.anchor = GridBagConstraints.WEST;
    // Add label with constraints to panel
    statusPanel.add(statusLabel, innerConstraints);

    // Configure outer constraints
    outerConstraints.fill = GridBagConstraints.BOTH;
    outerConstraints.anchor = GridBagConstraints.NORTH;
    // Add status panel to main JFrame
    add(statusPanel, outerConstraints);
  }

  /**
   * Make the panel with the dropdown box (JComboBox)
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private void makeInteractivePanels() {
    // Create outer constraints
    var outerConstraints = makeOuterConstraints();

    // Generate panel to hold the dropdown and the label
    var interactivePanel = new JPanel();
    interactivePanel.setLayout(new GridBagLayout());
    interactivePanel.setBackground(GUI_Config.BACKGROUND_COLOR);

    // Generate panel for the dropdown
    var characterSelectPanel = makeCharacterSelectPanel();

    // Configure constraints and add the dropdown panel to the interactive panel
    outerConstraints.anchor = GridBagConstraints.NORTHWEST;
    interactivePanel.add(characterSelectPanel, outerConstraints);

    // Generate the buttons panel and add it to the interactive panel
    var roleSelectPanel = makeRoleSelectPanel();
    outerConstraints.anchor = GridBagConstraints.NORTHEAST;
    outerConstraints.gridx = 1;
    interactivePanel.add(roleSelectPanel, outerConstraints);

    // Configure constraints and add the interactive panel to the JFrame
    outerConstraints.anchor = GridBagConstraints.NORTH;
    outerConstraints.fill = GridBagConstraints.HORIZONTAL;
    outerConstraints.gridx = 0;
    outerConstraints.gridy = 1;
    add(interactivePanel, outerConstraints);
  }

  /**
   * Generate and return the Role Select Panel with a Summoner's Rift Background and buttons properly laid out
   *
   * @return Role Select Panel
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private JPanel makeRoleSelectPanel() {
    var innerConstraints = new GridBagConstraints();

    var roleSelectPanel = new JPanel();
    roleSelectPanel.setLayout(new GridBagLayout());
    roleSelectPanel.setBackground(GUI_Config.PRIMARY_COLOR);

    var roleSelectLabel = new JLabel(GUI_Config.ROLE_SELECT_LABEL);
    roleSelectLabel.setFont(GUI_Config.LABEL_FONT);
    roleSelectLabel.setForeground(GUI_Config.ACCENT_COLOR);

    var roleButtonsPanel = makeRoleButtonsPanel();
    roleButtonsPanel.setBackground(Color.MAGENTA);

    innerConstraints.anchor = GridBagConstraints.NORTH;
    roleSelectPanel.add(roleSelectLabel, innerConstraints);

    innerConstraints.gridy = 1;
    innerConstraints.fill = GridBagConstraints.BOTH;
    innerConstraints.anchor = GridBagConstraints.NORTHWEST;
    roleSelectPanel.add(roleButtonsPanel, innerConstraints);

    return roleSelectPanel;
  }

  /**
   * This function makes the buttons panel and lays things out. It is an unending cycle of layout misery.
   *
   * @return Panel with background and buttons
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private JPanel makeRoleButtonsPanel() {
    var innerConstraints = new GridBagConstraints();

    // Make and configure panel with background art
    var roleButtonsPanel = new JPanel() {
      Image backgroundImage;

      void setBackgroundImage(Image img) {
        backgroundImage = img;
      }

      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
      }
    };
    roleButtonsPanel.setLayout(new GridBagLayout());
    var summonersRift = new ImageIcon(GUI_Config.SUMMONERS_RIFT_LOCATION).getImage();
    roleButtonsPanel.setBackground(Color.MAGENTA);
    roleButtonsPanel.setBackgroundImage(summonersRift);
    roleButtonsPanel.setPreferredSize(new Dimension((int) (summonersRift.getWidth(null) * GUI_Config.SUMMONERS_RIFT_SCALING), (int) (summonersRift.getHeight(null) * GUI_Config.SUMMONERS_RIFT_SCALING)));

    // Make and configure Buttons
    var adcButton = new JButton(GUI_Config.ADC_BUTTON_DESCRIPTION);
    adcButton.setPreferredSize(GUI_Config.ROLE_BUTTON_DIMENSION);
    adcButton.setFont(GUI_Config.ROLE_BUTTON_FONT);
//    adcButton.setOpaque(false);
//    adcButton.setContentAreaFilled(false);
    adcButton.setBackground(GUI_Config.PRIMARY_COLOR);
    adcButton.setForeground(GUI_Config.BACKGROUND_COLOR);
    adcButton.addActionListener(e -> {
      selectedRole = LOLRole.ADC;
      updateCharacterSelectComboBox();
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_CHAMPION, selectedRole));
    });

    var jungleButton = new JButton(GUI_Config.JUNGLE_BUTTON_DESCRIPTION);
    jungleButton.setPreferredSize(GUI_Config.ROLE_BUTTON_DIMENSION);
    jungleButton.setFont(GUI_Config.ROLE_BUTTON_FONT);
//    jungleButton.setOpaque(false);
//    jungleButton.setContentAreaFilled(false);
    jungleButton.setBackground(GUI_Config.PRIMARY_COLOR);
    jungleButton.setForeground(GUI_Config.BACKGROUND_COLOR);
    jungleButton.addActionListener(e -> {
      selectedRole = LOLRole.JUNGLE;
      updateCharacterSelectComboBox();
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_CHAMPION, selectedRole));
    });

    var midButton = new JButton(GUI_Config.MID_BUTTON_DESCRIPTION);
    midButton.setPreferredSize(GUI_Config.ROLE_BUTTON_DIMENSION);
    midButton.setFont(GUI_Config.ROLE_BUTTON_FONT);
//    midButton.setOpaque(false);
//    midButton.setContentAreaFilled(false);
    midButton.setBackground(GUI_Config.PRIMARY_COLOR);
    midButton.setForeground(GUI_Config.BACKGROUND_COLOR);
    midButton.addActionListener(e -> {
      selectedRole = LOLRole.MID;
      updateCharacterSelectComboBox();
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_CHAMPION, selectedRole));
    });

    var supportButton = new JButton(GUI_Config.SUPPORT_BUTTON_DESCRIPTION);
    supportButton.setPreferredSize(GUI_Config.ROLE_BUTTON_DIMENSION);
    supportButton.setFont(GUI_Config.ROLE_BUTTON_FONT);
//    supportButton.setOpaque(false);
//    supportButton.setContentAreaFilled(false);
    supportButton.setBackground(GUI_Config.PRIMARY_COLOR);
    supportButton.setForeground(GUI_Config.BACKGROUND_COLOR);
    supportButton.addActionListener(e -> {
      selectedRole = LOLRole.SUPPORT;
      updateCharacterSelectComboBox();
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_CHAMPION, selectedRole));
    });

    var topButton = new JButton(GUI_Config.TOP_BUTTON_DESCRIPTION);
    topButton.setPreferredSize(GUI_Config.ROLE_BUTTON_DIMENSION);
    topButton.setFont(GUI_Config.ROLE_BUTTON_FONT);
//    topButton.setOpaque(false);
//    topButton.setContentAreaFilled(false);
    topButton.setBackground(GUI_Config.PRIMARY_COLOR);
    topButton.setForeground(GUI_Config.BACKGROUND_COLOR);
    topButton.addActionListener(e -> {
      selectedRole = LOLRole.TOP;
      updateCharacterSelectComboBox();
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_CHAMPION, selectedRole));
    });

    // Lay buttons in panel
    var emptyBox = new JPanel();
    emptyBox.setPreferredSize(GUI_Config.ROLE_BUTTON_SPACER_DIMENSION);
    emptyBox.setBackground(Color.CYAN);
    emptyBox.setOpaque(false);

    innerConstraints.anchor = GridBagConstraints.NORTHWEST;
    innerConstraints.weighty = 1;
    roleButtonsPanel.add(topButton, innerConstraints);

    innerConstraints.gridy = 1;
    roleButtonsPanel.add(emptyBox, innerConstraints);

    emptyBox = new JPanel();
    emptyBox.setPreferredSize(GUI_Config.ROLE_BUTTON_SPACER_DIMENSION);
    emptyBox.setBackground(Color.MAGENTA);
    emptyBox.setOpaque(false);

    innerConstraints.gridy = 2;
    roleButtonsPanel.add(emptyBox, innerConstraints);

    innerConstraints.anchor = GridBagConstraints.CENTER;
    innerConstraints.gridx = 1;
    innerConstraints.gridy = 8;
    roleButtonsPanel.add(midButton, innerConstraints);

    innerConstraints.gridx = 2;
    innerConstraints.gridy = 9;
    roleButtonsPanel.add(jungleButton, innerConstraints);

    innerConstraints.anchor = GridBagConstraints.SOUTH;
    innerConstraints.gridy = 11;
    roleButtonsPanel.add(supportButton, innerConstraints);

    innerConstraints.anchor = GridBagConstraints.SOUTHEAST;
    innerConstraints.gridx = 4;
    innerConstraints.gridy = 10;
    roleButtonsPanel.add(adcButton, innerConstraints);

    return roleButtonsPanel;
  }

  /**
   * This updates the JComboBox to ensure that it holds the champions that can play the selected role.
   * <p>
   * It also enables the box to select a champion, and to update the Interface, when the bot is not running
   */
  private void updateCharacterSelectComboBox() {
    selectedChampion = null;
    if (selectedRole == null || characters.get(selectedRole) == null)
      characterSelectComboBox.setModel(new JComboBox<String>().getModel());
    else characterSelectComboBox.setModel(new JComboBox<>(characters.get(selectedRole)).getModel());
    characterSelectComboBox.setSelectedIndex(-1);
    characterSelectComboBox.addActionListener(e -> {
      if (!botRunning) {
        selectedChampion = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
        statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_READY, selectedChampion, selectedRole));
        setBGColor(GUI_Config.BACKGROUND_COLOR);
      }
    });
  }

  /**
   * This creates the panel in which the JComboBox and its supporting label live.
   *
   * @return JPanel with JComboBox for champion selection
   */
  private JPanel makeCharacterSelectPanel() {
    var innerConstraints = new GridBagConstraints();

    var characterSelectPanel = new JPanel();
    characterSelectPanel.setLayout(new GridBagLayout());
    characterSelectPanel.setBackground(GUI_Config.PRIMARY_COLOR);

    var characterSelectLabel = new JLabel(GUI_Config.CHARACTER_SELECT_LABEL);
    characterSelectLabel.setFont(GUI_Config.LABEL_FONT);
    characterSelectLabel.setForeground(GUI_Config.ACCENT_COLOR);

    characterSelectComboBox = new JComboBox<>();
    updateCharacterSelectComboBox();

    innerConstraints.anchor = GridBagConstraints.NORTH;
    characterSelectPanel.add(characterSelectLabel, innerConstraints);
    innerConstraints.gridy = 1;
    innerConstraints.insets = new Insets(1, 4, 3, 4);
    innerConstraints.fill = GridBagConstraints.HORIZONTAL;
    characterSelectPanel.add(characterSelectComboBox, innerConstraints);
    return characterSelectPanel;
  }

  /**
   * Gets the characters from an input file and stores them into the Map
   *
   * @param fileInfo - Filename and path of the file with character data.
   * @return Map filled with data from the file
   */
  private HashMap<LOLRole, String[]> getCharacters(String fileInfo) {
    var champions = new HashMap<LOLRole, String[]>();
    var workingChampions = new HashMap<String, HashSet<LOLRole>>();
    try (Scanner scanner = new Scanner(new File(fileInfo))) {
      while (scanner.hasNext()) {
        var line = scanner.nextLine();
        if (line.strip().isEmpty() || line.strip().charAt(0) == '#') continue;
        var lineTokens = line.split(":");
        if (lineTokens.length != 2) continue;
        var name = lineTokens[0].strip();
        var roles = lineTokens[1].split(",");
        Arrays.setAll(roles, i -> roles[i].strip().toLowerCase());
        var currentRoles = workingChampions.get(name);
        if (currentRoles == null)
          workingChampions.put(name, new HashSet<>(Arrays.asList(stringToLOLRole(roles))));
        else currentRoles.addAll(Arrays.asList(stringToLOLRole(roles)));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    var adcChampions = new HashSet<String>();
    var jungleChampions = new HashSet<String>();
    var midChampions = new HashSet<String>();
    var supportChampions = new HashSet<String>();
    var topChampions = new HashSet<String>();
    for (var champion : workingChampions.keySet()) {
      var roles = workingChampions.get(champion);
      if (roles.contains(LOLRole.ADC)) adcChampions.add(champion);
      if (roles.contains(LOLRole.JUNGLE)) jungleChampions.add(champion);
      if (roles.contains(LOLRole.MID)) midChampions.add(champion);
      if (roles.contains(LOLRole.SUPPORT)) supportChampions.add(champion);
      if (roles.contains(LOLRole.TOP)) topChampions.add(champion);
    }
    champions.put(LOLRole.ADC, Arrays.copyOf(adcChampions.toArray(), adcChampions.size(), String[].class));
    champions.put(LOLRole.JUNGLE, Arrays.copyOf(jungleChampions.toArray(), jungleChampions.size(), String[].class));
    champions.put(LOLRole.MID, Arrays.copyOf(midChampions.toArray(), midChampions.size(), String[].class));
    champions.put(LOLRole.SUPPORT, Arrays.copyOf(supportChampions.toArray(), supportChampions.size(), String[].class));
    champions.put(LOLRole.TOP, Arrays.copyOf(topChampions.toArray(), topChampions.size(), String[].class));
    for (var champion : champions.keySet())
      Arrays.sort(champions.get(champion));
    return champions;
  }

  /**
   * Converts a string array to a LOLRole array and returns it.
   *
   * @param arr String array to convert element by element to a LOLRole
   * @return Array of LOLRole objects created by parsing the input array
   */
  private LOLRole[] stringToLOLRole(String[] arr) {
    var roles = new LOLRole[arr.length];
    for (var i = 0; i < arr.length; ++i)
      if (arr[i].equals(LOLRole.ADC.toString().toLowerCase())) roles[i] = LOLRole.ADC;
      else if (arr[i].equals(LOLRole.JUNGLE.toString().toLowerCase())) roles[i] = LOLRole.JUNGLE;
      else if (arr[i].equals(LOLRole.MID.toString().toLowerCase())) roles[i] = LOLRole.MID;
      else if (arr[i].equals(LOLRole.SUPPORT.toString().toLowerCase())) roles[i] = LOLRole.SUPPORT;
      else if (arr[i].equals(LOLRole.TOP.toString().toLowerCase())) roles[i] = LOLRole.TOP;
      else roles[i] = null;
    return roles;
  }

  /**
   * Load the champions from a file, emptying the map beforehand and deselecting a champion
   */
  private void loadChampions() {
    if (!botRunning) {
      selectedChampion = null;
      if (statusLabel != null)
        statusLabel.setText(GUI_Config.STATUS_LABEL_CHAMPION);
      characters = getCharacters(GUI_Config.DATA_PATH + "Champions.txt");
    }
  }

  /**
   * Initializes bot running
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private void runBot() {
    if (!botRunning) {
      if (selectedChampion == null && selectedRole == null) statusLabel.setText(GUI_Config.STATUS_LABEL_NOT_READY);
      else if (selectedChampion == null) statusLabel.setText(GUI_Config.STATUS_LABEL_NOT_READY_CHAMPION);
      else {
        statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_PLAYING, selectedChampion, selectedRole));
        setBGColor(Color.GREEN);
        botRunning = true;
      }
    }
  }

  /**
   * Kills bot thread
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private void killBot() {
    if (botRunning) {
      statusLabel.setText(String.format(GUI_Config.STATUS_LABEL_READY, selectedChampion, selectedRole));
      setBGColor(Color.RED);
      botRunning = false;
    }
  }

  /**
   * Set background color of main window
   *
   * @param c Color to set the background
   * @author Dante Barbieri <pulchroxloom>
   */
  private void setBGColor(Color c) {
    getContentPane().setBackground(c);
  }
}
