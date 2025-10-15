import javax.swing.*;
import java.awt.*;


public class AdminHome {
    JFrame f;

    public AdminHome(String adminName) {
        // Frame
        f = new JFrame("Gas Booking System - Admin Home");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 500);
        f.setLayout(new BorderLayout());

        // ===== Header =====
        JLabel title = new JLabel("Welcome Admin: " + adminName, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(700, 60));
        f.add(title, BorderLayout.NORTH);

        // ===== Panel for Buttons =====
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBackground(new Color(255, 245, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton btnBookings = new JButton("📖 View Bookings");
        JButton btnUsers = new JButton("👥 Manage Users");
        JButton btnLogout = new JButton("🚪 Logout");

        styleButton(btnBookings);
        styleButton(btnUsers);
        styleButton(btnLogout);

        panel.add(btnBookings);
        panel.add(btnUsers);
        panel.add(btnLogout);

        f.add(panel, BorderLayout.CENTER);

        // ===== Actions =====
        btnBookings.addActionListener(e -> {
            // Open BookingInfo page
            new adminSee();
        });

        btnUsers.addActionListener(e -> {
            // Open ManageUsers page
            new ManageUser();
        });

        btnLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(f, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                f.dispose();
                new LoginPage(); // back to login
            }
        });

        // ===== Show Frame =====
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(255, 140, 0));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 60));
    }

    public static void main(String[] args) {
        new AdminHome("Admin"); // test run
    }
}
