import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Demo {

	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		try( FileInputStream fis = new FileInputStream( "conf.properties" ) ) {
			props.load(fis);
		}
		
		Class.forName( props.getProperty( "jdbc.driver.class" ) );
		
		String url = props.getProperty("jdbc.url");
		String login = props.getProperty("jdbc.login");
		String password = props.getProperty("jdbc.password");
		
		try (Connection connection = DriverManager.getConnection(url, login, password)) {
//			String strSql = "INSERT INTO T_Users (IdUser, Login, Password, ConnectionNumber) VALUES ( 6, 'Bourne', 'Jason', 8 )";
//			try ( Statement statement = connection.createStatement() ) {
//				statement.executeUpdate(strSql);
//			}
			
			String strSql = "SELECT * FROM T_Users";
			try ( Statement statement = connection.createStatement(); ResultSet resultset = statement.executeQuery(strSql) ) {
				while(resultset.next()) {
					int rsIdUser = resultset.getInt(1);
					String rsLogin = resultset.getString(2);
					String rsPassword = resultset.getString("password");
					int rsConnectionNumber = resultset.getInt("connectionNumber");
					
					System.out.printf("%d : %s %s - %d\n", rsIdUser, rsLogin, rsPassword, rsConnectionNumber);
				}
			}
		}
		
	}

}
