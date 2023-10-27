package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.Utilities;

public class SaveFileRecordGUI {

    private HashMap<String, Double> cashier_Record_List;
    private JTextArea CashierRecordTextArea;
    private DecimalFormat df = new DecimalFormat("#.00");
    private Control control;
    private CartGUI cartGUI;
    private JPanel cartRecordPanel;

    public SaveFileRecordGUI(CartGUI cartGUI) {
        cashier_Record_List = new HashMap<>();
        CashierRecordTextArea = new JTextArea();
        this.control = new Control();
        this.cartGUI = cartGUI;
    }

    public HashMap<String, Double> getCashier_Record_List() {
        return cashier_Record_List;
    }

    public void setCashier_Record_List(HashMap<String, Double> cashier_Record_List) {
        this.cashier_Record_List = cashier_Record_List;
    }

    public void addCashierRecord(int cartOrderId, Double bill) {
        String str_order_id = String.valueOf(cartOrderId);
        cashier_Record_List.put(str_order_id, bill);
        this.updateCashierRecord();
    }

    public void updateCashierRecord() {
        String cashierOutput = this.cashierOutputString();
        this.CashierRecordTextArea.setText(cashierOutput);
    }

    public String cashierOutputString() {
        StringBuilder cashierOutput = new StringBuilder();

        for (Map.Entry<String, Double> entry : cashier_Record_List.entrySet()) {
            String current_order_id = entry.getKey();
            Double bill = entry.getValue();

            cashierOutput.append("Cart ID: " + current_order_id + " Bill: $ " + df.format(bill) + "\n");
        }

        return cashierOutput.toString();
    }

    public JPanel addCheckCartRecord() {
        cartRecordPanel = new JPanel(new GridLayout(2, 0));

        CashierRecordTextArea = new JTextArea();
        control.setFont(this.CashierRecordTextArea);
        this.CashierRecordTextArea.setEditable(true);

//        JButton refundPurchase = this.getRefundOrder();
//        JButton refundButton = control.createButton("Refund");
//        refundButton.addActionListener(e -> this.getRefundOrder());
        this.CashierRecordTextArea.setPreferredSize(new Dimension(400, 200));
        cartRecordPanel.add(new JScrollPane(CashierRecordTextArea), BorderLayout.CENTER);
//        cartRecordPanel.add(refundButton, BorderLayout.SOUTH);

        CashierRecordTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int getLine = CashierRecordTextArea.viewToModel2D(e.getPoint());
                    try {
                        int selectIndex = CashierRecordTextArea.getLineOfOffset(getLine);

                        int lineStartOffset = Utilities.getRowStart(CashierRecordTextArea, getLine);
                        int lineEndOffset = Utilities.getRowEnd(CashierRecordTextArea, getLine);

                        //get line start and end point
                        String line = CashierRecordTextArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);

                        for (Map.Entry<String, Double> entry : cashier_Record_List.entrySet()) {
                            String current_order_id = entry.getKey();
                            Double bill = entry.getValue();

                            //return value from string line
                            String[] lineParts = line.split(" ");
                            if (line.startsWith("Cart ID: ")) {
                                String orderIDArea = lineParts[2];
                                double billArea = df.parse(lineParts[5]).doubleValue();

                                //check if get line orderIDArea is equal to id in cashier_Record_List
                                //if yes run refund method
                                if (orderIDArea.equals(current_order_id)) {
                                    int option = JOptionPane.showConfirmDialog(cartRecordPanel, "Do you wish to refund this order",
                                            "Confirm  refund order", JOptionPane.YES_NO_OPTION);

                                    //Check if confirm yes
                                    if (option == JOptionPane.YES_OPTION) {
                                        String inputRefundAmount = JOptionPane.showInputDialog(cartRecordPanel,
                                                "Bill: $" + billArea + "\nEnter Refund Amount:");

                                        try {
                                            
                                            //parse user refund amoung input as a double value
                                            double refundAmount = Double.parseDouble(inputRefundAmount);

                                            //check if refund is less than bill amoung
                                            if (refundAmount <= billArea) {
                                                double current_bill = billArea - refundAmount;

                                                //inform user of successfull refund. Refund amount is less than bill
                                                JOptionPane.showMessageDialog(cartRecordPanel, "Refund successful! \nCurrent Bill Amount: " + df.format(current_bill),
                                                        "Refund Success", JOptionPane.INFORMATION_MESSAGE);

                                                //replace cashier record in this key from old value to new value
                                                cashier_Record_List.replace(current_order_id, billArea, current_bill);
                                                updateCashierRecord();
                                            } else {
                                                
                                                //inform user of refund failure. refund amoung request is more than bill
                                                JOptionPane.showMessageDialog(cartRecordPanel,
                                                        "Refund fail! \nRefund request amount is more than refundable amount.",
                                                        "Refund Failed", JOptionPane.ERROR_MESSAGE);
                                                break;
                                            }

                                        //check if input is numeric value
                                        } catch (NumberFormatException numex) {
                                            JOptionPane.showMessageDialog(cartRecordPanel, "Invalid input. Please enter a valid numeric amount.",
                                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else {
                                    //
                                    JOptionPane.showMessageDialog(cartRecordPanel,
                                            "Data check error. Please check record data return correct value.",
                                            "Check Record", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else {
                    //return when user click count is more than one
                    JOptionPane.showMessageDialog(cartRecordPanel, "Please select an item to refund.",
                            "Refund Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return cartRecordPanel;
    }

//    public JPanel getRefundOrder(JButton refundButton) {
    public void getRefundOrder() {
        JPanel refundOrderPanel = new JPanel(new BorderLayout());

        JButton refundButton = control.createButton("Refund");
        refundButton.addActionListener((ActionEvent e) -> {

            // Inside your SearchGUI class constructor
//        return refundOrderPanel;
        });
    }

}
