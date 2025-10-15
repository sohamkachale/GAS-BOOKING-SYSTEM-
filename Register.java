import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register {
    public static void main(String[] args) {
        // Frame setup
        JFrame f = new JFrame("Gas Booking System - Register");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 800);
        f.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Gas Booking System - Register", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(600, 60));
        f.add(title, BorderLayout.NORTH);

        // Panel
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

        JTextField tf1 = new JTextField(18);
        tf1.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tf1, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        panel.add(emailLabel, gbc);

        JTextField tf2 = new JTextField(18);
        tf2.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tf2, gbc);

        // Phone Number
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont);
        panel.add(phoneLabel, gbc);

        JTextField tfPhone = new JTextField(18);
        tfPhone.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfPhone, gbc);

        // Alternate Phone
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel altPhoneLabel = new JLabel("Alternate Phone:");
        altPhoneLabel.setFont(labelFont);
        panel.add(altPhoneLabel, gbc);

        JTextField tfAltPhone = new JTextField(18);
        tfAltPhone.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfAltPhone, gbc);

        // Aadhar
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel aadharLabel = new JLabel("Aadhar Number:");
        aadharLabel.setFont(labelFont);
        panel.add(aadharLabel, gbc);

        JTextField tfAadhar = new JTextField(18);
        tfAadhar.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfAadhar, gbc);

        // DOB
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel dobLabel = new JLabel("Date of Birth (DD-MM-YYYY):");
        dobLabel.setFont(labelFont);
        panel.add(dobLabel, gbc);

        JTextField tfDob = new JTextField(18);
        tfDob.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tfDob, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        panel.add(genderLabel, gbc);

        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> cbGender = new JComboBox<>(genders);
        cbGender.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(cbGender, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 7;
        JLabel addrLabel = new JLabel("Address:");
        addrLabel.setFont(labelFont);
        panel.add(addrLabel, gbc);

        JTextArea tfAddress = new JTextArea(3, 18);
        tfAddress.setFont(fieldFont);
        tfAddress.setLineWrap(true);
        tfAddress.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(tfAddress);
        gbc.gridx = 1;
        panel.add(scroll, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 8;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        panel.add(passLabel, gbc);

        JPasswordField tf3 = new JPasswordField(18);
        tf3.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(tf3, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 9;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(labelFont);
        panel.add(roleLabel, gbc);

        String[] roles = {"User"};
        JComboBox<String> cbRole = new JComboBox<>(roles);
        cbRole.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(cbRole, gbc);

        // Register button
        JButton bt = new JButton("Register");
        bt.setBackground(new Color(255, 140, 0));
        bt.setForeground(Color.WHITE);
        bt.setFocusPainted(false);
        bt.setFont(new Font("Arial", Font.BOLD, 20));
        bt.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bt, gbc);

        f.add(panel, BorderLayout.CENTER);

        // Action
        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String uname = tf1.getText();
                String email = tf2.getText();
                String phone = tfPhone.getText();
                String altPhone = tfAltPhone.getText();
                String aadhar = tfAadhar.getText();
                String dob = tfDob.getText();
                String gender = cbGender.getSelectedItem().toString();
                String address = tfAddress.getText();
                String pass = new String(tf3.getPassword());
                String role = cbRole.getSelectedItem().toString();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");

                    String query = "INSERT INTO register (rg_name, rg_email, rg_p, rg_pno1, rg_adh, rg_dob, rg_gen, rg_add, rg_pass, rg_role) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, uname);
                    pst.setString(2, email);
                    pst.setString(3, phone);
                    pst.setString(4, altPhone);
                    pst.setString(5, aadhar);
                    pst.setString(6, dob);
                    pst.setString(7, gender);
                    pst.setString(8, address);
                    pst.setString(9, pass);
                    pst.setString(10, role);

                    int rows = pst.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(f, "✅ Registration Successful!");

                        // ✅ Send welcome email
                        EmailService emailService = new EmailService();
                        String subject = "Welcome to SohamGas Agency";

                        String body = "Dear " + uname + ",\n\n"
                                + "Thank you for registering with SohamGas Agency. We are delighted to have you on board!\n\n"
                                + "Your account has been successfully created.\n"
                                + "Username: " + uname + "\n\n"
                                + "You can now log in to your account and conveniently book your gas cylinders anytime.\n\n"
                                + "If you have any questions, our support team is always ready to assist you.\n\n"
                                + "Warm regards,\n"
                                + "SohamGas Agency Team";


                        emailService.sendEmail(email, subject, body);

                        f.dispose();
                        new LoginPage();
                    } else {
                        JOptionPane.showMessageDialog(f, "⚠ Registration Failed!");
                    }

                    pst.close();
                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "❌ Error: " + ex.getMessage());
                }
            }
        });

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
