import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String connectionString = "jdbc:mysql://localhost/diablo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Connection connection =
                DriverManager.getConnection(
                        connectionString, "root", "");

        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT user_name " +
                "FROM users");

        ResultSet rs = stmt.executeQuery();

        StringBuilder sb = new StringBuilder();

        while(rs.next()){
        sb.append(rs.getString("user_name")).append(System.lineSeparator());
        }

        System.out.println(sb);
    }
}
