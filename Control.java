package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.Product;
import COMP603_ProjectGroup13.CheckOrderID;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class Control {

    private Product product;
    private int cartOrderID;
    private CardLayout mainLayout;
    private JPanel pageControlPanel;
    private static int NEXT_ORDER_ID = 0;
    private double totalCost = 0; //calculate the cost of item in cart
    private Font font;
    private CheckOrderIDGUI checkOrderIDGUI;
    private DecimalFormat df = new DecimalFormat("#0.00");

    public Control() {
        checkOrderIDGUI = new CheckOrderIDGUI();
        Control.NEXT_ORDER_ID = checkOrderIDGUI.checkOrderID();
        this.cartOrderID = Control.NEXT_ORDER_ID;
        this.pageControlPanel = new JPanel();
        this.mainLayout = new CardLayout();
        this.pageControlPanel.setLayout(mainLayout);
    }

    //close frame
    public void closeFrame(JFrame frame) {
        frame.dispose();
    }
    
    public void setFont(JTextArea textArea) {
        font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        textArea.setFont(font);
    }
    
    public int getCartOrderID() {
        return this.cartOrderID;
    }
    
    public void incrementedCartOrderId() {
        this.cartOrderID = ++Control.NEXT_ORDER_ID;
    }

    public JPanel getPageControlPanel() {
        return this.pageControlPanel;
    }

    public void addPagePanel(JPanel panel, String panelName) {
        this.pageControlPanel.add(panel, panelName);
    }

    //show PageControlPanel base on cardName
    public void showCard(String cardName) {
        mainLayout.show(this.getPageControlPanel(), cardName);
    }

    //calculate the total cost of item in cart_product_list
    public double calculateTotalCost(DefaultListModel<Product> cartProductList) {
        this.totalCost = 0;
        for (int i = 0; i < cartProductList.size(); i++) {
            product = cartProductList.getElementAt(i);
            this.totalCost += product.getItemPrice();
        }
        return Double.parseDouble(df.format(this.totalCost));
    }

    public JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }

    public void clearButton(JButton clearButton, JTextArea logArea) {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
            }
        });
    }

    public JPanel returnButton() {

        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Return to Categories");
        returnButton.addActionListener((ActionEvent e) -> {
            this.showCard("Categories");
        });
        returnPanel.add(returnButton);

        return returnPanel;
    }

    public String extractLineValue(String line, int lineAtIndex) {
        String[] lineParts = line.split(" ");
        return lineParts[lineAtIndex];
    }

    public String extractLineDetails(int getLine, JTextArea textArea) throws BadLocationException {
        int lineStartOffset = Utilities.getRowStart(textArea, getLine);
        int lineEndOffset = Utilities.getRowEnd(textArea, getLine);
        return textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);
    }
    
    public void removeElementIndex(JTextArea textArea, int indexAdjust, JPanel panel, DefaultListModel<Product> list) {
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int getLine = textArea.viewToModel2D(e.getPoint());
                    try {
                        int getIndex = textArea.getLineOfOffset(getLine);

                        int selectIndex = getIndex - indexAdjust;

                        list.removeElementAt(selectIndex);

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select an item to delete.",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void removeAllElement(DefaultListModel<Product> list, JPanel panel) {
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                    "Cart is already empty.",
                    "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int response = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to remove all products from the cart?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                list.removeAllElements();
                JOptionPane.showMessageDialog(panel,
                        "All products has been removed from the cart.",
                        "Cart Cleared", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
