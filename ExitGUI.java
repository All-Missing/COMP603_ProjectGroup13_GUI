package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.SaveCashierFileRecords;
import COMP603_ProjectGroup13.Staff_Record;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ExitGUI extends JFrame {

    private Control control;
    private CartGUI cartGUI;
    private static SaveCashierFileRecord saveRecords;
    private SaveFileRecordGUI saveRecordGUI;
    private HashMap<String, Double> cashier_records;
    private Staff_Record staffRecord;
    private HashMap<String, String> staffList;
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel userPwdLabel;
    private JPasswordField pwdField;
    private JTextArea logArea;
    private JPanel exitPanel;
    private int shift_id;

    public ExitGUI(Control control, int shift_id, HashMap<String, Double> cashier_records) {
        this.shift_id = shift_id;
        this.control = control;
        this.cartGUI = new CartGUI(control);
        this.saveRecordGUI = saveRecordGUI;
        this.cashier_records = new HashMap<>();
//        saveRecordGUI.addCashierRecord(1, 50.0); // Example of adding a record
        this.cashier_records = cashier_records;
        saveRecords = new SaveCashierFileRecord();
        JPanel createExitPanel = creatExitPanel();
        this.add(createExitPanel);
    }

    public int getShiftID() {
        return this.shift_id;
    }

    public JPanel creatExitPanel() {
        exitPanel = new JPanel(new BorderLayout());

        userNameLabel = new JLabel();
        userPwdLabel = new JLabel();
        userNameField = new JTextField();
        pwdField = new JPasswordField();
        logArea = new JTextArea();

        JPanel northPanel = this.userInputInterface(userNameLabel, userNameField,
                userPwdLabel, pwdField);
        exitPanel.add(northPanel, BorderLayout.NORTH);

        JPanel schrollInterface = this.schrollInterface(logArea);
        exitPanel.add(schrollInterface, BorderLayout.CENTER);

        JPanel southPanel = this.buttonInterface(userNameField, pwdField, logArea);
        exitPanel.add(southPanel, BorderLayout.SOUTH);

        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Staff Logout");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        return exitPanel;
    }

    public JPanel userInputInterface(JLabel userNameLabel, JTextField userNameField,
            JLabel userPwdLabel, JPasswordField pwdField) {
        JPanel userInputInterface = new JPanel(new GridLayout(2, 2));

        userNameLabel.setText("UserName: ");
        userPwdLabel.setText("Password: ");

        userInputInterface.add(userNameLabel);
        userInputInterface.add(userNameField);
        userInputInterface.add(userPwdLabel);
        userInputInterface.add(pwdField);

        return userInputInterface;
    }

    public JPanel schrollInterface(JTextArea logArea) {
        JPanel outputArea = new JPanel(new BorderLayout());
//        logArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logArea);
        outputArea.add(scrollPane, BorderLayout.CENTER);
        return outputArea;
    }

    public JPanel buttonInterface(JTextField userNameField,
            JPasswordField pwdField, JTextArea logArea) {
        //South JPanel
        JPanel southPanel = new JPanel(new BorderLayout());

        JButton logOutButton = control.createButton("Log Out");
        JButton clearButton = control.createButton("Clear");
        JButton saveButton = control.createButton("Save");

        logOutButton.addActionListener(e -> logOutButton(userNameField,
                pwdField, logArea, southPanel));
        clearButton.addActionListener(e -> control.clearButton(clearButton, logArea));
        saveButton.addActionListener(e -> this.saveFile());

        southPanel.add(clearButton, BorderLayout.WEST);
        southPanel.add(logOutButton, BorderLayout.CENTER);
        southPanel.add(saveButton, BorderLayout.EAST);

        return southPanel;
    }

    public void saveFile() {
        String username = userNameField.getText().trim();
        String password = new String(pwdField.getPassword()).trim();

        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please make sure to enter you name and password before saving file.",
                    "Save File Fail", JOptionPane.ERROR_MESSAGE);
        } else if (cashier_records.isEmpty()) {
            logArea.append("File is empty. No records to save.\n");
        } else {
            logArea.append("File is save succesfully.\n");
            saveRecords.saveFileRecord(cashier_records, String.valueOf(this.getShiftID()), password, username, exitPanel);
        }
    }

    public void informSaveFileBeforeExit(HashMap<String, Double> cashier_records, boolean isLoginValid, JPanel panel) {
        if (isLoginValid) {
            if (cashier_records.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Log out success!",
                        "Confirm log out", JOptionPane.INFORMATION_MESSAGE);
                control.closeFrame(this);
                ShiftGUI shiftGUI = new ShiftGUI();
            } else {
                logArea.append("Fail to log out. Please save file before logging out.\n\n");
            }
        }
    }

    public void closeFrame() {
        this.dispose();
    }

    public void logOutButton(JTextField userNameField,
            JPasswordField pwdField, JTextArea logArea, JPanel panel) {

        staffRecord = new Staff_Record();
        staffList = staffRecord.getStaff_list();

        boolean isLoginValid = false;
        for (Map.Entry<String, String> entry : staffList.entrySet()) {
            String userName = entry.getKey();
            String userPwd = entry.getValue();

            //Check username and password login
            if (userName.equalsIgnoreCase(userNameField.getText().trim()) && userPwd.equals(pwdField.getText())) {
                //When log out, this area should be able to read log out succeed.
                logArea.append("User name: " + userName + " logging out...\n");

                isLoginValid = true;
                informSaveFileBeforeExit(cashier_records, isLoginValid, panel);

                break;
            }
        }
        if (!isLoginValid) {
            logArea.append("Logout failed! Please check your username and password again!\n");
        }
    }

    public static void main(String[] args) {
//        Control control = new Control();
//        CartGUI cartGUI = new CartGUI(control);
//        SaveFileRecordGUI saveRecordGUI = new SaveFileRecordGUI(cartGUI);
//        SwingUtilities.invokeLater(() -> {
//            ExitGUI exitGUI = new ExitGUI(control, 1, saveRecordGUI);
//        });
    }
}
