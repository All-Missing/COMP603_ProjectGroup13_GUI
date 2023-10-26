package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import COMP603_ProjectGroup13.SaleProcess;
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

    private HashMap<String, Double> cashier_Record_List;
    private HashMap<String, Double> refund_Record_List;
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

    public HashMap<String, Double> getCashier_Record_List() {
        return cashier_Record_List;
    }

    public HashMap<String, Double> getRefund_Record_List() {
        return refund_Record_List;
    }

    public void addCashierRecord(int cartOrderId, Double bill) {
        String str_order_id = String.valueOf(cartOrderId);
        cashier_Record_List.put(str_order_id, bill);
        CashierRecordTextArea.append("Cart ID: " + str_order_id + "\t Total Cost: $" + bill + "\n");
    }

    public HashMap<String, Double> addRefundRecord(HashMap<String, Double> cashier_Record_List) {
        for (Map.Entry<String, Double> getList : cashier_Record_List.entrySet()) {
            String orderID = String.valueOf(getList.getKey());
            double bill = getList.getValue();
            this.getRefund_Record_List().put(orderID, bill);
        }
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
//            control.RefundOrder(CashierRecordTextArea, refundOrderPanel, refund_Record_List, cashier_Record_List);
//                    JOptionPane.showMessageDialog(refundOrderPanel, "Please selected cart for to be refund.",
//                            "Refund Puchase", JOptionPane.ERROR_MESSAGE);
//                }
//            refundOrderPanel.add(refundOrderButton, BorderLayout.SOUTH);
//        });
        return refundOrderPanel;
    }

    
}
