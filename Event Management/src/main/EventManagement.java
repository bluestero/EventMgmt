package main;

//Imports
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.UIManager.*;
import java.sql.*;
import java.util.Vector;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import jxl.*;
import jxl.write.*;
import jxl.write.Label;

public class EventManagement {

	//Object Declarations
	private JFrame frLogin, frCategories, frMembers, frEvents, frSelectedEvent, frEventData;
	private Connection Con;
	Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int ScreenHeight = ScreenSize.height;
	int ScreenWidth = ScreenSize.width;
	public JTextField tfAddEvent, tfContactNo, tfAmount;
	private JTable tableEvents, tableEventData;
	public Vector<String> v;
	public DefaultComboBoxModel<String> cbm;
	public JComboBox<String> cbEvents;
	public JPanel panelMain;
	public DefaultTableCellRenderer centerRenderer;
	public String EventName, EventName2, EventNameMain, EventNameMain2;	
	public JLabel lblEventList, lblEventName, lblEventNameShow;
	public JFileChooser chooser;
	public String choosertitle, CurrentDir, CurrentFile;
	
	
	
	
	//Frames
	public void Login()
	{
		

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		frLogin = new JFrame();
		frLogin.getContentPane().setBackground(Color.DARK_GRAY);
		frLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frLogin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frLogin.getContentPane().setLayout(null);
		frLogin.setUndecorated(true);
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int ScreenHeight = ScreenSize.height;
		int ScreenWidth = ScreenSize.width;
		frLogin.setSize(ScreenWidth, ScreenHeight);
		
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.DARK_GRAY);
		panelMain.setBounds(0, 0, ScreenWidth, ScreenWidth);
		frLogin.getContentPane().add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login Page");
		lblLogin.setBounds((int) (ScreenWidth*0.285),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.43),(int)(ScreenHeight*0.1));
		panelMain.add(lblLogin);
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setBackground(SystemColor.text);
		lblLogin.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/20));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds((int) (ScreenWidth*0.25),(int)(ScreenHeight*0.35), (int) (ScreenWidth*0.15),(int)(ScreenHeight*0.035));
		panelMain.add(lblUsername);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		JTextField tfUsername = new JTextField();
		tfUsername.setBounds((int) (ScreenWidth*0.45),(int)(ScreenHeight*0.35), (int) (ScreenWidth*0.3),(int)(ScreenHeight*0.05));
		panelMain.add(tfUsername);
		tfUsername.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		tfUsername.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfUsername.setColumns(10);
		
		JPasswordField pfPassword = new JPasswordField();
		pfPassword.setBounds((int) (ScreenWidth*0.45),(int)(ScreenHeight*0.45), (int) (ScreenWidth*0.3),(int)(ScreenHeight*0.05));
		panelMain.add(pfPassword);
		pfPassword.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pfPassword.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds((int) (ScreenWidth*0.25),(int)(ScreenHeight*0.45), (int) (ScreenWidth*0.15),(int)(ScreenHeight*0.05));
		panelMain.add(lblPassword);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));

		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds((int) (ScreenWidth*0.4),(int)(ScreenHeight*0.58), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.07));
		panelMain.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					PreparedStatement prst = Con.prepareStatement("Select *from Alegria_Login where Username=? and Password=?");
					prst.setString(1, tfUsername.getText().toString());
					prst.setString(2, pfPassword.getText().toString());
					ResultSet rs = prst.executeQuery();
					if(rs.next())
					{
						if(rs.getString(1).equals("alegria_admin"))
						{			
							if(lblEventList!=null)
							{
							frEvents.setVisible(true);
							}
							else
							{
							Events();
							}
							frLogin.setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Login Successful.");
						}
						
						tfUsername.setText("");
						pfPassword.setText("");
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Login Unsuccessful.");
					}
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,e2);
				}
			}
		});
		btnLogin.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		frLogin.setVisible(true);
	}
	
	public void Categories()
	{
		frCategories = new JFrame();
		frCategories.getContentPane().setBackground(Color.DARK_GRAY);
		frCategories.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frCategories.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frCategories.setUndecorated(true);
		frCategories.getContentPane().setLayout(null);
		frCategories.setSize(ScreenWidth, ScreenHeight);
		
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.DARK_GRAY);
		panelMain.setBounds(0, 0, ScreenWidth, ScreenHeight);
		frCategories.getContentPane().add(panelMain);
		panelMain.setLayout(null);
		
		JButton btnMembers = new JButton("Members");
		btnMembers.setBounds((int) (ScreenWidth*0.3875),(int)(ScreenHeight*0.46), (int) (ScreenWidth*0.225),(int)(ScreenHeight*0.08));
		panelMain.add(btnMembers);
		btnMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frMembers.setVisible(true);
				frCategories.setVisible(false);
			}
		});
		btnMembers.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		JButton btnEvents = new JButton("Events");
		btnEvents.setBounds((int) (ScreenWidth*0.3875),(int)(ScreenHeight*0.3), (int) (ScreenWidth*0.225),(int)(ScreenHeight*0.08));
		panelMain.add(btnEvents);
		btnEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lblEventList!=null)
				{
				frEvents.setVisible(true);
				}
				else
				{
				Events();
				}
				frCategories.setVisible(false);
			}
		});
		btnEvents.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		JButton btnAttendance = new JButton("Attendance");
		btnAttendance.setBounds((int) (ScreenWidth*0.3875),(int)(ScreenHeight*0.62), (int) (ScreenWidth*0.225),(int)(ScreenHeight*0.08));
		panelMain.add(btnAttendance);
		btnAttendance.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/35));
		
		JLabel lblMainCategories = new JLabel("Categories");
		lblMainCategories.setBounds((int) (ScreenWidth*0.3875),(int)(ScreenHeight*0.15), (int) (ScreenWidth*0.235),(int)(ScreenHeight*0.1));
		panelMain.add(lblMainCategories);
		lblMainCategories.setForeground(Color.WHITE);
		lblMainCategories.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/20));
		lblMainCategories.setBackground(Color.WHITE);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds((int) (ScreenWidth*0.001),(int)(ScreenHeight*0.001), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.05));
		panelMain.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frLogin.setVisible(true);
				frCategories.setVisible(false);
			}
		});
		btnLogout.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/42));
	}
	
	public void Members()
	{
		frMembers = new JFrame();
		frMembers.getContentPane().setBackground(Color.DARK_GRAY);
		frMembers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frMembers.getContentPane().setLayout(null);
		frMembers.setSize(ScreenWidth, ScreenHeight);
		
		JLabel lblMembersOfAlegria = new JLabel("Members Of Alegria");
		lblMembersOfAlegria.setForeground(Color.WHITE);
		lblMembersOfAlegria.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/20));
		lblMembersOfAlegria.setBackground(Color.WHITE);
		lblMembersOfAlegria.setBounds(0, 0, ScreenWidth, ScreenHeight);
		frMembers.getContentPane().add(lblMembersOfAlegria);
		
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frCategories.setVisible(true);
				frMembers.setVisible(false);				
			}
		});
		button.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/38));
		button.setBounds((int) (ScreenWidth*0.001),(int)(ScreenHeight*0.001), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.05));
		frMembers.getContentPane().add(button);
		frMembers.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frMembers.setUndecorated(true);
	}
	
	public void Events()
	{
		frEvents = new JFrame();
		frEvents.getContentPane().setBackground(Color.DARK_GRAY);
		frEvents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frEvents.getContentPane().setLayout(null);
		frEvents.setSize(ScreenWidth, ScreenHeight);
		
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.DARK_GRAY);
		panelMain.setBounds(0, 0, ScreenWidth, ScreenWidth);
		frEvents.getContentPane().add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblEventList = new JLabel("All Event List");
		lblEventList.setBounds((int) (ScreenWidth*0.35),(int)(ScreenHeight*0.05), (int) (ScreenWidth*0.3),(int)(ScreenHeight*0.1));
		panelMain.add(lblEventList);
		lblEventList.setForeground(Color.WHITE);
		lblEventList.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/20));
		lblEventList.setBackground(Color.WHITE);
		
		JLabel lblEventName = new JLabel("Event Name");
		lblEventName.setForeground(Color.WHITE);
		lblEventName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		lblEventName.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.115),(int)(ScreenHeight*0.035));
		panelMain.add(lblEventName);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setForeground(Color.WHITE);
		lblAmount.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		lblAmount.setBounds((int)(ScreenWidth*0.405),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.075),(int)(ScreenHeight*0.035));
		panelMain.add(lblAmount);
		
		tfAddEvent = new JTextField();
		tfAddEvent.setBounds((int)(ScreenWidth*0.13),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.25),(int)(ScreenHeight*0.035));
		panelMain.add(tfAddEvent);
		tfAddEvent.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		tfAddEvent.setColumns(10);
		tfAddEvent.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		tfAmount = new JTextField();
		tfAmount.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		tfAmount.setColumns(10);
		tfAmount.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfAmount.setBounds((int)(ScreenWidth*0.485),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.25),(int)(ScreenHeight*0.035));
		panelMain.add(tfAmount);
		
		v = new Vector<>();
		cbm = new DefaultComboBoxModel<>(v);
		cbEvents = new JComboBox<>();		
		cbEvents.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.275), (int) (ScreenWidth*0.275),(int)(ScreenHeight*0.035));
		panelMain.add(cbEvents);
		cbEvents.setMinimumSize(new Dimension(50, 200));
		cbEvents.setPreferredSize(new Dimension(50, 200));
		cbEvents.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		UpdateCombo();
		
		
		JButton btnAddEvent = new JButton("Add Event");
		btnAddEvent.setBounds((int)(ScreenWidth*0.78),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.135),(int)(ScreenHeight*0.035));
		panelMain.add(btnAddEvent);
		btnAddEvent.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		btnAddEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(tfAddEvent.getText().toString().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Enter Event Name.");
					}
					else
					{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/College","NangaBaccha","");
						
						Statement st = c.createStatement();
						EventName = tfAddEvent.getText().toString().replaceAll(" ", "").toLowerCase();
						st.executeUpdate("CREATE TABLE "+EventName+"(EntryID int NOT NULL AUTO_INCREMENT, CollegeCode varchar(45) NOT NULL, Name varchar(45) NOT NULL, ContactNo varchar(45) NOT NULL,PRIMARY KEY (`EntryID`))");
						
						PreparedStatement prst = c.prepareStatement("INSERT INTO alegria_events(EventName,Amount) VALUES(?,?)");
						prst.setString(1, tfAddEvent.getText().toString());
						prst.setInt(2,Integer.parseInt(tfAmount.getText().toString()) );
						prst.executeUpdate();
						
						UpdateTable(tableEvents);
						UpdateCombo();
						CenterTable(tableEvents);
						tfAddEvent.setText("");
						tfAmount.setText("");
						JOptionPane.showMessageDialog(null, "Event Added Successfully.");
					}				
					
				}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
				
			}
		});
		
		JButton btnRemoveEvent = new JButton("Remove Event");
		btnRemoveEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{					
					String EventName = null;
					PreparedStatement prst = Con.prepareStatement("SELECT *FROM alegria_events WHERE EventID = ?");
					String EventNumber = cbEvents.getSelectedItem().toString().split("-")[0].replaceAll("[^0-9]", "");
					prst.setString(1, EventNumber);
					ResultSet rs = prst.executeQuery();
					if(rs.next())
					{
					EventName = rs.getString(2);
					}
					
					String EventName2 = EventName.replaceAll(" ","").toLowerCase();
					Statement st = Con.createStatement();
					st.executeUpdate("DROP TABLE "+EventName);
					PreparedStatement prst2 = Con.prepareStatement("DELETE FROM alegria_events WHERE EventID=?");
					prst2.setString(1, EventNumber);
					prst2.execute();
					
					UpdateCombo();
					UpdateTable(tableEvents);
					CenterTable(tableEvents);
					tfAddEvent.setText("");
					tfAmount.setText("");
					JOptionPane.showMessageDialog(null,"Successfully Removed"+EventName2+".");
					}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
			}
		});
		btnRemoveEvent.setBounds((int)(ScreenWidth*0.46),(int)(ScreenHeight*0.275), (int) (ScreenWidth*0.165),(int)(ScreenHeight*0.035));
		panelMain.add(btnRemoveEvent);
		btnRemoveEvent.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{										
					int a = cbEvents.getItemCount();
					for(int i = 0; i<a; i++)
					{		
						String EventName = null;
						PreparedStatement prst = Con.prepareStatement("SELECT * FROM alegria_events WHERE EventID = ?");
						String EventNumber = cbEvents.getItemAt(i).toString().split("-")[0].replaceAll("[^0-9]", "");
						prst.setString(1, EventNumber);
						ResultSet rs = prst.executeQuery();
						if(rs.next())
						{
						EventName = rs.getString(2).replaceAll(" ","").toLowerCase();
						}
						
						Statement st = Con.createStatement();
						st.executeUpdate("DROP TABLE "+EventName);
						prst = Con.prepareStatement("DELETE FROM alegria_events WHERE EventID=?");
						prst.setString(1, EventNumber);
						prst.execute();
						
						//EventName = cbEvents.getSelectedItem().toString().replaceAll("[^A-Za-z]", "").toLowerCase();
						//EventName2 = cbEvents.getSelectedItem().toString().replaceAll("[^A-Za-z ]", "");
						
					}					
					Statement st = Con.createStatement();
					st.executeUpdate("TRUNCATE TABLE alegria_events");
					
					UpdateCombo();
					UpdateTable(tableEvents);
					CenterTable(tableEvents);
					tfAddEvent.setText("");
					tfAmount.setText("");
					JOptionPane.showMessageDialog(null,"Successfully Removed All Elements.\nNote: The EventID will start from 1.");
					}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
			}
		});
		btnRemoveAll.setBounds((int)(ScreenWidth*0.645),(int)(ScreenHeight*0.275), (int) (ScreenWidth*0.135),(int)(ScreenHeight*0.035));
		panelMain.add(btnRemoveAll);
		btnRemoveAll.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.375), (int) (ScreenWidth*0.4),(int)(ScreenHeight*0.55));
		panelMain.add(scrollPane);
		scrollPane.setOpaque(true);
		
		tableEvents = new JTable();
		tableEvents.setFont(new Font("Arial", Font.PLAIN, ScreenWidth/85));
		scrollPane.setViewportView(tableEvents);
		tableEvents.setEnabled(true);
		UpdateTable(tableEvents);
		CenterTable(tableEvents);
		
		btnAddEvent.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		
		JButton btnGoToEvent = new JButton("Go To Event");
		btnGoToEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					EventNameMain = cbEvents.getSelectedItem().toString().replaceAll("[^A-Za-z]", "").toLowerCase();
					
					if(lblEventNameShow!=null)
					{
					frSelectedEvent.setVisible(true);
					}
					else
					{
					frSelectedEvent();
					}
					frEvents.setVisible(false);
					
					try
					{
						PreparedStatement prst = Con.prepareStatement("SELECT * FROM alegria_events WHERE EventID = ?");
						String EventNumber = cbEvents.getSelectedItem().toString().split("-")[0].replaceAll("[^0-9]", "");
						prst.setString(1, EventNumber);
						ResultSet rs = prst.executeQuery();
						if(rs.next())
						{
						lblEventNameShow.setText(rs.getString(2));
						}
					}
					catch(Exception ae)
					{
						JOptionPane.showMessageDialog(null, ae);
					}
					
					UpdateTable2(tableEventData);
					CenterTable(tableEventData);
					
					
			}
		});
		btnGoToEvent.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/56));
		btnGoToEvent.setBounds((int)(ScreenWidth*0.305),(int)(ScreenHeight*0.275), (int) (ScreenWidth*0.14),(int)(ScreenHeight*0.035));
		panelMain.add(btnGoToEvent);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frCategories.setVisible(true);
				frEvents.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		btnBack.setBounds((int) (ScreenWidth*0.001),(int)(ScreenHeight*0.001), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.05));
		panelMain.add(btnBack);
		
		JLabel lblNewName = new JLabel("Change Name Of Selected Event:");
		lblNewName.setForeground(Color.WHITE);
		lblNewName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		lblNewName.setBounds((int)(ScreenWidth*0.46),(int)(ScreenHeight*0.375), (int) (ScreenWidth*0.3),(int)(ScreenHeight*0.04));
		panelMain.add(lblNewName);
		
		JLabel lblNewName2 = new JLabel("New Name");
		lblNewName2.setForeground(Color.WHITE);
		lblNewName2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		lblNewName2.setBounds((int)(ScreenWidth*0.46),(int)(ScreenHeight*0.45), (int) (ScreenWidth*0.11),(int)(ScreenHeight*0.04));
		panelMain.add(lblNewName2);
		
		JTextField tfNewName = new JTextField();
		tfNewName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		tfNewName.setColumns(10);
		tfNewName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfNewName.setBounds((int)(ScreenWidth*0.60),(int)(ScreenHeight*0.45), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.04));
		panelMain.add(tfNewName);
		
		JLabel lblNewAmount = new JLabel("New Amount");
		lblNewAmount.setForeground(Color.WHITE);
		lblNewAmount.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		lblNewAmount.setBounds((int)(ScreenWidth*0.46),(int)(ScreenHeight*0.525), (int) (ScreenWidth*0.13),(int)(ScreenHeight*0.04));
		panelMain.add(lblNewAmount);
		
		JTextField tfNewAmount = new JTextField();
		tfNewAmount.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		tfNewAmount.setColumns(10);
		tfNewAmount.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfNewAmount.setBounds((int)(ScreenWidth*0.60),(int)(ScreenHeight*0.525), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.04));
		panelMain.add(tfNewAmount);
		
		JButton btnNewName = new JButton("Change Data");
		btnNewName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{										
					String EventNumber = cbEvents.getSelectedItem().toString().split("-")[0].replaceAll("[^0-9]", "");
					String NewAmount = "";
					String OldName = "";
					String NewName = "";
					
					PreparedStatement prst2 = Con.prepareStatement("SELECT EventName,Amount FROM alegria_events WHERE EventID=?");
					prst2.setString(1, EventNumber);
					ResultSet rs = prst2.executeQuery();
					if(rs.next())
					{
						OldName = rs.getString(1);
						NewAmount = rs.getString(2);
					}
					
					if(!tfNewAmount.getText().isEmpty())
					{
						NewAmount = tfNewAmount.getText().toString();
					}
					
					if(tfNewName.getText().isEmpty())
					{
						NewName = OldName;
					}
					else
					{
						NewName = tfNewName.getText().toString();
						Statement st = Con.createStatement();						
						st.executeUpdate("CREATE TABLE "+NewName.replaceAll(" ", "").toLowerCase()+"(EntryID int NOT NULL AUTO_INCREMENT, CollegeCode varchar(45) NOT NULL, Name varchar(45) NOT NULL, ContactNo varchar(45) NOT NULL,PRIMARY KEY (`EntryID`))");
						st.executeUpdate("DROP TABLE "+OldName.replaceAll(" ", "").toLowerCase());
					}	
									
					PreparedStatement prst = Con.prepareStatement("UPDATE alegria_events SET EventName=?, Amount=? WHERE EventID=?");
					prst.setString(1, NewName);
					prst.setString(2, NewAmount);
					prst.setString(3, EventNumber);
					prst.execute();
					
					
					JOptionPane.showMessageDialog(null, "Data Changed Successfully.");
					tfNewName.setText("");
					tfNewAmount.setText("");
					
					UpdateCombo();
					UpdateTable(tableEvents);
					CenterTable(tableEvents);
					
				}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
			}
		});
		
		
		btnNewName.setBounds((int)(ScreenWidth*0.46),(int)(ScreenHeight*0.6), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.04));
		panelMain.add(btnNewName);
		btnNewName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		
		
		
		frEvents.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frEvents.setUndecorated(true);
		
		UpdateCombo();
		UpdateTable(tableEvents);
		CenterTable(tableEvents);
		
		frEvents.setVisible(true);
		}
	
	
	public void frSelectedEvent()
	{
		frSelectedEvent = new JFrame();
		frSelectedEvent.getContentPane().setBackground(Color.DARK_GRAY);
		frSelectedEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frSelectedEvent.getContentPane().setLayout(null);
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int ScreenHeight = ScreenSize.height;
		int ScreenWidth = ScreenSize.width;
		frSelectedEvent.setSize(ScreenWidth, ScreenHeight);
		
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.DARK_GRAY);
		panelMain.setBounds(0, 0, ScreenWidth, ScreenHeight);
		frSelectedEvent.getContentPane().add(panelMain);
		panelMain.setLayout(null);
		
		lblEventNameShow = new JLabel();
		lblEventNameShow.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/20));
		lblEventNameShow.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.15), (int) (ScreenWidth*0.3),(int)(ScreenHeight*0.035));
		try
		{
			PreparedStatement prst = Con.prepareStatement("SELECT *FROM alegria_events WHERE EventID = ?");
			String EventNumber = cbEvents.getSelectedItem().toString().split("-")[0].replaceAll("[^0-9]", "");
			prst.setString(1, EventNumber);
			ResultSet rs = prst.executeQuery();
			if(rs.next())
			{
			lblEventNameShow.setText(rs.getString(2));
			}
		}
		catch(Exception ae)
		{
			JOptionPane.showMessageDialog(null, ae);
		}
		
		panelMain.add(lblEventNameShow);
		lblEventNameShow.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/38));
		lblEventNameShow.setForeground(Color.WHITE);
		lblEventNameShow.setBackground(new Color(240, 240, 240));
		
		JLabel lblEventFirstName = new JLabel("Add Data:");
		lblEventFirstName.setForeground(Color.MAGENTA);
		lblEventFirstName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/40));
		lblEventFirstName.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.209), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblEventFirstName);
		
		JLabel lblCollegeCode = new JLabel("College Code");
		lblCollegeCode.setForeground(Color.WHITE);
		lblCollegeCode.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblCollegeCode.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.27), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblCollegeCode);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblName.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.335), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblName);
		
		JLabel lblContactNo = new JLabel("Contact No");
		lblContactNo.setForeground(Color.WHITE);
		lblContactNo.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblContactNo.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.40), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblContactNo);
		
		JTextField tfCollegeCode = new JTextField();
		tfCollegeCode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCollegeCode.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfCollegeCode.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.27), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfCollegeCode);
		tfCollegeCode.setColumns(10);
		
		JTextField tfName = new JTextField();
		tfName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfName.setColumns(10);
		tfName.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.335), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfName);
		
		tfContactNo = new JTextField();
		tfContactNo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfContactNo.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfContactNo.setColumns(10);
		tfContactNo.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.40), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfContactNo);
		
		JButton btnAddData = new JButton("Add Data");
		btnAddData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						if(tfName.getText().toString().equals("") && tfContactNo.getText().toString().equals(""))
						{
							JOptionPane.showMessageDialog(null, "Enter Details First.");
							
						}
						else
						{
							PreparedStatement prst = Con.prepareStatement("INSERT INTO "+lblEventNameShow.getText().replaceAll(" ","").toLowerCase()+"(CollegeCode,Name,ContactNo) VALUES(?,?,?)");
							if(tfCollegeCode.getText().isEmpty())
							{
								prst.setString(1, "1");
							}
							else
							{
								prst.setString(1, tfCollegeCode.getText().toString());
							}
							prst.setString(2, tfName.getText());
							prst.setString(3, tfContactNo.getText().toString());
							prst.execute();
							JOptionPane.showMessageDialog(null, "Data Added Successfully.");
							tfCollegeCode.setText("");
							tfName.setText("");
							tfContactNo.setText("");
							
							UpdateTable2(tableEventData);
							CenterTable(tableEventData);
						}
							
				}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
				
			}
		});
		btnAddData.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		btnAddData.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.465), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.035));
		panelMain.add(btnAddData);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		horizontalBox.setBackground(Color.WHITE);
		horizontalBox.setBounds((int)(ScreenWidth*0.005),(int)(ScreenHeight*0.545), (int) (ScreenWidth*0.410),(int)(ScreenHeight*0.42));
		panelMain.add(horizontalBox);
		
		Box horizontalBox2 = Box.createHorizontalBox();
		horizontalBox2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		horizontalBox2.setBackground(Color.WHITE);
		horizontalBox2.setBounds((int)(ScreenWidth*0.005),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.410),(int)(ScreenHeight*0.32));
		panelMain.add(horizontalBox2);
		
		JLabel lblEventLastName = new JLabel("Change Data:");
		lblEventLastName.setForeground(Color.MAGENTA);
		lblEventLastName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/40));
		lblEventLastName.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.555), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblEventLastName);
		
		JLabel lblEntryID = new JLabel("Entry ID");
		lblEntryID.setForeground(Color.WHITE);
		lblEntryID.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblEntryID.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.616), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblEntryID);
		
		JLabel lblCollegeCode2 = new JLabel("College Code");
		lblCollegeCode2.setForeground(Color.WHITE);
		lblCollegeCode2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblCollegeCode2.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.681), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblCollegeCode2);
		
		JLabel lblName2 = new JLabel("Name");
		lblName2.setForeground(Color.WHITE);
		lblName2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblName2.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.746), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblName2);
		
		JLabel lblContactNo2 = new JLabel("Contact No");
		lblContactNo2.setForeground(Color.WHITE);
		lblContactNo2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblContactNo2.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.811), (int) (ScreenWidth*0.17),(int)(ScreenHeight*0.035));
		panelMain.add(lblContactNo2);
		
		JTextField tfEntryID = new JTextField();
		tfEntryID.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfEntryID.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfEntryID.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.616), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfEntryID);
		tfEntryID.setColumns(10);
		
		JTextField tfCollegeCode2 = new JTextField();
		tfCollegeCode2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCollegeCode2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfCollegeCode2.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.681), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfCollegeCode2);
		tfCollegeCode2.setColumns(10);
		
		JTextField tfName2 = new JTextField();
		tfName2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfName2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfName2.setColumns(10);
		tfName2.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.746), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfName2);
		
		JTextField tfContactNo2 = new JTextField();
		tfContactNo2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfContactNo2.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfContactNo2.setColumns(10);
		tfContactNo2.setBounds((int)(ScreenWidth*0.205),(int)(ScreenHeight*0.811), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfContactNo2);
		
		JButton btnChangeData = new JButton("Change Data");
		btnChangeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						if(tfName2.getText().toString().equals("") && tfContactNo2.getText().toString().equals(""))
						{
							JOptionPane.showMessageDialog(null, "Enter Details First.");
							
						}
						else
						{
							PreparedStatement prst = Con.prepareStatement("UPDATE "+lblEventNameShow.getText().replaceAll(" ","").toLowerCase()+" SET CollegeCode=? , Name=? , ContactNo=? WHERE (EntryID=?)");
							if(tfCollegeCode2.getText().isEmpty())
							{
								prst.setString(1, "1");
							}
							else
							{
								prst.setString(1, tfCollegeCode2.getText().toString());
							}
							prst.setString(2, tfName2.getText());
							prst.setString(3, tfContactNo2.getText().toString());
							prst.setString(4, tfEntryID.getText().toString());
							prst.execute();
							JOptionPane.showMessageDialog(null, "Data Changed Successfully.");
							tfCollegeCode2.setText("");
							tfName2.setText("");
							tfContactNo2.setText("");
							tfEntryID.setText("");
							
							UpdateTable2(tableEventData);
							CenterTable(tableEventData);
						}
							
				}
				catch(Exception ae)
				{
					JOptionPane.showMessageDialog(null, ae);
				}
				
			}
		});
		btnChangeData.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/54));
		btnChangeData.setBounds((int)(ScreenWidth*0.01),(int)(ScreenHeight*0.871), (int) (ScreenWidth*0.15),(int)(ScreenHeight*0.035));
		panelMain.add(btnChangeData);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frEvents.setVisible(true);
				frSelectedEvent.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/48));
		btnBack.setBounds((int) (ScreenWidth*0.001),(int)(ScreenHeight*0.001), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.05));
		panelMain.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds((int)(ScreenWidth*0.45),(int)(ScreenHeight*0.367), (int) (ScreenWidth*0.525),(int)(ScreenHeight*0.60));
		panelMain.add(scrollPane);
		scrollPane.setOpaque(true);
		
		tableEventData = new JTable();
		tableEventData.setFont(new Font("Arial", Font.PLAIN, ScreenWidth/85));
		scrollPane.setViewportView(tableEventData);
		tableEventData.setEnabled(true);
		
		JLabel lblExportName = new JLabel("File Name");
		lblExportName.setForeground(Color.WHITE);
		lblExportName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		lblExportName.setBounds((int)(ScreenWidth*0.45),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.1),(int)(ScreenHeight*0.035));
		panelMain.add(lblExportName);
		
		JTextField tfExportName = new JTextField();
		tfExportName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfExportName.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/50));
		tfExportName.setColumns(10);
		tfExportName.setBounds((int)(ScreenWidth*0.57),(int)(ScreenHeight*0.2), (int) (ScreenWidth*0.2),(int)(ScreenHeight*0.035));
		panelMain.add(tfExportName);
		
		JButton btnExportDir = new JButton("Set Directory");
		btnExportDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		        
			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) { 
			    	CurrentDir = chooser.getCurrentDirectory().toString();
			    	CurrentFile = chooser.getSelectedFile().toString();
			      }
			    else {
			      System.out.println("No Selection ");
			      }
			}
		});
		btnExportDir.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/60));
		btnExportDir.setBounds((int)(ScreenWidth*0.45),(int)(ScreenHeight*0.255), (int) (ScreenWidth*0.12),(int)(ScreenHeight*0.035));
		panelMain.add(btnExportDir);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String Location = "";
					String FileName = "";
					String Username = System.getProperty("user.name");
					
					if(tfExportName.getText().isEmpty())
					{
						FileName = lblEventNameShow.getText().replace(" ", "_");
					}
					else
					{
						FileName = tfExportName.getText().toString();
					}
					if(CurrentFile == null)
					{						
						CurrentFile = "C:\\Users\\"+Username+"\\Desktop";
					}
					else
					{
						CurrentFile = chooser.getSelectedFile().toString();
					}
					
					Location = CurrentFile+"\\"+FileName+".xls";				
                  Export(tableEventData, new File(Location));
                  JOptionPane.showMessageDialog(null, "Data Saved At "+Location+" Successfully.");	
                  CurrentFile=null;
                  tfExportName.setText("");
                  
              } 
				catch (Exception ex) {																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									
                  ex.printStackTrace();
              }
			}
		});
		btnExport.setFont(new Font("Garamond", Font.BOLD, ScreenWidth/60));
		btnExport.setBounds((int)(ScreenWidth*0.45),(int)(ScreenHeight*0.310), (int) (ScreenWidth*0.12),(int)(ScreenHeight*0.035));
		panelMain.add(btnExport);		
		
		UpdateTable2(tableEventData);
		CenterTable(tableEventData);
		
		frSelectedEvent.setUndecorated(true);
		frSelectedEvent.setVisible(true);
	}
	
	
	
	
	
	//Functions
	
	public static TableModel resultSetToTableModel(ResultSet rs) {
      try {
          ResultSetMetaData metaData = rs.getMetaData();
          int numberOfColumns = metaData.getColumnCount();
          Vector columnNames = new Vector();

          // Get the column names
          for (int column = 0; column < numberOfColumns; column++) {
              columnNames.addElement(metaData.getColumnLabel(column + 1));
          }

          // Get all rows.
          Vector rows = new Vector();

          while (rs.next()) {
              Vector newRow = new Vector();

              for (int i = 1; i <= numberOfColumns; i++) {
                  newRow.addElement(rs.getObject(i));
              }

              rows.addElement(newRow);
          }

          return new DefaultTableModel(rows, columnNames);	
      } catch (Exception e) {
          e.printStackTrace();

          return null;
      }
  }
	
	
	public void UpdateTable(JTable table)
	{
		try
		{
			PreparedStatement prst = Con.prepareStatement("SELECT *FROM alegria_events");
			ResultSet rs = prst.executeQuery();
			table.setModel(resultSetToTableModel(rs));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void UpdateTable2(JTable table)
	{
		try
		{
			PreparedStatement prst = Con.prepareStatement("SELECT *FROM "+lblEventNameShow.getText().toString().replaceAll(" ", "").toLowerCase());
			ResultSet rs = prst.executeQuery();
			table.setModel(resultSetToTableModel(rs));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void UpdateCombo()
	{
		try
		{
			PreparedStatement prst = Con.prepareStatement("SELECT *FROM alegria_events");
			ResultSet rs = prst.executeQuery();
			v.removeAllElements();
			while(rs.next())
			{
				v.add(rs.getString(1)+" - "+rs.getString(2));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		cbEvents.setModel(cbm);
		if(cbEvents.getItemCount()==0)
		{
		cbEvents.setSelectedIndex(-1);
		}
		else
		{
			cbEvents.setSelectedIndex(0);
		}
		
	}
	
	
	public void CenterTable(JTable table)
	{
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		JTableHeader header = table.getTableHeader();
		DefaultTableCellRenderer  render = (DefaultTableCellRenderer) header.getDefaultRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
	}
	
	
	
	public void DatabaseConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/College","NangaBaccha","");
		}
		catch(Exception ew)
		{
			System.out.println(ew);
		}
	}
	
	public void Export(JTable table, File file)
	{
      try {

          WritableWorkbook workbook1 = Workbook.createWorkbook(file);
          WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0); 
          WritableFont eventFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false);
          WritableCellFormat eventFormat = new WritableCellFormat(eventFont);
          
          TableModel model = table.getModel();

          for (int i = 0; i < model.getColumnCount(); i++) {
              Label column = new Label(i, 0, model.getColumnName(i),eventFormat);                
              sheet1.addCell(column);
              Label white = new Label(i,1,"");
              sheet1.addCell(white);
          }
          int j = 0;
          for (int i = 0; i < model.getRowCount(); i++) {
              for (j = 0; j < model.getColumnCount(); j++) {
                  Label row = new Label(j, i + 2, 
                          model.getValueAt(i, j).toString());
                  sheet1.addCell(row);
              }
          }
          workbook1.write();
          workbook1.close();	
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }
	
	
	
	
	public static void main(String args[])
	{
		EventManagement project = new EventManagement();
		project.Login();
		project.Categories();
		//project.Events();
		//project.frSelectedEvent();
		project.Members();
		project.DatabaseConnection();
	}
}