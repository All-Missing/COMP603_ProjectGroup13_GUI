package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SaleProcessGUI {

    private PurchaseGUI purchaseGUI;
    private PaymentGUI paymentGUI;
    private CartGUI cartGUI;
    private Control control;
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

        this.createPage();

        JPanel cartPanel = cartGUI.addCartPanel();
        mainPanel.add(cartPanel, BorderLayout.EAST);

        JPanel pageControlPanel = control.getPageControlPanel();
        mainPanel.add(pageControlPanel, BorderLayout.CENTER);

        // Add Button Panel at the bottom
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    public void createPage() {
//        JPanel loginPanel = loginGUI.createLoginPanel();
        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
//        JPanel logoutPanel = createExitPanel();

//        control.addPagePanel(loginPanel, "Login");
        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
//        this.addPagePanel(logoutPanel, "LogOut");
    }

    //login, purchase, payment, exit buttons
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
//        JButton loginButton = control.createButton("Login");
        JButton purchaseButton = control.createButton("Purchase");
        JButton paymentButton = control.createButton("Payment");
        JButton exitButton = control.createButton("Exit");

//        purchaseButton.addActionListener(e -> control.showCard("Login"));
        purchaseButton.addActionListener(e -> control.showCard("Purchase"));
        paymentButton.addActionListener(e -> control.showCard("Payment"));
        exitButton.addActionListener(e -> control.showCard("Exit"));

//        buttonPanel.add(loginButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
