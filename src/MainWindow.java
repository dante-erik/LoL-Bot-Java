import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class MainWindow extends JFrame {

    private final static String[] resolutionSizes = new String[]{"1024 x 576", "1280 x 720", "1600 x 900", "1920 x 1080", "2560 x 1440"};

    private GlobalKeyboardHook keyboardHook;
    private boolean botRunning = false;
    private boolean controlPressed = false;
    private String selectedChampion;
    private LOLRole selectedRole;
    private Dimension selectedDimension;
    private Thread botThread;
    private Timer upTime;
    private Timer botOutputTime;
    private Instant startTime;
    private HashMap<LOLRole, String[]> characters;
    private ByteArrayOutputStream bos;
    private JPanel rootPanel;
    private JButton startBotButton;
    private JButton stopBotButton;
    private JLabel statusLabel;
    private JLabel selectChampionLabel;
    private JComboBox<String> selectChampionComboBox;
    private JPanel leftBodyPanel;
    private JPanel rightBodyPanel;
    private JPanel threadButtonsPanel;
    private JPanel statusPanel;
    private JPanel selectRolePanel;
    private JLabel selectRoleLabel;
    private JPanel selectRoleButtonsPanel;
    private JButton topButton;
    private JButton jungleButton;
    private JButton midButton;
    private JButton adcButton;
    private JButton supportButton;
    private JPanel resolutionPanel;
    private JLabel resolutionLabel;
    private JComboBox<String> resolutionComboBox;
    private JLabel currentChampionLabel;
    private JLabel timerLabel;
    private JPanel activeRunningLabel;
    private JLabel botOutputLabel;
    private JScrollPane botOutputScrollPanel;
    private JPanel botOutputPanel;
    private JLabel botOutputLabelHeader;
    private JPanel selectChampionPanel;

    public MainWindow() {
        super(GUIProperties.APPLICATION_NAME);
        initializeKeyboardHook();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> keyboardHook.shutdownHook()));
        setIconImage(new ImageIcon(GUIProperties.FAVICON_LOCATION).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setFontForAllComponents();
        setThreadButtonActionListeners();
        setRoleButtonActionListeners();
        loadChampions();
        pack();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignore) {
            }
        }
        var window = new MainWindow();
        window.setVisible(true);
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
                else if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_V && controlPressed && !botRunning) runBot();
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) controlPressed = false;
            }
        });
    }

    private void setFontForAllComponents() {
        startBotButton.setFont(GUIProperties.STANDARD_FONT_LARGE);
        stopBotButton.setFont(GUIProperties.STANDARD_FONT_LARGE);
        statusLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        selectChampionLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        selectChampionComboBox.setFont(GUIProperties.STANDARD_FONT_LARGE);
        selectRoleLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        topButton.setFont(GUIProperties.STANDARD_FONT);
        jungleButton.setFont(GUIProperties.STANDARD_FONT);
        midButton.setFont(GUIProperties.STANDARD_FONT);
        adcButton.setFont(GUIProperties.STANDARD_FONT);
        supportButton.setFont(GUIProperties.STANDARD_FONT);
        resolutionLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        resolutionComboBox.setFont(GUIProperties.STANDARD_FONT_LARGE);
        currentChampionLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        timerLabel.setFont(GUIProperties.STANDARD_FONT_LARGE);
        botOutputLabelHeader.setFont(GUIProperties.STANDARD_FONT_LARGE);
        botOutputLabel.setFont(GUIProperties.MONOSPACED);
    }

    private void createUIComponents() {
        selectRoleButtonsPanel = new JPanel() {
            final Image backgroundImage = new ImageIcon(GUIProperties.SUMMONERS_RIFT_LOCATION).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d;
                Container c = getParent();
                if (c == null) return new Dimension(10, 10);
                else d = c.getSize();
                int w = (int) d.getWidth();
                int h = (int) d.getHeight();
                int s = Math.min(w, h);
                return new Dimension(s, s);
            }
        };
        resolutionComboBox = new JComboBox<>(resolutionSizes);
        resolutionComboBox.setSelectedIndex(1);
        resolutionComboBox.addActionListener(e -> selectedDimension = parseDimension((String) Objects.requireNonNull(((JComboBox<?>) e.getSource()).getSelectedItem())));
        selectedDimension = parseDimension((String) Objects.requireNonNull(resolutionComboBox.getSelectedItem()));
    }

    private Dimension parseDimension(String selectedItem) {
        String[] components = selectedItem.split("x");
        if (components.length != 2) {
            statusLabel.setText(GUIProperties.STATUS_LABEL_BAD_RESOLUTION);
            return null;
        }
        for (var i = 0; i < components.length; ++i) components[i] = components[i].strip();
        var width = 0;
        var height = 0;
        try {
            width = Integer.parseInt(components[0]);
            height = Integer.parseInt(components[1]);
        } catch (NumberFormatException e) {
            statusLabel.setText(GUIProperties.STATUS_LABEL_BAD_RESOLUTION);
            return null;
        }
        return new Dimension(width, height);
    }

    private void setThreadButtonActionListeners() {
        startBotButton.addActionListener(e -> runBot());
        stopBotButton.addActionListener(e -> killBot());
    }

    private void resetRoleButtonLookAndFeel() {
        adcButton.setFont(GUIProperties.STANDARD_FONT);
        jungleButton.setFont(GUIProperties.STANDARD_FONT);
        midButton.setFont(GUIProperties.STANDARD_FONT);
        supportButton.setFont(GUIProperties.STANDARD_FONT);
        topButton.setFont(GUIProperties.STANDARD_FONT);
    }

    private ActionListener createRoleButtonActionListener(JButton button, LOLRole role) {
        return e -> {
            selectedRole = role;
            loadChampions();
            updateCharacterSelectComboBox();
            resetRoleButtonLookAndFeel();
            button.setFont(GUIProperties.STANDARD_BOLD_FONT);
            statusLabel.setText(String.format(GUIProperties.STATUS_LABEL_CHAMPION, role));
            currentChampionLabel.setIcon(null);
            currentChampionLabel.setText(role.toString());
        };
    }

    private void setRoleButtonActionListeners() {
        resetRoleButtonLookAndFeel();
        adcButton.addActionListener(createRoleButtonActionListener(adcButton, LOLRole.ADC));
        jungleButton.addActionListener(createRoleButtonActionListener(jungleButton, LOLRole.JUNGLE));
        midButton.addActionListener(createRoleButtonActionListener(midButton, LOLRole.MID));
        supportButton.addActionListener(createRoleButtonActionListener(supportButton, LOLRole.SUPPORT));
        topButton.addActionListener(createRoleButtonActionListener(topButton, LOLRole.TOP));
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
            if (selectedRole != null)
                statusLabel.setText(GUIProperties.STATUS_LABEL_CHAMPION);
            characters = getCharacters(GUIProperties.CHAMPIONS_FILE);
        }
    }

    /**
     * This updates the JComboBox to ensure that it holds the champions that can play the selected role.
     * <p>
     * It also enables the box to select a champion, and to update the Interface, when the bot is not running
     */
    private void updateCharacterSelectComboBox() {
        selectedChampion = null;
        if (selectedRole == null || characters.get(selectedRole) == null)
            selectChampionComboBox.setModel(new JComboBox<String>().getModel());
        else selectChampionComboBox.setModel(new JComboBox<>(characters.get(selectedRole)).getModel());
        selectChampionComboBox.setSelectedIndex(-1);
        selectChampionComboBox.addActionListener(e -> {
            if (!botRunning) {
                selectedChampion = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
                statusLabel.setText(String.format(GUIProperties.STATUS_LABEL_READY, selectedChampion, selectedRole));
                updateCurrentChampionLabel();
                threadButtonsPanel.setBackground(GUIProperties.LOL_BACKGROUND_BLUE);
            }
        });
    }

    private String capitalizeFirstLetter(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.toLowerCase().substring(1);
    }

    private String convertToURLStyle(String championName) {
        if (championName == null) return null;
        else if (championName.equals("Jarvan IV")) return "JarvanIV";
        else if (championName.equals("Kog'Maw")) return "KogMaw";
        else if (championName.equals("Rek'Sai")) return "RekSai";
        var styleName = championName;
        styleName = capitalizeFirstLetter(styleName);
        var styleNameList = styleName.split(" ");
        for (var i = 1; i < styleNameList.length; ++i) styleNameList[i] = capitalizeFirstLetter(styleNameList[i]);
        StringBuilder sb = new StringBuilder(styleNameList[0]);
        for (var i = 1; i < styleNameList.length; ++i) sb.append(styleNameList[i]);
        var retStr = sb.toString();
        return retStr.replaceAll("\\W", "");
    }

    private URL getChampionImage() {
        var urlName = convertToURLStyle(selectedChampion);
        if (urlName == null) return null;
        else try {
            return new URL(String.format(GUIProperties.CHAMPION_ICONS, urlName));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void updateCurrentChampionLabel() {
        if (!botRunning) {
            currentChampionLabel.setText(selectedRole.toString());
            currentChampionLabel.setIcon(GUIProperties.LOADING_ICON);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    var championImageURL = getChampionImage();
                    if (championImageURL == null) currentChampionLabel.setIcon(null);
                    else currentChampionLabel.setIcon(new ImageIcon(getChampionImage()));
                }
            }.start();
        }
    }

    /**
     * Initializes bot running
     *
     * @author Dante Barbieri <pulchroxloom>
     */
    private void runBot() {
        if (!botRunning) {
            if (selectedChampion == null && selectedRole == null)
                statusLabel.setText(GUIProperties.STATUS_LABEL_NOT_READY);
            else if (selectedChampion == null) statusLabel.setText(GUIProperties.STATUS_LABEL_NOT_READY_CHAMPION);
            else if (selectedDimension == null) statusLabel.setText(GUIProperties.STATUS_LABEL_BAD_RESOLUTION);
            else {
                statusLabel.setText(String.format(GUIProperties.STATUS_LABEL_PLAYING, selectedChampion, selectedRole));
                threadButtonsPanel.setBackground(Color.GREEN);
                toggleSetupBotComponents();
                botRunning = true;
                try {
                    botThread = new Thread() {
                        private Bot bot = new Bot(selectedRole, selectedChampion, selectedDimension);

                        @Override
                        public synchronized void start() {
                            super.start();
                            bos = new ByteArrayOutputStream();
                            System.setOut(new PrintStream(bos));
                        }

                        @Override
                        public void run() {
                            super.run();
                            bot.run();
                        }

                        @Override
                        public void interrupt() {
                            super.interrupt();
                            bot.running.compareAndSet(true, false);
                        }
                    };
                } catch (Exception ignore) {
                }
                botThread.start();
                startTime = Instant.now();
                upTime = new Timer(1, e -> timerLabel.setText(String.format(GUIProperties.TIME_LABEL_FORMAT,
                        durationToString(Duration.between(startTime, Instant.now())))));
                upTime.start();
                botOutputTime = new Timer(100, e -> {
                    botOutputLabel.setText("<html>" +
                            bos.toString(StandardCharsets.UTF_8).replaceAll("&", "&amp").replaceAll("<", "&lt;")
                                    .replaceAll(">", "&gt;").replaceAll("\"", "&quot").replaceAll("\n", "<br>")
                                    .replaceAll("\t", "&#9") + "</html>");
                    var sb = botOutputScrollPanel.getVerticalScrollBar();
                    sb.setValue(sb.getMaximum());
                });
                botOutputTime.start();
                setState(JFrame.ICONIFIED);
            }
        }
    }

    private String durationToString(Duration between) {
        StringBuilder duration = new StringBuilder();
        if (between.toDays() != 0)
            duration.append(String.format("%d:", between.toDaysPart()));
        if (between.toHours() != 0)
            duration.append(String.format("%02d:", between.toHoursPart()));
        if (between.toMinutes() != 0)
            duration.append(String.format("%02d:", between.toMinutesPart()));
        if (between.toSeconds() != 0)
            duration.append(String.format("%02d.", between.toSecondsPart()));
        if (between.toMillis() != 0)
            duration.append(String.format("%03d", between.toMillisPart()));
        return duration.toString();
    }

    private void toggleSetupBotComponents() {
        stopBotButton.setEnabled(!stopBotButton.isEnabled());
        startBotButton.setEnabled(!startBotButton.isEnabled());
        selectChampionComboBox.setEnabled(!selectChampionComboBox.isEnabled());
        resolutionComboBox.setEnabled(!resolutionComboBox.isEnabled());
        adcButton.setEnabled(!adcButton.isEnabled());
        jungleButton.setEnabled(!jungleButton.isEnabled());
        midButton.setEnabled(!midButton.isEnabled());
        supportButton.setEnabled(!supportButton.isEnabled());
        topButton.setEnabled(!topButton.isEnabled());
    }

    /**
     * Kills bot thread
     *
     * @author Dante Barbieri <pulchroxloom>
     */
    private void killBot() {
        if (botRunning) {
            statusLabel.setText(String.format(GUIProperties.STATUS_LABEL_READY, selectedChampion, selectedRole));
            threadButtonsPanel.setBackground(Color.RED);
            toggleSetupBotComponents();
            botRunning = false;
            botThread.interrupt();
            upTime.stop();
            botOutputTime.stop();
            setState(JFrame.NORMAL);
        }
    }
}
