import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Booking {
    public static void main(String[] args) {
        JFrame f = new JFrame("Gas Booking System - Booking");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 700);
        f.setLayout(new BorderLayout());

        JLabel title = new JLabel("Gas Booking System - Booking", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(600, 60));
        f.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 245, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Booking Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Booking Name:"), gbc);
        JTextField tfName = new JTextField(20);
        tfName.setFont(fieldFont);
        gbc.gridx = 1; panel.add(tfName, gbc);

        // Contact Number
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contact Number:"), gbc);
        JTextField tfContact = new JTextField(20);
        tfContact.setFont(fieldFont);
        gbc.gridx = 1; panel.add(tfContact, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        JTextField tfEmail = new JTextField(20);
        tfEmail.setFont(fieldFont);
        gbc.gridx = 1; panel.add(tfEmail, gbc);

        // Agency
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Gas Agency:"), gbc);
        JTextField tfAgency = new JTextField("sohamGas", 20); // default value
        tfAgency.setFont(fieldFont);
        gbc.gridx = 1; panel.add(tfAgency, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Address:"), gbc);
        JTextArea tfAddress = new JTextArea(3, 20);
        tfAddress.setFont(fieldFont);
        tfAddress.setLineWrap(true);
        tfAddress.setWrapStyleWord(true);
        gbc.gridx = 1;
        panel.add(new JScrollPane(tfAddress), gbc);

        // Booking Date
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Booking Date (DD-MM-YYYY):"), gbc);
        JTextField tfDate = new JTextField(20);
        tfDate.setFont(fieldFont);
        gbc.gridx = 1; panel.add(tfDate, gbc);

        // Gas Type
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Gas Type:"), gbc);
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Domestic", "Commercial"});
        cbType.setFont(fieldFont);
        gbc.gridx = 1; panel.add(cbType, gbc);

        // Gas Quantity
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Gas Quantity:"), gbc);
        JComboBox<String> cbQuantity = new JComboBox<>(new String[]{"1 Cylinder", "2 Cylinders", "3 Cylinders", "4 Cylinders"});
        cbQuantity.setFont(fieldFont);
        gbc.gridx = 1; panel.add(cbQuantity, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(new JLabel("Total Price (₹):"), gbc);
        JTextField tfPrice = new JTextField(20);
        tfPrice.setFont(fieldFont);
        tfPrice.setEditable(false);
        gbc.gridx = 1; panel.add(tfPrice, gbc);

        // Auto-update price
        Runnable updatePrice = () -> {
            String selectedType = cbType.getSelectedItem().toString();
            int qty = cbQuantity.getSelectedIndex() + 1;
            int unitPrice = selectedType.equals("Domestic") ? 1100 : 1800;
            tfPrice.setText(String.valueOf(unitPrice * qty));
        };
        cbType.addActionListener(e -> updatePrice.run());
        cbQuantity.addActionListener(e -> updatePrice.run());
        updatePrice.run();

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 245, 230));

        JButton bt = new JButton("Book Now");
        bt.setBackground(new Color(255, 140, 0));
        bt.setForeground(Color.WHITE);
        bt.setFont(new Font("Arial", Font.BOLD, 20));
        bt.setPreferredSize(new Dimension(180, 50));

        JButton backBtn = new JButton("⬅ Back");
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.setPreferredSize(new Dimension(180, 50));

        btnPanel.add(bt);
        btnPanel.add(backBtn);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

        f.add(panel, BorderLayout.CENTER);

        // Book Now Action
        bt.addActionListener(e -> {
            String name = tfName.getText();
            String contact = tfContact.getText();
            String email = tfEmail.getText();
            String agency = tfAgency.getText();
            String address = tfAddress.getText();
            String dateStr = tfDate.getText();
            String type = cbType.getSelectedItem().toString();
            String quantity = cbQuantity.getSelectedItem().toString();
            double price = Double.parseDouble(tfPrice.getText());

            String deliveryDate;
            java.sql.Date sqlBookingDate = null;
            java.sql.Date sqlDeliveryDate = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date bookingDate = sdf.parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(bookingDate);
                cal.add(Calendar.DAY_OF_MONTH, 3);

                deliveryDate = sdf.format(cal.getTime());
                sqlBookingDate = new java.sql.Date(bookingDate.getTime());
                sqlDeliveryDate = new java.sql.Date(cal.getTimeInMillis());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(f, "Enter booking date in DD-MM-YYYY format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");

                String sql = "INSERT INTO booking (b_name, b_con, b_email, b_agency, b_add, b_bd, b_deliveryd, b_gtype, b_gqty, b_price)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, contact);
                pst.setString(3, email);
                pst.setString(4, agency);
                pst.setString(5, address);
                pst.setDate(6, sqlBookingDate);
                pst.setDate(7, sqlDeliveryDate);
                pst.setString(8, type);
                pst.setString(9, quantity);
                pst.setDouble(10, price);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(f, "✅ Booking Saved!\nConfirmation email will be sent.", "Booking Successful", JOptionPane.INFORMATION_MESSAGE);

                // Send email
                String bookingDetails = "Gas Type: " + type +
                        "\nQuantity: " + quantity +
                        "\nPrice: ₹" + price +
                        "\nDelivery Date: " + deliveryDate;
                EmailService emailService = new EmailService();
                emailService.confirmBooking(email, name, bookingDetails);

                pst.close();
                con.close();

                // 🔹 After success → go back to Home
                f.dispose();
                Home.main(null);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            f.dispose();
            Home.main(null);
        });

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
