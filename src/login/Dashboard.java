package login;

import javax.swing.*;
import java.awt.*;
import login.SpaceCatPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static login.SlidingMenu.SLIDE_WIDTH;
import static login.SlidingMenu.TOP_MARGIN;

public class Dashboard {
    private JFrame frame;
    private JLabel backgroundLabel;
    private MenuBar menuBar;
    private SlidingMenu slidingMenu;
    private RightPanel rightPanel;
    private Image originalImage;
    private SpaceCatPanel spaceCatPanel;

    Dashboard() {
        initializeFrame();
        setBackImage();
        createMenuBar();
        createSlidingMenu();
    }

    private void initializeFrame() {
        frame = new JFrame("University Management System");
        frame.setSize(1350, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set custom icon
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("img/universityLOGO.png")); // replace with your icon path
        frame.setIconImage(icon.getImage());

        // Create a layered pane to layer components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1350, 720));
        frame.setContentPane(layeredPane);

        // Set layout to null for absolute positioning
        layeredPane.setLayout(null);

        frame.setVisible(true);
    }

    private void setBackImage() {
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 1350, 720); // Set initial bounds for the background image
        backgroundLabel.setOpaque(true); // Make the label opaque to show background color
        backgroundLabel.setBackground(new Color(0, 0, 0)); // Set the background to black for a space theme
        backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundLabel.setVerticalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // Add to default layer

        // Add SpaceCat panel
        spaceCatPanel = new SpaceCatPanel();
        spaceCatPanel.setBounds(0, 0, 1350, 720);
        frame.getContentPane().add(spaceCatPanel, JLayeredPane.PALETTE_LAYER); // Add to palette layer

        // Load the image in a separate thread
        new Thread(() -> {
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/palace.jpg"));
                originalImage = originalIcon.getImage();
                SwingUtilities.invokeLater(() -> {
                    resizeBackgroundImage();
                    frame.getContentPane().remove(spaceCatPanel);
                    frame.repaint();
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    spaceCatPanel.setLoadingText("Image not found");
                    frame.repaint();
                });
            }
        }).start();

        // Resize background image when frame is resized
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeBackgroundImage();
            }
        });
    }

    private void resizeBackgroundImage() {
        if (originalImage == null) return; // Do nothing if image is not loaded yet
        int width = frame.getWidth();
        int height = frame.getHeight();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_FAST); // Use SCALE_FAST for faster scaling
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, width, height);
    }

    private void createMenuBar() {
        menuBar = new MenuBar(this); // Pass Dashboard instance to MenuBar
        frame.getContentPane().add(menuBar, JLayeredPane.PALETTE_LAYER); // Add to palette layer
    }

    private void createSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        frame.getContentPane().add(slidingMenu, JLayeredPane.MODAL_LAYER); // Add to modal layer
    }

    public void showRightPanel(String text) {
        if (rightPanel == null) {
            rightPanel = new RightPanel();
            frame.getContentPane().add(rightPanel, JLayeredPane.MODAL_LAYER); // Add to modal layer
        }
        rightPanel.showFeature(text);
        adjustRightPanel();
        rightPanel.setVisible(true);
    }

    public void adjustRightPanel() {
        if (rightPanel != null) {
            if (slidingMenu.isMenuOpen()) {
                rightPanel.setBounds(SLIDE_WIDTH, TOP_MARGIN, 1350 - SLIDE_WIDTH, 720 - TOP_MARGIN);
            } else {
                rightPanel.setBounds(0, TOP_MARGIN, 1350, 720 - TOP_MARGIN);
            }
        }
    }

    public void toggleSlidingMenu() {
        slidingMenu.toggleMenu();
        adjustRightPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}

