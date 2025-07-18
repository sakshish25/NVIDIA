package Nvdia;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Profile extends JFrame {
    private JTextField nameField, mobileField;
    private JComboBox<String> cityBox;
    private JLabel speedLabel, priceLabel, planTypeLabel, planDataLabel, planDurationLabel;
    private JButton editButton, saveButton;
    private boolean isEditing = false;
    private String loggedInUser;

    public Profile() {
        setTitle("User Profile - Nvidia Broadband");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        loggedInUser = SignInPage.getLoggedInUser();
        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "No user signed in!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Adding background color to the main panel
        mainPanel.setBackground(Color.lightGray);  // Light gray color for background

        JPanel detailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("User Information"));
        
        // Set background color for the details panel
        detailsPanel.setBackground(Color.GRAY); // White background for the details section
        detailsPanel.setOpaque(true);

        detailsPanel.add(new JLabel("Name:"));
        nameField = createTextField();
        nameField.setEditable(false);
        detailsPanel.add(nameField);

        detailsPanel.add(new JLabel("Mobile:"));
        mobileField = createTextField();
        mobileField.setEditable(false);
        detailsPanel.add(mobileField);

        detailsPanel.add(new JLabel("City:"));
        String[] cities = {"Select City", "Austin", "Texas", "Raleigh", "North Carolina", "Kansas City", "Missouri", "New York City", "Manhattan"};
        cityBox = new JComboBox<>(cities);
        createComboBox(cityBox);

        cityBox.setEditable(false);
        detailsPanel.add(cityBox);

        JPanel subscriptionPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        subscriptionPanel.setBorder(BorderFactory.createTitledBorder("Subscription Details"));

        // Set faint gray background color for the subscription panel
        subscriptionPanel.setBackground(Color.GRAY); // Light gray background for the subscription section
        subscriptionPanel.setOpaque(true);

        subscriptionPanel.add(new JLabel("Plan Type:"));
        planTypeLabel = new JLabel();
        subscriptionPanel.add(planTypeLabel);

        subscriptionPanel.add(new JLabel("Speed:"));
        speedLabel = new JLabel();
        subscriptionPanel.add(speedLabel);

        subscriptionPanel.add(new JLabel("Plan Price:"));
        priceLabel = new JLabel();
        subscriptionPanel.add(priceLabel);

        subscriptionPanel.add(new JLabel("Plan Duration:"));
        planDurationLabel = new JLabel();
        subscriptionPanel.add(planDurationLabel);

        subscriptionPanel.add(new JLabel("Plan Data:"));
        planDataLabel = new JLabel();
        subscriptionPanel.add(planDataLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Set background color for button panel
        buttonPanel.setBackground(new Color(220, 220, 220));  // Light gray background for button panel

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditing = true;
                toggleEditableFields(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditing = false;
                toggleEditableFields(false);
                updateUserProfile();
            }
        });

        mainPanel.add(detailsPanel);
        mainPanel.add(subscriptionPanel);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        add(mainPanel, BorderLayout.CENTER);

        fetchUserProfile();
        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setMaximumSize(new Dimension(200, 25));
        return textField;
    }

    public void createComboBox(JComboBox<String> city) {
        city.setFont(new Font("Arial", Font.PLAIN, 12));
        city.setBackground(new Color(33, 171, 230)); // Blue background for combo box
        city.setForeground(Color.BLACK);
        city.setFocusable(false);
        city.setEditable(false);

        city.setMaximumSize(new Dimension(300, 30));  // Limits max size
        city.setPreferredSize(new Dimension(250, 30)); // Preferred width & height
        city.setMinimumSize(new Dimension(200, 30));  // Ensures it doesn't shrink too much
        
        // Set the text color of JComboBox items to black
        for (int i = 0; i < city.getItemCount(); i++) {
            city.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setForeground(Color.BLACK); // Set text color to black
                    return label;
                }
            });
        }
    }

    private void toggleEditableFields(boolean enable) {
        nameField.setEditable(enable);
        mobileField.setEditable(enable);
        cityBox.setEditable(enable);
        saveButton.setEnabled(enable);
        editButton.setEnabled(!enable);
    }

    private void fetchUserProfile() {
        String username = SignInPage.getLoggedInUser();
        if (username == null) {
            JOptionPane.showMessageDialog(this, "No user signed in!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        JDBCConnection c = new JDBCConnection();
        try (Connection conn = c.s.getConnection()) {
            String query = "SELECT r.username, r.phno, r.city, p.planType, p.speed, p.planPrice, p.planDuretion, p.planData " +
                    "FROM registretion r " +
                    "LEFT JOIN broadband_plans p ON r.phno = p.phno " +
                    "WHERE r.username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loggedInUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("username"));
                mobileField.setText(rs.getString("phno"));
                cityBox.setSelectedItem(rs.getString("city"));
                planTypeLabel.setText(rs.getString("planType") != null ? rs.getString("planType") : "N/A");
                speedLabel.setText(rs.getString("speed") != null ? rs.getString("speed") : "N/A");
                priceLabel.setText(rs.getString("planPrice") != null ? rs.getString("planPrice") : "N/A");
                planDurationLabel.setText(rs.getString("planDuretion") != null ? rs.getString("planDuretion") : "N/A");
                planDataLabel.setText(rs.getString("planData") != null ? rs.getString("planData") : "N/A");
            } else {
                JOptionPane.showMessageDialog(null, "User data not found!");
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }

    private void updateUserProfile() {
        JDBCConnection c = new JDBCConnection();
        try (Connection conn = c.s.getConnection()) {
            String query = "UPDATE registretion SET username = ?, phno = ?, city = ? WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nameField.getText());
            ps.setString(2, mobileField.getText());
            ps.setString(3, cityBox.getSelectedItem().toString());
            ps.setString(4, loggedInUser);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Profile Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Update Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Profile();
    }
}
