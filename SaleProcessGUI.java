package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SaleProcessGUI {

    private Control control;
    private CartGUI cartGUI;
    private PurchaseGUI purchaseGUI;
    private PaymentGUI paymentGUI;
    private JFrame frame;
    private JPanel mainPanel;
//    private LoginGUI loginGUI;
    private ExitGUI exitGUI;
    

    public SaleProcessGUI() {
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
//        this.loginGUI = new LoginGUI(control);
        this.exitGUI = new ExitGUI(control, cartGUI);
        this.purchaseGUI = new PurchaseGUI(control, cartGUI);
        this.paymentGUI = new PaymentGUI(control, cartGUI);
        initializeFrame();
    }

    public void initializeFrame() {
        frame = new JFrame("Sale Process");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        createMainPanel();
        frame.setVisible(true);
    }

    public JPanel createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel pageControlPanel = control.getPageControlPanel();
        mainPanel.add(pageControlPanel, BorderLayout.CENTER);

        this.addSalePage();

        JPanel buttonPanel = this.createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public void checkExit(String checkExit) {

        switch (checkExit) {
            case "Purchase":
                control.showCard("Categories");
                control.showCard("Cart");
                break;
            case "Payment":
                control.showCard("Payment");
                break;
            case "Exit":
                control.showCard("Exit");
//                control.closeFrame(frame);
                break;
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JPanel PurchaseButtonPanel = this.createMainControlButton("Purchase", "Purchase");
        JPanel PaymentButtonPanel = this.createMainControlButton("Payment", "Payment");
        JPanel ExitButtonPanel = this.createMainControlButton("Exit", "Exit");
        
        buttonPanel.add(PurchaseButtonPanel);
        buttonPanel.add(PaymentButtonPanel);
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
//        JPanel salePanel = new JPanel(new BorderLayout());

        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
        JPanel cartPanel = cartGUI.createCartPanel();
        JPanel exitPanel = exitGUI.createLogOutPanel(frame);

        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
        control.addPagePanel(cartPanel, "Cart");
        control.addPagePanel(exitPanel, "Exit");

//        return salePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
