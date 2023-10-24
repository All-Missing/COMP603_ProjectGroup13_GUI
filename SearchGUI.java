package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
//import COMP603_ProjectGroup13_DB.RetrieveCashierDB;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SearchGUI {
    
    private List<Product> productListDB;
//    private RetrieveCashierDB retrieveDB;
    
    
    public SearchGUI() {
//        productListDB = retrieveDB.RetrieveProductList();
        
    }
    
    //Create researchFunction
    public JPanel addResearchFunction() {
        JPanel researchPanel = new JPanel(new GridLayout(0, 1));

        for (Product product : productListDB) {
            String item_id = product.getItem_id();
            String item = product.getItem();
            Double item_price = product.getItemPrice();
            String category = product.getCategory();

            String labelText = item_id + " " + item + " " + item_price + " " + category;
            JLabel label = new JLabel(labelText);
            researchPanel.add(label);
        }

        return researchPanel;
    }
    
    
    public static void main(String[] args) {
        
    }
}
