package login.features;

import javax.swing.*;
import java.awt.*;

public class UniformlyBounce extends JPanel {
    private double y; // Vertical position of the ball
    private double vy; // Vertical velocity of the ball
    private final double gravity = 9.8; // Acceleration due to gravity
    private final double bounciness = 0.7; // Bounciness factor (energy loss on bounce)
    private long lastUpdateTime;
    private final int floorLevel = 450; // Custom floor level
    private final int ballRadius = 15; // Radius of the ball

    private JLabel heightLabel;
    private JLabel speedLabel;
    private JButton restartButton;

    public UniformlyBounce() {
        y = 50; // Initial position of the ball
        vy = 0; // Initial velocity of the ball

        // Set panel properties for fitting into the right panel
        setPreferredSize(new Dimension(700, 500));
        setLocation(400, 150);
        setBackground(new Color(102, 140, 255));
        setLayout(null);

        // Initialize last update time
        lastUpdateTime = System.currentTimeMillis();

        // Initialize the labels and button
        heightLabel = new JLabel("Height: " + y);
        speedLabel = new JLabel("Speed: " + vy);
        restartButton = new JButton("Restart");

        heightLabel.setBounds(10, 10, 200, 20);
        speedLabel.setBounds(10, 40, 200, 20);
        restartButton.setBounds(10, 70, 100, 30);

        add(heightLabel);
        add(speedLabel);
        add(restartButton);

        // Add action listener to the restart button
        restartButton.addActionListener(e -> resetBall());

        // Start the animation timer
        Timer timer = new Timer(50, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    // Method to update the ball's position and velocity
    private void update() {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastUpdateTime) / 1000.0; // Time elapsed since last update in seconds

        vy += gravity * deltaTime; // Increase velocity by gravity * time
        y += vy * deltaTime; // Update position by velocity * time

        lastUpdateTime = currentTime;

        // Bounce the ball if it hits the custom floor level
        if (y > floorLevel - ballRadius) { // Adjust for ball's radius
            y = floorLevel - ballRadius; // Set the position to the custom floor level
            vy = -vy * bounciness; // Reverse and reduce velocity by bounciness factor

            // Small adjustment to prevent the ball from sticking to the floor due to minimal velocity
            if (Math.abs(vy) < 0.1) {
                vy = 0;
            }
        }

        // Update the labels with the current height and speed
        heightLabel.setText(String.format("Height: %.2f", floorLevel - (y + ballRadius)));
        speedLabel.setText(String.format("Speed: %.2f", vy));
    }

    // Method to reset the ball's position and velocity
    private void resetBall() {
        y = 50; // Reset to initial height
        vy = 0; // Reset velocity
        lastUpdateTime = System.currentTimeMillis(); // Reset the time
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(350, (int) y - ballRadius, ballRadius * 2, ballRadius * 2); // Draw the ball at the current position, adjusting for radius

        // Draw the custom floor
        g.setColor(new Color(102, 255, 102));
        g.fillRect(0, floorLevel, getWidth(), getHeight() - floorLevel);
    }
}
