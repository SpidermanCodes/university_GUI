package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpaceCatPanel extends JPanel {
    private boolean eyesOpen = true;
    private String loadingText = "Loading, please wait...";

    public SpaceCatPanel() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eyesOpen = !eyesOpen;
                repaint();
            }
        });
        timer.start();
    }

    public void setLoadingText(String text) {
        this.loadingText = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g); // Draw the cosmic background first
        drawCat(g);
        drawLoadingText(g);
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw a vibrant gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(179, 102, 255), getWidth(), getHeight(), new Color(0, 0, 50));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Add cosmic effect
        drawCosmicEffect(g2d);
    }

    private void drawCat(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw space cat's face
        g2d.setColor(new Color(30, 30, 30)); // Dark gray for the face
        g2d.fillOval(475, 200, 400, 400); // Wider face

        // Draw space cat's ears
        g2d.setColor(new Color(30, 30, 30)); // Dark gray for the ears
        g2d.fillPolygon(new int[]{475, 525, 575}, new int[]{300, 100, 300}, 3);
        g2d.fillPolygon(new int[]{875, 825, 775}, new int[]{300, 100, 300}, 3);

        // Draw space cat's inner ears
        g2d.setColor(new Color(50, 50, 50)); // Slightly lighter gray for the inner ears
        g2d.fillPolygon(new int[]{485, 525, 565}, new int[]{300, 140, 300}, 3);
        g2d.fillPolygon(new int[]{865, 825, 785}, new int[]{300, 140, 300}, 3);

        // Draw space cat's eyes
        g2d.setColor(Color.WHITE);
        g2d.fillOval(575, 325, 50, 50);
        g2d.fillOval(725, 325, 50, 50);

        // Draw space cat's pupils
        g2d.setColor(Color.CYAN);
        if (eyesOpen) {
            g2d.fillOval(595, 345, 10, 10);
            g2d.fillOval(745, 345, 10, 10);
        } else {
            g2d.fillOval(575, 350, 50, 10);
            g2d.fillOval(725, 350, 50, 10);
        }

        // Draw space cat's nose
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(665, 425, 20, 20);

        // Draw space cat's mouth
        g2d.setColor(Color.WHITE);
        g2d.drawArc(655, 440, 40, 30, 0, -180);
    }

    private void drawCosmicEffect(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255, 150)); // Semi-transparent white for stars
        for (int i = 0; i < 200; i++) { // Increase number of stars
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 3, 3); // Larger stars
        }

        // Add some larger glowing stars
        g2d.setColor(new Color(255, 255, 255, 255)); // Brighter white for larger stars
        for (int i = 0; i < 20; i++) { // Increase number of larger stars
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 15, 15); // Larger size for glowing stars
        }

        // Add nebula effect
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.setColor(new Color(255, 0, 255, 50)); // Semi-transparent magenta
            g2d.fillOval(x, y, 100, 100); // Nebula shape
        }

        // Add more cosmic colors
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.setColor(new Color(0, 255, 255, 50)); // Semi-transparent cyan
            g2d.fillOval(x, y, 150, 150); // Larger nebula shape
        }
    }

    private void drawLoadingText(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(loadingText);
        int textX = (getWidth() - textWidth) / 2;
        int textY = getHeight() / 2 + 250;
        g.drawString(loadingText, textX, textY);
    }
}
