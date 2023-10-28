package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PaymentGUI {

    private Control control;
    private CartGUI cartGUI;
    private SaveFileRecordGUI saveFileRecordGUI;
    private SaveCashierFileRecord saveCashierFileRecord;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private JPanel paymentPanel;

    public PaymentGUI(Control control, CartGUI cartGUI) {
        this.control = control;
        this.cartGUI = cartGUI;
        this.saveFileRecordGUI = new SaveFileRecordGUI();
        this.saveCashierFileRecord = new SaveCashierFileRecord();
    }

    public JPanel createPaymentPanel() {
        paymentPanel = new JPanel(new BorderLayout());

        JPanel paymentButtonsPanel = this.createPaymentButtonsPanel();
        JPanel checkPreviousCartPanel = saveFileRecordGUI.addCheckCartRecord();

        paymentPanel.add(checkPreviousCartPanel, BorderLayout.CENTER);
        paymentPanel.add(paymentButtonsPanel, BorderLayout.SOUTH);

        return paymentPanel;
    }

    public JPanel createPaymentButtonsPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JButton cardButton = control.createButton("Card");
        cardButton.addActionListener(e -> paymentProcess("Eftpos"));

        JButton cashButton = control.createButton("Cash");
        cashButton.addActionListener(e -> paymentProcess("Cash"));

        JButton cancelCartButton = control.createButton("Cancel Cart");
        cancelCartButton.addActionListener(e -> cancelCart());

        buttonPanel.add(cardButton, BorderLayout.WEST);
        buttonPanel.add(cashButton, BorderLayout.EAST);
        buttonPanel.add(cancelCartButton, BorderLayout.CENTER);

        return buttonPanel;
    }

    public void paymentProcess(String paymentType) {
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());
        int cartOrderId = control.getCartOrderID();

        //take user input
        String inputAmount = JOptionPane.showInputDialog(paymentPanel,
                "Total Price: $" + totalCost + "\nPayment Method: " + paymentType + "\nEnter Payment Amount:");

        if (inputAmount != null) {
            try {
                double paymentAmount = Double.parseDouble(inputAmount);

                //if cash return change. if card payment return 0 due to no change
                double change = paymentAmount - totalCost;

                switch (paymentType) {
                    case "Eftpos":
                        this.checkPayment(paymentAmount, cartOrderId, totalCost, 0.00);
                        break;
                    case "Cash":
                        this.checkPayment(paymentAmount, cartOrderId, totalCost, change);
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                //input is not numeric
                JOptionPane.showMessageDialog(paymentPanel, "Invalid input. Please enter a valid numeric amount.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // User canceled the input dialog
            JOptionPane.showMessageDialog(paymentPanel, "Payment cancelled. Items remain in the cart.",
                    "Payment cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //check if payment is success or fail
    public void checkPayment(double paymentAmount, int cartOrderId, double totalCost, double change) {

        if (paymentAmount >= totalCost) {
            //return success payment prompt
            JOptionPane.showMessageDialog(paymentPanel, "Payment successful! Thank you for your purchase.\n"
                    + "Change: " + df.format(change),
                    "Payment Success", JOptionPane.INFORMATION_MESSAGE);

            saveFileRecordGUI.addCashierRecord(cartOrderId, totalCost);//add cart record to cashier record
            control.incrementedCartOrderId();//increament id after each cart payment success

            cartGUI.getCartProductList().removeAllElements();
            cartGUI.updateCartProductList();

        } else {
            int option = JOptionPane.showConfirmDialog(paymentPanel,
                    "Insufficient payment. Do you want to try again?",
                    "Payment Failed", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(paymentPanel, "Payment declined. Items remain in the cart.",
                        "Payment Declined", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void cancelCart() {
        //prompt user cart is empty
        if (cartGUI.getCartProductList().isEmpty()) {
            JOptionPane.showMessageDialog(paymentPanel,
                    "Cart is empty. No item to cancelled",
                    "Cart Empty", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int cancelCartPrompt = JOptionPane.showConfirmDialog(paymentPanel,
                    "Are you sure you want to cancel this purchase?",
                    "Confirm Cancel", JOptionPane.YES_NO_OPTION);

            if (cancelCartPrompt == JOptionPane.YES_OPTION) {
                cartGUI.getCartProductList().clear();
                cartGUI.updateCartProductList();
                //click no mean cancel to remove item
            } else {
                JOptionPane.showMessageDialog(paymentPanel,
                        "Cancel to remove item from cart. Items remain in the cart.",
                        "Confirm Cancel", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void saveFile(int shift_id, String username, String password) {
        //file empty, don't require save function
        if (saveFileRecordGUI.getCashier_Record_List().isEmpty()) {
            JOptionPane.showMessageDialog(paymentPanel, "File is Empty. No records to save.",
                    "Save & Exit", JOptionPane.INFORMATION_MESSAGE);
            //save if there is record found in cashier_record_list
        } else {
            saveCashierFileRecord.saveFileRecord(saveFileRecordGUI.getCashier_Record_List(),
                    String.valueOf(shift_id), password, username, paymentPanel);

            JOptionPane.showMessageDialog(paymentPanel, "File is save. Exiting...",
                    "Save & Exit", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
