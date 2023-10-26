package COMP603_ProjectGroup13_GUI;

import COMP603_ProjectGroup13.SaleProcess;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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

public class SaveCashierFileRecord {

    private final HashMap<String, Double> cashier_Record_List;
    private SaleProcess saleProcess;
    private Control control;
    private DecimalFormat df = new DecimalFormat("#.00");

    public SaveCashierFileRecord() {
        this.control = new Control();
        this.saleProcess = new SaleProcess();
        this.cashier_Record_List = saleProcess.getCashierRecord();
    }

    public JPanel saveFileRecordsButton(HashMap<String, Double> cashier_Record_List) {
        JPanel saveFileRecordsButton = new JPanel(new BorderLayout());

        JButton saveFileButton = control.createButton("Save");
        saveFileButton.addActionListener((ActionEvent e) -> {
            this.saveFileRecords(cashier_Record_List, saveFileRecordsButton);
        });
        saveFileRecordsButton.add(saveFileButton, BorderLayout.SOUTH);

        return saveFileRecordsButton;
    }

    private void saveFileRecords(HashMap<String, Double> cashier_Record_List, JPanel panel) {
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
