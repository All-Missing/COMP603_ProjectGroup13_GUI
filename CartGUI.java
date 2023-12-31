package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CartGUI {

    public DecimalFormat df = new DecimalFormat("#0.00");
    private JPanel cartPanel;
    private Control control;
    private Product product;
    private JTextArea cartTextArea;
    private DefaultListModel<Product> cartProductList;

    public CartGUI(Control control) {
        this.control = new Control();
        this.cartTextArea = new JTextArea();
        this.cartProductList = new DefaultListModel<>();
    }

    public DefaultListModel<Product> getCartProductList() {
        return this.cartProductList;
    }
    
    public JTextArea getCartTextArea() {
        return this.cartTextArea;
    }
    
    public void updateCartProductList() {
        String cartOutput = this.cartOutputString();
        this.cartTextArea.setText(cartOutput);
    }
    
    public void addToCart(String itemId, String itemName, double itemPrice, String categories) {
        this.product = new Product(itemId, itemName, itemPrice, categories);
        this.cartProductList.addElement(product);
        System.out.println(product.getItem_id());
        this.updateCartProductList();
    }
    
    public JPanel createCartPanel() {
        this.cartPanel = new JPanel(new BorderLayout());

        cartTextArea.setEditable(true);
        control.setFont(cartTextArea);

        this.updateCartProductList();

        JPanel removePanel = this.removeProductFromCart();

        JPanel cartContainerPanel = new JPanel(new BorderLayout());
        cartContainerPanel.setPreferredSize(new Dimension(400, 600));
        cartContainerPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);
        cartContainerPanel.add(removePanel, BorderLayout.SOUTH);

        this.cartPanel.add(cartContainerPanel, BorderLayout.EAST);

        return this.cartPanel;
    }

    public String cartOutputString() {
        String currentCartID = String.valueOf(control.getCartOrderID());
        StringBuilder cartTable = new StringBuilder();
        cartTable.append(String.format(" CartID: %-5s\n", currentCartID));
        cartTable.append(String.format(" %-5s\t%-35s%-10s\n", "Index", "Item", "Price"));

        double totalBill = 0;
        for (int index = 0; index < this.getCartProductList().size(); index++) {
            Product products = this.getCartProductList().getElementAt(index);
            String itemName = products.getItem();
            double pricePerItem = products.getItemPrice();
            totalBill += pricePerItem;

            cartTable.append(String.format(" %-7s%-35s$%-10.2f\n", index + 1, itemName, pricePerItem));
        }
        cartTable.append(String.format("\n\n\n%-30s Total Bill: $" + df.format(totalBill), " "));

        return cartTable.toString();
    }

    public JPanel removeProductFromCart() {
        JPanel removePanel = new JPanel(new BorderLayout());

        JButton removeOneElementButton = control.createButton("Remove Product");
        JButton removeAllElementButton = control.createButton("Remove All Product");

        removeOneElementButton.addActionListener((ActionEvent e) -> {
            control.removeElementIndex(cartTextArea, 2, cartPanel, this.getCartProductList());
            this.updateCartProductList();
        });

        removeAllElementButton.addActionListener((ActionEvent e) -> {
            control.removeAllElement(cartProductList, cartPanel);
            this.updateCartProductList();
        });

        removePanel.add(removeOneElementButton, BorderLayout.CENTER);
        removePanel.add(removeAllElementButton, BorderLayout.SOUTH);

        return removePanel;
    }
}
