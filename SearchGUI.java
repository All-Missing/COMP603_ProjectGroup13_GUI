package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import COMP603_ProjectGroup13_DB.CashierDBManager;
import COMP603_ProjectGroup13_DB.RetrieveCashierDB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    private List<Product> productListDB;
    private RetrieveCashierDB retrieveDB;
//    private JPanel searchPanel;
    private JTextArea searchTextArea;
    private JTextField searchTextField;
    private JPanel searchPanel;
    private JLabel searchLabel;
    private JButton searchItemButton;
    private JButton searchListButton;
    private JButton clearButton;
    private JButton returnButton;
    private Control control;
    
        
    public SearchGUI(Control control) {
        
        dbManager = new CashierDBManager();
        conn = dbManager.getCashierDBConnection();        
        this.retrieveDB = new RetrieveCashierDB();
        this.productListDB = retrieveDB.RetrieveProductList();
        this.control = control;
        initComponents();
        initComponents();
        initActionPerforms();
    }
    
//    public JPanel CreateSearchPanel() {
//        JPanel getSearchPanel = new JPanel();
//        JPanel getComponent = this.initComponents();
//        getSearchPanel.add(getComponent, BorderLayout.CENTER);        
//
//        return getSearchPanel;
//    }
    
    public JPanel getSearchButtonPanel() {
        return this.searchPanel;
    }
    
//    public JPanel initComponents() {
//        JPanel getComponent = new JPanel();
//        //Why I cant call control object to invoke createButton
//        searchListButton = new JButton("Search List");
//        searchItemButton = control.createButton("Search Item");
//        clearButton = control.createButton("Clear");
//        returnButton = control.createButton("Return");
////        this.searchItemButton = new JButton("Search Item");
////        this.clearButton = new JButton("Clear");
////        this.returnButton = new JButton("Return");
//        this.searchLabel = new JLabel("Search:");
//        this.searchTextField = new JTextField(20);
//        searchTextField.setText("Enter itemID or item name...");
//        this.searchTextArea = new JTextArea(ROW_AREA, COLUMN_AREA);        
//        
//        initPanels();
//        initActionPerforms();
//        
//        return getComponent;
//    }
    
    public void initComponents() {        
        //Why I cant call control object to invoke createButton
        searchListButton = control.createButton("Search List");
        searchItemButton = control.createButton("Search Item");
        clearButton = control.createButton("Clear");
        returnButton = control.createButton("Return");

//        this.searchListButton = new JButton("Search List");
//        this.searchItemButton = new JButton("Search Item");
//        this.clearButton = new JButton("Clear");
//        this.returnButton = new JButton("Return");
        this.searchLabel = new JLabel("Search:");
        this.searchTextField = new JTextField(20);
        searchTextField.setText("Enter itemID or item name...");
        this.searchTextArea = new JTextArea(ROW_AREA, COLUMN_AREA);         
    }
    
//    public JPanel initComponents() {
//        JPanel getComponent = new JPanel();
//        //Why I cant call control object to invoke createButton
//        searchListButton = new JButton("Search List");
//        searchItemButton = control.createButton("Search Item");
//        clearButton = control.createButton("Clear");
//        returnButton = control.createButton("Return");
////        this.searchItemButton = new JButton("Search Item");
////        this.clearButton = new JButton("Clear");
////        this.returnButton = new JButton("Return");
//        this.searchLabel = new JLabel("Search:");
//        this.searchTextField = new JTextField(20);
//        searchTextField.setText("Enter itemID or item name...");
//        this.searchTextArea = new JTextArea(ROW_AREA, COLUMN_AREA);        
//        
//        initPanels();
//        initActionPerforms();
//        
//        return getComponent;
//    }
    
    public void initPanels() {                
        
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
        searchPanel.add(southPanel, BorderLayout.SOUTH);                
        
        this.add(searchPanel, BorderLayout.CENTER);
        this.setTitle("Search Products");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);        
    }
    
    public void initActionPerforms() {
        
        ActionListener buttonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (e.getSource() == searchListButton) {
                    searchList();
                }   else if (e.getSource() == searchItemButton) {
                    
                }   else if (e.getSource() == clearButton) {
                     clear(); 
                }   else if (e.getSource() == returnButton) {
                    // Handle the "Return" button action
                }   
            }
        };
        searchListButton.addActionListener(buttonAction);
        searchItemButton.addActionListener(buttonAction);
        clearButton.addActionListener(buttonAction);
        returnButton.addActionListener(buttonAction);
    }
    
    //Create researchFunction
    public void searchList() {        

        for (Product product : productListDB) {
            String item_id = product.getItem_id();
            String item = product.getItem();
            Double item_price = product.getItemPrice();
            String category = product.getCategory();
//            System.out.println(item_id+" "+item+" "+item_price+" "+category);//Testing
            searchTextArea.append(item_id+" "+item+" "+item_price+" "+category+"\n");
        }
    }
    
    
    
    public void clear() {
        searchTextArea.setText("");
    } 
    
    
    public static void main(String[] args) {
        Control control = new Control();
        SearchGUI searchGUI = new SearchGUI(control);
        
    }
}    
