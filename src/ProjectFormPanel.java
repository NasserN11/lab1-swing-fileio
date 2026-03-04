import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel {

    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (validateFields())
                saveToFile();
        }
    }

    private class ClearButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            projectNameField.setText("");
            teamLeaderField.setText("");
            startDateField.setText("");

            teamSizeCombo.setSelectedIndex(0);
            projectTypeCombo.setSelectedIndex(0);
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private boolean validateFields() {

        String projectName = projectNameField.getText().trim();
        String teamLeader = teamLeaderField.getText().trim();
        String startDate = startDateField.getText().trim();

        if (projectName.isEmpty()) {
            showErrorDialog("Project Name can't be empty!");
            return false;
        }

        if (teamLeader.isEmpty()) {
            showErrorDialog("Team Leader can't be empty!");
            return false;
        }

        if (startDate.isEmpty()) {
            showErrorDialog("Start Date can't be empty!");
            return false;
        }

        if (projectTypeCombo.getSelectedIndex() == -1) {
            showErrorDialog("Please select a Project Type!");
            return false;
        }

        if (teamSizeCombo.getSelectedIndex() == -1) {
            showErrorDialog("Please enter a Team Size!");
            return false;
        }

        String datePattern = "\\d{2}/\\d{2}/\\d{4}";
        if (!startDate.matches(datePattern)) {
            showErrorDialog("Start Date must be in format: DD/MM/YYYY");
            return false;
        }

        return true;
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return now.format(formatter);
    }

    private void saveToFile() {
        String filename = "projects.txt";

        String projectName = projectNameField.getText().trim();
        String teamLeader = teamLeaderField.getText().trim();
        String teamSize = (String)teamSizeCombo.getSelectedItem();
        String projectType = (String)projectTypeCombo.getSelectedItem();
        String startDate = startDateField.getText().trim();
        String recordTime = getCurrentDateTime();

        String record = String.format(
                "Project Name: %s | Team Leader : %s | Team Size : %s | Project Type : %s | Start Date : %s | Record Time : %s%n",
                projectName, teamLeader, teamSize, projectType, startDate, recordTime
        );

        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(record);

            JOptionPane.showMessageDialog(
                    this,
                    "Project info saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


        } catch (IOException e) {
            showErrorDialog("Error saving to file: " + e.getMessage());
        } ;

    }

    // JTextField
    private JTextField projectNameField;
    private JTextField teamLeaderField;
    private JTextField startDateField;

    // JComboBox
    private JComboBox<String> teamSizeCombo;
    private String[] teamSize = {"1-3", "4-6", "7-10", "10+"};

    private JComboBox<String> projectTypeCombo;
    private String[] projectTypes = {"Web", "Mobile", "Desktop", "API"};

    // JButton
    private JButton saveButton;
    private JButton clearButton;



    public ProjectFormPanel() {

        // Initialize all components
        projectNameField = new JTextField(20);
        teamLeaderField = new JTextField(20);
        startDateField = new JTextField(20);

        teamSizeCombo = new JComboBox<>(teamSize);
        projectTypeCombo = new JComboBox<>(projectTypes);

        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");


        // Set up layout
        setLayout(new GridLayout(6, 2, 5, 5));


        // Add components to panel

        add(new JLabel("Project Name:"));
        add(projectNameField);

        add(new JLabel("Team Leader:"));
        add(teamLeaderField);

        add(new JLabel("Team Size:"));
        add(teamSizeCombo);

        add(new JLabel("Project Type:"));
        add(projectTypeCombo);

        add(new JLabel("Start Date:"));
        add(startDateField);

        add(saveButton);
        add(clearButton);

        saveButton.addActionListener(new SaveButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
    }
}
