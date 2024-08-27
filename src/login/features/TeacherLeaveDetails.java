package login.features;

import login.utils.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class TeacherLeaveDetails extends JPanel implements ActionListener {

    Choice cEmpId;
    JTable table;
    JButton search, print, cancel;

    public TeacherLeaveDetails() {


        setLayout(null);

        // Set background color
        setBackground(new Color(255, 102, 51)); //  orange color

        JLabel heading = new JLabel("Search by Emp id");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        cEmpId = new Choice();
        cEmpId.setBounds(180, 20, 150, 20);
        add(cEmpId);

        try (Connection con = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM teacher";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cEmpId.add(rs.getString("empId"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable();

        try (Connection con = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM teacherleave";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        cancel = new JButton("Cancel");
        cancel.setBounds(220, 70, 80, 20);
        cancel.addActionListener(this);
        add(cancel);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM teacherleave WHERE empId = ?";
            try (Connection con = DatabaseUtils.getConnection();
                 PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, cEmpId.getSelectedItem());
                try (ResultSet rs = ps.executeQuery()) {
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }
}
