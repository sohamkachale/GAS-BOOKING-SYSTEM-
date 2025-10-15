import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServicePage extends JFrame {
    private JTextArea selectedArea; // 🔹 TextArea for live selection preview
    private JPanel panel;
    private String username;
// 🔹 Store services panel globally

    public ServicePage( String username) {
        this.username = username;
        setTitle("Gas Booking System - Services");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Header
        JLabel title = new JLabel("Gas Services", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setPreferredSize(new Dimension(800, 70));
        add(title, BorderLayout.NORTH);

        // 🔹 Main wrapper panel
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(255, 245, 230));
        add(wrapper, BorderLayout.CENTER);

        // 🔹 Inner content panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 245, 230));

        // Fonts
        Font checkFont = new Font("Arial", Font.BOLD, 18);
        Font descFont = new Font("Arial", Font.PLAIN, 14);

        // Add services
        addService(panel, "Pipeline Changing",
                "Our experts replace damaged or old gas pipelines with safe, high-quality materials.",
                checkFont, descFont);

        addService(panel, "Cylinder Changing (if leakage)",
                "In case of leakage or damage, we provide a new gas cylinder immediately to ensure safety.",
                checkFont, descFont);

        addService(panel, "Gas Servicing",
                "Regular servicing of gas stoves and pipelines to avoid accidents and improve efficiency.",
                checkFont, descFont);

        addService(panel, "Regulator Exchange",
                "We provide a new gas regulator if your old one is faulty, ensuring safe gas flow.",
                checkFont, descFont);

        addService(panel, "New Gas Connection",
                "Apply for a new gas connection quickly with minimal documentation and faster approval.",
                checkFont, descFont);

        // 🔹 Submit Button
        JButton submitBtn = new JButton("Submit Services");
        submitBtn.setBackground(new Color(255, 140, 0));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 18));
        submitBtn.setPreferredSize(new Dimension(250, 50));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(submitBtn);

        // Center panel in wrapper
        wrapper.add(panel);

        // 🔹 Live Selected Services Area
        selectedArea = new JTextArea(6, 40);
        selectedArea.setEditable(false);
        selectedArea.setFont(new Font("Arial", Font.PLAIN, 15));
        selectedArea.setBorder(BorderFactory.createTitledBorder("Selected Services"));




        add(new JScrollPane(selectedArea), BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> {
            String text = selectedArea.getText().trim();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one service!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Show confirmation dialog
                JOptionPane.showMessageDialog(this,
                        "You have selected the following services:\n\n" + text,
                        "Services Confirmed",
                        JOptionPane.INFORMATION_MESSAGE);

                // After user clicks OK, go back to Home page
                new Home(username);  // Make sure loggedUser is available here
                dispose();  // Close current frame
            }
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔹 Helper function
    private void addService(JPanel panel, String serviceText, String description, Font checkFont, Font descFont) {
        JCheckBox cb = new JCheckBox(serviceText);
        cb.setFont(checkFont);
        cb.setBackground(new Color(255, 245, 230));

        JLabel desc = new JLabel("<html><div style='width:400px;'>" + description + "</div></html>");
        desc.setFont(descFont);
        desc.setForeground(Color.DARK_GRAY);

        // 🔹 Update textarea when checkbox is clicked
        cb.addItemListener(e -> updateSelectedServices());

        panel.add(cb);
        panel.add(desc);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
    }

    // 🔹 Refresh textarea with all selected services
    private void updateSelectedServices() {
        StringBuilder sb = new StringBuilder();
        for (Component c : panel.getComponents()) {
            if (c instanceof JCheckBox) {
                JCheckBox cb = (JCheckBox) c;
                if (cb.isSelected()) {
                    sb.append(cb.getText()).append("\n");
                }
            }
        }
        selectedArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new ServicePage("");
    }
}
