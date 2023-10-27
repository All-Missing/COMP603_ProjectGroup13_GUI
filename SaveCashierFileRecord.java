package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.SaleProcess;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SaveCashierFileRecord {

    private final HashMap<String, Double> cashier_Record;
    private SaveFileRecordGUI saveRecordGUI;
    private Control control;
    private CartGUI cartGUI;
    private DecimalFormat df = new DecimalFormat("#.00");

    public SaveCashierFileRecord() {
        this.control = new Control();
        this.cartGUI = new CartGUI(control);
//        this.saveFileRecordGUI = saveFileRecordGUI;
        this.saveRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.cashier_Record = saveRecordGUI.getCashier_Record_List();
        
    }
    
    public HashMap<String, Double> getCashier_Record() {
        return this.cashier_Record;
    }
    
    public boolean saveFileCheck(JPanel panel) {
        if (this.getCashier_Record().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Check file, confirm file saved, logging out.",
                    "Confirm file save", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    public JPanel saveFileRecordsButton() {
        JPanel saveFileRecordsButton = new JPanel(new BorderLayout());

        JButton saveFileButton = control.createButton("Save");
        saveFileButton.addActionListener((ActionEvent e) -> {
            this.saveFileRecords(cashier_Record, saveFileRecordsButton);
        });
        saveFileRecordsButton.add(saveFileButton, BorderLayout.SOUTH);

        return saveFileRecordsButton;
    }
    
    public void clearButton(JButton clearButton, JTextArea logArea) {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
            }
        });
    }
     
    public void saveFileRecords(HashMap<String, Double> cashier_Record_List, JPanel panel) {
        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter("./file_records/BillOrder_Records.txt", true));

            String line;
            //Test if cashier_record is empty
            if (cashier_Record_List.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Cashier record is empty!",
                        "File Empty", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Map.Entry<String, Double> entry : cashier_Record_List.entrySet()) {
                    String current_order_id = entry.getKey();
                    Double bill = entry.getValue();
                    System.out.println(current_order_id);

                    line = "OrderID: " + current_order_id + " Bill: " + bill;

                    bw.write(line);
                    bw.newLine();
                }

                JOptionPane.showMessageDialog(panel, "Data saved to file successfully.",
                        "Success save file", JOptionPane.INFORMATION_MESSAGE);
            }
            bw.close();

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
}
