package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Staff_Record;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.*;

public class LoginGUI extends JFrame {

    private JTextArea logArea;
    private JTextField userNameField;
    private JPasswordField pwdField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel userNameLabel;
    private JLabel userPwdLabel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private Staff_Record staffRecord;
    private SaleProcessGUI saleProcessGUI;
    private Control control;
    private int shift_id;
    private String username;
    private String password;

    public LoginGUI(int shift_id) {
        this.shift_id = shift_id;
        this.control = new Control();
        initComponents();
        initPanels();
        initActionPerforms();
    }

    public int getShiftID() {
        return this.shift_id;
    }

    //Gather components
    public JPanel initComponents() {
        JPanel componentPanel = new JPanel();
        userNameLabel = new JLabel("UserName:", SwingConstants.RIGHT);
        userPwdLabel = new JLabel("Password:", SwingConstants.RIGHT);
        userNameField = new JTextField();
        pwdField = new JPasswordField();
        logArea = new JTextArea(18, 38);
        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");

        return componentPanel;
    }

    //Initialize JPanel
    public void initPanels() {

        //North JPanel
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(userNameLabel);
        northPanel.add(userNameField);
        northPanel.add(userPwdLabel);
        northPanel.add(pwdField);
        add(northPanel, BorderLayout.NORTH);

        //Center JPanel
        centerPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(logArea);
        centerPanel.add(scrollPane);
        add(centerPanel, BorderLayout.CENTER);

        //South JPanel
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(loginButton);
        southPanel.add(clearButton);
        add(southPanel, BorderLayout.SOUTH);

        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Staff Login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void closeFrame() {
        this.dispose();
    }

    //Initialize actionListeners 
    public void initActionPerforms() {
        //Register login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffRecord = new Staff_Record();
                HashMap<String, String> staffList = staffRecord.getStaff_list();

                boolean isLoginValid = false;
                for (Map.Entry<String, String> entry : staffList.entrySet()) {
                    String userName = entry.getKey();
                    String userPwd = entry.getValue();
                    //Check username and password login
                    if (userName.equalsIgnoreCase(userNameField.getText().trim()) && userPwd.equals(pwdField.getText().trim())) {
                        logArea.append("ShiftID: " + shift_id + " User name: " + userName + " login succeed!\n");
                        
                        JOptionPane.showMessageDialog(centerPanel, "Login success. \nStart Shift : " + getShiftID(),
                                "Inform login succeed", JOptionPane.INFORMATION_MESSAGE);
                        
                        username = userName;
                        password = userPwd;
                        
                        closeFrame();
                        saleProcessGUI = new SaleProcessGUI(shift_id, username, password);

                        isLoginValid = true;
                        break;
                    }
                }
                if (!isLoginValid) {
                    logArea.append("Login failed! Please check your username and password again!\n");
                }
            }
        });

        //Register clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
            }
        });
    }

    public static void main(String[] args) {
        LoginGUI guiFrame = new LoginGUI(1);
    }

}
