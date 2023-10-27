package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PaymentGUI {

    private SaveFileRecordGUI saveFileRecordGUI;
    private SaveCashierFileRecord saveCashierFileRecord;
    private Control control;
    private CartGUI cartGUI;
    private JPanel paymentPanel;

    public PaymentGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.cartGUI = cartGUI;
        this.saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.saveCashierFileRecord = new SaveCashierFileRecord();
    }

//    public JPanel createPaymentPanel(int shift_id, String username, String password) {
    public JPanel createPaymentPanel() {
        paymentPanel = new JPanel(new BorderLayout());

        JPanel paymentButtonsPanel = this.createPaymentButtonsPanel();
        JPanel checkPreviousCartPanel = saveFileRecordGUI.addCheckCartRecord();

        paymentPanel.add(checkPreviousCartPanel, BorderLayout.CENTER);
        paymentPanel.add(paymentButtonsPanel, BorderLayout.SOUTH);

        return paymentPanel;
    }

//    public JPanel createPaymentButtonsPanel(int shift_id, String username, String password) {
    public JPanel createPaymentButtonsPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        JButton cardButton = control.createButton("Card");
        cardButton.addActionListener(e -> handleCardPayment());

        JButton cashButton = control.createButton("Cash");
        cashButton.addActionListener(e -> handleCardPayment());

        JButton cancelCartButton = control.createButton("Cancel Cart");
        cancelCartButton.addActionListener(e -> cancelCart());
        
//        JButton saveButton = control.createButton("Save");
//        saveButton.addActionListener(e -> saveRecords(shift_id, username, password));

        buttonPanel.add(cardButton, BorderLayout.WEST);
        buttonPanel.add(cashButton, BorderLayout.WEST);
        buttonPanel.add(cancelCartButton, BorderLayout.WEST);
//        buttonPanel.add(saveButton, BorderLayout.WEST);

        return buttonPanel;
    }

    public void handleCardPayment() {
        JPanel cardPayment = new JPanel(new BorderLayout());
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());
        int cartOrderId = control.getCartOrderID();

        String inputAmount = JOptionPane.showInputDialog(cardPayment,
                "Total Price: $" + totalCost + "\nPayment Method: Eftpos" + "\nEnter Payment Amount:");

        if (inputAmount != null) {
            try {
                double paymentAmount = Double.parseDouble(inputAmount);

                if (paymentAmount >= totalCost) {
                    JOptionPane.showMessageDialog(cardPayment, "Payment successful! Thank you for your purchase.",
                            "Payment Success", JOptionPane.INFORMATION_MESSAGE);

                    saveFileRecordGUI.addCashierRecord(cartOrderId, totalCost);
                    control.incrementedCartOrderId();

                    cartGUI.getCartProductList().removeAllElements();
                    cartGUI.updateCartProductList();
                } else {
                    int option = JOptionPane.showConfirmDialog(cardPayment,
                            "Insufficient payment. Do you want to try again?",
                            "Payment Failed", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(cardPayment, "Payment declined. Items remain in the cart.",
                                "Payment Declined", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(cardPayment, "Invalid input. Please enter a valid numeric amount.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        // User canceled the input dialog
        JOptionPane.showMessageDialog(cardPayment, "Payment canceled. Items remain in the cart.",
                "Payment Canceled", JOptionPane.WARNING_MESSAGE);
    }

    public void handleCashPayment() {
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());

    }

//    public void saveRecords(int shift_id, String username, String password) {
//            saveCashierFileRecord.saveFileRecord(saveFileRecordGUI.getCashier_Record_List(), 
//                    String.valueOf(shift_id), password, username, paymentPanel);
//    }

    public void cancelCart() {
        JPanel cancelCart = new JPanel(new BorderLayout());

        int cancelCartPrompt = JOptionPane.showConfirmDialog(cancelCart,
                "Are you sure you want to cancel this purchase?",
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);

        if (cancelCartPrompt == JOptionPane.YES_OPTION) {
            cartGUI.getCartProductList().clear();
            cartGUI.updateCartProductList();
        }
    }

}
