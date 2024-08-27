package login;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RightPanel extends JPanel {
    private CardLayout cardLayout;
    private Map<String, JPanel> featurePanels;

    public RightPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        featurePanels = new HashMap<>();
        loadFeaturePanels();
    }

    private void loadFeaturePanels() {
        // Add feature panels to the card layout
        addFeaturePanel("Pendulum", new login.features.NonLinearPendulam());
        addFeaturePanel("Pendulum", new login.features.NonLinearPendulam());
        addFeaturePanel("New Student", new login.features.NewStudent());
        addFeaturePanel("New Teacher", new login.features.NewTeacher());
        addFeaturePanel("Student Details", new login.features.StudentDetails());
        addFeaturePanel("Faculty Details", new login.features.TeacherDetails());
        addFeaturePanel("Student Leave", new login.features.StudentLeave());
        addFeaturePanel("Teacher Leave", new login.features.TeacherLeave());
        addFeaturePanel("StudentLeave Details", new login.features.StudentLeaveDetails());
        addFeaturePanel("TeacherLeave Details", new login.features.TeacherLeaveDetails());
        addFeaturePanel("Update Student", new login.features.UpdateStudent());
        addFeaturePanel("Update Teacher", new login.features.UpdateTeacher());
        addFeaturePanel("Enter Marks", new login.features.EnterMarks());
        addFeaturePanel("Exam Results", new login.features.ExaminationDetails());
        addFeaturePanel("Student Fee Form", new login.features.StudentFeeForm());
        addFeaturePanel("Fee Structure", new login.features.FeeStructure());
        addFeaturePanel("About", new login.features.About());
        addFeaturePanel("Bouncing Ball", new login.features.UniformlyBounce()); //
    }

    private void addFeaturePanel(String name, JPanel panel) {
        featurePanels.put(name, panel);
        add(panel, name);
    }

    public void showFeature(String featureName) {
        cardLayout.show(this, featureName);
    }
}
