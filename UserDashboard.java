package Nvdia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UserDashboard extends JFrame
{
	static String status ;
	static long phoneNo;
	static String planType;
	static double planPrice;
	static String planData;
	static String speed;
	static String planDuration;
	static long accountNo;
	static JPanel proP;
	
	public UserDashboard() throws SQLException
	{
		setUpFrame();
		JPanel addHeaderPanel = createHeader();				// We have not used addComponents method so create separate panel to call createHeader().
		add(addHeaderPanel, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel rightPanel = createAccountDetailsPanel();
		mainPanel.add(rightPanel, BorderLayout.CENTER);
		
		JPanel leftPanel = createPromotionPanel();
		mainPanel.add(leftPanel, BorderLayout.WEST);
		
		add(mainPanel, BorderLayout.CENTER);
		
		
	}	
		
	
	public void setUpFrame()
	{
		setSize(1920, 1080);
		setLayout(new BorderLayout());
		setTitle("Nvidia-Fibernet");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(240, 240, 240));
		setVisible(true);
	}
		
	public JButton createStyledButton(String text)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setForeground(new Color(51, 51, 51));
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		button.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				button.setFont(new Font("Arial", Font.BOLD, 14));   // Always first add font and then color
				button.setForeground(new Color(255, 51, 85));
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				button.setFont(new Font("Arial", Font.PLAIN, 14));
				button.setForeground(new Color(51, 51, 51));
			}
		});
		
		button.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				switch (text) 
				{
	               case "Pay Bills":
	                   SwingUtilities.invokeLater(new Runnable() 
	                   {
	                       public void run() 
	                       {
	                           try 
	                           {
	                               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                               PaymentGUI p = new PaymentGUI();
	                           } 
	                           catch (Exception ex) 
	                           {
	                               ex.printStackTrace();
	                           }
	                          // new PaymentGUI().setVisible(true);
	                       }
	                   });
	                   break;
	                   
	               case "Service Requests":
	            	   
	                   if (UserDashboard.status == "INACTIVE") 			// 
	                   {
	                       JOptionPane.showMessageDialog(UserDashboard.this,"Inactive Plan. Service Request Cannot Be Raised","Failed",JOptionPane.INFORMATION_MESSAGE);
	                   } 
	                   else 
	                   {
	                	   new ServiceRequest();
	                   }
	                   break;
	               
	               case "New Connection":   
	                BuyConnection.getPhoneNo = phoneNo;
						try 
						{
							new BuyConnection();
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException e1) 
						{
							e1.printStackTrace();
						} 
	                 
	                  // new BuyConnection();
	                   break;
	                   
	               case "FAQ":
	                   
	            	  FAQ  f = new FAQ();
	                   break;
	           }

			}
		});
		
		return button;
	}
	
	public JPanel createHeader()
	{
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		
		JLabel title = new JLabel("Nvidia Fibernet");
		title.setFont(new Font("Arial",Font.BOLD, 24));
		title.setForeground(new Color(255,51,85));
		
		
		JPanel navMenu = new JPanel();
		navMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		navMenu.setBackground(Color.WHITE);
		
		String [] menuItems = {"Pay Bills", "Service Requests", "New Connection", "FAQ"};
		for (String item : menuItems) 
		{
			JButton menuButtons = createStyledButton(item);
			navMenu.add(menuButtons);
		}
		
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dropDownPanel.setBackground(Color.WHITE);
		
		JButton dropDownButton = new JButton("Options");
		dropDownButton.setFont(new Font("Arial", Font.PLAIN, 14));
		dropDownButton.setForeground(new Color(51, 51, 51));
		dropDownButton.setBackground(Color.WHITE);
		dropDownButton.setFocusPainted(false);
		dropDownButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		
		JPopupMenu menus = new JPopupMenu();
		JMenuItem profileOption = new JMenuItem("Profile");
		JMenuItem logoutOption = new JMenuItem("LogOut");
		
		profileOption.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 new Profile();
			}
		});
			
		logoutOption.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int choice = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Logout ?","Confirm", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE);
				
				if(choice == JOptionPane.YES_OPTION)
				{
						dispose();
						new HomePage();
				}
			}
		});
		
		menus.add(profileOption);
		menus.add(logoutOption);
		
		dropDownButton.addActionListener(new ActionListener() 
		{
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {
	            menus.show(dropDownButton, 0, dropDownButton.getHeight());
	        }
	    });
		
		dropDownPanel.add(dropDownButton);
		headerPanel.add(title, BorderLayout.WEST);
		
		headerPanel.add(navMenu, BorderLayout.CENTER);
		headerPanel.add(dropDownPanel, BorderLayout.EAST);
		
		return headerPanel;
	}
	
	public boolean getStatus() throws SQLException
	{
		JDBCConnection con = new JDBCConnection();
		String query ="select * from broadband_plans where phno='"+ phoneNo +"'"+";";
		
		ResultSet rs = con.s.executeQuery(query);
		if(rs.next())
		{
			planType = rs.getString("planType");
			planPrice =	rs.getDouble("planPrice");
			planData = 	rs.getString("planData");
			speed = rs.getString("speed");
			planDuration = rs.getString("planDuretion");
			accountNo = rs.getLong("accountNo");
			return true;
		}
		else
		{
			return false;
		}	
	}

	public JPanel createAccountDetailsPanel() throws SQLException
	{
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		detailsPanel.setBackground(Color.WHITE);
		detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		if(getStatus())
		{
			status = "ACTIVE";
		}
		else
		{
			status = "INACTIVE";
			planType = "NA";
			speed = "NA";
			planData = "NA";
		}
		
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBackground(Color.WHITE);
		
		JLabel statusLabel = new JLabel(UserDashboard.status);
		statusLabel.setBackground(Color.WHITE);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
		statusLabel.setForeground(new Color(40,167,69));
		
		statusPanel.add(statusLabel);
		
		JPanel planPanel = new JPanel();
		planPanel.setLayout(new BoxLayout(planPanel, BoxLayout.Y_AXIS));
		planPanel.setBackground(Color.WHITE);
		
		JLabel planName = new JLabel(UserDashboard.planType);
		planName.setFont(new Font("Arial", Font.BOLD, 18));
		
		planPanel.add(planName);
		
		JPanel duePanel = new JPanel();
		duePanel.setLayout(new BoxLayout(duePanel, BoxLayout.Y_AXIS));
		duePanel.setBackground(Color.WHITE);
		
		JLabel dueDate = new JLabel();
		dueDate.setFont(new Font("Arial",Font.BOLD,14));
		
		duePanel.add(dueDate);
		
		JLabel dueDateLabel = new JLabel("25th Feb 2025");
		dueDateLabel.setFont(new Font("Arial",Font.PLAIN, 12));
		duePanel.add(dueDateLabel);
		
		
		JPanel usagePanel = new JPanel();
		usagePanel.setBackground(Color.WHITE);
		
		JLabel usageLabel = new JLabel("46.5 GB Of 150 GB");
		usageLabel.setFont(new Font("Arial",Font.BOLD,12));
		usagePanel.add(usageLabel);
		
		JPanel upgradePanel = new JPanel();
		upgradePanel.setBackground(new Color(255,240,240));
		detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel upgradeLabel = new JLabel("Upgrade Plan");
		upgradeLabel.setFont(new Font("Arial",Font.PLAIN,12));
		upgradePanel.add(upgradeLabel);	
		
		JButton upgradeButton = new JButton("upgrade");
		upgradeButton.setBackground(Color.WHITE);
		upgradePanel.add(upgradeButton);
		
		detailsPanel.add(statusPanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		detailsPanel.add(planPanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		detailsPanel.add(duePanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		detailsPanel.add(usagePanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		detailsPanel.add(upgradePanel);
		
		
		return detailsPanel;
	}
	
	public static JPanel createPromotionPanel() {
		
	    proP = new JPanel();
		proP.setBackground(Color.white);

  proP.setLayout(new BorderLayout());
  
		JPanel gradP = new JPanel();

		JPanel gradientPanel = new JPanel() {
	         @Override
	         protected void paintComponent(Graphics g) {
	             super.paintComponent(g);
	             Graphics2D g2d = (Graphics2D) g;
	             GradientPaint gradient = new GradientPaint(
	                     0, 0, new Color(255, 100, 150),
	                     getWidth(), getHeight(), new Color(255, 150, 200)
	             );
	             g2d.setPaint(gradient);
	             g2d.fillRect(0, 0, getWidth(), getHeight());
	         }
	     };

	     JLabel promoText = new JLabel("<html><div style='text-align: center;'>" +
	             "Pay your Nvidia Fibernet<br/>bill via CRED UPI and Get<br/>" +
	             "<span style='font-size: 24px;'>up to Rs. 250*</span><br/>" +
	             "Cashback</div></html>");
	     promoText.setHorizontalAlignment(JLabel.CENTER);
	     promoText.setFont(new Font("Arial", Font.BOLD, 18));
	     promoText.setForeground(Color.WHITE);
	     gradientPanel.setLayout(new GridBagLayout());
	     gradientPanel.add(promoText);
          proP.add(gradientPanel,BorderLayout.CENTER);
		return proP;
	} 
	
	public static void main(String[] args) throws SQLException 
	{
		new UserDashboard();
	}

}