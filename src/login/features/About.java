package login.features;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class About extends JPanel {

    public About() {
        setPreferredSize(new Dimension(700, 500));
        setLocation(400, 150);
        setBackground(new Color(34, 45, 50));

        // Set layout before adding components
        setLayout(null);

        // Load and scale the image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/about.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(200, 20, 300, 200);
        add(image);

        // Headings and labels with new fonts and colors
        JLabel heading = new JLabel("<html>University Management System</html>");
      //  JLabel heading = new JLabel("<html>University<br/>Management System</html>");
        heading.setBounds(50, 230, 600, 80);
        heading.setFont(new Font("Verdana", Font.BOLD, 28));
        heading.setForeground(new Color(210, 180, 140));
        add(heading);

        JLabel name = new JLabel("Developed By: Prashant");
        name.setBounds(50, 320, 550, 40);
        name.setFont(new Font("Verdana", Font.PLAIN, 24));
        name.setForeground(Color.WHITE);
        add(name);

        JLabel rollno = new JLabel("YouTube: @SpidermanCodes");
        rollno.setBounds(50, 370, 550, 40);
        rollno.setFont(new Font("Verdana", Font.PLAIN, 24));
        rollno.setForeground(Color.WHITE);
        add(rollno);

        JLabel contact = new JLabel("Email: huffingtoniscool@gmail.com");
        contact.setBounds(50, 420, 550, 40);
        contact.setFont(new Font("Verdana", Font.PLAIN, 24));
        contact.setForeground(Color.WHITE);
        add(contact);

        // Rounded borders and background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 45, 50), 0, getHeight(), new Color(50, 70, 80));
                g2d.setPaint(gp);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
                super.paintComponent(g);
            }
        };
        backgroundPanel.setBounds(0, 0, 700, 500);
        backgroundPanel.setOpaque(false);
        add(backgroundPanel);

        setVisible(true);
    }
}
