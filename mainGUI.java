package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class mainGUI {

    private ProductList productList;
//    private Product product;
    private HashMap<String, Product> product_records;
    private Map<String, String> productCategories;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel cartPanel;
    private DefaultListModel<String> cartProductList;
    private CardLayout mainLayout;
    private JPanel pageControlPanel;

    public mainGUI() {
        initializeFrame();
        createMainPanel();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Sale Process");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        pageControlPanel = new JPanel();
        mainLayout = new CardLayout();
        pageControlPanel.setLayout(mainLayout);

        JPanel purchasePanel = createPurchasePanel();
        pageControlPanel.add(purchasePanel, "Purchase");

        JPanel paymentPanel = createPaymentPanel();
        pageControlPanel.add(paymentPanel, "Payment");

//        JPanel logoutPanel = createExitPanel();
//        cardPanel.add(exitPanel, "Exit");

        mainPanel.add(pageControlPanel, BorderLayout.CENTER);

        // Add Button Panel at the bottom
        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    //login, purchase, payment, exit buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton purchaseButton = new JButton("Purchase");
        JButton paymentButton = new JButton("Payment");
        JButton exitButton = new JButton("Exit");

        purchaseButton.addActionListener(e -> mainLayout.show(pageControlPanel, "Purchase"));
        paymentButton.addActionListener(e -> mainLayout.show(pageControlPanel, "Payment"));
        exitButton.addActionListener(e -> mainLayout.show(pageControlPanel, "Exit"));

        buttonPanel.add(purchaseButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }

    private JPanel createPurchasePanel() {
        JPanel purchasePanel = new JPanel(new BorderLayout());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel categoriesPanel = addProductsCatagories();
        purchasePanel.add(categoriesPanel, BorderLayout.WEST);
        this.addCartPanel();
        
        JPanel removePanel = this.removeProductFromCart();
        purchasePanel.add(removePanel, BorderLayout.EAST);

        return purchasePanel;
    }

    private JPanel removeProductFromCart() {
        JPanel removePanel = new JPanel(new BorderLayout());

        // Remove selected item from the cartListModel
        cartPanel = new JPanel(new BorderLayout());
        cartProductList = new DefaultListModel<>();
        JList<String> cartList = new JList<>(cartProductList);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove Product");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cartList.getSelectedIndex();
                if (selectedIndex != -1) {
                    cartProductList.removeElementAt(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an item to delete from the cart.",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removePanel.add(cartPanel, BorderLayout.CENTER);
        removePanel.add(removeButton, BorderLayout.SOUTH);

        return removePanel;
    }

    private JPanel returnButton() {
        JPanel returnPanel = new JPanel();

        // Add a return button
        JButton returnButton = new JButton("Return to Categories");
        returnButton.addActionListener(e -> mainLayout.show(pageControlPanel, "Categories"));

        returnPanel.add(returnButton, BorderLayout.SOUTH);

        return returnPanel;
    }

    private JPanel createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new BorderLayout());

        JPanel receiptPanel = new JPanel();
        JTextArea receiptTextArea = new JTextArea(10, 20);
        receiptPanel.add(new JScrollPane(receiptTextArea));

        JPanel paymentOptionsPanel = new JPanel();
        JButton cardButton = new JButton("Pay with Card");
        JButton cashButton = new JButton("Pay with Cash");
        JButton refundButton = new JButton("Refund");

        JTextField amountField = new JTextField(10);
        JLabel amountLabel = new JLabel("Enter Amount:");

        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                receiptTextArea.append("Paid with Card: $" + amount + "\n");
            }
        });

        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                receiptTextArea.append("Paid with Cash: $" + amount + "\n");
            }
        });

        refundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                receiptTextArea.append("Refunded: $" + amount + "\n");
            }
        });

        paymentOptionsPanel.add(cardButton);
        paymentOptionsPanel.add(cashButton);
        paymentOptionsPanel.add(refundButton);
        paymentOptionsPanel.add(amountLabel);
        paymentOptionsPanel.add(amountField);

        paymentPanel.add(receiptPanel, BorderLayout.CENTER);
        paymentPanel.add(paymentOptionsPanel, BorderLayout.SOUTH);

        return paymentPanel;
    }

    private JPanel addProductsCatagories() {
        JPanel categoriesPanel = new JPanel(new GridLayout(4, 2));
        
        productList = new ProductList();
        product_records = productList.getProduct_records();

        productCategories = new HashMap<>();
        productCategories.put("PI", "Pies");
        productCategories.put("SA", "Savouries");
        productCategories.put("MD", "Muffins & Donuts");
        productCategories.put("CS", "Cakes & Slices");
        productCategories.put("PC", "Pasties & Cookies");
        productCategories.put("COF", "Coffee");
        productCategories.put("CHO", "Hot Chocolate");
        productCategories.put("FU", "Fuel");

        for (Map.Entry<String, String> entry : productCategories.entrySet()) {
            String categoryId = entry.getKey();
            String categoryName = entry.getValue();

            JButton categoryButton = new JButton(categoryName);
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDetailedProductPanel(categoryId);
                    mainLayout.show(pageControlPanel, "Products");
                }
            });
            categoriesPanel.add(categoryButton);
        }

        pageControlPanel.add(categoriesPanel, "Categories");
        return categoriesPanel;
    }

    private void showDetailedProductPanel(String categoryId) {
        JPanel productPanel = new JPanel(new GridLayout(0, 2));
//        Product productDetail;

        for (Product products : product_records.values()) {
            if (products.getItem_id().contains(categoryId)) {
                JButton productButton = new JButton(products.getItem());
                productButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        productDetail = new Product(products.getItem_id(), products.getItem(), products.getItemPrice(), String.valueOf(product.get_quantity()));
                        String itemName = products.getItem();
                        cartProductList.addElement(itemName);
                        updateCartPanel();
                    }
                });
                productPanel.add(productButton);
            }
        }
        
        JPanel returnPanel = this.returnButton();
        productPanel.add(returnPanel);         

        pageControlPanel.removeAll();
        pageControlPanel.add(new JScrollPane(productPanel), "Products");
        pageControlPanel.revalidate();
        pageControlPanel.repaint();    
    }

    private void addCartPanel() {
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(250, 600));
        cartProductList = new DefaultListModel<>();
        JList<String> cartList = new JList<>(cartProductList);
        JScrollPane scrollPane = new JScrollPane(cartList);

        cartPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(cartPanel, BorderLayout.EAST);
    }
    
    private void updateCartPanel() {
        
        cartPanel.removeAll();

        // Add the updated cart items to the cart panel
        JList<String> cartList = new JList<>(cartProductList);
        JScrollPane scrollPane = new JScrollPane(cartList);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        cartPanel.revalidate();
        cartPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainGUI());
    }
}
