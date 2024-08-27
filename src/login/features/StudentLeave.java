package login.features;

import login.utils.DatabaseUtils;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentLeave extends JPanel implements ActionListener {

    Choice crollno, ctime;
    JDateChooser dcdate;
    JButton submit, cancel;

    public StudentLeave() {

        setSize(500, 550);
        setLocation(550, 100);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel heading = new JLabel("Apply Leave (Student)");
        heading.setBounds(40, 50, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblrollno = new JLabel("Search by Roll Number");
        lblrollno.setBounds(60, 100, 200, 20);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblrollno);

        crollno = new Choice();
        crollno.setBounds(60, 130, 200, 20);
        add(crollno);

        try (Connection con = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM student";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    crollno.add(rs.getString("rollno"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60, 180, 200, 20);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(60, 210, 200, 25);
        add(dcdate);

        JLabel lbltime = new JLabel("Time Duration");
        lbltime.setBounds(60, 260, 200, 20);
        lbltime.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbltime);

        ctime = new Choice();
        ctime.setBounds(60, 290, 200, 20);
        ctime.add("Full Day");
        ctime.add("Half Day");
        add(ctime);

        submit = new JButton("Submit");
        submit.setBounds(60, 350, 100, 25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(200, 350, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = crollno.getSelectedItem();
            String date = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            String duration = ctime.getSelectedItem();

            String query = "INSERT INTO studentleave (rollno, date, duration) VALUES (?, ?, ?)";

            try (Connection con = DatabaseUtils.getConnection();
                 PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, rollno);
                ps.setString(2, date);
                ps.setString(3, duration);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Leave Confirmed");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to confirm leave.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }
}
