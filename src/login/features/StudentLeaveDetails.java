package login.features;

import login.utils.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class StudentLeaveDetails extends JPanel implements ActionListener {

    Choice crollno;
    JTable table;
    JButton search, print, cancel;

    public StudentLeaveDetails() {

        setBackground(Color.WHITE);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel heading = new JLabel("Search by Roll Number");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        crollno = new Choice();
        crollno.setBounds(180, 20, 150, 20);
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

        table = new JTable();

        try (Connection con = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM studentleave";
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
            String query = "SELECT * FROM studentleave WHERE rollno = ?";
            try (Connection con = DatabaseUtils.getConnection();
                 PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, crollno.getSelectedItem());
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
