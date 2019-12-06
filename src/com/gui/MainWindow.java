package com.gui;

import com.gui.configuration.GUI_Config;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class MainWindow extends JFrame {

  private static boolean botRunning;
  private Thread botThread;

  MainWindow() {
    super(GUI_Config.APPLICATION_NAME);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    setSize(1920, 1080);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        setBGColor(Color.GREEN);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        setBGColor(Color.RED);
      }
    });
    setVisible(true);
  }

  private void setBGColor(Color c) {
    getContentPane().setBackground(c);
  }

  public static void main(String[] args) {
    // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
    GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

    System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");

    for (Map.Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
      System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
    }

    keyboardHook.addKeyListener(new GlobalKeyAdapter() {

      @Override
      public void keyPressed(GlobalKeyEvent event) {
        System.out.println(event);
        if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
          botRunning = false;
        }
      }

      @Override
      public void keyReleased(GlobalKeyEvent event) {
        System.out.println(event);
      }
    });

    try {
      while (botRunning) {
        Thread.sleep(128);
      }
    } catch (InterruptedException e) {
      //Do nothing
    } finally {
      keyboardHook.shutdownHook();
    }

    new MainWindow();
  }
}
