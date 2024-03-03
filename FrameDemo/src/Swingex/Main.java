//					Please Create a table "users" in your MySQL database using below query
//					and make sure to provide correct details about your database inside " actionPerformed() " method
//					
//					Create table if not exists Users (
//						         Name Varchar(20) not null,
//						         Mobile BIGINT Unique not null,
//						         Email Varchar(35) Unique not null,
//						         UserName Varchar(15) Unique not null,
//						         Pass Varchar(255) not null,
//						         ConPass Varchar(255) not null,
//						         Primary key (UserName)
//						     );

package Swingex;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Main extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main frame = new Main();
					frame.setVisible(true);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void openLoginPage()
	{
        Login login = new Login();
        login.setVisible(true);
        this.dispose(); // Closing the current frame
    }
	
	public void openRegisterPage()
	{
        Register register = new Register();
        register.setVisible(true);
        this.dispose(); // Closing the current frame
    }

	/**
	 * Create the frame.
	 */
	public Main()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 317, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rbRegister = new JRadioButton("Register new User");
		buttonGroup.add(rbRegister);
		rbRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		rbRegister.setBounds(71, 60, 174, 23);
		contentPane.add(rbRegister);
		
		JLabel lblNewLabel = new JLabel("Select your Choice");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(71, 11, 174, 29);
		contentPane.add(lblNewLabel);
		
		JRadioButton rbLogin = new JRadioButton("Login existing User");
		buttonGroup.add(rbLogin);
		rbLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		rbLogin.setBounds(71, 96, 174, 23);
		contentPane.add(rbLogin);
		
		JButton btnSubmit = new JButton("OK");
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
					

					if(rbRegister.isSelected())
					{
						openRegisterPage();
					}
					else if(rbLogin.isSelected())
					{
						String query = "Select * from Users";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						ResultSet resultSet = preparedStatement.executeQuery();
					    
					    if (resultSet.next())
					    {
							openLoginPage();
					    }
					    
					    else
					    {
					    	JOptionPane.showMessageDialog(btnSubmit, "There are no records found in your Database!\n              Please Register First");
					    }
					}
				}
				
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSubmit.setBounds(20, 148, 80, 35);
		contentPane.add(btnSubmit);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClear.setBounds(187, 148, 95, 35);
		contentPane.add(btnClear);
	}
}


