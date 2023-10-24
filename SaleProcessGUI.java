package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
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

    public SaleProcessGUI() {
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
//        this.loginGUI = new LoginGUI();
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

//        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
//        mainPanel.add(purchasePanel, BorderLayout.CENTER);
//        JPanel paymentPanel = paymentGUI.createPaymentButtonsPanel();
//        mainPanel.add(paymentPanel, BorderLayout.CENTER);
        addPage();

        JPanel cartPanel = cartGUI.createCartPanel();
        mainPanel.add(cartPanel, BorderLayout.EAST);
        
        // Add Button Panel at the bottom
        JPanel buttonPanel = control.createMainButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    public void addPage() {
        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
//        JPanel cartPanel = cartGUI.createCartPanel();

        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
//        control.addPagePanel(cartPanel, "Cart");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
