package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {
    private static Connection connexion;
    private String url = "jdbc:mysql://localhost:3306/binomotroon";
    private String login = "root";
    private String password = "root";

    private DataAccess() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = DriverManager.getConnection(this.url, this.login, this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getInstance() {
        if (connexion == null)
            new DataAccess();
        return connexion;
    }
    public static void close() {
        try {
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connexion = null;
    }
}

