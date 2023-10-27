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
    private Staff_Record staffRecord;
    private HashMap<String, String> staffList;
    private HashMap<String, Double> bill_records;
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel userPwdLabel;
    private JPasswordField pwdField;
    private JTextArea logArea;
    private int shift_id;

    public ExitGUI(Control control, CartGUI cartGUI, int shift_id) {
        this.shift_id = shift_id;
        this.control = control;
        this.cartGUI = cartGUI;
        this.saveRecordGUI = new SaveFileRecordGUI(cartGUI);
        saveRecords = new SaveCashierFileRecord();
        JPanel exitPanel = creatExitPanel();
        this.add(exitPanel);
    }

    public int getShiftID() {
        return this.shift_id;
    }

    public JTextField getUserNameField() {
        return userNameField;
    }

    public JPasswordField getPwdField() {
        return pwdField;
    }

    public JPanel creatExitPanel() {
        JPanel exitPanel = new JPanel(new BorderLayout());

        userNameLabel = new JLabel();
        userPwdLabel = new JLabel();
        userNameField = new JTextField();
        pwdField = new JPasswordField();
        logArea = new JTextArea();

        JPanel northPanel = this.userInputInterface(userNameLabel, getUserNameField(),
                userPwdLabel, getPwdField());
        exitPanel.add(northPanel, BorderLayout.NORTH);

        JPanel schrollInterface = this.schrollInterface(logArea);
        exitPanel.add(schrollInterface, BorderLayout.CENTER);

        JPanel southPanel = this.buttonInterface(getUserNameField(), getPwdField(), logArea);
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

        bill_records = saveRecordGUI.getCashier_Record_List();                

        JButton logOutButton = control.createButton("Log Out");
        JButton clearButton = control.createButton("Clear");
        JButton saveButton = control.createButton("Save");

        logOutButton.addActionListener(e -> logOutButton(userNameField,
                pwdField, logArea, southPanel));
        clearButton.addActionListener(e -> control.clearButton(clearButton, logArea));
        saveButton.addActionListener(e -> this.saveFileRecordsButton());

        southPanel.add(clearButton, BorderLayout.WEST);
        southPanel.add(logOutButton, BorderLayout.CENTER);
        southPanel.add(saveButton, BorderLayout.EAST);

        return southPanel;
    }

    public JPanel saveFileRecordsButton() {
        JPanel saveFileRecordsButton = new JPanel(new BorderLayout());

        JButton saveFileButton = control.createButton("Save");
        saveFileButton.addActionListener((ActionEvent e) -> {
            saveRecords.saveFileRecord(bill_records, String.valueOf(this.getShiftID()),
                    this.getUserNameField().getText(), this.getPwdField().getText(), saveFileRecordsButton);
        });
        saveFileRecordsButton.add(saveFileButton, BorderLayout.SOUTH);

        return saveFileRecordsButton;
    }

    public void informSaveFileBeforeExit(boolean isLoginValid, boolean saveFile, JPanel panel) {
        if (isLoginValid) {
            if (saveFile) {
                control.closeFrame(this);
            } else if (!saveFile) {
                JOptionPane.showMessageDialog(panel, "File is not empty. Please save file before logout",
                        "Confirm file save", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
                System.out.println("Log out succeed");
                logArea.append("This user name: " + userName + " logout succeed!\n");
                //When log out, this area should be able to read log out succeed.

                isLoginValid = true;

                boolean saveFile = saveRecords.saveFileCheck(panel);
                
                informSaveFileBeforeExit(isLoginValid, saveFile, panel);

                break;
            }
        }

        if (!isLoginValid) {
            logArea.append("Logout failed! Please check your username and password again!\n");
        }

//                logOutButton.setEnabled(false);
    }

    public static void main(String[] args) {
        Control control = new Control();
        CartGUI cartGUI = new CartGUI(control);
//        SaveFileRecordGUI saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
        SwingUtilities.invokeLater(() -> {
//            ExitGUI exitGUI = new ExitGUI(control, cartGUI, );
        });
    }
}
