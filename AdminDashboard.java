package Nvdia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AdminDashboard extends JFrame {

    JPanel proP;
    JButton billGenerator, accountDetails, serviceRequest, profile ,btnCalculate ,  btnGenerateBill ;
    JPanel mainPanel, contentP, billP;
    static long transactionId;
    private double tax = 4.99, dueFine = 0.0;
    static Double total;
    static String ac;
    AdminDashboard() {
        createNewFrame();
    }

    public void createNewFrame() {
    	setSize(1920, 1080);
        setTitle("NvidiaBroadband-Service Request");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setContentPane(createSidebarPanel());
        setVisible(true);
    }

    private JPanel createSidebarPanel() {
        setTitle("Admin Dashboard");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel sidebar = new CustomPanel();
        sidebar.setPreferredSize(new Dimension((int) (1366 * 0.23), getHeight()));
        sidebar.setBackground(Color.DARK_GRAY);
        contentP = new JPanel(new BorderLayout());
        mainPanel.add(contentP, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 15, 15));
        buttonPanel.setOpaque(false);

        billGenerator = createStyledButton("Bill Generator");
        accountDetails = createStyledButton("Account Details");
        serviceRequest = createStyledButton("Service Requests");
        profile = createStyledButton("Profile");

        billGenerator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBillGeneratorPanel();
            }
        });
        
        accountDetails.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showAccountDetails();
			}
		});
        
        serviceRequest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				serviceRequest();
			}
		});

        profile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				profile();
			}
		});
        buttonPanel.add(billGenerator);
        buttonPanel.add(accountDetails);
        buttonPanel.add(serviceRequest);
        buttonPanel.add(profile);

        sidebar.setLayout(new BorderLayout());
        sidebar.add(buttonPanel, BorderLayout.NORTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        return mainPanel;
    }
    
    protected void profile() {
		
    	contentP.removeAll(); // Clear previous content

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel adminId = new JLabel("Admin Name:");
        JLabel adminName = new JLabel("Password:");
//        JLabel adminEmail = new JLabel("Email:");  
//        JLabel adminRole = new JLabel("Role:");
//      
        String  adminN = AdminSignIn.AdminName;
         String adminP = AdminSignIn.adminPass; 
        JTextField txtAccountId = new JTextField(adminN ,20);
        JTextField txtPlanBill = new JTextField( adminP ,20);
        
        txtAccountId.setEditable(false);
        txtPlanBill.setEditable(true);
//        JTextField txtDueFine = new JTextField( 20);
//        JTextField txtStateTax = new JTextField( 20);
       
        JButton btnUpdate = new JButton("Update Profile");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedAdminName = txtAccountId.getText();  // Assuming this holds the admin name
                String updatedPassword = txtPlanBill.getText();

                if (updatedAdminName.isEmpty() || updatedPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Please fill all fields!");
                    return;
                }

                JDBCConnection con = new JDBCConnection();
                String query = "UPDATE admin SET password = ? WHERE username = ?";  // Updated query

                try (PreparedStatement ps = con.con.prepareStatement(query)) {
                    ps.setString(1, updatedPassword);
                    ps.setString(2, updatedAdminName); // Matching based on admin_name

                    int rowsUpdated = ps.executeUpdate(); // Execute update query

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(AdminDashboard.this, "Password updated successfully for " + updatedAdminName);
                    } else {
                        JOptionPane.showMessageDialog(AdminDashboard.this, "Could not update! Admin name not found.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Database error: " + ex.getMessage());
                }
            }
        });

      
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(adminId, gbc);
        gbc.gridx = 1;
        panel.add(txtAccountId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(adminName, gbc);
        gbc.gridx = 1;
        panel.add(txtPlanBill, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
      

        // New panel for buttons to make them appear side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnUpdate);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        contentP.add(panel, BorderLayout.CENTER);
        contentP.revalidate();
        contentP.repaint();
	}

    private void serviceRequest() {
        System.out.println("In method");
        contentP.removeAll();

        JDBCConnection con = new JDBCConnection();
        List<Object[]> rowData = new ArrayList<>();
        List<String> originalStatuses = new ArrayList<>(); // To track original statuses

        if (con != null) {
            String query = "SELECT * FROM service_request;";

            try {
                ResultSet rs = con.s.executeQuery(query);
                while (rs.next()) {
                    rowData.add(new Object[]{
                        rs.getString("request_id"),
                        rs.getString("username"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("date")
                    });
                    originalStatuses.add(rs.getString("status")); // Store original status
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection failed");
        }

        // Convert List<Object[]> to Object[][]
        Object[][] data = new Object[rowData.size()][];
        for (int i = 0; i < rowData.size(); i++) {
            data[i] = rowData.get(i);
        }

        // Define column headers
        String[] columnNames = {"UserRequest_id", "username", "Issue Type", "status", "date"};

        // Create a custom table model to make only the "status" column editable
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Allow editing only for the "status" column
            }
        };

        JTable table = new JTable(model);

        // Define dropdown options for status
        String[] statusOptions = {"In Progress", "Resolved", "Pending", "Closed"};

        // Set a combo box editor for the status column
        TableColumn statusColumn = table.getColumnModel().getColumn(3);
        JComboBox<String> comboBox = new JComboBox<>(statusOptions);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(table);
        contentP.add(scrollPane, BorderLayout.CENTER);

        // Create and add the "Refresh" button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 20));
        contentP.add(refreshButton, BorderLayout.SOUTH);

        // Action listener for the refresh button
        refreshButton.addActionListener(e -> {
            // Ensure any ongoing edits are stopped
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }

            // Iterate through the table to find changes
            for (int i = 0; i < table.getRowCount(); i++) {
                String originalStatus = originalStatuses.get(i);
                String currentStatus = (String) table.getValueAt(i, 3);

                // If status has changed, update the database
                if (!originalStatus.equals(currentStatus)) {
                    String requestId = (String) table.getValueAt(i, 0); // Get request_id

                    String updateQuery = "UPDATE service_request SET status = ? WHERE request_id = ?";
                    try (PreparedStatement pstmt = con.con.prepareStatement(updateQuery)) {
                        pstmt.setString(1, currentStatus);
                        pstmt.setString(2, requestId);
                        pstmt.executeUpdate();

                        // Update the originalStatuses list
                        originalStatuses.set(i, currentStatus);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        contentP.revalidate();
        contentP.repaint();
    }


    private void showAccountDetails() {
        System.out.println("In method");
        contentP.removeAll();
        
        JDBCConnection con = new JDBCConnection();
        List<Object[]> rowData = new ArrayList<>();

        if (con != null) {
            String query = "SELECT registretion.username, broadband_plans.phno, broadband_plans.planType, " +
                           "broadband_plans.planPrice, broadband_plans.accountNo " +
                           "FROM broadband_plans " +
                           "INNER JOIN registretion ON broadband_plans.phno = registretion.phno;";

            try {
                ResultSet rs = con.s.executeQuery(query);
                while (rs.next()) {
                    rowData.add(new Object[]{
                        rs.getString("username"),
                        rs.getString("phno"),
                        rs.getString("planType"),
                        rs.getString("planPrice"),
                        rs.getString("accountNo")
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection failed");
        }

        // Convert List<Object[]> to Object[][]
        Object[][] data = new Object[rowData.size()][];
        for (int i = 0; i < rowData.size(); i++) {
            data[i] = rowData.get(i);
        }

        // Define column headers
        String[] columnNames = {"Username", "Phone", "Plan Type", "Plan Price", "Account No"};

        // Create JTable
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add table to content panel
        contentP.add(scrollPane, BorderLayout.CENTER);
        contentP.revalidate();
        contentP.repaint();
    }


    private void showBillGeneratorPanel() {
        contentP.removeAll(); // Clear previous content

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblAccountId = new JLabel("Account ID:");
        JLabel lblPlanBill = new JLabel("Plan Bill (Monthly):");
        JLabel lblDueFine = new JLabel("Due Fine ($):");  
        JLabel lblStateTax = new JLabel("State Tax:");
        JLabel lblTotalAmount = new JLabel("Total Amount ($):");

        JTextField txtAccountId = new JTextField(20);
        JTextField txtPlanBill = new JTextField("0.00", 20);
        JTextField txtDueFine = new JTextField("0.00", 20);
        JTextField txtStateTax = new JTextField("18.00", 20);
        JTextField txtTotalAmount = new JTextField("0.00", 20);
        txtTotalAmount.setEditable(false);

        btnCalculate = new JButton("Calculate Total");
        
        btnCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JDBCConnection con = new JDBCConnection();
				
				ac = txtAccountId.getText().trim();
				String query = "SELECT planPrice FROM broadband_plans WHERE accountNo = '" + ac + "';";

				 double planp =0.0;
				 String value = null ;
				try {
					ResultSet rs = con.s.executeQuery(query);
					if(rs.next()) {
						
						String plan= rs.getString("planPrice");
					  planp = Double.parseDouble(plan);
					  value = String.valueOf(planp);
					  txtPlanBill.setText(value);
					}
			
					else {
						JOptionPane.showMessageDialog(panel, "Account does not Exist ", "", JOptionPane.INFORMATION_MESSAGE);
						
					}
				} catch (SQLException e1 ) {
					JOptionPane.showMessageDialog(panel, "Account does not Exist ", "", JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				String planAmountText = txtPlanBill.getText(); // Get text from JTextField
				String dueAmt = txtDueFine.getText();
				String stateTax = txtStateTax.getText();
				
				double planAmount = 0.0; // Default value
				double dueAmount = 0.0;
				double statTax = 0.0;
				double totalAmt = 0.0;

				try {
				    planAmount = Double.parseDouble(planAmountText); // Convert to double
				    dueAmount = Double.parseDouble(dueAmt);
				    statTax = Double.parseDouble(stateTax);
				    
				    totalAmt = planAmount + dueAmount + statTax;
				    total = totalAmt;
				    String amt = String.valueOf(totalAmt);
				    txtTotalAmount.setText(amt);
				    
				} catch (NumberFormatException n) {
				    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
				}
			}
		});
      
        btnGenerateBill = new JButton("Generate Bill");
        btnGenerateBill.addActionListener(new ActionListener() {
			
        	@Override
			public void actionPerformed(ActionEvent e) 
			{	
				String accountNo = txtAccountId.getText();
				JDBCConnection con = new JDBCConnection();
				
	    		double total = Double.parseDouble(txtTotalAmount.getText()); 
	    		String roundedStr = String.format("%.2f", total);
	    		System.out.println(roundedStr);  
	    		
	    		
	    		 double dueFine = Double.parseDouble(txtDueFine.getText());
	    		
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String today = sdf.format(new Date());
	    		
	    		generateTransactionId();
	    		String query = "INSERT INTO bill VALUES (?, ?, ?, ?, ?, ?, ?)";
	    		
	    		JDBCConnection c = new JDBCConnection();
	    		
	    		
	    		PreparedStatement ps;

	    		try 
				{
					
					ps = c.con.prepareStatement(query);
					
					ps.setString(1, accountNo);  // String value
		    		ps.setString(2, roundedStr);      // Decimal/Double value
		    		ps.setLong(3, transactionId); // Integer value
		    		ps.setString(4, "None");     // String value
		    		ps.setDouble(5, AdminDashboard.this.dueFine);
		    		ps.setDouble(6, tax);
		    		ps.setString(7, today);       // Date should be in 'YYYY-MM-DD' format

		    		int i = ps.executeUpdate();

		    		if(i > 0)
					{
		    			JOptionPane.showMessageDialog(AdminDashboard.this, "Bill Generated Successfully !!!", "Success",JOptionPane.PLAIN_MESSAGE);
					}	
					else
					{
						JOptionPane.showMessageDialog(AdminDashboard.this, "Bill was not Generated !!!", "Error",JOptionPane.PLAIN_MESSAGE);
					}	
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblAccountId, gbc);
        gbc.gridx = 1;
        panel.add(txtAccountId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPlanBill, gbc);
        gbc.gridx = 1;
        panel.add(txtPlanBill, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblDueFine, gbc);
        gbc.gridx = 1;
        panel.add(txtDueFine, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblStateTax, gbc);
        gbc.gridx = 1;
        panel.add(txtStateTax, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblTotalAmount, gbc);
        gbc.gridx = 1;
        panel.add(txtTotalAmount, gbc);

        // New panel for buttons to make them appear side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnGenerateBill);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        contentP.add(panel, BorderLayout.CENTER);
        contentP.revalidate();
        contentP.repaint();
    }

    public void generateTransactionId()
	 {
	 	double max = 1000000000;
	 	double min = 1;
	 	
	 	transactionId = (long) (Math.random()*(max-min)+1);
	 }

	class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, Color.DARK_GRAY, getWidth(), getHeight(), Color.GRAY);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        return button;
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}