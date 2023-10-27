package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.CheckShiftID;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ShiftGUI extends JFrame {

    private static final int ROW_AREA = 20;
    private static final int COLUMN_AREA = 30;

    private JTextArea logArea;
    private JScrollPane scrollPanel;
    private JButton confirmButton;
    private JButton noButton;
    private JButton clearButton;
    private JPanel shiftPanel;
    private Control control;
    private LoginGUI loginGUI;
    private ExitGUI exitGUI;
    private int shift_id;
    private CheckShiftID cShiftID;

    public ShiftGUI() {
        this.control = new Control();
        cShiftID = new CheckShiftID();
        this.shift_id = cShiftID.checkShiftID();
        initComponents();
        initPanels();
        initActionPerforms();
    }

    public JPanel getShiftPanel() {
        return this.shiftPanel;
    }

    public void initComponents() {
        logArea = new JTextArea(ROW_AREA, COLUMN_AREA);
        logArea.setText("Loading Cashier Gas Station app ... please wait ...!!!\n");
        confirmButton = control.createButton("Confirm");
        clearButton = control.createButton("Clear");
    }

    public void initPanels() {
        shiftPanel = new JPanel();

        //Create buttonPanel
        JPanel logPanel = new JPanel();
        scrollPanel = new JScrollPane(logArea);
        logPanel.add(scrollPanel);
        logPanel.setPreferredSize(new Dimension(250, 250));

        shiftPanel.add(logPanel, BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(clearButton);
        shiftPanel.add(buttonsPanel, BorderLayout.CENTER);

        //Create logPanel        
        this.add(shiftPanel, BorderLayout.CENTER);
        this.setTitle("Cashier App");
        this.setLocationRelativeTo(null);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void initActionPerforms() {
        ActionListener buttonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == confirmButton) {
                    confirmButtonAction();
                } else if (e.getSource() == clearButton) {
                    logArea.setText("");
                }
            }
        };
        confirmButton.addActionListener(buttonAction);
        clearButton.addActionListener(buttonAction);
    }

    public void confirmButtonAction() {
        Boolean isStarNewShift = false;
        while (!isStarNewShift) {
            int option = JOptionPane.showConfirmDialog(shiftPanel, "Do you wish to start a new shift",
                    "Confirm to login process....", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                logArea.append("User login process....");
                control.closeFrame(this);
                loginGUI = new LoginGUI(shift_id);
                shift_id = cShiftID.checkShiftID();
                isStarNewShift = true;
            } else if (option == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(shiftPanel, "User don't wish to login!",
                        "", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }



    public static void main(String[] args) {
        ShiftGUI shiftGUI = new ShiftGUI();

    }

}
