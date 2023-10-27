package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Staff_Record;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.*;

public class LoginGUI extends JFrame {

    private static final int ROW_AREA = 10;
    private static final int COLUMN_AREA = 20;

    private JTextArea logArea;
    private JComboBox<String> nameCombo;
    private JTextField userNameField;
    private JPasswordField pwdField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel userNameLabel;
    private JLabel userPwdLabel;
    private JPanel northPanel;
    private JPanel southPanel;
    private Staff_Record staffRecord;
    private SaleProcessGUI saleProcessGUI;
    private Control control;
    private int shift_id;
    
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
        logArea = new JTextArea(ROW_AREA, COLUMN_AREA);
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
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        //South JPanel
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(loginButton);
        southPanel.add(clearButton);
        add(southPanel, BorderLayout.SOUTH);

        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
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
                        System.out.println("Login succeed");
                        logArea.append("ShiftID: "+shift_id+" this user name:" + userNameField.getText() + " login succeed!\n");
                        closeFrame();
                        saleProcessGUI = new SaleProcessGUI();

                        //When log out, this area should be able to read log out succeed.
                        
                        //When log out, this area should be able to read log out succeed. Implement this
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

