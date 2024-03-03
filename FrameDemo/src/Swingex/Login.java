package Swingex;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Login extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args)
//	{
//		EventQueue.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					Login frame = new Login();
//					frame.setVisible(true);
//				} 
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws NoSuchAlgorithmException 
	 */
	
	public String passwordhashing(String password) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		byte [] hasgBytes = messageDigest.digest(password.getBytes());
		
		StringBuilder stringBuilder = new StringBuilder();
		for(byte b : hasgBytes)
		{
			stringBuilder.append(String.format("%02x", b));
		}
		return stringBuilder.toString();
	}
	
	
	public void openMainPage()
	{
        Main main = new Main();
        main.setVisible(true);
        this.dispose(); // Closing the current frame
    }
	
	public Login()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(111, 25, 105, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(30, 79, 95, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(30, 123, 95, 23);
		contentPane.add(lblNewLabel_2);
		
		userName = new JTextField();
		userName.setBounds(152, 82, 165, 20);
		contentPane.add(userName);
		userName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(152, 126, 165, 20);
		contentPane.add(password);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
//				database connections

				String url = "jdbc:mysql://localhost:3306/registerlogin";
				String dbuserName = "root";
				String dbpassWord = "root123";
				
				String hashedPassword = null;
				try 
				{
					hashedPassword = passwordhashing(new String(password.getPassword()));
				} 
				catch
				(NoSuchAlgorithmException e1) 
				{
					e1.printStackTrace();
				}
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url, dbuserName, dbpassWord);
					
					String query = "SELECT * FROM Users WHERE UserName = ? AND Pass = ?";
				    PreparedStatement preparedStatement = connection.prepareStatement(query);

					preparedStatement.setString(1, userName.getText());
					preparedStatement.setString(2, hashedPassword);

				    ResultSet resultSet = preparedStatement.executeQuery();

				    if (resultSet.next())
				    {
				        System.out.println("Login Successful!");
				    	JOptionPane.showMessageDialog(btnSubmit, "Successfully Logged in!");
				    	userName.setText("");
						password.setText("");
						openMainPage();
						return;
				    } 
				    else
				    {
				        System.out.println("Invalid Credentials!");
				    	JOptionPane.showMessageDialog(btnSubmit, "Invalid Credentials!");
				    	userName.setText("");
						password.setText("");
						return;
				    }
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSubmit.setBounds(48, 175, 100, 40);
		contentPane.add(btnSubmit);
		
		JButton btnClr = new JButton("CLEAR");
		btnClr.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				userName.setText("");
				password.setText("");
			}
		});
		btnClr.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClr.setBounds(191, 175, 89, 40);
		contentPane.add(btnClr);
	}
}


