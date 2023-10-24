package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SaveFileRecordGUI {

//    private DefaultListModel<String> cashier_Record;
    private Map<String, DefaultListModel<Product>> cashier_Record_List;
    private Map<String, Map<String, DefaultListModel<Product>>> refund_Record_List;
    private JTextArea CashierRecordTextArea;
    private Control control;
    private CartGUI cartGUI;

    public SaveFileRecordGUI(CartGUI cartGUI) {
        cashier_Record_List = new HashMap<>();
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
        this.getCashier_Record_List().put(cartOrderId, cartProductList);
        return this.getCashier_Record_List();
    }

    public Map<String, Map<String, DefaultListModel<Product>>> addRefundRecord(String refundOrderID, Map<String, DefaultListModel<Product>> cashier_Record_List) {
        this.getRefund_Record_List().put(refundOrderID, cashier_Record_List);
        return this.getRefund_Record_List();
    }

    public void updateCashierRecord() {
        String cartRecordOutput = this.cartRecordOutputString();
        this.CashierRecordTextArea.setText(cartRecordOutput);
    }

    public JPanel addCheckCartRecord() {
        JPanel cartRecordPanel = new JPanel(new BorderLayout());

        CashierRecordTextArea = new JTextArea();
        control.setFont(this.CashierRecordTextArea);
        this.CashierRecordTextArea.setEditable(true);

        JPanel refundPurchase = this.getRefundOrder();
                
        this.CashierRecordTextArea.setPreferredSize(new Dimension(400, 200));
        cartRecordPanel.add(new JScrollPane(CashierRecordTextArea), BorderLayout.CENTER);
        cartRecordPanel.add(refundPurchase, BorderLayout.SOUTH);

        this.updateCashierRecord();

        return cartRecordPanel;
    }

    public JPanel getRefundOrder() {
        JPanel refundOrderPanel = new JPanel(new BorderLayout());

//        JButton refundOrderButton = control.createButton("Refund");

//        refundOrderButton.addActionListener((ActionEvent e) -> {
            control.RefundOrder(CashierRecordTextArea, refundOrderPanel, refund_Record_List, cashier_Record_List);
            this.updateCashierRecord();

//                    JOptionPane.showMessageDialog(refundOrderPanel, "Please selected cart for to be refund.",
//                            "Refund Puchase", JOptionPane.ERROR_MESSAGE);
//                }
//            refundOrderPanel.add(refundOrderButton, BorderLayout.SOUTH);

//        });
        
        return refundOrderPanel;
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
