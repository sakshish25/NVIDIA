package Nvdia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AdminSignIn extends JFrame
{
	
	JTextField usernamefield;
	JPasswordField passwordfield;
	JButton signInButton, goBackButton, signUpButton;
	JCheckBox termsbox;
	static String AdminName ,adminPass ; 
	
	public AdminSignIn()
	{
		setUpFrame();
		initComponents();
		addComponents();
		setUpListeners();
	}
	
	public void setUpFrame()
	{
		setSize(1920, 1080);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(createBgImage());
		setVisible(true);
	}
	
	public JPanel createBgImage()
	{
		return new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g) 
			{
				ImageIcon icon = new ImageIcon("../oops/src/Nvdia/adminsiginup.jpg");
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
		
		// Creating mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.add(Box.createVerticalStrut(50));
        
        JLabel title = new JLabel("Admin Sign-In", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(50));
        rowForm("Username: ", usernamefield, mainPanel);
        
        mainPanel.add(Box.createVerticalStrut(30));
        rowForm("Password: ", passwordfield, mainPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        termsbox.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(termsbox);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30)); 			// Flowlayout for buttons. so buttons will flown away from each other
		buttonPanel.setOpaque(false);
		buttonPanel.add(signInButton);
		buttonPanel.add(signUpButton);
		buttonPanel.add(goBackButton);
		
		mainPanel.add(buttonPanel);
        
        add(mainPanel);
	
	}
	
	public JTextField createStyledTextField(int columns)
	{
		JTextField textfield = new JTextField(columns);
		textfield.setLayout(new FlowLayout(FlowLayout.RIGHT));
		textfield.setFont(new Font("Arial", Font.PLAIN, 14));
		textfield.setBackground(new Color(255, 255,255));
		textfield.setForeground(new Color(33, 33, 33));
		textfield.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		
		 textfield.setMaximumSize(new Dimension(300, 30));  // Limits max size
		 textfield.setPreferredSize(new Dimension(250, 30)); // Preferred width & height
		 textfield.setMinimumSize(new Dimension(200, 30));  // Ensures it doesn't shrink too much
		
		return textfield;
	}
	
	public JPasswordField createStyledPasswordTextField(int columns)
	{
		JPasswordField password = new JPasswordField(columns);
		password.setLayout(new FlowLayout(FlowLayout.RIGHT));
		password.setFont(new Font("Arial", Font.PLAIN, 14));
		password.setBackground(new Color(255, 255,255));
		password.setForeground(new Color(33, 33, 33));
		password.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		
		 password.setMaximumSize(new Dimension(300, 30));  // Limits max size
		 password.setPreferredSize(new Dimension(250, 30)); // Preferred width & height
		 password.setMinimumSize(new Dimension(200, 30));  // Ensures it doesn't shrink too much
		
		return password;
	}
	
	public void initComponents()
	{
		usernamefield = createStyledTextField(20);
		passwordfield = createStyledPasswordTextField(20);
		
		termsbox = createstyledCheckBox("Agree to terms and conditions");
		
		signInButton = createStyledButtons("Sign-In", Color.BLACK);
		signUpButton = createStyledButtons("Sign-Up", Color.BLACK);
		goBackButton = createStyledButtons("Go Back", Color.BLACK);
	
	}
	
	public void rowForm(String label, JComponent component, JPanel formPanel)
	{
		JPanel rowpanel = new JPanel();
		rowpanel.setLayout(new BoxLayout(rowpanel, BoxLayout.X_AXIS));
		rowpanel.setOpaque(false);
		
		JLabel theLabel = new JLabel(label);
		theLabel.setForeground(Color.WHITE);
		theLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		theLabel.setPreferredSize(new Dimension(120, 25));
		
		rowpanel.add(theLabel);
		rowpanel.add(Box.createRigidArea(new Dimension(10, 0)));
		rowpanel.add(component);
		
		formPanel.add(rowpanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	public JCheckBox createstyledCheckBox(String text)
	{
		JCheckBox checkbox = new JCheckBox(text);
		checkbox.setForeground(Color.WHITE);
		checkbox.setFont(new Font("Arial", Font.PLAIN, 14));
		checkbox.setOpaque(false);
		checkbox.setFocusPainted(false);
		return checkbox;
	}
	
	private JButton createStyledButtons(String text, Color backcolor)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 15));
		button.setForeground(Color.WHITE);
		button.setBackground(backcolor);
		button.setOpaque(true);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2), BorderFactory.createEmptyBorder(12, 25, 12, 25)));
		
		button.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				button.setBackground(backcolor.brighter());
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				button.setBackground(backcolor);
			}
		});
		
		return button;
	}
	
	public void handleSignIn()
	{
		if(!(termsbox.isSelected()))
		{
			showErrorMsg("Agree to terms and conditions first!!");
			return;
		}
		
		String uname = usernamefield.getText().trim();
		String pass = passwordfield.getText().trim();
		
		if(uname.isEmpty() || pass.isEmpty())
		{
			showErrorMsg("All fields are required to fill");
			return;
		}
		
		JDBCConnection con = new JDBCConnection();
		
		if(con.con==null)
		{
			showErrorMsg("Database Connection Failed");
			return;
		}
		else
		{
			System.out.println("Database connection established successfull!");
		}
		
		System.out.println(pass);
		String query ="SELECT * FROM admin WHERE username='" + uname + "' AND password='" + pass + "';";

		System.out.println("Executing query: "+query);
		
		try
		{
			ResultSet rs = con.s.executeQuery(query);
			if(rs.next())
			{
				AdminName = uname;
				adminPass = pass;
				JOptionPane.showMessageDialog(this, "Welcome "+ uname, "Success", JOptionPane.INFORMATION_MESSAGE);
				
				new AdminDashboard();
			}
			else
			{
				showErrorMsg("Admin not exists!");
			}
			rs.close();
			con.con.close();
		}
		catch(SQLException e)
		{
			showErrorMsg("Database Error! Please try again.");
			e.printStackTrace();
		}
	}
	
	public void handleSignUp()
	{
	    	
		int choice = JOptionPane.showConfirmDialog(this, "New Admin ? Register Account first!" ,"Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			
		if(choice == JOptionPane.YES_OPTION)
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
					
				@Override
				public void run() 
				{
					new AdminSignUpPage();
						
				}
			});
			dispose();
		}
	}
	
	public void handleGoBack()
	{
		int choice = JOptionPane.showConfirmDialog(this, "Go Back To Home Page?" ,"Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		
		if(choice == JOptionPane.YES_OPTION)
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				
				@Override
				public void run() 
				{
					new HomePage();
					
				}
			});
			dispose();
		}
	}
	
	public void setUpListeners()
	{
		signInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleSignIn();
				
			}
		});
		
		signUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleSignUp();
				
			}
		});
		
		goBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleGoBack();
				
			}
		});
	}
	
	public void showErrorMsg(String msg)
	{
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) 
	{
		new AdminSignIn();
		
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} 
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) 
				{
					e.printStackTrace();
				}
				
			}
			
		});
	}

}