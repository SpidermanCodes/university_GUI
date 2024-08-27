package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SlidingMenu extends JPanel {
    public static final int SLIDE_WIDTH = 300; // Width of the sliding menu
    public static final int TOP_MARGIN = 40; // Space at the top to avoid overlapping with the menu bar
    private static final int ANIMATION_SPEED = 10; // Speed of the animation
    private Timer timer;
    private boolean isMenuOpen;
    private Dashboard dashboard;

    public SlidingMenu(Dashboard dashboard) {
        this.dashboard = dashboard;
        setBounds(-SLIDE_WIDTH, TOP_MARGIN, SLIDE_WIDTH, 720 - TOP_MARGIN); // Initially positioned outside the frame with space at the top
        setBackground(new Color(45, 45, 48)); // Dark background color
        setLayout(null);

        // Example content with different options for each button
        addButtonsWithOptions();
    }

    private void addButtonsWithOptions() {
        String[] buttonNames = {"Add new", "View details", "Apply leave", "Leave details", "Update details", "Examinations", "Fee details", "Utility", "Physics Simulation", "About"};
        List<String[]> optionsList = List.of(
                new String[]{"New Student", "New Teacher"},
                new String[]{"Student Details", "Faculty Details"},
                new String[]{"Student Leave", "Teacher Leave"},
                new String[]{"StudentLeave Details", "TeacherLeave Details"},
                new String[]{"Update Student", "Update Teacher"},
                new String[]{"Enter Marks", "Exam Results"},
                new String[]{"Fee Structure", "Student Fee Form"},
                new String[]{"Calculator", "Notepad"},
                new String[]{"Pendulum", "Bouncing Ball"},
                new String[]{"About"}
        );

        int yPosition = 20;

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = createButton(buttonNames[i], optionsList.get(i));
            button.setBounds(20, yPosition, 260, 40); // Increased width and height for a better look
            add(button);
            yPosition += 50;
        }
    }

    private JButton createButton(String name, String[] options) {
        JButton button = new JButton(name) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(new Color(60, 63, 65)); // Slightly lighter dark color for buttons
        button.setForeground(Color.WHITE); // White text for contrast
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for readability
        button.setPreferredSize(new Dimension(260, 40)); // Adjusted size
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(85, 139, 247)); // Change to a blue color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 63, 65)); // Change back to original color
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPopupMenu(button, options, e.getX(), e.getY());
            }
        });
        return button;
    }

    private void showPopupMenu(JButton button, String[] options, int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(new Color(45, 45, 48)); // Match menu background with the panel
        popupMenu.setForeground(Color.WHITE); // Match menu text color
        for (String option : options) {
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.setBackground(new Color(60, 63, 65)); // Match item background
            menuItem.setForeground(Color.WHITE); // White text for contrast
            menuItem.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font for menu items
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleOptionSelection(option);
                }
            });
            popupMenu.add(menuItem);
        }
        popupMenu.show(button, x, y);
    }

    private void handleOptionSelection(String option) {
        switch (option) {
            case "Notepad":
                openNotepad();
                break;
            case "Calculator":
                openCalculator();
                break;
            default:
                dashboard.showRightPanel(option);
        }
    }

    private void openNotepad() {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCalculator() {
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    public void toggleMenu() {
        if (isMenuOpen) {
            slideOut();
        } else {
            slideIn();
        }
    }

    private void slideIn() {
        timer = new Timer(ANIMATION_SPEED, new ActionListener() {
            int x = getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                x += 10;
                if (x >= 0) {
                    x = 0;
                    timer.stop();
                    dashboard.adjustRightPanel(); // Adjust the right panel
                }
                setLocation(x, TOP_MARGIN);
                revalidate();
                repaint();
            }
        });
        timer.start();
        isMenuOpen = true;
    }

    private void slideOut() {
        timer = new Timer(ANIMATION_SPEED, new ActionListener() {
            int x = getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 10;
                if (x <= -SLIDE_WIDTH) {
                    x = -SLIDE_WIDTH;
                    timer.stop();
                    dashboard.adjustRightPanel(); // Adjust the right panel
                }
                setLocation(x, TOP_MARGIN);
                revalidate();
                repaint();
            }
        });
        timer.start();
        isMenuOpen = false;
    }
}
