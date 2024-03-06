package Swingex;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textMob;
	private JTextField textEmail;
	private JTextField textName;
	private JTextField textUserName;
	private JPasswordField pass;
	private JPasswordField conPass;

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
//					Register frame = new Register();
//					frame.setVisible(true);
//				} 
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public void openLoginPage()
	{
        Login login = new Login();
        login.setVisible(true);
        this.dispose(); // Closing the current frame
    }
	
	/**
	 * Create the frame.
	 */
	
//	Method to Hashing the Passwords
	public String passwordhashing(String password) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte [] hashBytes = messageDigest.digest(password.getBytes());
		
		StringBuilder stringBuilder = new StringBuilder();
		for(byte b : hashBytes)
		{
			stringBuilder.append(String.format("%02x", b));
		}
		return stringBuilder.toString();
	}
	
	public Register()
	{
		setTitle("Registration Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(41, 62, 79, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Email Id :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(41, 118, 79, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Mobile no. :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(41, 175, 95, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Username :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(357, 62, 104, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Password :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(357, 118, 95, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Confirm Password :");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(357, 175, 147, 14);
		contentPane.add(lblNewLabel_5);
		
		textMob = new JTextField();
		textMob.setBounds(143, 174, 147, 20);
		contentPane.add(textMob);
		textMob.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(143, 117, 147, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		textName = new JTextField();
		textName.setBounds(143, 61, 147, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		textUserName = new JTextField();
		textUserName.setBounds(514, 61, 147, 20);
		contentPane.add(textUserName);
		textUserName.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("New User Register");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_6.setBounds(261, 11, 175, 28);
		contentPane.add(lblNewLabel_6);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
//				database connections
				String url = "jdbc:mysql://localhost:3306/registerlogin";
				String dbuserName = "root";
				String dbpassWord = "root123";
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url, dbuserName, dbpassWord);
					
					//Checks if any fields are empty
					 if (textName.getText().isEmpty() || textMob.getText().isEmpty() || textEmail.getText().isEmpty() || textUserName.getText().isEmpty() || pass.getPassword().length == 0 || conPass.getPassword().length == 0)
					 {
	                        JOptionPane.showMessageDialog(btnSubmit, "Please fill in all fields.");
	                        return; // Exit the method early if any field is empty
	                 }
					

					 // Converting password and confirm password to String
					 	String password = passwordhashing(new String(pass.getPassword()));
	                    String confirmPassword = passwordhashing(new String(conPass.getPassword()));
	                    
	                 //regEx for validating the name
	                    String namePattern = "^[a-zA-Z]{0,20}";
	                    Pattern namePatt = Pattern.compile(namePattern);
	                    Matcher nameMatch = namePatt.matcher(textName.getText());
	                    if(!nameMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please ensure your Name contains only Alphabets (Max 20 characters)");
							return;
	                    }
	                    
		             //regEx for validating the Mobile number
	                    String mobilePattern = "^[0-9]{10}$";
	                    Pattern mobilePatt = Pattern.compile(mobilePattern);
	                    Matcher mobileMatch = mobilePatt.matcher(textMob.getText());
	                    if(!mobileMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please ensure your Mobile number is exactly 10 digits");
							return;
	                    }
	                    
		              //regEx for validating the Email  
	                    String emailPattern = "^[a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]{2,7}$";
	                    Pattern emailPatt = Pattern.compile(emailPattern);
	                    Matcher emailMatch = emailPatt.matcher(textEmail.getText());
	                    if(!emailMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please enter a valid email address. (eg: 'username@domain.extension')");
							return;
	                    }
	                    
		             //regEx for validating the UserName
	                    String usrNamePattern = "^[a-zA-Z0-9]{5,15}$";
	                    Pattern usrNamePatt = Pattern.compile(usrNamePattern);
	                    Matcher usrNameMatch = usrNamePatt.matcher(textUserName.getText());
	                    if(!usrNameMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please ensure your User name is between 5 and 15 Alphanumeric characters");
							return;
	                    }
	                    
		              //regEx for validating the Password
	                    String passPattern = "^.{6,10}$";
	                    Pattern passPatt = Pattern.compile(passPattern);
	                    Matcher passMatch = passPatt.matcher(new String(pass.getPassword()));
	                    if(!passMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please ensure your password is between 6 and 10 characters in length.");
							return;
	                    }
	                    
			          //regEx for validating the Password
	                    String conPassPattern = "^.{6,10}$";
	                    Pattern conPassPatt = Pattern.compile(conPassPattern);
	                    Matcher conPassMatch = conPassPatt.matcher(new String(conPass.getPassword()));
	                    if(!conPassMatch.matches())
	                    {
							JOptionPane.showMessageDialog(btnSubmit, "Please ensure your password is between 6 and 10 characters in length.");
							return;
	                    }
	                    
					
	                 // Checking if passwords match
	                    if (!password.equals(confirmPassword))
	                    {
	                        JOptionPane.showMessageDialog(btnSubmit, "Password do not Match!");
	                        pass.setText("");
	                        conPass.setText("");
	                        return; // Exit the method early if passwords do not match
	                    }
					
				        
				        String query1 = "Insert into Users(Name, Mobile, Email, UserName, Pass, ConPass) values(?, ?, ?, ?, ?, ?)";
						PreparedStatement preparedStatement = connection.prepareStatement(query1);
						
						try
						{
							preparedStatement.setString(1, textName.getText());
							long mobileNumber = Long.parseLong(textMob.getText());
				            preparedStatement.setLong(2, mobileNumber);
				            preparedStatement.setString(3, textEmail.getText());
				            preparedStatement.setString(4, textUserName.getText());
							preparedStatement.setString(5, password);
							preparedStatement.setString(6, confirmPassword);
						}
						
						catch (NumberFormatException e1)
						{
						    JOptionPane.showMessageDialog(btnSubmit, "Invalid mobile number format.");
							textMob.setText("");
						    return;

						}
//						catch (SQLException e1)
//						{
//						    JOptionPane.showMessageDialog(btnSubmit, "Invalid mobile number format.");
//						    return;
//
//						}
						
						
						try
						{
							int entries = preparedStatement.executeUpdate();
							if(entries==0)
							{
								JOptionPane.showMessageDialog(btnSubmit, "Failed to Register User.");
								System.out.println("Failed to add User!!");
								return;
							}
							else
							{
								JOptionPane.showMessageDialog(btnSubmit, entries+" User Registered Succesfully!!");
								textName.setText("");
								textEmail.setText("");
								textMob.setText("");
								textUserName.setText("");
								pass.setText("");
								conPass.setText("");
								System.out.println("User added Succesfully!!");
				                openLoginPage(); // Calling the method to open the Login page
							}
						}
						catch (SQLIntegrityConstraintViolationException ex)
						{
							 String errorMessage = ex.getMessage();
				                if (errorMessage.contains("Mobile"))
				                {
				                    JOptionPane.showMessageDialog(btnSubmit, "Mobile number already exists. Please use a different number.");
				                    textMob.setText(""); // Cleares the mobile number field
				                    return;
				                } 
				                else if (errorMessage.contains("Email"))
				                {
				                    JOptionPane.showMessageDialog(btnSubmit, "Email ID already exists. Please use a different email.");
				                    textEmail.setText(""); // Cleares the email field
				                    return;
				                }
				                else if (errorMessage.contains("PRIMARY"))
				                {
				                    JOptionPane.showMessageDialog(btnSubmit, "User name already exists. Please use a different User name.");
				                    textUserName.setText(""); // Cleares the User name field
				                    return;
				                }
			                
			            }
							
							
//					}
					
					
				} 
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSubmit.setBounds(211, 234, 100, 33);
		contentPane.add(btnSubmit);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textName.setText("");
				textEmail.setText("");
				textMob.setText("");
				textUserName.setText("");
				pass.setText("");
				conPass.setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnReset.setBounds(361, 234, 100, 33);
		contentPane.add(btnReset);
		
		pass = new JPasswordField();
		pass.setBounds(514, 117, 147, 20);
		contentPane.add(pass);
		
		conPass = new JPasswordField();
		conPass.setBounds(514, 174, 147, 20);
		contentPane.add(conPass);
	}
}



