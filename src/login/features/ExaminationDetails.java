package login.features;

import login.utils.DatabaseUtils;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ExaminationDetails extends JPanel implements ActionListener {

    JTextField search;
    JButton submit, cancel;
    JTable table;

    public ExaminationDetails() {
        setSize(1000, 475);
        setLocation(300, 100);
        setLayout(null);

        // Set background color
        setBackground(new Color(128, 159, 255)); // light blue color

        JLabel heading = new JLabel("Check Result");
        heading.setBounds(80, 15, 400, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading);

        search = new JTextField();
        search.setBounds(80, 90, 200, 30);
        search.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(search);

        submit = new JButton("Result");
        submit.setBounds(300, 90, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(440, 90, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 130, 1000, 310);
        add(jsp);

        loadStudentData();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int row = table.getSelectedRow();
                search.setText(table.getModel().getValueAt(row, 2).toString());
            }
        });

        setVisible(true);
    }

    private void loadStudentData() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            displayMarks(search.getText());
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    private void displayMarks(String rollno) {
        JFrame marksFrame = new JFrame("Student Marks");
        marksFrame.setSize(500, 600);
        marksFrame.setLocation(500, 100);
        marksFrame.setLayout(null);
        marksFrame.getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Delhi Technical University");
        heading.setBounds(100, 10, 500, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        marksFrame.add(heading);

        JLabel subheading = new JLabel("Result of Examination 2022");
        subheading.setBounds(100, 50, 500, 20);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 18));
        marksFrame.add(subheading);

        JLabel lblrollno = new JLabel("Roll Number: " + rollno);
        lblrollno.setBounds(60, 100, 500, 20);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(lblrollno);

        JLabel lblsemester = new JLabel();
        lblsemester.setBounds(60, 130, 500, 20);
        lblsemester.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(lblsemester);

        JLabel sub1 = new JLabel();
        sub1.setBounds(100, 200, 500, 20);
        sub1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(sub1);

        JLabel sub2 = new JLabel();
        sub2.setBounds(100, 230, 500, 20);
        sub2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(sub2);

        JLabel sub3 = new JLabel();
        sub3.setBounds(100, 260, 500, 20);
        sub3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(sub3);

        JLabel sub4 = new JLabel();
        sub4.setBounds(100, 290, 500, 20);
        sub4.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(sub4);

        JLabel sub5 = new JLabel();
        sub5.setBounds(100, 320, 500, 20);
        sub5.setFont(new Font("Tahoma", Font.PLAIN, 18));
        marksFrame.add(sub5);

        loadMarksData(rollno, lblsemester, sub1, sub2, sub3, sub4, sub5);

        JButton cancelMarks = new JButton("Back");
        cancelMarks.setBounds(250, 500, 120, 25);
        cancelMarks.setBackground(Color.BLACK);
        cancelMarks.setForeground(Color.WHITE);
        cancelMarks.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancelMarks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                marksFrame.dispose();
            }
        });
        marksFrame.add(cancelMarks);

        marksFrame.setVisible(true);
    }

    private void loadMarksData(String rollno, JLabel lblsemester, JLabel sub1, JLabel sub2, JLabel sub3, JLabel sub4, JLabel sub5) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String subjectQuery = "SELECT * FROM subject WHERE rollno = ?";
            try (PreparedStatement pst1 = conn.prepareStatement(subjectQuery)) {
                pst1.setString(1, rollno);
                try (ResultSet rs1 = pst1.executeQuery()) {
                    if (rs1.next()) {
                        sub1.setText(rs1.getString("subject1"));
                        sub2.setText(rs1.getString("subject2"));
                        sub3.setText(rs1.getString("subject3"));
                        sub4.setText(rs1.getString("subject4"));
                        sub5.setText(rs1.getString("subject5"));
                    }
                }
            }

            String marksQuery = "SELECT * FROM marks WHERE rollno = ?";
            try (PreparedStatement pst2 = conn.prepareStatement(marksQuery)) {
                pst2.setString(1, rollno);
                try (ResultSet rs2 = pst2.executeQuery()) {
                    if (rs2.next()) {
                        sub1.setText(sub1.getText() + " ------------ " + rs2.getString("marks1"));
                        sub2.setText(sub2.getText() + " ------------ " + rs2.getString("marks2"));
                        sub3.setText(sub3.getText() + " ------------ " + rs2.getString("marks3"));
                        sub4.setText(sub4.getText() + " ------------ " + rs2.getString("marks4"));
                        sub5.setText(sub5.getText() + " ------------ " + rs2.getString("marks5"));
                        lblsemester.setText("Semester " + rs2.getString("semester"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
