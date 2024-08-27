package login.features;

import login.utils.DatabaseUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class StudentFeeForm extends JPanel implements ActionListener {

    private Choice crollno;
    private JComboBox<String> cbcourse, cbbranch, cbsemester;
    private JLabel labeltotal;
    private JButton update, pay, back;

    public StudentFeeForm() {
        setSize(900, 500);
        setLocation(300, 100);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel lblrollnumber = new JLabel("Select Roll No");
        lblrollnumber.setBounds(40, 60, 150, 20);
        lblrollnumber.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 60, 150, 20);
        add(crollno);

        try (Connection conn = DatabaseUtils.getConnection()) {
            String query = "SELECT rollno FROM student";
            try (PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    crollno.add(rs.getString("rollno"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(40, 100, 150, 20);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblname);

        JLabel labelname = new JLabel();
        labelname.setBounds(200, 100, 150, 20);
        labelname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelname);

        JLabel lblfname = new JLabel("Father's Name");
        lblfname.setBounds(40, 140, 150, 20);
        lblfname.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblfname);

        JLabel labelfname = new JLabel();
        labelfname.setBounds(200, 140, 150, 20);
        labelfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelfname);

        crollno.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try (Connection conn = DatabaseUtils.getConnection()) {
                    String query = "SELECT * FROM student WHERE rollno = ?";
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setString(1, crollno.getSelectedItem());
                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next()) {
                                labelname.setText(rs.getString("name"));
                                labelfname.setText(rs.getString("fname"));
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel lblcourse = new JLabel("Course");
        lblcourse.setBounds(40, 180, 150, 20);
        lblcourse.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblcourse);

        String[] course = {"BTech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = new JComboBox<>(course);
        cbcourse.setBounds(200, 180, 150, 20);
        cbcourse.setBackground(Color.WHITE);
        add(cbcourse);

        JLabel lblbranch = new JLabel("Branch");
        lblbranch.setBounds(40, 220, 150, 20);
        lblbranch.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblbranch);

        String[] branch = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbbranch = new JComboBox<>(branch);
        cbbranch.setBounds(200, 220, 150, 20);
        cbbranch.setBackground(Color.WHITE);
        add(cbbranch);

        JLabel lblsemester = new JLabel("Semester");
        lblsemester.setBounds(40, 260, 150, 20);
        lblsemester.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblsemester);

        String[] semester = {"Semester1", "Semester2", "Semester3", "Semester4", "Semester5", "Semester6", "Semester7", "Semester8"};
        cbsemester = new JComboBox<>(semester);
        cbsemester.setBounds(200, 260, 150, 20);
        cbsemester.setBackground(Color.WHITE);
        add(cbsemester);

        JLabel lbltotal = new JLabel("Total Payable");
        lbltotal.setBounds(40, 300, 150, 20);
        lbltotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lbltotal);

        labeltotal = new JLabel();
        labeltotal.setBounds(200, 300, 150, 20);
        labeltotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labeltotal);

        update = new JButton("Update");
        update.setBounds(30, 380, 100, 25);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);

        pay = new JButton("Pay Fee");
        pay.setBounds(150, 380, 100, 25);
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.addActionListener(this);
        pay.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(pay);

        back = new JButton("Back");
        back.setBounds(270, 380, 100, 25);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        back.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            try (Connection conn = DatabaseUtils.getConnection()) {
                String query = "SELECT * FROM fee WHERE course = ?";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, course);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            labeltotal.setText(rs.getString(semester));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == pay) {
            String rollno = crollno.getSelectedItem();
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            String branch = (String) cbbranch.getSelectedItem();
            String total = labeltotal.getText();

            try (Connection conn = DatabaseUtils.getConnection()) {
                String query = "INSERT INTO collegefee (rollno, course, branch, semester, total) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, rollno);
                    pst.setString(2, course);
                    pst.setString(3, branch);
                    pst.setString(4, semester);
                    pst.setString(5, total);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "College fee submitted successfully");
                    setVisible(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }
}
