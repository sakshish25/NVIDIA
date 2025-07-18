
	package Nvdia;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Component;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;

	import javax.swing.BorderFactory;
	import javax.swing.Box;
	import javax.swing.BoxLayout;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.SwingConstants;
	import javax.swing.SwingUtilities;
	import javax.swing.UIManager;
	import javax.swing.UnsupportedLookAndFeelException;
	import javax.swing.border.Border;

import Project2.SignInPage;

	public class HomePage extends JFrame {
	    private JButton existingCustomer, newConnection, adminSignIn;

	    public HomePage() {
	        setUpFrame();
	        initComponents(); // Initialize buttons before adding components
	        addComponents();
	        setUpListeners();
	    }

	    // Setup Frame and its properties
	    public void setUpFrame() {
	    	setSize(1920, 1080);
	        setTitle("Nvidia- Home Page");
	        setVisible(true);
	        setContentPane(createBgImage());
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }

	    // Create Background Image with overlay
	    public JPanel createBgImage() 
	    {
	        return new JPanel() 
	        {
	            @Override
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

	    // Add components to the frame
	    public void addComponents() 
	    {
	        setLayout(new BorderLayout());

	        // Creating mainPanel
	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	        mainPanel.setOpaque(false);

	        // Creating title Panel
	        JPanel titlePanel = new JPanel();
	        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	        titlePanel.setOpaque(false);
	        
	        JLabel title = new JLabel("Welcome To Nvidia Fibernet", SwingConstants.CENTER);
	        title.setForeground(Color.WHITE);
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        titlePanel.add(title);
	        titlePanel.setForeground(Color.WHITE);
	        titlePanel.setFont(new Font("Arial", Font.BOLD, 24));
	        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
	        

	        // Add vertical spacing before the title
	        mainPanel.add(Box.createVerticalStrut(50));
	        mainPanel.add(titlePanel);

	        // Add vertical spacing after the title
	        mainPanel.add(Box.createVerticalStrut(50));

	        // Creating button Panel with semi-transparent background
	        JPanel buttonPanel = new JPanel() 
	        {
	            @Override
	            protected void paintComponent(Graphics g) 
	            {
	                super.paintComponent(g);
	                g.setColor(new Color(0, 0, 0, 100));
	                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
	            }
	        };
	        buttonPanel.setOpaque(false);

	        // Add buttons to buttonPanel with vertical spacing
	        buttonPanel.add(existingCustomer);
	        buttonPanel.add(Box.createVerticalStrut(15));
	        buttonPanel.add(newConnection);
	        buttonPanel.add(Box.createVerticalStrut(15));
	        buttonPanel.add(adminSignIn);

	        // Center align Buttons
	        existingCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
	        newConnection.setAlignmentX(Component.CENTER_ALIGNMENT);
	        adminSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);

	        mainPanel.add(buttonPanel);

	        // Set content pane and add vertical space around the main panel
	        JPanel contentPane = (JPanel) getContentPane();
	        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	        contentPane.add(Box.createVerticalGlue());
	        contentPane.add(mainPanel);
	        contentPane.add(Box.createVerticalGlue());
	        setVisible(true);
	    }

	    // Create buttons with hover effect
	    private JButton createButtons(String buttonTitle, Color color) {
	        JButton button = new JButton(buttonTitle);
	        button.setSize(300, 50);
	        button.setForeground(Color.WHITE);
	        button.setFont(new Font("Arial", Font.BOLD, 15));
	        button.setBackground(color);
	        button.setFocusPainted(false);
	        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),BorderFactory.createEmptyBorder(12, 25, 12, 25)));
	        button.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                button.setBackground(color.brighter());
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                button.setBackground(color);
	            }
	        });
	        return button;
	    }

	    // Initialize buttons
	    public void initComponents() {
	        this.existingCustomer = createButtons("Existing-Customer", new Color(30, 144, 255));
	        this.newConnection = createButtons("New Connection", new Color(220, 20, 60));
	        this.adminSignIn = createButtons("Admin Sign-In", new Color(50, 205, 50));
	    }
	    
	    private void handleExistingUser()
	    {
//	    	int option = JOptionPane.showConfirmDialog(this, "Existing User?", "Confirm", JOptionPane.YES_NO_OPTION);
//	    	
//	    	if(option == JOptionPane.YES_NO_OPTION)
//	    	{
//	    		JOptionPane.showMessageDialog(null, "Successfully Logged in", "Success", JOptionPane.INFORMATION_MESSAGE);
//	    	}
	    	int choice = JOptionPane.showConfirmDialog(this, "Existing User? Go to SignInPage!" ,"Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			
			if(choice == JOptionPane.YES_OPTION)
			{
				SwingUtilities.invokeLater(new Runnable() 
				{
					
					@Override
					public void run() 
					{
						 new Nvdia.SignInPage();
						
					}
				});
				dispose();
			}
	    }
	    
	    private void handleNewConnection()
	    {
//	    	int option = JOptionPane.showConfirmDialog(this, "New User ?" , "Confirlm", JOptionPane.YES_NO_OPTION);
//	    	
//	    	if(option == JOptionPane.YES_NO_OPTION)
//	    	{
//	    		JOptionPane.showMessageDialog(null, "Login Successfull", "Success", JOptionPane.INFORMATION_MESSAGE);
//	    	}
	    	
	    	int choice = JOptionPane.showConfirmDialog(this, "New User ? Register Account first!" ,"Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			
			if(choice == JOptionPane.YES_OPTION)
			{
				SwingUtilities.invokeLater(new Runnable() 
				{
					
					@Override
					public void run() 
					{
						new SignUp();
						
					}
				});
				dispose();
			}
	    }
	    
	    private void handleAdminSignIn()
	    {
	    	int option = JOptionPane.showConfirmDialog(this, "Admin Sign In ?" , "Confirlm", JOptionPane.YES_NO_OPTION);
	    	
	    	if(option == JOptionPane.YES_NO_OPTION)
	    	{
	    		SwingUtilities.invokeLater(new Runnable() 
				{
					
					@Override
					public void run() 
					{
						new AdminSignIn();
						
					}
				});
				dispose();
	    	}
	    }
	    
	    public void setUpListeners()
	    {
	    	existingCustomer.addActionListener(new ActionListener() 
	    	{
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					handleExistingUser();	
				}
				
			});
	    	
	    	newConnection.addActionListener(new ActionListener() 
	    	{
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					handleNewConnection();	
				}
				
			});
	    	
	    	adminSignIn.addActionListener(new ActionListener() 
	    	{
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					handleAdminSignIn();	
				}
				
			});
	    	
	    }

	    public static void main(String[] args) 
	    {
	        new HomePage();
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
