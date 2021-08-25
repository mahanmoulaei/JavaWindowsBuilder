import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentCourseRegistration {

	private JFrame frmLasalleCollege;
	private JTable table;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldContact;
	private JTextField textFieldCourse;
	private JTextField textFieldSearch;
	private Connection Connection;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentCourseRegistration window = new StudentCourseRegistration();
					window.frmLasalleCollege.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentCourseRegistration() {
		initialize();
		Connection = SQLiteConnection.dbConnector();
		refreshEverything();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLasalleCollege = new JFrame();
		frmLasalleCollege.setTitle("LaSalle College - Course Registration");
		frmLasalleCollege.getContentPane().setBackground(new Color(102, 204, 255));
		frmLasalleCollege.setBounds(100, 100, 776, 520);
		frmLasalleCollege.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLasalleCollege.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(345, 102, 405, 355);
		frmLasalleCollege.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {			
					int row = table.getSelectedRow();
					String ID = table.getModel().getValueAt(row, 0).toString();
					String query = "SELECT * FROM StudentInfo WHERE ID='" + ID + "'";
					PreparedStatement pst = Connection.prepareStatement(query);
					ResultSet rs =  pst.executeQuery();
					textFieldID.setEditable(false);
					while(rs.next()) {	
						textFieldID.setText(rs.getString("ID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldContact.setText(rs.getString("Contact"));
						textFieldCourse.setText(rs.getString("Course"));
					}		
					pst.close();
					rs.close();	
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);		
				}
			}
		});
		scrollPane.setViewportView(table);
		
		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldID.setBounds(151, 101, 165, 36);
		frmLasalleCollege.getContentPane().add(textFieldID);
		textFieldID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldName.setColumns(10);
		textFieldName.setBounds(151, 164, 165, 36);
		frmLasalleCollege.getContentPane().add(textFieldName);
		
		textFieldContact = new JTextField();
		textFieldContact.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldContact.setColumns(10);
		textFieldContact.setBounds(151, 229, 165, 36);
		frmLasalleCollege.getContentPane().add(textFieldContact);
		
		textFieldCourse = new JTextField();
		textFieldCourse.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldCourse.setColumns(10);
		textFieldCourse.setBounds(151, 294, 165, 36);
		frmLasalleCollege.getContentPane().add(textFieldCourse);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("Resources\\new_LaSalle_college.png"));
		lblLogo.setBounds(10, 0, 333, 90);
		frmLasalleCollege.getContentPane().add(lblLogo);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblID.setBounds(41, 105, 100, 25);
		frmLasalleCollege.getContentPane().add(lblID);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblName.setBounds(41, 168, 100, 25);
		frmLasalleCollege.getContentPane().add(lblName);
		
		JLabel lblContact = new JLabel("Contact:");
		lblContact.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblContact.setBounds(41, 233, 100, 25);
		frmLasalleCollege.getContentPane().add(lblContact);
		
		JLabel lblCourse = new JLabel("Course:");
		lblCourse.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblCourse.setBounds(41, 298, 100, 25);
		frmLasalleCollege.getContentPane().add(lblCourse);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldID.getText().isEmpty() || textFieldName.getText().isEmpty() || textFieldContact.getText().isEmpty() || textFieldCourse.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "One or more text field(s) are empty!", "Action Not Possible", JOptionPane.ERROR_MESSAGE);
				} else {	
					try {
						
						String query = "UPDATE StudentInfo set Name='" + textFieldName.getText() + "', Contact='" + textFieldContact.getText() + "', Course='" + textFieldCourse.getText() + "' where ID='" + textFieldID.getText() + "'";
						PreparedStatement pst = Connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Data Updated Succesfully!");
						pst.close();
				
						refreshEverything();
					} catch (Exception e2) {
					// TODO: handle exception
						JOptionPane.showMessageDialog(null, e2);
					}	
				}
			}
		});
		btnUpdate.setFocusable(false);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnUpdate.setBounds(184, 365, 132, 30);
		frmLasalleCollege.getContentPane().add(btnUpdate);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFocusable(false);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldID.getText().isEmpty() || textFieldName.getText().isEmpty() || textFieldContact.getText().isEmpty() || textFieldCourse.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "One or more text field(s) are empty!", "Action Not Possible", JOptionPane.ERROR_MESSAGE);
				} else {		
					int yes = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Add Data?", "Add Data", JOptionPane.YES_NO_OPTION);
					if(yes == 0) {		
						try {
							String query = "INSERT INTO StudentInfo (ID, Name, Contact, Course) VALUES (?,?,?,?)";
							PreparedStatement pst = Connection.prepareStatement(query);
							pst.setString(1, textFieldID.getText());
							pst.setString(2, textFieldName.getText());
							pst.setString(3, textFieldContact.getText());
							pst.setString(4, textFieldCourse.getText());
		
							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null, "Data Added Succesfully!");	
							refreshEverything();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2);
						}
					}
				}
			}				
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnAdd.setBounds(10, 365, 132, 30);
		frmLasalleCollege.getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldID.getText().isEmpty() || textFieldName.getText().isEmpty() || textFieldContact.getText().isEmpty() || textFieldCourse.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "One or more text field(s) are empty!", "Action Not Possible", JOptionPane.ERROR_MESSAGE);
				} else {	
					int yes = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Delete Data?", "Delete Data", JOptionPane.YES_NO_OPTION);
					if(yes == 0) {				
						try {
							String query = "DELETE FROM StudentInfo WHERE ID='" + textFieldID.getText() + "'";
							PreparedStatement pst = Connection.prepareStatement(query);
							pst.execute();				
							pst.close();
							JOptionPane.showMessageDialog(null, "Data Removed Succesfully!");
							refreshEverything();				
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2);
						}	
					}
				}
			}
		});
		btnDelete.setFocusable(false);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnDelete.setBounds(9, 427, 132, 30);
		frmLasalleCollege.getContentPane().add(btnDelete);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setFocusable(false);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshEverything();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnClear.setBounds(184, 427, 132, 30);
		frmLasalleCollege.getContentPane().add(btnClear);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String query = "SELECT ID, Name, Contact, Course FROM StudentInfo WHERE Name=?";
					PreparedStatement pst = Connection.prepareStatement(query);
					pst.setString(1, textFieldSearch.getText());
					ResultSet rs =  pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		textFieldSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldSearch.setColumns(10);
		textFieldSearch.setBounds(559, 54, 191, 36);
		frmLasalleCollege.getContentPane().add(textFieldSearch);
		
		JLabel lblSearchByName = new JLabel("Search By Name");
		lblSearchByName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 23));
		lblSearchByName.setBounds(345, 54, 204, 36);
		frmLasalleCollege.getContentPane().add(lblSearchByName);
	}
	
	private void refreshEverything() {
		
		try {		
			String query = "SELECT ID, Name, Contact, Course FROM StudentInfo";
			PreparedStatement pst = Connection.prepareStatement(query);
			ResultSet rs =  pst.executeQuery();	
			table.setModel(DbUtils.resultSetToTableModel(rs));	
			
			textFieldID.setText(null);
			textFieldID.setEditable(true);
			
			textFieldName.setText(null);
			
			textFieldContact.setText(null);
			
			textFieldCourse.setText(null);
			
			textFieldSearch.setText(null);
			
			rs.close();
			pst.close();	
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}
}
