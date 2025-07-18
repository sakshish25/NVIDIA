package Nvdia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

public class ServiceRequest extends JFrame {

	JTextField Requestid, user;
	JButton submitRequest, clearForm, goBack;
	JSpinner dateSpinner;
	private JComboBox<String> connectionIssue;

	ServiceRequest() {
		createNewFrame();
		initComponents();
		addComponents();
		handleSubmitRequest();
	}

	public void createNewFrame() {
		setSize(1920, 1080);
	        setTitle("NvidiaBroadband-Service Request");
	        setVisible(true);
	       // setContentPane(createBgImage());
	        setContentPane(new GradientPanel());
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

	private JPanel createPromotionPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addComponents() {

		setLayout(new BorderLayout());

		// Creating mainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);
		mainPanel.add(Box.createVerticalStrut(50));

		JLabel title = new JLabel("Service Request Form", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(title);
		mainPanel.add(Box.createVerticalStrut(160));
		rowForm("Request ID: ", Requestid, mainPanel);

		mainPanel.add(Box.createVerticalStrut(20));
		rowForm("User : ", user, mainPanel);

		mainPanel.add(Box.createVerticalStrut(20));
		String[] c = { "Connection Issue", "Speed Problem", "Bill Related", "Technical Support", "Plan Upgrade",
				"General Enquiry" };
		connectionIssue = new JComboBox<String>(c);
		createComboBox(connectionIssue);
		rowForm("Request Type :", connectionIssue, mainPanel);
		mainPanel.add(Box.createVerticalStrut(20));
		addDatespinner();
		rowForm("Date:", dateSpinner, mainPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Horizontal alignment with spacing
		buttonPanel.setOpaque(false); // Keep the background transparent

		// Adding buttons to the row
		buttonPanel.add(submitRequest);
		buttonPanel.add(clearForm);
		buttonPanel.add(goBack);

		// Add the button panel to the main panel
		mainPanel.add(Box.createVerticalStrut(30)); // Space before buttons
		mainPanel.add(buttonPanel);
		add(mainPanel);

	}

	public void addDatespinner() {
		dateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");

		dateSpinner.setEditor(dateEditor);
		dateSpinner.setPreferredSize(new Dimension(200, 10));
		dateSpinner.setMaximumSize(new Dimension(700, 30)); // Limits max size
		dateSpinner.setPreferredSize(new Dimension(550, 30)); // Preferred width & height
		dateSpinner.setMinimumSize(new Dimension(200, 30));
	}

	public void createComboBox(JComboBox<String> city) {
		connectionIssue.setFont(new Font("Arial", Font.BOLD, 14));
		connectionIssue.setBackground(new Color(255, 255, 255));
		connectionIssue.setForeground(Color.BLACK);
		connectionIssue.setFocusable(false);

		connectionIssue.setMaximumSize(new Dimension(700, 30)); // Limits max size
		connectionIssue.setPreferredSize(new Dimension(550, 30)); // Preferred width & height
		connectionIssue.setMinimumSize(new Dimension(200, 30)); // Ensures it doesn't shrink too much

	}

	public void initComponents() {
		Requestid = createStyledTextField(20);
		int randomId = (int) (Math.random() * 9000) + 1000;
		Requestid.setText(String.valueOf(randomId));
		user = createStyledTextField(20);

		submitRequest = createStyledButtons("Submit Request");
		clearForm = createStyledButtons("clear Form");
		goBack = createStyledButtons("Go Back");

	}

	public void addButtons(JButton button) {

		JPanel rowpanel = new JPanel();
		rowpanel.setLayout(new BoxLayout(rowpanel, BoxLayout.X_AXIS));
		rowpanel.setOpaque(false);
	}

	private JButton createStyledButtons(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 15));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(50, 140, 200));
		button.setOpaque(true);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		addButtons(button);
		return button;
	}

