package cratejdbctestbed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class App {

    public static void SimpleSelect() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "crate");
        connectionProps.put("tcpKeepAlive", true);

        try {
            System.out.println("Initializing");
            Connection conn = DriverManager.getConnection("jdbc:crate://localhost:5432/", connectionProps);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM iris");
            System.out.println("Query");
            ResultSet results = stmt.executeQuery();
            long size = 0;
            while (results.next()) {
                size++;
            }
            System.out.println("Length: " + size);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void ColumnPrivileges() {
        final String URL = "jdbc:postgresql://localhost:5432/doc?user=crate";

        try (var conn = DriverManager.getConnection(URL)) {
            var stmt = conn.createStatement();
            stmt.execute("DROP USER IF EXISTS wolfgang");
            stmt.execute("CREATE USER wolfgang WITH (password = 's3cr3t')");
            stmt.execute("GRANT ALL PRIVILEGES ON SCHEMA doc TO wolfgang");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try (var conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/doc", "wolfgang", "s3cr3t")) {
            var stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS tbl");
            stmt.execute("CREATE TABLE tbl (x int, y int)");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }  

        try (var conn = DriverManager.getConnection(URL)) {
            var results = conn.getMetaData().getColumnPrivileges("", "doc", "tbl", "");
            System.out.println("Column privileges: " + results.next());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting");
        // SimpleSelect();
        ColumnPrivileges();
        System.out.println("Done");
    }
}
