package login.features;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import login.utils.DatabaseUtils;
import java.awt.event.*;

public class TeacherDetails extends JPanel implements ActionListener {

    Choice cEmpId;
    JTable table;
    JButton search, print, update, add, cancel;

    public TeacherDetails() {


        setLayout(null);

        // Set background color
        setBackground(new Color(255, 102, 51)); //  orange color

        JLabel heading = new JLabel("Search by Emp ID");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        cEmpId = new Choice();
        cEmpId.setBounds(180, 20, 150, 20);
        add(cEmpId);

        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM teacher    ")) {

            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table = new JTable();

        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM teacher")) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

        add = new JButton("Add");
        add.setBounds(220, 70, 80, 20);
        add.addActionListener(this);
        add(add);

        update = new JButton("Update");
        update.setBounds(320, 70, 80, 20);
        update.addActionListener(this);
        add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(420, 70, 80, 20);
        cancel.addActionListener(this);
        add(cancel);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM teacher WHERE empId = '" + cEmpId.getSelectedItem() + "'";
            try (Connection conn = DatabaseUtils.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == add) {
            // Clear and add NewTeacher panel to the right panel
            JPanel parentPanel = (JPanel) this.getParent();
            parentPanel.removeAll();
            parentPanel.add(new NewTeacher());
            parentPanel.revalidate();
            parentPanel.repaint();
        } else if (ae.getSource() == update) {
            JPanel parentPanel = (JPanel) this.getParent();
            parentPanel.removeAll();
            parentPanel.add(new UpdateTeacher());
            parentPanel.revalidate();
            parentPanel.repaint();
        } else if (ae.getSource() == cancel) {
            // Handle cancel button action
        }
    }
}
