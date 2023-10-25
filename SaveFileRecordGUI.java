package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SaveFileRecordGUI {

    private Map<String, DefaultListModel<Product>> cashier_Record_List;
    private Map<String, Map<String, DefaultListModel<Product>>> refund_Record_List;
    private JTextArea CashierRecordTextArea;
    private Control control;
    private CartGUI cartGUI;

    public SaveFileRecordGUI(CartGUI cartGUI) {
        cashier_Record_List = new HashMap<>();
//        this.cashier_Record_List = getCashierRecord();
        refund_Record_List = new HashMap<>();
        CashierRecordTextArea = new JTextArea();
        this.control = new Control();
        this.cartGUI = cartGUI;
    }

    public Map<String, DefaultListModel<Product>> getCashier_Record_List() {
        return cashier_Record_List;
    }

    public Map<String, Map<String, DefaultListModel<Product>>> getRefund_Record_List() {
        return refund_Record_List;
    }

    public Map<String, DefaultListModel<Product>> addCashierRecord(String cartOrderId, DefaultListModel<Product> cartProductList) {
        Set<String> processedCarts = new HashSet<>();
        if (!processedCarts.contains(cartOrderId)) {
            double totalCost = control.calculateTotalCost(cartProductList);
            CashierRecordTextArea.append("Cart ID: " + cartOrderId + "\t Total Cost: $" + totalCost + "\n");
            processedCarts.add(cartOrderId);
        }

        this.getCashier_Record_List().put(cartOrderId, cartProductList);
        return this.getCashier_Record_List();
    }

    public Map<String, Map<String, DefaultListModel<Product>>> addRefundRecord(String refundOrderID, Map<String, DefaultListModel<Product>> cashier_Record_List) {
        this.getRefund_Record_List().put(refundOrderID, cashier_Record_List);
        return this.getRefund_Record_List();
    }

    public JPanel addCheckCartRecord() {
        JPanel cartRecordPanel = new JPanel(new BorderLayout());

        CashierRecordTextArea = new JTextArea();
        control.setFont(this.CashierRecordTextArea);
        this.CashierRecordTextArea.setEditable(true);

//        JPanel refundPurchase = this.getRefundOrder();
                
        this.CashierRecordTextArea.setPreferredSize(new Dimension(400, 200));
        cartRecordPanel.add(new JScrollPane(CashierRecordTextArea), BorderLayout.CENTER);
//        cartRecordPanel.add(refundPurchase, BorderLayout.SOUTH);


        return cartRecordPanel;
    }

    public JPanel getRefundOrder() {
        JPanel refundOrderPanel = new JPanel(new BorderLayout());

//        JButton refundOrderButton = control.createButton("Refund");

//        refundOrderButton.addActionListener((ActionEvent e) -> {
            control.RefundOrder(CashierRecordTextArea, refundOrderPanel, refund_Record_List, cashier_Record_List);

//                    JOptionPane.showMessageDialog(refundOrderPanel, "Please selected cart for to be refund.",
//                            "Refund Puchase", JOptionPane.ERROR_MESSAGE);
//                }
//            refundOrderPanel.add(refundOrderButton, BorderLayout.SOUTH);

//        });
        
        return refundOrderPanel;
    }
    
    public JPanel saveFileRecordsButton(Map<String, DefaultListModel<Product>> record) {
        JPanel saveFileRecordsButton = new JPanel(new BorderLayout());

        JButton saveFileButton = control.createButton("Save");
        saveFileButton.addActionListener((ActionEvent e) -> {
            this.saveFileRecords(record, saveFileRecordsButton);
        });
        saveFileRecordsButton.add(saveFileButton, BorderLayout.SOUTH);

        return saveFileRecordsButton;
    }

    private void saveFileRecords(Map<String, DefaultListModel<Product>> cashier_Record_List, JPanel panel) {
        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter("./file_records/Cart_Records.txt", true));


            //Test if cashier_record is empty
            if (cashier_Record_List.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Cashier_record is empty!",
                        "File Empty", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Map.Entry<String, DefaultListModel<Product>> entry : cashier_Record_List.entrySet()) {
                    String current_order_id = entry.getKey();
                    DefaultListModel<Product> current_order = entry.getValue();

                    System.out.println(current_order);
                    if (current_order != null && current_order.size() > 0) {
                        for (int i = 0; i < current_order.size(); i++) {
                            Product product = current_order.getElementAt(i);
                            String line = "OrderID: " + current_order_id + " ID: " + product.getItem_id()
                                    + " Name: " + product.getItem() + " Price: " + product.getItemPrice();

                            bw.write(line);
                            bw.newLine();
                        }
                    }
                }
                bw.close();
                JOptionPane.showMessageDialog(panel, "Data saved to file successfully.",
                        "Success save file", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
            JOptionPane.showMessageDialog(panel, "File not found: " + ex.getMessage(),
                    "Error save file", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            System.out.println("Error when saving data: " + ex.getMessage());
            JOptionPane.showMessageDialog(panel, "Error when saving data: " + ex.getMessage(),
                    "Error save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String cartRecordOutputString() {
        StringBuilder content = new StringBuilder();

        for (Map.Entry<String, DefaultListModel<Product>> entry : this.getCashier_Record_List().entrySet()) {
            String cartId = entry.getKey();
            DefaultListModel<Product> productLists = entry.getValue();

            double totalCost = control.calculateTotalCost(productLists);

            content.append("Cart ID: ").append(cartId).append("\t Total Cost: $").append(totalCost).append("\n");
        }

        return content.toString();
    }

}
