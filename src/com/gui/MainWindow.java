package com.gui;

import com.gui.configuration.GUI_Config;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

  private boolean controlPressed;
  private boolean botRunning;
  private GlobalKeyboardHook keyboardHook;
  private JLabel statusLabel;
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

    var outerConstraints = new GridBagConstraints();
    outerConstraints.weightx = 0.3;
    outerConstraints.weighty = 0.3;
    outerConstraints.ipadx = 6;
    outerConstraints.ipady = 10;

    var innerConstraints = new GridBagConstraints();

    var statusPanel = new JPanel();
    statusPanel.setLayout(new GridBagLayout());
    statusPanel.setBackground(GUI_Config.PRIMARY_COLOR);

    statusLabel = new JLabel(GUI_Config.STATUS_LABEL_DEFAULT);
    statusLabel.setFont(GUI_Config.LABEL_FONT);
    statusLabel.setForeground(GUI_Config.ACCENT_COLOR);

    statusPanel.add(statusLabel, innerConstraints);

    outerConstraints.fill = GridBagConstraints.HORIZONTAL;
    outerConstraints.anchor = GridBagConstraints.NORTH;
    add(statusPanel, outerConstraints);

    var characterSelectPanel = new JPanel();
    characterSelectPanel.setLayout(new GridBagLayout());
    characterSelectPanel.setBackground(GUI_Config.PRIMARY_COLOR);

    var characterSelectLabel = new JLabel(GUI_Config.CHARACTER_SELECT_LABEL);
    characterSelectLabel.setFont(GUI_Config.LABEL_FONT);
    characterSelectLabel.setForeground(GUI_Config.ACCENT_COLOR);

    characterSelectPanel.add(characterSelectLabel, innerConstraints);

    outerConstraints.fill = GridBagConstraints.NONE;
    outerConstraints.anchor = GridBagConstraints.NORTHWEST;
    outerConstraints.gridy = 1;
    add(characterSelectPanel, outerConstraints);
  }

  /**
   * Initializes bot running
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private void runBot() {
    setBGColor(Color.GREEN);
    botRunning = true;
  }

  /**
   * Kills bot thread
   *
   * @author Dante Barbieri <pulchroxloom>
   */
  private void killBot() {
    setBGColor(Color.RED);
    botRunning = false;
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
