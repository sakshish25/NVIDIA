package Nvdia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserRegistretion extends JFrame {
	private JTextField usernamefield, mobilenofield;
	private JComboBox<String> cities;
	private JCheckBox termsbox;
	private JButton signUpButton, SignInButton;
	
	public UserRegistretion()
	{
		setUpFrame();
		initComponents();
		addComponents();
	}
	
	public void setUpFrame()
	{
		setSize(1920, 1080);
		setTitle("Registration Page");
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(bgImage());
	}
	
	public JPanel bgImage()
	{
		return new JPanel()
		{
			protected void paintComponent(Graphics g) 
			{
				ImageIcon icon = new ImageIcon("../oops/src/Nvdia/WhatsApp Image 2025-02-10 at 15.08.42_39861d98.jpg");
                Image image = icon.getImage();
                
                double panelWidth = getWidth();
                double panelHeight = getHeight();
                double imageWidth = image.getWidth(this);
                double imageHeight = image.getHeight(this);
                double scaled = Math.max(panelWidth / imageWidth, panelHeight / imageHeight);
                
                int scaledWidth = (int) (scaled * imageWidth);
                int scaledHeight = (int) (scaled * imageHeight);
                int x = (int) ((panelWidth - scaledWidth) / 2);
                int y = (int) ((panelHeight - scaledHeight) / 2);
                
                g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
                g.setColor(new Color(0, 0, 0, 150)); // Transparent overlay
                g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
	}

	public void addComponents()
	{
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);
		mainPanel.add(Box.createVerticalStrut(50));
		
		JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setAlignmentX(CENTER_ALIGNMENT);
	
		mainPanel.add(title);
		
		mainPanel.add(Box.createVerticalStrut(50));
		rowForm("Username", usernamefield, mainPanel);
		
		mainPanel.add(Box.createVerticalStrut(30));
		rowForm("Mobile Number", mobilenofield, mainPanel);
		mainPanel.add(Box.createVerticalStrut(20));
//		rowForm("City",cities,mainPanel);
//		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		rowForm("City", cities, mainPanel);
		
		mainPanel.add(Box.createVerticalStrut(30));
		mainPanel.add(createCheckBox("Agree to terms and conditions"));
		mainPanel.add(termsbox);
		mainPanel.add(Box.createVerticalStrut(30));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30,10));
		buttonPanel.setOpaque(false);
		buttonPanel.add(signUpButton);
		mainPanel.add(Box.createVerticalStrut(30));
		buttonPanel.add(SignInButton);
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
	}
	
	public JTextField createStyledTextField(int width)
	{
		JTextField textfield = new JTextField(width);
		textfield.setFont(new Font("Arial", Font.PLAIN, 14));
		textfield.setBackground(new Color(255, 255, 255));
		textfield.setForeground(new Color(33, 33, 33));
		textfield.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		textfield.setAlignmentX(CENTER_ALIGNMENT);
		
		textfield.setMaximumSize(new Dimension(300, 30));  // Limits max size
		textfield.setPreferredSize(new Dimension(250, 30)); // Preferred width & height
		textfield.setMinimumSize(new Dimension(200, 30));  // Ensures it doesn't shrink too much
		return textfield;
		
	}
	
	public void createComboBox(JComboBox<String> city)
	{
		city.setFont(new Font("Arial", Font.BOLD, 14));
		city.setBackground(new Color(33, 171, 230));
		city.setForeground(Color.WHITE);
		city.setFocusable(false);
		
		city.setMaximumSize(new Dimension(300, 30));  // Limits max size
		city.setPreferredSize(new Dimension(250, 30)); // Preferred width & height
		city.setMinimumSize(new Dimension(200, 30));  // Ensures it doesn't shrink too much
		
	}
	
	public void initComponents()
	{
		usernamefield = createStyledTextField(20);
		mobilenofield = createStyledTextField(20);
		
		String[] c = {"Select City", "Pune", "Delhi", "Banglore", "Mumbai", "Chennai", "Hyderabad"};
		cities = new JComboBox<String>(c);
		createComboBox(cities);
		
		termsbox = createCheckBox("Agree to terms and conditions");
		
		signUpButton = createStyledButton("Sign Up", Color.BLUE);
		
		signUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new RegistretionDB();
				JOptionPane.showMessageDialog(SignInButton, "Sign in successfully ","success", JOptionPane.OK_OPTION);
				
			}
		});
		SignInButton = createStyledButton("Sign In", Color.RED);
	}
	
	public void rowForm(String label, JComponent component, JPanel formPanel)
	{
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
		rowPanel.setOpaque(false);
		
		JLabel theLabel = new JLabel(label);
		theLabel.setForeground(Color.WHITE);
		theLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		theLabel.setPreferredSize(new Dimension(50,20));
		
		rowPanel.add(theLabel);
		//rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		rowPanel.add(component);
		
		formPanel.add(rowPanel);
		//formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private JCheckBox createCheckBox(String text)
	{
		termsbox = new JCheckBox(text);
		termsbox.setFont(new Font("Arial", Font.PLAIN, 14));
		termsbox.setForeground(Color.WHITE);
		termsbox.setBackground(new Color(30, 30, 30));
		termsbox.setOpaque(false);
		termsbox.setAlignmentX(CENTER_ALIGNMENT);
		return termsbox;
	}
	
	private JButton createStyledButton(String text, Color borderColor)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 18));
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
		button.setAlignmentX(CENTER_ALIGNMENT);
		return button;
	}
	
	public static void main(String[] args) 
	{
		new UserRegistretion();
	}

}

