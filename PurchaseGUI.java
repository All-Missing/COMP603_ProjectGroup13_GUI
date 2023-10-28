package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.ProductList;
import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PurchaseGUI {

    private Map<String, String> productCategories;
    private ProductList productList;
    private HashMap<String, Product> product_records;
    private Control control;
    private CartGUI cartGUI;

    public PurchaseGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.cartGUI = cartGUI;        
        this.productCategories = new HashMap<>();
        this.productList = new ProductList();
        this.product_records = productList.getProduct_records();
    }

    public JPanel createPurchasePanel() {
        JPanel purchasePanel = new JPanel(new BorderLayout());

        JPanel categoriesPanel = this.addProductsCategories();
        purchasePanel.add(categoriesPanel, BorderLayout.CENTER);
        
        control.addPagePanel(categoriesPanel, "Categories");

        return purchasePanel;
    }

    public JPanel addProductsCategories() {
        JPanel managePanel = new JPanel(new BorderLayout());
        JPanel categoriesPanel = new JPanel(new GridLayout(4, 2));

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

            JButton categoryButton = control.createButton(categoryName);
            categoryButton.addActionListener((ActionEvent e) -> {
                try {
                    JPanel productPanel = this.addProductPanel(categoryId);
                    control.addPagePanel(productPanel, "Product");
                    control.showCard("Product");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            });
            categoriesPanel.add(categoryButton);
        }

        managePanel.add(categoriesPanel, BorderLayout.CENTER);

        return categoriesPanel;
    }

    public JPanel addProductPanel(String categoryId) {
        JPanel productPanel = new JPanel(new BorderLayout());

        JPanel addProductPanel = this.createProductButton(categoryId);

        JPanel returnPanel = control.returnButton(); // Invoke the returnButton method

        productPanel.add(addProductPanel, BorderLayout.CENTER);
        productPanel.add(returnPanel, BorderLayout.SOUTH);

        return productPanel;
    }

    public JPanel createProductButton(String categoryId) {
        JPanel addProductPanel = new JPanel(new GridLayout(0, 2));
        for (Product products : product_records.values()) {
            if (products.getItem_id().contains(categoryId)) {
                String itemName = products.getItem();

                JButton productButton = control.createButton(itemName);
                productButton.addActionListener((ActionEvent e) -> {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            cartGUI.addToCart(products.getItem_id(), products.getItem(),
                                    products.getItemPrice(), products.getCategory());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(addProductPanel, "No product found from this categories.",
                                    "No Product Found", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                });
                addProductPanel.add(productButton);
            }
        }
        return addProductPanel;
    }

}
