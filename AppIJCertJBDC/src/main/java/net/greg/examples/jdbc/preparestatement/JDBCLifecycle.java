package net.greg.examples.jdbc.preparestatement;

import java.sql.*;
import java.util.Properties;


public final class JDBCLifecycle {

  private static void certify(String[] args) {

    if (args.length > 0) {
      OPCODE = (args[0].toUpperCase().startsWith("C")) ? CREATE : DROP;
    }

    String PASSCODE = System.getenv().get(KEY);

    System.err.println(
      " PASSCODE: " + PASSCODE);


    try (
      Connection conn =
        DriverManager.getConnection(
          "jdbc:mysql://127.0.0.1:3306/test",
          "root", PASSCODE);

      PreparedStatement prepStatementDrop =
        conn.prepareStatement(SQL_DROP);

      PreparedStatement prepStatementCreate =
        conn.prepareStatement(SQL_CREATE)) {

      if (conn != null && OPCODE.equals(DROP)) {
        prepStatementDrop.execute();
      }
      else if (conn != null && OPCODE.equals(CREATE)) {
        prepStatementCreate.execute();
      }
      else {
        System.err.println("Failed to connect. Hint:  ZETA.");
      }
    }
    catch (SQLException e) {
      System.err.println("SQL State: " + e.getSQLState() + " - " + e.getMessage());
    }
  }


  public static void main(String[] args) {
    JDBCLifecycle.certify(args);
  }


  private static final String SQL_CREATE =
    new StringBuilder("CREATE TABLE ANY").
      append("(").
      append(" ID INT NOT NULL AUTO_INCREMENT,").
      append(" MONIKER VARCHAR(100) NOT NULL,").
      append(" CREATED_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,").
      append(" PRIMARY KEY (ID)").
      append(")").toString();

  private static final String SQL_DROP =
    "DROP TABLE IF EXISTS ANY";


  private static final String CREATE = "CREATE";
  private static final String DROP = "DROP";

  private static final String KEY = "ZETA";
  private static String OPCODE = CREATE;



  /*  NB  Tentative Placement  */
  /// private static final Properties properties = new Properties();



  /*  NB  Tentative/Incomplete Mappings  */
  /*
  static {

    // SSL Mode
    // TODO   properties.put("sslMode", SSLModeType.VERIFY_CA.name());

    // Setting the Username, Password
    properties.put("user", "root");
    properties.put("password", "marty22!");

    // Setting the truststore
    properties.put("trustCertificateKeyStoreUrl", "file:path_to_truststore_file");
    properties.put("trustCertificateKeyStorePassword", "mypassword");

    // Setting the keystore
    properties.put("clientCertificateKeyStoreUrl", "file:path_to_keystore_file");
    properties.put("clientCertificateKeyStorePassword", "mypassword");
  }
  */
}
