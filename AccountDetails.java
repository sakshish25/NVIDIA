package Nvdia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AccountDetails extends JFrame
{
	private JButton billGeneratorButton, accountDetailsButton, serviceRequestButton, profileButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private JDBCConnection dbcon;
	private JPanel rightPanel, innerPanel;
	private ScrollPane scrollpane;
	private double tax = 4.99, dueFine = 0.0;
	public AccountDetails()
	{
		setUpFrame();
		add(createLeftPanel(), BorderLayout.WEST);
		add(createRightPanel(), BorderLayout.CENTER);
		try 
		{
			fetchData();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setUpFrame()
	{
		setSize(1920, 1080);
		setLayout(new BorderLayout());
		setTitle("Admin Dashboard");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	public JPanel createLeftPanel()
	{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(new Color(50, 50, 50));
		leftPanel.setPreferredSize(new Dimension(200, 600));
		
		billGeneratorButton = createStyledButtons("Bill Generator", new Dimension(250, 30));
		accountDetailsButton = createStyledButtons("Account Details", new Dimension(250, 30));
		serviceRequestButton = createStyledButtons("Service Request", new Dimension(250, 30));
		profileButton = createStyledButtons("Profile", new Dimension(250, 30));
		
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(billGeneratorButton);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(accountDetailsButton);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(serviceRequestButton);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(profileButton);		
		
		billGeneratorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				showBillGeneratorPanel();
			}
		});
		
		accountDetailsButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				showAccountPanel();
			}
		});
		
		serviceRequestButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				showServiceRequestsPanel();
			}
		});
		
		profileButton.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				showProfilePanel();
			}
		});
		
		return leftPanel;
	}
	
	public JPanel createRightPanel()
	{
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(new Color(238, 238, 238));
        
        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(new Color(255, 255, 255));
        innerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        rightPanel.add(innerPanel, BorderLayout.NORTH);
        
        // Table Setup
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        innerPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(innerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(refreshButton, BorderLayout.EAST);
        	
        refreshButton.addActionListener(new ActionListener() 
        {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					fetchData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        
        rightPanel.add(innerPanel, BorderLayout.CENTER);
        rightPanel.add(bottomPanel,BorderLayout.SOUTH);
        return rightPanel;
	}
	
	private JButton createStyledButtons(String text, Dimension size)
	{
		JButton button = new JButton(text);
	    button.setFont(new Font("Arial", Font.BOLD, 14));
	    button.setForeground(Color.WHITE);
	    button.setBackground(new Color(70, 70, 70));
	    button.setOpaque(false);
	    button.setFocusPainted(false);
	    button.setContentAreaFilled(false);
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    button.setMaximumSize(new Dimension(180, 40));
	    button.setAlignmentX(CENTER_ALIGNMENT);
	    button.setPreferredSize(size); // Set custom width & height
	    return button;
	}
	
	public Component fetchData() throws SQLException
	{
		try
		{
			dbcon = new JDBCConnection();
			Connection conn = dbcon.con;
			String query = "SELECT a.mobileno, r.username, a.plantype, a.planprice, a.accountno " +
		               "FROM broadband_plans AS a " +
		               "INNER JOIN registrations AS r " +
		               "ON CAST(a.mobileno AS CHAR) = CAST(r.mobileno AS CHAR);";

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Executing query: " + query);

			tableModel.setRowCount(0); // Clear Previous data
			tableModel.setColumnIdentifiers(new String[] {"Mobile Number","Username", "Plan Type", "Plan Price", "Account Number"});
			
			while(rs.next())
			{
				tableModel.addRow(new Object[]
				{
					rs.getString("mobileno"),	
					rs.getString("username"),	
					rs.getString("planType"),	
					rs.getString("planPrice"),	
					rs.getString("accountNo"),	
				});
			}
			rs.close();
			ps.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Dtabase Error: "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return accountDetailsButton;
	}
	
	private void showBillGeneratorPanel() 
	{
        rightPanel.removeAll();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new BillGenerator(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
	
	private void showProfilePanel() 
	{
        rightPanel.removeAll();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new Profile(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
	
	private void showAccountPanel()
	{
		rightPanel.removeAll();
		rightPanel.setLayout(new BorderLayout());
        
		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new BorderLayout());
		
		try
		{
			fetchData();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		accountPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		rightPanel.add(accountPanel, BorderLayout.CENTER);
		
        rightPanel.revalidate();
        rightPanel.repaint();
	}
	
	// Service Requests GUI
	private void showServiceRequestsPanel() 
	{
	    if (rightPanel == null) 
	    {
	        System.out.println("Right Panel is NULL");
	        rightPanel = createRightPanel(); // Ensure initialization
	    }
	    
	    rightPanel.removeAll();

	    JPanel serviceRequestPanel = new JPanel();
	    serviceRequestPanel.setLayout(new BorderLayout());

	    DefaultTableModel serviceTableModel = new DefaultTableModel();
	    JTable serviceTable = new JTable(serviceTableModel);
	    JScrollPane scrollPane = new JScrollPane(serviceTable);

	    serviceTableModel.setColumnIdentifiers(new String[]{"Request ID", "User", "Type", "Status", "Date"}); 
	    
	    try 
	    {
	        dbcon = new JDBCConnection();
	        Connection conn = dbcon.con;
	        String query = "SELECT request_id, user, type, status, date FROM service_request";

	        PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();

	        serviceTableModel.setRowCount(0); // Clear Previous Data

	        while (rs.next()) 
	        {
	            serviceTableModel.addRow(new Object[]
	            {
	                rs.getString("request_id"),
	                rs.getString("user"),
	                rs.getString("type"),
	                rs.getString("status"),
	                rs.getString("date")
	            });
	        }
	        rs.close();
	        ps.close();
	    } 
	    catch (Exception ex) 
	    {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    String [] options = {"Pending","In Progress" ,"Resolved", "Completed"};
	    JComboBox<String> statusOptions = new JComboBox<String>(options);
	   
	    TableColumn statusColumn = serviceTable.getColumnModel().getColumn(3);
	    statusColumn.setCellEditor(new DefaultCellEditor(statusOptions));

	    // Add event listener to update status in the database
	    serviceTable.getModel().addTableModelListener(e -> {
	        int row = e.getFirstRow();
	        int column = e.getColumn();

	        if (column == 3) // Status column
	        {
	            String requestId = (String) serviceTableModel.getValueAt(row, 0);
	            String newStatus = (String) serviceTableModel.getValueAt(row, 3);
	            try 
	            {
	                Connection conn = dbcon.con;
	                String updateQuery = "UPDATE service_request SET status = ? WHERE request_id = ?";
	                PreparedStatement updatePs = conn.prepareStatement(updateQuery);
	                updatePs.setString(1, newStatus);
	                updatePs.setString(2, requestId);
	                updatePs.executeUpdate();
	                updatePs.close();
	            } 
	            catch (Exception ex) 
	            {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(serviceRequestPanel, "Failed to update status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    
	    serviceRequestPanel.add(scrollPane, BorderLayout.CENTER); // Correct variable name

	    JButton refreshButton = new JButton("Refresh");
	    refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
	    refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				showServiceRequestsPanel();
			}
		});

	    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    bottomPanel.add(refreshButton);
	    serviceRequestPanel.add(bottomPanel, BorderLayout.SOUTH);

	    rightPanel.add(serviceRequestPanel, BorderLayout.CENTER);
	    rightPanel.revalidate();
	    rightPanel.repaint();
	}


// BillGenerator GUI
	
	class BillGenerator extends JPanel
	{
		private  JTextField accountIdField, planBillField, dueFineField, taxField, totalAmountField;
		private JButton calculateTotalButton, generateBillButton;
		private Connection conn;
		static long transactionId;
		
		public BillGenerator()
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBorder(new EmptyBorder(20, 50, 20, 50));
			
			JLabel accountIdLabel = new JLabel("Account ID: ");
			JLabel planBillLabel = new JLabel("Plan Bill (Monthly): ");
			JLabel dueFineLabel = new JLabel("Due Fine ($): ");
			JLabel taxLabel = new JLabel("Tax: ");
			JLabel totallabel = new JLabel("Total Amount ($): ");
			
			accountIdField = new JTextField(10);
			accountIdField.setPreferredSize(new Dimension(200,20));
			planBillField = new JTextField("0.00", 10);
			planBillField.setPreferredSize(new Dimension(200,20));
			dueFineField = new JTextField("0.00", 10);
			dueFineField.setPreferredSize(new Dimension(200,20));
			taxField = new JTextField("39.00", 10);
			taxField.setPreferredSize(new Dimension(200,20));
			totalAmountField = new JTextField("0.00", 10);
			totalAmountField.setPreferredSize(new Dimension(200,20));
			totalAmountField.setEditable(false);
			
			
			calculateTotalButton = new JButton("Calculate Total");
			generateBillButton = new JButton("Generate Bill");
			
			JPanel formPanel = new JPanel(new GridLayout(5,2,10,10));
			formPanel.add(accountIdLabel);
			formPanel.add(accountIdField);
			formPanel.add(planBillLabel);
			formPanel.add(planBillField);
			formPanel.add(dueFineLabel);
			formPanel.add(dueFineField);
			formPanel.add(taxLabel);
			formPanel.add(taxField);
			formPanel.add(totallabel);
			formPanel.add(totalAmountField);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			buttonPanel.add(calculateTotalButton);
			buttonPanel.add(generateBillButton);
			
			add(formPanel);
			add(buttonPanel);
			
			
			calculateTotalButton.addActionListener(new ActionListener() 
			{
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					String accountNo = accountIdField.getText();
					JDBCConnection con = new JDBCConnection();
					
					try
					{
						ResultSet rs =  con.s.executeQuery("Select planPrice from broadband_plans where accountNo = '"+accountNo+"';");
						
						if(rs.next())
						{	
							double planPrice = Double.parseDouble(rs.getString("planPrice"));
							dueFine = Double.parseDouble(dueFineField.getText());
							
							planBillField.setText(Double.toString(planPrice));
							
							double total = planPrice+tax+dueFine;
							
							totalAmountField.setText(Double.toString(total));
					
						}	
						
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}

				} 

			});
			generateBillButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{	
					String accountNo = accountIdField.getText();
					JDBCConnection con = new JDBCConnection();
					
		    		double total = Double.parseDouble(totalAmountField.getText()); 
		    		String roundedStr = String.format("%.2f", total);
		    		System.out.println(roundedStr);  
		    		
		    		
		    		dueFine = Double.parseDouble(dueFineField.getText());
		    		
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
			    		ps.setDouble(5, dueFine);
			    		ps.setDouble(6, tax);
			    		ps.setString(7, today);       // Date should be in 'YYYY-MM-DD' format

			    		int i = ps.executeUpdate();

			    		if(i > 0)
						{
			    			JOptionPane.showMessageDialog(AccountDetails.this, "Bill Generated Successfully !!!", "Success",JOptionPane.PLAIN_MESSAGE);
						}	
						else
						{
							JOptionPane.showMessageDialog(AccountDetails.this, "Bill was not Generated !!!", "Error",JOptionPane.PLAIN_MESSAGE);
						}	
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		
//		 private void fetchUserDetails() 
//		 {
//		        String accountId = accountIdField.getText();
//		        String query = "SELECT billAmount, dueFine FROM bill WHERE accountNo = ?";
//		        try (PreparedStatement ps = conn.prepareStatement(query)) 
//		        {
//		            ps.setString(1, accountId);
//		            ResultSet rs = ps.executeQuery();
//		            if (rs.next()) 
//		            {
//		            	transactionId= generateTransactionId();
//		                planBillField.setText(rs.getString("billAmount"));
//		                dueFineField.setText(rs.getString("dueFine"));
//		            } 
//		            else 
//		            {
//		                JOptionPane.showMessageDialog(this, "Account ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
//		            }
//		        } 
//		        catch (SQLException ex) 
//		        {
//		            ex.printStackTrace();
//		        }
//		  }
		
//		private void calculateTotal() 
//		{
//	        try 
//	        {
//	            double planBill = Double.parseDouble(planBillField.getText());
//	            double dueFine = Double.parseDouble(dueFineField.getText());
//	            double stateTax = Double.parseDouble(taxField.getText());
//	            double totalAmount = planBill + dueFine + stateTax;
//	            totalAmountField.setText(String.valueOf(totalAmount));
//	        } 
//	        catch (NumberFormatException ex) 
//	        {
//	            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
//	        }  
//	    }
//		
//		private void generateBill() 
//		 {
//		        String accountId = accountIdField.getText();
//		        String totalAmount = totalAmountField.getText();
//		        if (accountId.isEmpty() || totalAmount.isEmpty()) 
//		        {
//		            JOptionPane.showMessageDialog(this, "Please complete all fields.", "Error", JOptionPane.ERROR_MESSAGE);
//		            return;
//		        }
//		        String query = "INSERT INTO bill (accountNo, billAmount, date) VALUES (?, ?, NOW())";
//		        try (PreparedStatement ps = conn.prepareStatement(query)) 
//		        {
//		            ps.setString(1, accountId);
//		            ps.setString(2, totalAmount);
//		            ps.executeUpdate();
//		            JOptionPane.showMessageDialog(this, "Bill generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//		        } 
//		        catch (SQLException ex) 
//		        {
//		            ex.printStackTrace();
//		        }
//		   }
		
		 public void generateTransactionId()
		 {
		 	double max = 1000000000;
		 	double min = 1;
		 	
		 	transactionId = (long) (Math.random()*(max-min)+1);
		 }
	}
	
	
class Profile extends JPanel
{

	private JTextField adminIdField, nameField, emailField,roleField;
	private JButton updateProfile;
	
	public Profile()
	{
		initComponents();
		addComponents();
	}
	
	public void addComponents()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(new Color(240, 240, 240));
		mainPanel.setOpaque(false);
		
		mainPanel.add(Box.createVerticalStrut(50));
		addRow("Admin ID: ", adminIdField, mainPanel);
		mainPanel.add(Box.createVerticalStrut(10));
		addRow("Name: ", nameField, mainPanel);
		mainPanel.add(Box.createVerticalStrut(10));
		addRow("Email: ", emailField, mainPanel);
		mainPanel.add(Box.createVerticalStrut(10));
		addRow("Role: ", roleField, mainPanel);
		mainPanel.add(Box.createVerticalStrut(10));
		
		updateProfile.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(updateProfile);
	
		add(mainPanel);
	}
	
	public void initComponents()
	{
		
		adminIdField = createStyledTextField(20);
		nameField = createStyledTextField(20);
		emailField = createStyledTextField(20);
		roleField = createStyledTextField(20);
		
		updateProfile = createStyledButton("Update Profile");
	}
	
	public JTextField createStyledTextField(int columns)
	{
		JTextField textfield = new JTextField(columns);
		textfield.setLayout(new FlowLayout());
		textfield.setFont(new Font("Arial", Font.PLAIN, 14));
		textfield.setBackground(Color.WHITE);
		textfield.setForeground(Color.BLACK);
		return textfield;
	}
	
	public JButton createStyledButton(String text)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 18));
		button.setForeground(Color.BLACK);
		button.setOpaque(false);
		button.setFocusPainted(false);
		return button;
	}
	
	public void addRow(String label, JComponent component, JPanel formPanel)
	{
		JPanel rowPanel = new JPanel();
		//rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
		rowPanel.setLayout(new GridLayout(1, 2, 2, 2));
		rowPanel.setOpaque(false);
		
		JLabel thelabel = new JLabel(label);
		thelabel.setForeground(Color.BLACK);
		thelabel.setFont(new Font("Arial", Font.BOLD, 14));
		thelabel.setPreferredSize(new Dimension(100, 25));
		
		component.setPreferredSize(new Dimension(180, 25));
		
		rowPanel.add(thelabel);
		rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		rowPanel.add(component);
		
		formPanel.add(rowPanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
}

	public static void main(String[] args) 
	{
		new AccountDetails();
	}
}