	public JTextField createStyledTextField(int columns) {
		JTextField textfield = new JTextField(columns);
		textfield.setLayout(new FlowLayout(FlowLayout.RIGHT));
		textfield.setFont(new Font("Arial", Font.PLAIN, 18));
		textfield.setBackground(new Color(255, 255, 255));
		textfield.setForeground(new Color(33, 33, 33));
		textfield.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));

		textfield.setMaximumSize(new Dimension(700, 30)); // Limits max size
		textfield.setPreferredSize(new Dimension(550, 30)); // Preferred width & height
		textfield.setMinimumSize(new Dimension(200, 30)); // Ensures it doesn't shrink too much

		return textfield;
	}

	public void rowForm(String label, JComponent component, JPanel formPanel) {
		JPanel rowpanel = new JPanel();
		rowpanel.setLayout(new BoxLayout(rowpanel, BoxLayout.X_AXIS));
		rowpanel.setOpaque(false);

		JLabel theLabel = new JLabel(label);
		theLabel.setForeground(Color.WHITE);
		theLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		theLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		theLabel.setPreferredSize(new Dimension(120, 25));

		rowpanel.add(theLabel);
		rowpanel.add(Box.createRigidArea(new Dimension(10, 0)));
		rowpanel.add(component);

		theLabel.setBounds(20, 5, 0, 0); // X=20, Y=5, Width=120, Height=25
		component.setBounds(250, 5, 200, 30); // X=250 (Right-aligned), Width=200, Height=30

		formPanel.add(rowpanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}

	class GradientPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint gradient = new GradientPaint(0, getHeight(), new Color(10, 40, 80), // Dark Blue at Bottom
					0, 0, new Color(50, 140, 200) // Light Blue at Top
			);
			g2d.setPaint(gradient);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void handleSubmitRequest() {

		submitRequest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String reqId = Requestid.getText();
				String username = user.getText().trim();
				Date date = (Date) dateSpinner.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Correct MySQL format
				String formattedDate = sdf.format(date);  // Convert Date to String
				String connIssue = (String) connectionIssue.getSelectedItem();

				if (!(username.isEmpty()) || date != null) {
					String un = SignInPage.usernamefield.getText().trim();
					if (!(username.equals(un))) {
						showErrorMsg("Enter the valid username ");
						return;
					} else {
						if (connectionIssue.equals("Connection Issue")) {
							showErrorMsg("Please Select issue type");
							return;
						}
						else {

						JDBCConnection con = new JDBCConnection();

						if (con.con == null) {
							showErrorMsg("Database Connection Failed");
							return;
						}

						try {

							String query1 = "SELECT * FROM service_request WHERE username='" + username + "';";
							ResultSet rs = con.s.executeQuery(query1);

							if (rs.next()) {
								showErrorMsg("Request is Already raised wait until it resolved ");
							}

							else {
								// Inserting the query to database after raising request
								String insertQuery = "INSERT INTO service_request (username, date , type , request_id) VALUES ('"
										+ username + "', '" + formattedDate + "', '" + connIssue + "','" + reqId + "' );";
								int rowsAffected = con.s.executeUpdate(insertQuery);

								if (rowsAffected > 0) {
									JOptionPane.showConfirmDialog(rootPane, "Registration Successfull!", "Success",
											JOptionPane.YES_OPTION);
								} else {
									showErrorMsg("Registration Failed! Please Try Again.");
								}
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
						
					}
				} 
				else {

					showErrorMsg("All fields are mandatory");
				}
			}
		});
		
		clearForm.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        user.setText("");
		        connectionIssue.setSelectedIndex(0); // Reset dropdown to first option

		        // Set dateSpinner to today's date
		        Calendar calendar = Calendar.getInstance();
		        Date today = calendar.getTime();
		        dateSpinner.setValue(today); // Correctly update dateSpinner
		    }
		});
	}

	public void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		new ServiceRequest();
	}
}
