import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage {
    public LoginPage() {
        // Frame
        JFrame f = new JFrame("Gas Booking System - Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 400);
        f.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("User Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204)); // Blue header
        title.setPreferredSize(new Dimension(500, 60));
        f.add(title, BorderLayout.NORTH);

        // Panel for form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 245, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        panel.add(userLabel, gbc);

        JTextField tfUser = new JTextField(18);
        tfUser.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfUser, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        panel.add(passLabel, gbc);

        JPasswordField tfPass = new JPasswordField(18);
        tfPass.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfPass, gbc);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(255, 140, 0));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 18));
        loginBtn.setPreferredSize(new Dimension(150, 40));

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);

        f.add(panel, BorderLayout.CENTER);

        // Button Action with JDBC
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = tfUser.getText().trim();
                String pass = new String(tfPass.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Please enter Username and Password!");
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");

                    // ✅ Get both password + role
                    String query = "SELECT rg_pass, rg_role FROM register WHERE rg_name = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, user);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String dbPass = rs.getString("rg_pass");
                        String role = rs.getString("rg_role");

                        if (dbPass.equals(pass)) {
                            if ("user".equalsIgnoreCase(role)) {
                                JOptionPane.showMessageDialog(f, "✅ Login Successful! Welcome " + user + " (User)");
                                f.dispose();
                                new Home(user); // user role
                            } else if ("admin".equalsIgnoreCase(role)) {
                                JOptionPane.showMessageDialog(f, "✅ Login Successful! Welcome " + user + " (Admin)");
                                f.dispose();
                                new AdminHome(user); // admin role
                            } else {
                                JOptionPane.showMessageDialog(f, "⚠ Unknown role assigned!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(f, "❌ Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(f, "⚠ User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    rs.close();
                    pst.close();
                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Center frame
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
