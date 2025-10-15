import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageUser{
    JFrame f;
    JTable userTable;
    DefaultTableModel model;
    Connection con;

    public ManageUser() {
        // Frame setup
        f = new JFrame("Gas Booking System - Manage Users");
        f.setSize(900, 500);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new BorderLayout());

        // ===== Header =====
        JLabel title = new JLabel("Manage Registered Users", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(900, 60));
        f.add(title, BorderLayout.NORTH);

        // ===== Table =====
        String[] columns = {"ID", "Username", "Email", "Phone", "Role"};
        model = new DefaultTableModel(columns, 0);
        userTable = new JTable(model);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(userTable);
        f.add(scroll, BorderLayout.CENTER);

        // ===== Bottom Buttons =====
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 245, 230));

        JButton btnLoad = new JButton("🔄 Load Users");
        JButton btnDelete = new JButton("🗑 Delete User");
        JButton btnClose = new JButton("❌ Close");

        styleButton(btnLoad);
        styleButton(btnDelete);
        styleButton(btnClose);

        btnPanel.add(btnLoad);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClose);

        f.add(btnPanel, BorderLayout.SOUTH);

        // ===== Database Connection =====
        connectDB();

        // ===== Button Actions =====
        btnLoad.addActionListener(e -> loadUsers());

        btnDelete.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(f, "⚠ Please select a user to delete!");
                return;
            }

            int userId = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure you want to delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(userId);
                loadUsers(); // refresh table
            }
        });

        btnClose.addActionListener(e -> f.dispose());

        // ===== Show Frame =====
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(255, 140, 0));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
    }

    // Connect DB
    public void connectDB() {
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

    // Load all users into table
    public void loadUsers() {
        model.setRowCount(0); // clear table
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rg_id, rg_name, rg_email, rg_p, rg_role FROM register");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("rg_id"),
                        rs.getString("rg_name"),
                        rs.getString("rg_email"),
                        rs.getString("rg_p"),
                        rs.getString("rg_role")
                });
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "⚠ Error loading users!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Delete user by ID
    public void deleteUser(int userId) {
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM register WHERE rg_id=?");
            pst.setInt(1, userId);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(f, "✅ User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(f, "⚠ User not found!");
            }

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "⚠ Error deleting user!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ManageUser();
    }
}
