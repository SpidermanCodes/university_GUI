package login.features;

import login.utils.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class EnterMarks extends JPanel implements ActionListener {

    Choice crollno;
    JComboBox<String> cbsemester;
    JTextField tfsub1, tfsub2, tfsub3, tfsub4, tfsub5, tfmarks1, tfmarks2, tfmarks3, tfmarks4, tfmarks5;
    JButton cancel, submit;

    public EnterMarks() {
        setSize(1000, 500);
        setLocation(300, 150);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/exam.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500, 40, 400, 300);
        add(image);

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setBounds(50, 0, 500, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(50, 70, 150, 20);
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 70, 150, 20);
        add(crollno);

        populateRollNumbers();

        JLabel lblsemester = new JLabel("Select Semester");
        lblsemester.setBounds(50, 110, 150, 20);
        add(lblsemester);

        String[] semester = {"1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester"};
        cbsemester = new JComboBox<>(semester);
        cbsemester.setBounds(200, 110, 150, 20);
        cbsemester.setBackground(Color.WHITE);
        add(cbsemester);

        JLabel lblentersubject = new JLabel("Enter Subject");
        lblentersubject.setBounds(100, 150, 200, 40);
        add(lblentersubject);

        JLabel lblentermarks = new JLabel("Enter Marks");
        lblentermarks.setBounds(320, 150, 200, 40);
        add(lblentermarks);

        tfsub1 = new JTextField();
        tfsub1.setBounds(50, 200, 200, 20);
        add(tfsub1);

        tfsub2 = new JTextField();
        tfsub2.setBounds(50, 230, 200, 20);
        add(tfsub2);

        tfsub3 = new JTextField();
        tfsub3.setBounds(50, 260, 200, 20);
        add(tfsub3);

        tfsub4 = new JTextField();
        tfsub4.setBounds(50, 290, 200, 20);
        add(tfsub4);

        tfsub5 = new JTextField();
        tfsub5.setBounds(50, 320, 200, 20);
        add(tfsub5);

        tfmarks1 = new JTextField();
        tfmarks1.setBounds(250, 200, 200, 20);
        add(tfmarks1);

        tfmarks2 = new JTextField();
        tfmarks2.setBounds(250, 230, 200, 20);
        add(tfmarks2);

        tfmarks3 = new JTextField();
        tfmarks3.setBounds(250, 260, 200, 20);
        add(tfmarks3);

        tfmarks4 = new JTextField();
        tfmarks4.setBounds(250, 290, 200, 20);
        add(tfmarks4);

        tfmarks5 = new JTextField();
        tfmarks5.setBounds(250, 320, 200, 20);
        add(tfmarks5);

        submit = new JButton("Submit");
        submit.setBounds(70, 360, 150, 25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(280, 360, 150, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        setVisible(true);
    }

    private void populateRollNumbers() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT rollno FROM student")) {
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = crollno.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            String sub1 = tfsub1.getText();
            String sub2 = tfsub2.getText();
            String sub3 = tfsub3.getText();
            String sub4 = tfsub4.getText();
            String sub5 = tfsub5.getText();
            String marks1 = tfmarks1.getText();
            String marks2 = tfmarks2.getText();
            String marks3 = tfmarks3.getText();
            String marks4 = tfmarks4.getText();
            String marks5 = tfmarks5.getText();

            try (Connection conn = DatabaseUtils.getConnection()) {
                String query1 = "INSERT INTO subject (rollno, semester, subject1, subject2, subject3, subject4, subject5) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String query2 = "INSERT INTO marks (rollno, semester, marks1, marks2, marks3, marks4, marks5) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps1 = conn.prepareStatement(query1);
                     PreparedStatement ps2 = conn.prepareStatement(query2)) {

                    ps1.setString(1, rollno);
                    ps1.setString(2, semester);
                    ps1.setString(3, sub1);
                    ps1.setString(4, sub2);
                    ps1.setString(5, sub3);
                    ps1.setString(6, sub4);
                    ps1.setString(7, sub5);
                    ps1.executeUpdate();

                    ps2.setString(1, rollno);
                    ps2.setString(2, semester);
                    ps2.setString(3, marks1);
                    ps2.setString(4, marks2);
                    ps2.setString(5, marks3);
                    ps2.setString(6, marks4);
                    ps2.setString(7, marks5);
                    ps2.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Marks Inserted Successfully");
                setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }
}
