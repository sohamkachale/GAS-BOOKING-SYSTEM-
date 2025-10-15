import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class adminSee {
    private JFrame f;
    private JTable table;
    private JButton btnLoad, btnExit;
    private DefaultTableModel tableModel;
    private Connection con;
    private String adminUser; // store admin username

    // No-arg constructor (keeps old code working) — delegates to the main constructor
    public adminSee() {
        this("Admin"); // default admin username if none provided
    }

    // Main constructor (preferred) that accepts admin username
    public adminSee(String adminUser) {
        this.adminUser = adminUser;
        initComponents();
        connectDB();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    // Initialize UI components
    private void initComponents() {
        f = new JFrame("Gas Booking System - Booking List (Admin View)");
        f.setSize(1000, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        // ===== Header =====
        JLabel title = new JLabel("Gas Booking System - All Bookings", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(1000, 60));
        f.add(title, BorderLayout.NORTH);

        // ===== Table Setup =====
        String[] columns = {"Booking ID", "Name", "Contact", "Agency", "Address",
                "Booking Date", "Delivery Date", "Gas Type", "Quantity", "Price"};

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        f.add(scrollPane, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 245, 230));

        btnLoad = new JButton("Load Bookings");
        btnLoad.setBackground(new Color(255, 140, 0));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFont(new Font("Arial", Font.BOLD, 16));
        btnLoad.setPreferredSize(new Dimension(160, 40));

        btnExit = new JButton("Back to Admin Home");
        btnExit.setBackground(new Color(255, 140, 0));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setPreferredSize(new Dimension(200, 40));

        btnPanel.add(btnLoad);
        btnPanel.add(btnExit);

        f.add(btnPanel, BorderLayout.SOUTH);

        // Button Actions
        btnLoad.addActionListener(e -> loadBookings());

        // On exit: dispose this window and open AdminHome with the admin username
        btnExit.addActionListener(e -> {
            f.dispose();
            // NOTE: this assumes AdminHome has a constructor AdminHome(String)
            // which matches the earlier code you showed (new AdminHome(user)).
            new AdminHome(adminUser);
        });
    }

    // Connect to MySQL Database
    private void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gas_booking", "root", "soham28");
            System.out.println("✅ Database Connected!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Load bookings into JTable
    private void loadBookings() {
        tableModel.setRowCount(0); // clear old data
        if (con == null) {
            JOptionPane.showMessageDialog(f, "No DB connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking");

            while (rs.next()) {
                Object[] row = {
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
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "❌ Error loading bookings!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch UI on EDT
        SwingUtilities.invokeLater(() -> new adminSee()); // or new adminSee("AdminUsername")
    }
}
