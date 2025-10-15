import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home {
    private String username;

    // ✅ Constructor to receive username
    public Home(String username) {
        this.username = username;
        createUI();
    }

    private void createUI() {
        JFrame f = new JFrame("Gas Booking System - Home");
        f.setSize(700, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel(" Gas Booking System - Welcome " + username, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(700, 70));
        f.add(title, BorderLayout.NORTH);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 245, 230));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        Font buttonFont = new Font("Arial", Font.BOLD, 22);

        // Booking Button
        JButton bookingBtn = new JButton("Booking Page");
        styleButton(bookingBtn, buttonFont);
        bookingBtn.addActionListener(e -> Booking.main(null));

        // Booking Info Button
        JButton bookingInfoBtn = new JButton("View My Bookings");
        styleButton(bookingInfoBtn, buttonFont);
        bookingInfoBtn.addActionListener(e -> {
            f.dispose();
            new BookingInfo(username);  // ✅ pass username here
        });

        // Service Button
        JButton serviceBtn = new JButton("Service Page");
        styleButton(serviceBtn, buttonFont);
        serviceBtn.addActionListener(e ->new ServicePage(username) );

        // Update Button
        JButton updateBtn = new JButton("Update Page");
        styleButton(updateBtn, buttonFont);
        updateBtn.addActionListener(e -> new updateBooking(username));

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel Booking");
        styleButton(cancelBtn, buttonFont);
        cancelBtn.addActionListener(e -> cancelpage.main(null));

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(bookingBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(serviceBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(updateBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(cancelBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(bookingInfoBtn, gbc);

        f.add(panel, BorderLayout.CENTER);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static void styleButton(JButton btn, Font font) {
        btn.setBackground(new Color(255, 140, 0));
        btn.setForeground(Color.WHITE);
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(250, 70));
        btn.setFocusPainted(false);
    }

    // Optional: for testing without login
    public static void main(String[] args) {
        new Home("");
    }
}
