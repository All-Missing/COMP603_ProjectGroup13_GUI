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
        this.saveRecordGUI = new SaveFileRecordGUI(cartGUI);
        this.cashier_Record = saveRecordGUI.getCashier_Record_List();

    }

    public void saveFileRecord(HashMap<String, Double> cashier_records, String shift_id, String staff_id, String staff_name, JPanel panel) {

        //Condition 
        String aShiftID = shift_id;
        String aStaffID = staff_id;
        String aStaffName = staff_name;

        saveFileRecords(cashier_records, aShiftID, aStaffID, aStaffName, panel);
    }

    private void saveFileRecords(HashMap<String, Double> cashier_records, 
            String shift_id, String staff_id, String staff_name, JPanel panel) {
        double total_balance = 0;
        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter("./file_records/BillOrder_records.txt", true));

            // First line of text file indicates staffID and staff name respectively
            String line = "---ShiftID: " + shift_id + " " + "staffID: " + staff_id + " staffName: " + staff_name;
            bw.write(line);
            bw.newLine();

            //Test if cashier_record is empty
            if (cashier_records.isEmpty()) {
                System.out.println("Cashier_record is empty!");
            } else {
                for (Map.Entry<String, Double> entry : cashier_records.entrySet()) {
                    String current_order_id = entry.getKey();
                    Double current_bill = entry.getValue();
                    total_balance += current_bill;

                    line = "OrderID: " + current_order_id + " Bill: $ " + df.format(current_bill);
                    bw.append(line);
                    bw.newLine();
                }
                line = "\t\t---Total balance earned per shift: $ " + df.format(total_balance);
                bw.append(line);
                bw.newLine();
                cashier_records.clear();
                bw.close();
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
}
