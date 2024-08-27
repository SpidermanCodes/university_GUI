package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JPanel {
    private static final Color MENU_BAR_COLOR = new Color(179, 179, 255); //187, 194, 187
    private JButton menuButton;
    private Dashboard dashboard;

    public MenuBar(Dashboard dashboard) {
        this.dashboard = dashboard;
        initializeMenuBar();
    }

    private void initializeMenuBar() {
        setBounds(0, 0, 1350, 40); // Adjust width to match the frame's width

        // Load the icon from the resources
        ImageIcon menuIcon = new ImageIcon(getClass().getResource("/img/smallSB.png"));

        // Create and configure the menu button with the icon
        menuButton = new JButton(menuIcon);
        menuButton.setBounds(10, 5, menuIcon.getIconWidth(), menuIcon.getIconHeight()); // Position and size of the button
        menuButton.setFocusPainted(false); // Remove the focus border
        menuButton.setContentAreaFilled(false); // Remove button background
        menuButton.setBorderPainted(false); // Remove button border

        // Add mouse listeners for hover and press effects
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuButton.setOpaque(true);
                menuButton.setBackground(new Color(85, 139, 247, 100)); // 153, 179, 255 Semi-transparent black background on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuButton.setOpaque(false);
                menuButton.setBackground(new Color(0, 0, 0, 0)); // Transparent background
            }

            @Override
            public void mousePressed(MouseEvent e) {
                menuButton.setOpaque(true);
                menuButton.setBackground(new Color(0, 45, 179, 90)); // More opaque background on press
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                menuButton.setOpaque(false);
                menuButton.setBackground(new Color(0, 0, 0, 0)); // Transparent background
                dashboard.toggleSlidingMenu(); // Toggle the sliding menu on button click
            }
        });

        // Set layout to null for absolute positioning
        setLayout(null);

        // Add the button to the panel
        add(menuButton);

        // Make menu bar visible immediately
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Fill the panel with the solid color
        g2.setColor(MENU_BAR_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}
