package login.features;

import login.utils.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateTeacher extends JPanel implements ActionListener {

    JTextField tfcourse, tfaddress, tfphone, tfemail, tfbranch;
    JLabel labelEmpId;
    JButton submit, cancel;
    Choice cEmpId;

    public UpdateTeacher() {
        setSize(900, 650);
        setLocation(350, 50);

        setLayout(null);

        // Set background color
        setBackground(new Color(255, 102, 51)); //  orange color

        JLabel heading = new JLabel("Update Teacher Details");
        heading.setBounds(50, 10, 500, 50);
        heading.setFont(new Font("Tahoma", Font.ITALIC, 35));
        add(heading);

        JLabel lblEmpId = new JLabel("Select Emp Id");
        lblEmpId.setBounds(50, 100, 200, 20);
        lblEmpId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblEmpId);

        cEmpId = new Choice();
        cEmpId.setBounds(250, 100, 200, 20);
        add(cEmpId);

        populateEmpIds();

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(50, 150, 100, 30);
        lblname.setFont(new Font("serif", Font.BOLD, 20));
        add(lblname);

        JLabel labelname = new JLabel();
        labelname.setBounds(200, 150, 150, 30);
        labelname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelname);

        JLabel lblfname = new JLabel("Father's Name");
        lblfname.setBounds(400, 150, 200, 30);
        lblfname.setFont(new Font("serif", Font.BOLD, 20));
        add(lblfname);

        JLabel labelfname = new JLabel();
        labelfname.setBounds(600, 150, 150, 30);
        labelfname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelfname);

        JLabel lblEmpIdLabel = new JLabel("Emp Id");
        lblEmpIdLabel.setBounds(50, 200, 200, 30);
        lblEmpIdLabel.setFont(new Font("serif", Font.BOLD, 20));
        add(lblEmpIdLabel);

        labelEmpId = new JLabel();
        labelEmpId.setBounds(200, 200, 200, 30);
        labelEmpId.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelEmpId);

        JLabel lbldob = new JLabel("Date of Birth");
        lbldob.setBounds(400, 200, 200, 30);
        lbldob.setFont(new Font("serif", Font.BOLD, 20));
        add(lbldob);

        JLabel labeldob = new JLabel();
        labeldob.setBounds(600, 200, 150, 30);
        labeldob.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labeldob);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(50, 250, 200, 30);
        lbladdress.setFont(new Font("serif", Font.BOLD, 20));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(400, 250, 200, 30);
        lblphone.setFont(new Font("serif", Font.BOLD, 20));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        JLabel lblemail = new JLabel("Email Id");
        lblemail.setBounds(50, 300, 200, 30);
        lblemail.setFont(new Font("serif", Font.BOLD, 20));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        JLabel lblqualification = new JLabel("Qualification");
        lblqualification.setBounds(400, 300, 200, 30);
        lblqualification.setFont(new Font("serif", Font.BOLD, 20));
        add(lblqualification);

        tfcourse = new JTextField();
        tfcourse.setBounds(600, 300, 150, 30);
        add(tfcourse);

        JLabel lblbranch = new JLabel("Department");
        lblbranch.setBounds(50, 350, 200, 30);
        lblbranch.setFont(new Font("serif", Font.BOLD, 20));
        add(lblbranch);

        tfbranch = new JTextField();
        tfbranch.setBounds(200, 350, 150, 30);
        add(tfbranch);

        populateTeacherDetails(labelname, labelfname, labeldob);

        cEmpId.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                populateTeacherDetails(labelname, labelfname, labeldob);
            }
        });

        submit = new JButton("Update");
        submit.setBounds(250, 500, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 500, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        setVisible(true);
    }

    private void populateEmpIds() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT empId FROM teacher")) {
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTeacherDetails(JLabel labelname, JLabel labelfname, JLabel labeldob) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM teacher WHERE empId = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, cEmpId.getSelectedItem());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        labelname.setText(rs.getString("name"));
                        labelfname.setText(rs.getString("fname"));
                        labeldob.setText(rs.getString("dob"));
                        tfaddress.setText(rs.getString("address"));
                        tfphone.setText(rs.getString("phone"));
                        tfemail.setText(rs.getString("email"));
                        tfcourse.setText(rs.getString("education"));
                        tfbranch.setText(rs.getString("department"));
                        labelEmpId.setText(rs.getString("empId"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String empId = labelEmpId.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String qualification = tfcourse.getText();
            String department = tfbranch.getText();

            try (Connection conn = DatabaseUtils.getConnection()) {
                String query = "UPDATE teacher SET address = ?, phone = ?, email = ?, education = ?, department = ? WHERE empId = ?";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, address);
                    ps.setString(2, phone);
                    ps.setString(3, email);
                    ps.setString(4, qualification);
                    ps.setString(5, department);
                    ps.setString(6, empId);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Teacher Details Updated Successfully");
                setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }
}
