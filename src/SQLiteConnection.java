import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class SQLiteConnection {
	
	public static Connection dbConnector() {
		
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:Studentdb.db");
			JOptionPane.showMessageDialog(null, "Connection Successfully Established ...");
			return connection;
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
}
