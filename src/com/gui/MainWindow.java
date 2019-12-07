package com.gui;

import com.gui.configuration.GUI_Config;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

  private GlobalKeyboardHook keyboardHook;
  private boolean controlPressed;
  private boolean botRunning;
  private Thread botThread;

  MainWindow() {
    super(GUI_Config.APPLICATION_NAME);

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

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    setSize(1280, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> keyboardHook.shutdownHook()));
    setVisible(true);
  }

  private void runBot() {
    setBGColor(Color.GREEN);
    botRunning = true;
  }

  private void killBot() {
    setBGColor(Color.RED);
    botRunning = false;
  }

  /**
   * Set background color of main window
   *
   * @param c Color to set the background
   */
  private void setBGColor(Color c) {
    getContentPane().setBackground(c);
  }

  public static void main(String[] args) {
    new MainWindow();
  }
}
