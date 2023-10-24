package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Control {

    private Product product;
    private int cartOrderID;
    private double totalCost;
    private CardLayout mainLayout;
    private JPanel pageControlPanel;
    private Stack<String> pageHistory;
    private Font font;

    public Control() {
        this.cartOrderID++;
        this.totalCost = 0;
        this.pageControlPanel = new JPanel();
        this.mainLayout = new CardLayout();
        this.pageControlPanel.setLayout(mainLayout);
        this.pageHistory = new Stack<>();
    }

//    public int incrementCardOrderId(int orderID) {
//        return this.cartOrderID++;
//    }
    public int incrementedCartOrderId(Map<String, DefaultListModel<Product>> cashier_Record_List) {
        int currentCartId = 0;
        for (Map.Entry<String, DefaultListModel<Product>> entry : cashier_Record_List.entrySet()) {
            String currentCart = entry.getKey();
            currentCartId = Integer.parseInt(currentCart);
            for(int i = 0; i <= currentCartId; i++)
            {
               this.cartOrderID = i;
            }
        }
        return this.cartOrderID;
    }

    public int getCartOrderID() {
        return cartOrderID;
    }

    public void setCartOrderID(int orderID) {
        this.cartOrderID = orderID;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void setFont(JTextArea textArea) {
        font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        textArea.setFont(font);
    }

    public JPanel getPageControlPanel() {
        return this.pageControlPanel;
    }

    public void addPagePanel(JPanel panel, String panelName) {
        this.pageControlPanel.add(panel, panelName);
    }

    public void showCard(String cardName) {
        mainLayout.show(this.getPageControlPanel(), cardName);
    }

    public double calculateTotalCost(DefaultListModel<Product> cartProductList) {
        this.totalCost = 0;
        for (int i = 0; i < cartProductList.size(); i++) {
            product = cartProductList.getElementAt(i);
            this.totalCost += product.getItemPrice();
        }
        return this.totalCost;
    }

    public JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }

    //login, purchase, payment, exit buttons
    public JPanel createMainButtonPanel() {
        JPanel buttonPanel = new JPanel();
//        JButton loginButton = control.createButton("Login");
        JButton purchaseButton = this.createButton("Purchase");
        JButton paymentButton = this.createButton("Payment");
        JButton exitButton = this.createButton("Exit");

//        purchaseButton.addActionListener(e -> control.showCard("Login"));
//        purchaseButton.addActionListener(e -> this.showCard("Purchase"));
        purchaseButton.addActionListener(e -> this.showCard("Categories"));
        paymentButton.addActionListener(e -> this.showCard("Payment"));
        exitButton.addActionListener(e -> this.showCard("Exit"));

//        buttonPanel.add(loginButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(paymentButton);
        //add searching features 
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    //bug return button not working
    public JPanel returnButton() {
        JPanel returnPanel = new JPanel();
        
        // Add a return button
        JButton returnButton = new JButton("Return to Categories");
//        returnButton.addActionListener(new ActionListener() {
        returnButton.addActionListener((ActionEvent e) -> {
            this.showCard("Categories");
        });
//        returnButton.addActionListener(e -> mainLayout.show(pageControlPanel, "Categories"));
        returnPanel.add(returnButton);

        return returnPanel;
    }

    public void removeElementIndex(JTextArea textArea, int indexAdjust, JPanel panel, DefaultListModel<Product> list) {
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int getLine = textArea.viewToModel2D(e.getPoint());
                    try {
                        int getIndex = textArea.getLineOfOffset(getLine);
                        int removeIndex = getIndex - indexAdjust;

                        list.removeElementAt(removeIndex);

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select an item to delete.",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void removeAllElement(DefaultListModel<Product> list, JPanel panel) {
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                    "Cart is already empty.",
                    "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int response = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to remove all products from the cart?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                list.removeAllElements();
            }
        }
    }
}
