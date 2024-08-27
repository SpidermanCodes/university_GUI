package login;

import login.utils.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class fresh {
    private JFrame frame;
    private JLayeredPane layeredPane;

    fresh() {
        frame = new JFrame();
        frame.setTitle("Title");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setResizable(false);

        // Set custom icon
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("img/universityLOGO.png")); // replace with your icon path
        frame.setIconImage(icon.getImage());

        // Create a layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.setContentPane(layeredPane);

        // Add background image
        ImageIcon originalIcon = new ImageIcon(ClassLoader.getSystemResource("img/redColor.jpg"));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        decoration();
        login();

        frame.setVisible(true);
    }

    private void login() {
        // Username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 150, 200, 30);
        usernameField.setOpaque(true);
        usernameField.setBackground(new Color(242, 192, 192));
        usernameField.setForeground(new Color(128, 128, 128));
        usernameField.setBorder(BorderFactory.createEmptyBorder());
        usernameField.setText("Username");

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(new Color(128, 128, 128));
                    usernameField.setText("Username");
                }
            }
        });

        layeredPane.add(usernameField, JLayeredPane.PALETTE_LAYER);

        // Password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 200, 200, 30);
        passwordField.setOpaque(true);
        passwordField.setBackground(new Color(242, 192, 192));
        passwordField.setForeground(new Color(128, 128, 128));
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setEchoChar((char) 0);
        passwordField.setText("Password");

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(new Color(128, 128, 128));
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText("Password");
                }
            }
        });

        layeredPane.add(passwordField, JLayeredPane.PALETTE_LAYER);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(250, 250, 100, 30);
        loginButton.setFocusable(false);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.RED);
        loginButton.setBorderPainted(false);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticateUser(username, password)) {
                usernameField.setText("");
                passwordField.setText("");
                System.out.println("Logged in");
                SwingUtilities.invokeLater(Dashboard::new);
                frame.dispose();
            } else {
                usernameField.setText("");
                passwordField.setText("");
                System.out.println("Invalid username or password");
            }
        });

        layeredPane.add(loginButton, JLayeredPane.PALETTE_LAYER);
    }

    private void decoration() {
        JButton closeButton = new JButton("x");
        closeButton.setBounds(frame.getWidth() - 50, 0, 50, 20);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.pink);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(Color.pink);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        layeredPane.add(closeButton, JLayeredPane.PALETTE_LAYER);
    }

    private boolean authenticateUser(String username, String password) {
        boolean isAuthenticated = false;

        try (Connection connection = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        isAuthenticated = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public static void main(String[] args) {
        new fresh();
    }
}
