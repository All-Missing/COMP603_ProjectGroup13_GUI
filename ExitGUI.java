package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Staff_Record;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class ExitGUI {

    private Control control;
    private CartGUI cartGUI;
    private SaveFileRecordGUI saveFileRecordGUI;
    private Staff_Record staffRecord;
    private HashMap<String, String> staffList;

    public ExitGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
    }

    public JPanel createLogOutPanel(JFrame frame) {
        JPanel exitPanel = new JPanel(new BorderLayout());

        JPanel userLogOut = this.exitPanel(frame);
        userLogOut.setPreferredSize(new Dimension(400, 50));
        JPanel saveButton = saveFileRecordGUI.saveFileRecordsButton(saveFileRecordGUI.getCashier_Record_List());

        exitPanel.add(userLogOut, BorderLayout.CENTER);
        exitPanel.add(saveButton, BorderLayout.EAST);

        return exitPanel;
    }

    public JPanel exitPanel(JFrame frame) {
        JPanel exitPanel = new JPanel(new BorderLayout());

        JLabel userNameLabel = new JLabel();
        JLabel userPwdLabel = new JLabel();
        JTextField userNameField = new JTextField();
        JPasswordField pwdField = new JPasswordField();
        JTextArea logArea = new JTextArea();

        JPanel northPanel = this.userInputInterface(userNameLabel, userNameField,
                userPwdLabel, pwdField);
        exitPanel.add(northPanel, BorderLayout.NORTH);

        JPanel schrollInterface = this.schrollInterface(logArea);
        exitPanel.add(schrollInterface, BorderLayout.AFTER_LINE_ENDS);

        JPanel southPanel = this.buttonInterface(frame, userNameField, pwdField, logArea);
        exitPanel.add(southPanel, BorderLayout.SOUTH);

        return exitPanel;
    }

    public JPanel userInputInterface(JLabel userNameLabel, JTextField userNameField,
            JLabel userPwdLabel, JPasswordField pwdField) {
        JPanel northPanel = new JPanel(new BorderLayout());

        JPanel userNameInputPanel = this.getUserNameInput(userNameLabel, userNameField);
        northPanel.add(userNameInputPanel, BorderLayout.NORTH);
        JPanel passwordInputPanel = this.getPasswordInput(userPwdLabel, pwdField);
        northPanel.add(passwordInputPanel, BorderLayout.SOUTH);

        return northPanel;
    }

    public JPanel getUserNameInput(JLabel userNameLabel, JTextField userNameField) {
        JPanel userNameInputPanel = new JPanel(new BorderLayout());

        userNameLabel = new JLabel("UserName: ", SwingConstants.RIGHT);
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(550, 50));
        userNameInputPanel.add(userNameLabel, BorderLayout.CENTER);
        userNameInputPanel.add(userNameField, BorderLayout.EAST);

        return userNameInputPanel;
    }

    public JPanel getPasswordInput(JLabel userPwdLabel, JPasswordField pwdField) {
        JPanel passwordInputPanel = new JPanel(new BorderLayout());

        userPwdLabel = new JLabel("Password: ", SwingConstants.RIGHT);
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(550, 50));
        passwordInputPanel.add(userPwdLabel, BorderLayout.CENTER);
        passwordInputPanel.add(pwdField, BorderLayout.EAST);

        return passwordInputPanel;
    }

    public JPanel schrollInterface(JTextArea logArea) {
        JPanel passwordInputPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        logArea.setPreferredSize(new Dimension(550, 100));
        JScrollPane scrollPane = new JScrollPane(logArea);
        passwordInputPanel.add(scrollPane, BorderLayout.CENTER);
        return passwordInputPanel;
    }

    public JPanel buttonInterface(JFrame frame, JTextField userNameField,
            JPasswordField pwdField, JTextArea logArea) {
        //South JPanel
        JPanel southPanel = new JPanel(new BorderLayout());

        JButton logOutButton = control.createButton("Log Out");
        JButton clearButton = control.createButton("Clear");

        logOutButton.addActionListener(e -> this.logOutButton(logOutButton, userNameField,
                pwdField, logArea, frame, southPanel));
        clearButton.addActionListener(e -> control.clearButton(clearButton, logArea));

        southPanel.add(logOutButton, BorderLayout.CENTER);
        southPanel.add(clearButton, BorderLayout.WEST);

        return southPanel;
    }

    public void informSaveFileBeforeExit(boolean isLoginValid, boolean saveFile, JFrame frame, JPanel panel) {
        if (isLoginValid) {
            if (saveFile) {
                control.closeFrame(frame);
            } else {
                JOptionPane.showMessageDialog(panel, "Please save cart record to file before log out",
                        "Save File", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public boolean saveFileCheck(JPanel panel) {
        if (saveFileRecordGUI.getCashier_Record_List().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Check file, confirm file saved, logging out.",
                    "Confirm file save", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public void logOutButton(JButton logOutButton, JTextField userNameField,
            JPasswordField pwdField, JTextArea logArea, JFrame frame, JPanel panel) {
//        logOutButton = control.createButton(buttonName);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                staffRecord = new Staff_Record();
                staffList = staffRecord.getStaff_list();

                boolean isLoginValid = false;
                for (Map.Entry<String, String> entry : staffList.entrySet()) {
                    String userName = entry.getKey();
                    String userPwd = entry.getValue();
                    //Check username and password login
                    if (userName.equalsIgnoreCase(userNameField.getText().trim()) && userPwd.equals(pwdField.getPassword())) {
                        System.out.println("Log out succeed");
                        logArea.append("This user name:" + userNameField.getText() + " login succeed!\n");
                        //When log out, this area should be able to read log out succeed.

                        //When log out, this area should be able to read log out succeed. Implement this
                        isLoginValid = true;

                        boolean saveFile = saveFileCheck(panel);

                        informSaveFileBeforeExit(isLoginValid, saveFile, frame, panel);

                        break;
                    }
                }
                if (!isLoginValid) {
                    logArea.append("Login failed! Please check your username and password again!\n");
                }
            }
        });
//        return logOutButton;
    }
}
