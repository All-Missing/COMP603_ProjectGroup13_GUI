package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SaleProcessGUI extends JFrame{

    private Control control;
    private CartGUI cartGUI;
    private PurchaseGUI purchaseGUI;
    private PaymentGUI paymentGUI;
    private JPanel mainPanel;
    private SearchGUI searchGUI;
    private ExitGUI exitGUI;
    private int shift_id;
    private String username;
    private String password;

    public SaleProcessGUI(int shift_id, String username, String password) {
        this.shift_id = shift_id;
        this.username = username;
        this.password = password;
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
        this.searchGUI = new SearchGUI(cartGUI);
        this.purchaseGUI = new PurchaseGUI(control, cartGUI);
        this.paymentGUI = new PaymentGUI(control, cartGUI);
        initializeFrame();
    }

    public void initializeFrame() {
        this.setTitle("Sale Process");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        createMainPanel();
        this.setVisible(true);
    }

    public JPanel createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel pageControlPanel = control.getPageControlPanel();
        mainPanel.add(pageControlPanel, BorderLayout.CENTER);

        this.addSalePage();
        
        //call cart panel
        JPanel cartPanel = cartGUI.createCartPanel();
        mainPanel.add(cartPanel, BorderLayout.EAST);

        // Add Button Panel at the bottom
        JPanel buttonPanel = this.createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);

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
                paymentGUI.saveFile(shift_id, username, password);
                control.closeFrame(this);
                exitGUI = new ExitGUI();
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
            this.checkExit(checkExitString);
        });

        createbButtonPanel.add(button);

        return createbButtonPanel;
    }

    public void addSalePage() {
        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
        JPanel cartPanel = cartGUI.createCartPanel();
        JPanel searchPanel = searchGUI.createSearchPanel();

        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
        control.addPagePanel(cartPanel, "Cart");
        control.addPagePanel(searchPanel, "Search");
    }

    public static void main(String[] args) {
        SaleProcessGUI sale = new SaleProcessGUI(1, "k", "d");
    }
}
