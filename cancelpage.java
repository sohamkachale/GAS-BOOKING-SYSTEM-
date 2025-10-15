import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class cancelpage extends JFrame {

    public cancelpage() {
        setTitle("Cancel Booking");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Booking ID Label
        JLabel idLabel = new JLabel("Booking ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc);

        // Booking ID TextField
        JTextField tfId = new JTextField(20);
        gbc.gridx = 1;
        add(tfId, gbc);

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBackground(new Color(255, 140, 0));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(cancelBtn, gbc);

        // Button Action
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookingId = tfId.getText().trim();

                if (bookingId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter Booking ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                   Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");
                    String sql = "DELETE FROM booking WHERE b_id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(bookingId));

                    int rows = pst.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "✅ Booking ID " + bookingId + " has been cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        tfId.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "⚠ No booking found with ID " + bookingId, "Not Found", JOptionPane.WARNING_MESSAGE);
                    }

                    pst.close();
                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLocationRelativeTo(null); // center frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new cancelpage();
    }
}
