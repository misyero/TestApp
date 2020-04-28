package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

	private static Connection getConnection() throws URISyntaxException, SQLException {
		// heroku configに設定されている値を取得。
		URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
		// :をデリミタとして必要な情報を抜き取る。
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		// JDBC用のURLを生成。
		String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
		
		return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public User findUser(User user) {
		User findedUser = null;
		
		try {
			// 
			Class.forName("com.mysql.cj.jdbc.Driver");
			// private
			try(Connection conn = getConnection()) {
				// SELECT
				String sql = "SELECT * FROM account WHERE nickname = ? AND password = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, user.getNickName());
				pStmt.setString(1, user.getPassword());
				// SELECT
				ResultSet rs = pStmt.executeQuery();
				// 
				// findedUser
				if (rs.next()) {
					// 
					String nickName = rs.getString("nickname");
					String password = rs.getString("password");
					findedUser = new User(nickName, password);
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return null;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		return findedUser;
	}
}
