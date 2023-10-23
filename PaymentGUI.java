package COMP603_ProjectGroup13_GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PaymentGUI {

    private SaveFileRecordGUI saveFileRecordGUI;
    private Control control;
    private CartGUI cartGUI;

    public PaymentGUI(Control control, CartGUI cartGUI) {
        this.saveFileRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.control = control;
        this.cartGUI = cartGUI;
    }

    public JPanel createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new BorderLayout());

        // test product
        JPanel paymentButtonsPanel = this.createPaymentButtonsPanel();
        JPanel checkPreviousCartPanel = saveFileRecordGUI.addCheckCartRecord();

        JPanel paymentContainerPanel = new JPanel(new BorderLayout());
        paymentContainerPanel.add(paymentPanel);
        paymentContainerPanel.add(paymentButtonsPanel, BorderLayout.CENTER);
        paymentContainerPanel.add(checkPreviousCartPanel, BorderLayout.SOUTH);

        return paymentContainerPanel;
    }

    public JPanel createPaymentButtonsPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        JButton cardButton = control.createButton("Card");
        cardButton.addActionListener(e -> handleCardPayment());

        JButton cashButton = control.createButton("Cash");
        cashButton.addActionListener(e -> handleCardPayment());

        //test
        JButton purchaseButton = control.createButton("product");
        purchaseButton.addActionListener(e -> cartGUI.addToCart("SA001", "Foos", 2.3, "Product"));

        JButton cancelCartButton = control.createButton("Cancel Cart");
        cancelCartButton.addActionListener(e -> cancelCart());

        JButton refundButton = control.createButton("Refund");
        refundButton.addActionListener(e -> handleRefund());

        buttonPanel.add(cardButton, BorderLayout.WEST);
        buttonPanel.add(cashButton, BorderLayout.WEST);
        buttonPanel.add(refundButton);
        buttonPanel.add(cancelCartButton);

        //test
        buttonPanel.add(purchaseButton);

        return buttonPanel;
    }

    public void handleCardPayment() {
        JPanel cardPayment = new JPanel(new BorderLayout());
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());
        String cartOrderId = Integer.toString(control.getCartOrderID());

        String inputAmount = JOptionPane.showInputDialog(cardPayment,
                "Total Price: $" + totalCost + "\nPayment Method: Eftpos" + "\nEnter Payment Amount:");

        try {
            double paymentAmount = Double.parseDouble(inputAmount);

            if (paymentAmount >= totalCost) {
                JOptionPane.showMessageDialog(cardPayment, "Payment successful! Thank you for your purchase.",
                        "Payment Success", JOptionPane.INFORMATION_MESSAGE);

                saveFileRecordGUI.addCashierRecord(cartOrderId, cartGUI.getCartProductList());
                saveFileRecordGUI.updateCashierRecord();
                cartGUI.getCartProductList().removeAllElements();
                control.incrementCardOrderId(Integer.valueOf(cartOrderId));
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

    public void handleCashPayment() {
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());

    }

    public void handleRefund() {
        double totalCost = control.calculateTotalCost(cartGUI.getCartProductList());

    }

    public void cancelCart() {
        JPanel cancelCart = new JPanel(new BorderLayout());

        int cancelCartPrompt = JOptionPane.showConfirmDialog(cancelCart,
                "Are you sure you want to cancel this purchase?",
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);

        if (cancelCartPrompt == JOptionPane.YES_OPTION) {
            cartGUI.getCartProductList().clear();
            cartGUI.updateCartProductList();
//            updateCartPanel();
        }
    }

}
