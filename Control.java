package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Control {

    private Product product;
    private int cartOrderID = 0;
    private double totalCost;
    private CardLayout mainLayout;
    private SaleProcessGUI saleGUI;
    private JPanel pageControlPanel;
    private Font font;

    public Control() {
        this.cartOrderID++;
        this.totalCost = 0;
        this.pageControlPanel = new JPanel();
        this.mainLayout = new CardLayout();
        this.pageControlPanel.setLayout(mainLayout);
    }

    public int incrementCardOrderId(int orderID) {
        return this.cartOrderID++;
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
        mainLayout.show(pageControlPanel, cardName);
//        mainLayout.next(pageControlPanel);
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

    // Return button should work correctly now
    public JPanel returnButton() {
        JPanel returnPanel = new JPanel();

        // Add a return button
        JButton returnButton = createButton("Return to Categories");
        returnButton.addActionListener(e -> showCard("Categories"));

        returnPanel.add(returnButton);

        return returnPanel;
}

}
