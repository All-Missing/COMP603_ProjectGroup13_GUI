package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
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
    private ExitGUI exitGUI;
    private JList<String> productListJList;
    private DefaultListModel<String> productListModel;
    private int shift_id;

    public SaleProcessGUI(int shift_id) {
        this.shift_id = shift_id;
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
        this.searchGUI = new SearchGUI(control, cartGUI);
        this.saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.exitGUI = new ExitGUI(control, shift_id, saveFileRecordGUI);
        this.purchaseGUI = new PurchaseGUI(control, cartGUI);
        this.paymentGUI = new PaymentGUI(control, cartGUI);
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
                control.closeFrame(frame);
                exitGUI = new ExitGUI(control, shift_id, saveFileRecordGUI);
                break;
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JPanel PurchaseButtonPanel = this.createMainControlButton("Purchase", "Purchase");
        JPanel PaymentButtonPanel = this.createMainControlButton("Payment", "Payment");
        JPanel searchButtonPanel = this.createMainControlButton("Search", "Search");
        JPanel ExitButtonPanel = this.createMainControlButton("Exit", "Exit");

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
//        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
