package login.features;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class NonLinearPendulam extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final int PANEL_WIDTH = 700;
    private final int PANEL_HEIGHT = 500;
    private final int TIMER_DELAY = 16; // Approx 60 FPS
    private final double GRAVITY = 9.81;
    private final double BOB_MASS = 1.0; // Mass of the bob in kg
    private final double DAMPING_FACTOR = 0.99; // Damping factor for energy loss per cycle

    private double originX;
    private double originY;
    private double bobX;
    private double bobY;
    private double length;
    private double angle;
    private double angularVelocity;
    private double angularAcceleration;

    private boolean dragging;
    private double stoppingTime;

    private Timer timer;

    public NonLinearPendulam() {
        this.length = 300;
        this.angle = Math.PI / 4; // 45 degrees
        this.angularVelocity = 0;
        this.angularAcceleration = 0;
        this.dragging = false;

        this.originX = PANEL_WIDTH / 2 + 250;
        this.originY = 50; // Start 50px below the top

        updateBobPosition();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.timer = new Timer(TIMER_DELAY, this);
        this.timer.start();

        this.stoppingTime = estimateStoppingTime();

        // Set panel properties to fit into the right panel
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(34, 45, 50));
        setLayout(null);
    }

    private void updateBobPosition() {
        bobX = originX + length * Math.sin(angle);
        bobY = originY + length * Math.cos(angle);
    }

    private double calculatePotentialEnergy() {
        double height = length * (1 - Math.cos(angle));
        return BOB_MASS * GRAVITY * height;
    }

    private double calculateKineticEnergy() {
        double velocity = length * angularVelocity;
        return 0.5 * BOB_MASS * velocity * velocity;
    }

    private double estimateStoppingTime() {
        double dampingRate = 1 - DAMPING_FACTOR;
        if (dampingRate == 0) return Double.POSITIVE_INFINITY;

        double initialEnergy = calculatePotentialEnergy() + calculateKineticEnergy();
        double currentEnergy = initialEnergy;
        double time = 0;
        double timeStep = TIMER_DELAY / 1000.0; // in seconds

        while (currentEnergy > 0.001 * initialEnergy) {
            currentEnergy *= DAMPING_FACTOR;
            time += timeStep;
        }
        return time;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the pendulum
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.drawLine((int) originX, (int) originY, (int) bobX, (int) bobY);
        g2d.setColor(Color.RED);
        g2d.fill(new Ellipse2D.Double(bobX - 10, bobY - 10, 20, 20));

        // Display energy values
        double potentialEnergy = calculatePotentialEnergy();
        double kineticEnergy = calculateKineticEnergy();
        double totalEnergy = potentialEnergy + kineticEnergy;

        g2d.setColor(Color.WHITE);
        g2d.drawString(String.format("Potential Energy: %.2f J", potentialEnergy), 10, 20);
        g2d.drawString(String.format("Kinetic Energy: %.2f J", kineticEnergy), 10, 40);
        g2d.drawString(String.format("Total Energy: %.2f J", totalEnergy), 10, 60);

        // Display estimated stopping time countdown
        g2d.drawString(String.format("Stopping Time Countdown: %.2f s", stoppingTime), 10, 80);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!dragging) {
            // Update the physics using the full non-linear pendulum equation
            angularAcceleration = (-GRAVITY / length) * Math.sin(angle);
            angularVelocity += angularAcceleration;
            angle += angularVelocity;

            // Damping
            angularVelocity *= DAMPING_FACTOR;

            updateBobPosition();

            // Update stopping time countdown
            stoppingTime -= TIMER_DELAY / 1000.0;
            if (stoppingTime < 0) {
                stoppingTime = 0;
            }
        }
        repaint();//
    }//


    @Override
    public void mousePressed(MouseEvent e) {
        double dx = e.getX() - bobX;
        double dy = e.getY() - bobY;
        if (Math.sqrt(dx * dx + dy * dy) < 10) {
            dragging = true;
            timer.stop();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            dragging = false;
            double dx = e.getX() - originX;
            double dy = e.getY() - originY;
            length = Math.sqrt(dx * dx + dy * dy);
            angle = Math.atan2(dx, dy);
            stoppingTime = estimateStoppingTime();
            timer.start();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            bobX = e.getX();
            bobY = e.getY();
            double dx = bobX - originX;
            double dy = bobY - originY;
            length = Math.sqrt(dx * dx + dy * dy);
            angle = Math.atan2(dx, dy);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
