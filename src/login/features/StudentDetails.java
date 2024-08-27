package login.features;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import login.utils.DatabaseUtils;

public class StudentDetails extends JPanel implements ActionListener {

    Choice crollno;
    JTable table;
    JButton search, print, update, add, cancel;

    public StudentDetails() {
        setLayout(null);
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel heading = new JLabel("Search by Roll Number");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        crollno = new Choice();
        crollno.setBounds(180, 20, 150, 20);
        add(crollno);

        populateRollNumbers();

        table = new JTable();
        populateTable();
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

    private void populateRollNumbers() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTable() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM student WHERE rollno = '" + crollno.getSelectedItem() + "'";
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
            switchToPanel(new NewStudent());
        } else if (ae.getSource() == update) {
            switchToPanel(new UpdateStudent());
        } else if (ae.getSource() == cancel) {
            // Implement the cancel functionality
            //switchToPanel(new MainPanel()); // Switch to the main panel or previous panel
        }
    }

    private void switchToPanel(JPanel panel) {
        JPanel parentPanel = (JPanel) this.getParent();
        parentPanel.removeAll();
        parentPanel.add(panel);
        parentPanel.revalidate();
        parentPanel.repaint();
    }
}
