package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CartGUI {

    public DecimalFormat df = new DecimalFormat("#.00");
    private DefaultListModel<Product> cartProductList;
    private JList<Product> cartList;
    private JTextArea cartTextArea;
    private JPanel cartPanel;
    private Product product;
    private Control control;

    public CartGUI(Control control) {
        this.control = control;
        this.cartTextArea = new JTextArea();
        this.cartProductList = new DefaultListModel<>();
        this.cartList = new JList<>(cartProductList);
    }

    public DefaultListModel<Product> getCartProductList() {
        return this.cartProductList;
    }

    public JList<Product> getCartList() {
        return this.cartList;
    }
    
    public String cartOutputString() {
        StringBuilder cartTable = new StringBuilder();
        cartTable.append(" CartID: 1\n");
        cartTable.append(String.format(" %-35s%-10s %-5s\n", "Item", "Price", "Cost"));

        for (int i = 0; i < this.getCartProductList().size(); i++) {
            Product products = this.getCartProductList().getElementAt(i);
            String itemName = products.getItem();
            double pricePerItem = products.getItemPrice();
            double cost = pricePerItem;

            cartTable.append(String.format(" %-35s$%-10.2f$%-5.2f\n", itemName, pricePerItem, cost));
        }

        return cartTable.toString();
    }

    public void updateCartProductList() {
        String cartOutput = this.cartOutputString();
        this.cartTextArea.setText(cartOutput);
    }

    public DefaultListModel<Product> addToCart(String itemId, String itemName, double itemPrice, String categories) {
        this.product = new Product(itemId, itemName, itemPrice, categories);
        this.cartProductList.addElement(product);
//        this.cartList.setModel(cartProductList);
        this.updateCartProductList();
        return cartProductList;
    }

    public JPanel addCartPanel() {
        this.cartPanel = new JPanel(new BorderLayout());

        this.addToCart("SA001", "Food", 2.34, "Product");
        this.addToCart("SA002", "Foods in the", 2.34, "Product");

        cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
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

    public JPanel removeProductFromCart() {
        JPanel removePanel = new JPanel(new BorderLayout());

        JButton removeOneElementButton = control.createButton("Remove Product");
        JButton removeAllElementButton = control.createButton("Remove All Product");

        removeOneElementButton.addActionListener((ActionEvent e) -> {
            removeElement();
        });

        removeAllElementButton.addActionListener((ActionEvent e) -> {
            removeAll();
        });

        removePanel.add(removeOneElementButton, BorderLayout.CENTER);
        removePanel.add(removeAllElementButton, BorderLayout.SOUTH);

        return removePanel;
    }

    public void removeElement() {
        int selectedIndex = this.getCartList().getSelectedIndex();
        if (selectedIndex != -1) {
            this.getCartProductList().removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(cartPanel, "Please select an item to delete from the cart.",
                    "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeAll() {
        if (this.getCartProductList().isEmpty()) {
            JOptionPane.showMessageDialog(cartPanel,
                    "Cart is already empty.",
                    "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int response = JOptionPane.showConfirmDialog(cartPanel,
                    "Are you sure you want to remove all products from the cart?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                this.getCartProductList().removeAllElements();
                JOptionPane.showMessageDialog(cartPanel,
                        "All products has been removed from the cart.",
                        "Cart Cleared", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
