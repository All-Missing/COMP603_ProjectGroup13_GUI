package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import COMP603_ProjectGroup13.ProductList;
import COMP603_ProjectGroup13_DB.RetrieveCashierDB;
import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PurchaseGUI {

    private Map<String, String> productCategories;
    private ProductList productList;
    private RetrieveCashierDB retrieveDB;
    private List<Product> productListDB;
    private HashMap<String, Product> product_records;
    private Control control;
    private CartGUI cartGUI;   
    private SearchGUI searchGUI;
    
    public PurchaseGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.cartGUI = cartGUI;
        this.searchGUI = new SearchGUI();
        this.productCategories = new HashMap<>();
        this.productList = new ProductList();
        this.product_records = productList.getProduct_records();
        this.retrieveDB = new RetrieveCashierDB();
        this.productListDB = new ArrayList<>();
        
    }

    public JPanel createPurchasePanel() {
        JPanel purchasePanel = new JPanel(new BorderLayout());

        JPanel categoriesPanel = addProductsCategories();        
        purchasePanel.add(categoriesPanel, BorderLayout.CENTER);
        
        //add research panel here 
        JPanel searchPanel = this.addResearchFunction();
        purchasePanel.add(searchPanel, BorderLayout.SOUTH);
        
        
//        JPanel categoriesPanel = addProductPanel("SA");
//        purchasePanel.add(categoriesPanel, BorderLayout.CENTER);
        
        return purchasePanel;
    }
         
    public JPanel addProductsCategories() {
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
                addProductPanel(categoryId);
                //Return catagories card
                control.showCard("Categories");
            });            
            categoriesPanel.add(categoryButton, BorderLayout.CENTER);        
        }                        
        control.addPagePanel(categoriesPanel, "Categories");
        
        JButton returnButton = control.createButton("Return to Main");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.showCard("Categories");
            }
        });        
        categoriesPanel.add(returnButton, BorderLayout.SOUTH);
        
        return categoriesPanel;
    }

    public JPanel addProductPanel(String categoryId) {
        JPanel productPanel = new JPanel(new GridLayout(0, 2));

        this.createProductButton(productPanel, categoryId);

        JPanel returnPanel = control.returnButton(); // Invoke the returnButton method

        JPanel productContainerPanel = new JPanel(new BorderLayout());
        productContainerPanel.add(productPanel, BorderLayout.CENTER);
        productContainerPanel.add(returnPanel, BorderLayout.SOUTH);

        control.getPageControlPanel().removeAll();
        control.addPagePanel(productContainerPanel, "Products");
        control.getPageControlPanel().revalidate();
        control.getPageControlPanel().repaint();

        return productContainerPanel;
    }


    public void createProductButton(JPanel productPanel, String categoryId) {
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
                            JOptionPane.showMessageDialog(productPanel, "No product found from this categories.",
                                    "No Product Found", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                });
                productPanel.add(productButton);
            }
        }
    }
        
}
