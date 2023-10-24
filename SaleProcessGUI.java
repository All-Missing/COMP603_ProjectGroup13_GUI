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

    public SaleProcessGUI() {
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
        this.purchaseGUI = new PurchaseGUI(control, cartGUI);
        this.paymentGUI = new PaymentGUI(control, cartGUI);
        initializeFrame();
    }

    public void initializeFrame() {
        frame = new JFrame("Sale Process");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);        
        createMainPanel();
//        addCategoriesPanel();
        frame.setVisible(true);
    }

    public JPanel createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

         // Add the "Categories" panel to the pageControlPanel
//        JPanel categoriesPanel = new JPanel();
//        control.addPagePanel(categoriesPanel, "Categories");
        
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
        JPanel purchasePanel = purchaseGUI.createPurchasePanel();
        JPanel paymentPanel = paymentGUI.createPaymentPanel();
        
        control.addPagePanel(purchasePanel, "Purchase");
        control.addPagePanel(paymentPanel, "Payment");
    }

    public JPanel createButtonPanel() {
        
        JPanel buttonPanel = new JPanel();
        
        JButton searchButton = control.createButton("Search");
        JButton purchaseButton = control.createButton("Purchase");
        JButton paymentButton = control.createButton("Payment");
        JButton exitButton = control.createButton("Exit");

        searchButton.addActionListener(e -> control.showCard("Search"));
        purchaseButton.addActionListener(e -> control.showCard("Purchase"));
        paymentButton.addActionListener(e -> control.showCard("Payment"));
        exitButton.addActionListener(e -> control.showCard("Exit"));

        buttonPanel.add(searchButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(exitButton);
        
        // Add a "Return to Categories" button here
//        JButton returnButton = control.createButton("Return to Categories");
//        returnButton.addActionListener(e -> control.showCard("Categories"));
//        buttonPanel.add(returnButton);
        
        return buttonPanel;
    }

//    public void addCategoriesPanel() {
//        JPanel categoriesPanel = new JPanel();
//        control.addPagePanel(categoriesPanel, "Categories");
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleProcessGUI());
    }
}
