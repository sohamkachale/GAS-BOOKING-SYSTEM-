import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookingInfo {
    JFrame f;
    JTable table;
    JButton btnLoad, btnExit;
    DefaultTableModel tableModel;
    Connection con;
    String loggedUser;

    public BookingInfo(String username) {  // pass username
        this.loggedUser = username;

        // Frame setup
        f = new JFrame("Gas Booking System - Booking List (User View)");
        f.setSize(1000, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        // ===== Header =====
        JLabel title = new JLabel("Gas Booking System - Your Bookings", JLabel.CENTER);
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

        btnLoad = new JButton("Load My Bookings");
        btnLoad.setBackground(new Color(255, 140, 0));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFont(new Font("Arial", Font.BOLD, 16));
        btnLoad.setPreferredSize(new Dimension(180, 40));

        btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(255, 140, 0));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setPreferredSize(new Dimension(160, 40));

        btnPanel.add(btnLoad);
        btnPanel.add(btnExit);

        f.add(btnPanel, BorderLayout.SOUTH);

        // ===== Database Connection =====
        connectDB();

        // Button Actions
        btnLoad.addActionListener(e -> loadBookings());
        btnExit.addActionListener(e -> {
            new Home(loggedUser);  // open home
            f.dispose();
        });

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    // Connect to MySQL Database
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

    // Load only the current user's bookings
    public void loadBookings() {
        tableModel.setRowCount(0); // clear old data
        try {
            String sql = "SELECT * FROM booking WHERE b_name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, loggedUser);

            ResultSet rs = pst.executeQuery();

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
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "❌ Error loading bookings!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new BookingInfo("");
    }
}
