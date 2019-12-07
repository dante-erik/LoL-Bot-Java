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
        if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) setBGColor(Color.GREEN);
        else setBGColor(Color.CYAN);
      }

      @Override
      public void keyReleased(GlobalKeyEvent event) {
        if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_C) setBGColor(Color.RED);
        else setBGColor(Color.MAGENTA);
      }
    });

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    setSize(1920, 1080);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> keyboardHook.shutdownHook()));
    setVisible(true);
  }

  private void setBGColor(Color c) {
    getContentPane().setBackground(c);
  }

  public static void main(String[] args) {
    new MainWindow();
  }
}
