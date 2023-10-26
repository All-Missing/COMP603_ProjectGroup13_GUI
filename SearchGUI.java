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
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchGUI extends JFrame {

    private static final int ROW_AREA = 20;
    private static final int COLUMN_AREA = 40;

    private final CashierDBManager dbManager;
    private final Connection conn;
    private Statement statement;
    private Product product;
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

    public SearchGUI() {
        dbManager = new CashierDBManager();
        conn = dbManager.getCashierDBConnection();
        this.retrieveDB = new RetrieveCashierDB();
        this.product = new Product();
        this.productListDB = retrieveDB.RetrieveProductList();
        initComponents();
        initPanels();
        initActionPerforms();
    }

    public SearchGUI(Control control) {
        this.control = control;
        dbManager = new CashierDBManager();
        conn = dbManager.getCashierDBConnection();
        this.retrieveDB = new RetrieveCashierDB();
        this.productListDB = retrieveDB.RetrieveProductList();
        this.cartGUI = new CartGUI(control);
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
                    clear();
                } else if (e.getSource() == returnButton) {
                    control.showCard("Categories");
                }
            }
        };

        // Update the "Search List" button to call addElementOneClick
        searchListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addElementOneClick();
            }
        });

        searchListButton.addActionListener(buttonAction);
        searchItemButton.addActionListener(buttonAction);
        clearButton.addActionListener(buttonAction);
        returnButton.addActionListener(buttonAction);
    }

    public void addElementOneClick() {
        // Add code to handle clicking a product in the searchTextArea
        searchTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = searchTextArea.viewToModel(e.getPoint()); // Get the selected row
                    if (selectedRow >= 0) {
                        // You can handle the click action here without adding the product to the cart
                        // For example, display product details or perform a different action

                        // Update the cart display if necessary
                        cartGUI.updateCartProductList();
                    }
                }
            }
        });
    }

    //Create researchFunction
    public void searchList() {

        for (Product product : productListDB) {
            String item_id = product.getItem_id();
            String item = product.getItem();
            Double item_price = product.getItemPrice();
            String category = product.getCategory();
            searchTextArea.append(item_id + " " + item + " " + item_price + " " + category + "\n");
        }
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

    public void clear() {
        searchTextArea.setText("");
    }

    public static void main(String[] args) {
//        Control control = new Control();
//        SearchGUI searchGUI = new SearchGUI(control);
        SearchGUI searchGUI = new SearchGUI();
    }
}
