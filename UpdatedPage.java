import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdatedPage extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfId, tfName, tfContact, tfDate;
    private JComboBox<String> cbType, cbQuantity;
    private JTextArea tfAddress;

    public UpdatedPage(String adminName) {
        setTitle("Gas Booking System - Update Booking");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Header =====
        JLabel headerLabel = new JLabel("Update Booking", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 102, 204));
        headerLabel.setPreferredSize(new Dimension(900, 50));
        add(headerLabel, BorderLayout.NORTH);

        // ===== Table Panel =====
        String[] columns = {"b_id", "Name", "Contact", "Agency", "Address", "Booking Date", "Delivery Date", "Gas Type", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(880, 200));
        add(scrollPane, BorderLayout.CENTER);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 245, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Booking ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Booking ID:"), gbc);
        tfId = new JTextField(20);
        tfId.setFont(fieldFont);
        tfId.setEditable(false); // read-only
        gbc.gridx = 1;
        formPanel.add(tfId, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Booking Name:"), gbc);
        tfName = new JTextField(20);
        tfName.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(tfName, gbc);

        // Contact
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        tfContact = new JTextField(20);
        tfContact.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(tfContact, gbc);

        // Gas Type
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Gas Type:"), gbc);
        cbType = new JComboBox<>(new String[]{"Domestic", "Commercial"});
        cbType.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(cbType, gbc);

        // Gas Quantity
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Gas Quantity:"), gbc);
        cbQuantity = new JComboBox<>(new String[]{"1 Cylinder", "2 Cylinders", "3 Cylinders", "4 Cylinders"});
        cbQuantity.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(cbQuantity, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Address:"), gbc);
        tfAddress = new JTextArea(3, 20);
        tfAddress.setLineWrap(true);
        tfAddress.setWrapStyleWord(true);
        tfAddress.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(tfAddress), gbc);

        // Booking Date
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Booking Date (DD-MM-YYYY):"), gbc);
        tfDate = new JTextField(20);
        tfDate.setFont(fieldFont);
        gbc.gridx = 1;
        formPanel.add(tfDate, gbc);

        // ===== Buttons =====
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 245, 230));

        JButton updateBtn = new JButton("Update Booking");
        updateBtn.setBackground(new Color(255, 140, 0));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 16));
        updateBtn.setPreferredSize(new Dimension(180, 40));

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setPreferredSize(new Dimension(120, 40));

        btnPanel.add(updateBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // ===== Load Bookings =====
        loadBookings();

        // ===== Table Row Selection =====
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfId.setText(table.getValueAt(row, 0).toString());
                tfName.setText(table.getValueAt(row, 1).toString());
                tfContact.setText(table.getValueAt(row, 2).toString());
                tfAddress.setText(table.getValueAt(row, 4).toString());
                tfDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(table.getValueAt(row, 5)));
                cbType.setSelectedItem(table.getValueAt(row, 7).toString());
                cbQuantity.setSelectedItem(table.getValueAt(row, 8).toString());
            }
        });

        // ===== Button Actions =====
        updateBtn.addActionListener(e -> updateBooking());
        backBtn.addActionListener(e -> {
            dispose();
            new AdminHome(adminName);
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBookings() {
        tableModel.setRowCount(0);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("b_id"),
                        rs.getString("b_name"),
                        rs.getString("b_con"),
                        rs.getString("b_agency"),
                        rs.getString("b_add"),
                        rs.getDate("b_bd"),
                        rs.getDate("b_deliveryd"),
                        rs.getString("b_gtype"),
                        rs.getString("b_gqty"),
                        rs.getString("b_price")
                });
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (ClassNotFoundException ce) {
            JOptionPane.showMessageDialog(this, "MySQL Driver not found. Add the JDBC jar to classpath.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Database Error: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBooking() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String name = tfName.getText();
            String contact = tfContact.getText();
            String address = tfAddress.getText();
            String type = cbType.getSelectedItem().toString();
            String quantity = cbQuantity.getSelectedItem().toString();
            String dateStr = tfDate.getText();

            if (name.isEmpty() || contact.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name, Contact, and Date are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date bookingDate = sdf.parse(dateStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(bookingDate);
            cal.add(Calendar.DAY_OF_MONTH, 3);

            java.sql.Date sqlBookingDate = new java.sql.Date(bookingDate.getTime());
            java.sql.Date sqlDeliveryDate = new java.sql.Date(cal.getTimeInMillis());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");

            String sql = "UPDATE booking SET b_name=?, b_con=?, b_gtype=?, b_gqty=?, b_add=?, b_bd=?, b_deliveryd=? WHERE b_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, contact);
            pst.setString(3, type);
            pst.setString(4, quantity);
            pst.setString(5, address);
            pst.setDate(6, sqlBookingDate);
            pst.setDate(7, sqlDeliveryDate);
            pst.setInt(8, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Booking Updated Successfully!");
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No booking found with ID: " + id);
            }

            pst.close();
            con.close();
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(this, "Enter booking date in DD-MM-YYYY format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(this, "Select a valid booking from the table!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ce) {
            JOptionPane.showMessageDialog(this, "MySQL Driver not found. Add the JDBC jar to classpath.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Database Error: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UpdatedPage("Admin"); // Replace "Admin" with the actual admin name
    }
}
