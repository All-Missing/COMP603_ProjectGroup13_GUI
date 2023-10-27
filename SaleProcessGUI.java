package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SaleProcessGUI {

    private Control control;
    private CartGUI cartGUI;
    private PurchaseGUI purchaseGUI;
    private PaymentGUI paymentGUI;
    private JFrame frame;
    private JPanel mainPanel;
    private SearchGUI searchGUI;
    private SaveFileRecordGUI saveFileRecordGUI;
    private SaveCashierFileRecord saveCashierFileRecord;
    private ExitGUI exitGUI;
    private JList<String> productListJList;
    private DefaultListModel<String> productListModel;
    private int shift_id;
    private String username;
    private String password;
    private HashMap<String, Double> cashier_records;

//    public SaleProcessGUI(int shift_id, String username, String password) {
    public SaleProcessGUI(int shift_id) {
        this.shift_id = shift_id;
//        this.username = username;
//        this.password = password;
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
        this.searchGUI = new SearchGUI(control, cartGUI);
        this.saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.saveCashierFileRecord = new SaveCashierFileRecord();
        this.purchaseGUI = new PurchaseGUI(control, cartGUI);
        this.paymentGUI = new PaymentGUI(control, cartGUI);
//        this.cashier_records = saveFileRecordGUI.getCashier_Record_List();
        initializeFrame();
    }

    public void initializeFrame() {
        frame = new JFrame("Sale Process");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        createMainPanel();
        frame.setVisible(true);
    }

    public HashMap<String, Double> getCashier_Records() {
        return this.cashier_records;
    }

    public JPanel createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel pageControlPanel = control.getPageControlPanel();
        mainPanel.add(pageControlPanel, BorderLayout.CENTER);

        this.addSalePage();

        JPanel cartPanel = cartGUI.createCartPanel();
        mainPanel.add(cartPanel, BorderLayout.EAST);

        // Add Button Panel at the bottom
        JPanel buttonPanel = this.createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    public void checkExit(String checkExit) {

        switch (checkExit) {
            case "Purchase":
                control.showCard("Categories");
//                control.showCard("Cart");
                break;
            case "Payment":
                control.showCard("Payment");
                break;
            case "Search":
                control.showCard("Search");
                break;
            case "Exit":
//                saveCashierFileRecord.saveFileRecord(cashier_records, String.valueOf(shift_id), password, username, mainPanel);
//                JOptionPane.showMessageDialog(mainPanel, "File is save. Exiting",
//                "Save & Exit", JOptionPane.INFORMATION_MESSAGE);
                control.closeFrame(frame);
                exitGUI = new ExitGUI(control);
                break;
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JPanel PurchaseButtonPanel = this.createMainControlButton("Purchase", "Purchase");
        JPanel PaymentButtonPanel = this.createMainControlButton("Payment", "Payment");
        JPanel searchButtonPanel = this.createMainControlButton("Search", "Search");
        JPanel ExitButtonPanel = this.createMainControlButton("Save & Exit", "Exit");

        buttonPanel.add(PurchaseButtonPanel);
        buttonPanel.add(PaymentButtonPanel);
        buttonPanel.add(searchButtonPanel);
        buttonPanel.add(ExitButtonPanel);

        return buttonPanel;

    }

    public JPanel createMainControlButton(String buttonName, String checkExitString) {
        JPanel createbButtonPanel = new JPanel();

        JButton button = control.createButton(buttonName);

        button.addActionListener(e -> {
//            control.showCard("Exit");
            this.checkExit(checkExitString);
        });

        createbButtonPanel.add(button);

        return createbButtonPanel;
    }

    public void addSalePage() {

        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
//        JPanel paymentPanel = paymentGUI.createPaymentPanel(shift_id, username, password);
        JPanel cartPanel = cartGUI.createCartPanel();
//        JPanel exitPanel = exitGUI.createLogOutPanel(frame);
        JPanel searchPanel = searchGUI.createSearchPanel();

        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
        control.addPagePanel(cartPanel, "Cart");
//        control.addPagePanel(exitPanel, "Exit");
        control.addPagePanel(searchPanel, "Search");

    }

    public static void main(String[] args) {
//        HashMap<String, Double> list = new HashMap<>();
//        SaleProcessGUI sale = new SaleProcessGUI(1, "k", "d");
//        list = sale.getCashier_Records();
//        for (Map.Entry<String, Double> getlist : list.entrySet()) {
//            String id = getlist.getKey();
//            double bill = getlist.getValue();
//
//            System.out.println(id);
//            System.out.println(bill);
//        }
//        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
