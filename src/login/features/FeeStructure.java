package login.features;

import login.utils.DatabaseUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FeeStructure extends JPanel {

    public FeeStructure() {
        setSize(1000, 700);
        setLocation(250, 50);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel heading = new JLabel("Fee Structure");
        heading.setBounds(50, 10, 400, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(heading);

        JTable table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 60, 1000, 700);
        add(jsp);

        loadFeeStructure(table);
    }

    private void loadFeeStructure(JTable table) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM fee";
            try (PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
