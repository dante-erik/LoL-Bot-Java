package com.gui;

import com.gui.configuration.GUI_Config;

import javax.swing.*;

public class MainWindow extends JFrame {

  public MainWindow() {
    super(GUI_Config.APPLICATION_NAME);
    setSize(1920, 1080);
    setVisible(true);
  }

  public static void main(String[] args) {
    new MainWindow();
  }
}
