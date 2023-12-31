package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import COMP603_ProjectGroup13_DB.CashierDBManager;
import COMP603_ProjectGroup13_DB.RetrieveCashierDB;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SearchGUI extends JFrame {

    private static final int ROW_AREA = 20;
    private static final int COLUMN_AREA = 40;
    private HashMap<String, Product> searchProductList;
    private final CashierDBManager dbManager;
    private final Connection conn;
    private Statement statement;
    private List<Product> productListDB;
    private RetrieveCashierDB retrieveDB;
    private JTextArea searchTextArea;
    private JTextField searchTextField;
    private JPanel searchPanel;
    private JLabel searchLabel;
    private JButton searchItemButton;
    private JButton searchListButton;
    private JButton clearButton;
    private JButton returnButton;
    private Control control;
    private CartGUI cartGUI;

    public SearchGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.cartGUI = cartGUI;
        this.searchProductList = new HashMap<>();
        dbManager = new CashierDBManager();
        conn = dbManager.getCashierDBConnection();
        this.retrieveDB = new RetrieveCashierDB();
        this.productListDB = retrieveDB.RetrieveProductList();
        initComponents();
        initActionPerforms();
    }

    public JPanel createSearchPanel() {
        JPanel getComponent = new JPanel(new BorderLayout());
        JPanel panel = this.initPanels();
        getComponent.add(panel, BorderLayout.CENTER);

        return getComponent;
    }

    public void initComponents() {

        this.searchListButton = new JButton("Search List");
        this.searchItemButton = new JButton("Search Item");
        this.clearButton = new JButton("Clear");
        this.returnButton = new JButton("Return");
        this.searchLabel = new JLabel("Search:");
        this.searchTextField = new JTextField(20);
        searchTextField.setText("Enter itemID or item name...");
        this.searchTextArea = new JTextArea(ROW_AREA, COLUMN_AREA);
    }

    public JPanel initPanels() {
        searchPanel = new JPanel();
        //Setup northPanel on search Panel
        JPanel northPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(searchTextArea);
        northPanel.add(scrollPane);
        searchPanel.add(northPanel, BorderLayout.NORTH);

        //Setup centerPanel on search Panel
        JPanel centerPanel = new JPanel();
        centerPanel.add(searchLabel);
        centerPanel.add(searchTextField);
        searchPanel.add(centerPanel, BorderLayout.CENTER);

        //Setup southPanel on search Panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 3));
        southPanel.add(searchListButton);
        southPanel.add(searchItemButton);
        southPanel.add(clearButton);
        southPanel.add(returnButton);
        searchPanel.add(southPanel, BorderLayout.AFTER_LINE_ENDS);

        return searchPanel;
    }

    public void initActionPerforms() {
        ActionListener buttonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == searchListButton) {
                    searchList();
                } else if (e.getSource() == searchItemButton) {
                    searchItemByID(searchTextField.getText().trim());
                } else if (e.getSource() == clearButton) {
                    control.clearButton(clearButton, searchTextArea);
                } else if (e.getSource() == returnButton) {
                    control.showCard("Categories");
                }
            }
        };

        //Mouse click button
        searchTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int getLine = searchTextArea.viewToModel2D(e.getPoint());
                    try {
                        String line = control.extractLineDetails(getLine, searchTextArea);
                        String orderIDArea = control.extractLineValue(line, 0);

                        for (Product product : productListDB) {
                            String item_id = product.getItem_id();
                            if (orderIDArea.equals(item_id)) {
                                confirmAddItem(product.getItem_id(), product.getItem(), product.getItemPrice(), product.getCategory());
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else {

                    JOptionPane.showMessageDialog(searchPanel, "Please select an item to delete.",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchListButton.addActionListener(buttonAction);
        searchItemButton.addActionListener(buttonAction);
        clearButton.addActionListener(buttonAction);
        returnButton.addActionListener(buttonAction);
    }

    public void confirmAddItem(String Item_id, String Item, double ItemPrice, String Category) {
        int option = JOptionPane.showConfirmDialog(searchPanel, "Do you wish to add this product to cart",
                "Confirm adding this product", JOptionPane.YES_NO_OPTION);
        //Check if confirm yes
        if (option == JOptionPane.YES_OPTION) {
            cartGUI.addToCart(Item_id, Item, ItemPrice, Category);
        }
        if (option == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(searchPanel, "Cancel selecting this product",
                    "Select next one?", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchList() {
        int index = 0;
        for (Product product : productListDB) {
            String item_id = product.getItem_id();
            String item = product.getItem();
            Double item_price = product.getItemPrice();
            String category = product.getCategory();
            searchTextArea.append(item_id + " " + item + " " + item_price + " " + category + "\n");
            index++;
            addProductList(item_id, product);
        }
    }

    public void addProductList(String item_id, Product product) {
        this.searchProductList.put(item_id, product);
    }

    //This method return a product details by passing its own item_id
    public void searchItemByID(String itemValues) {
        boolean isFound = false;
        for (Product p : productListDB) {
            if (p.getItem_id().equalsIgnoreCase(itemValues) || p.getItem().equalsIgnoreCase(itemValues)) {
                searchTextArea.append(p.getItem_id() + " " + p.getItem() + " "
                        + p.getItemPrice() + " " + p.getCategory() + "\n");
                isFound = true;
            }
        }
        if (!isFound) {
            searchTextArea.setText("This item is not found!\n");
        }
    }

}
